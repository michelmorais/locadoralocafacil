package Banco;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import Locadora.CATEGORIAFILMES;
import Locadora.CLIENTES;
import Locadora.CONTROLERESERVA;
import Locadora.DEVOLUCAO;
import Locadora.FILMES;
import Locadora.GENERO;
import Locadora.LOCACAO;
import Locadora.PEDIDOLOCACAO;
import Locadora.PROMOCAO;
import Locadora.RESERVA;
import Locadora.UTIL;

public class FireBird 
{
	private String DBURL;
	private String USER;
	private String PASSW;
	private Connection Conexao;
	private ResultSet rs;
	private java.sql.Statement statement;
	private String query;
	private static FireBird instance= null;
	public static FireBird getInstance()
	{
		if(instance==null)
			instance = new FireBird();
		instance.connect();
		return instance;
	}
	private FireBird() 
	{
		DBURL ="jdbc:firebirdsql:127.0.0.1:C:\\Locafacil\\BD\\LOCAFACIL.FDB";
		USER = "sysdba";
		PASSW = "masterkey";
	}
	public int getQtdeDisponivel(int id_filmes)
	{
		try
		{
			query = "select z(qtde_disponivel) from filmes where id_filmes = "+id_filmes+";";
			rs = statement.executeQuery(query);
			int qtde_disponivel=-1;
			if(rs.next())
				qtde_disponivel = rs.getInt(1);
			closeConnection();
			return qtde_disponivel;
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public int getId(String Tabela)
	{
		try
		{
			query = "select z(max(id_" + Tabela + ")) + 1 from " + Tabela + " ;";
			rs = statement.executeQuery(query);
			int id=-1;
			if(rs.next())
				id = rs.getInt(1);
			closeConnection();
			return id;
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null,e.toString());
			closeConnection();
			return -1;
		}
		
	}
	public boolean excluir(int id, String tabela)
	{
		query = "delete from "+tabela+" where id_" + tabela + " = " + id + ";";
		return executeQuery();
	}
	
 	public boolean insertOrUpdate(CATEGORIAFILMES cs)
	{
 		if(existe_id(cs.id_categoria, "categoria"))
 			query = "update categoria set nome = upper(ltrim(rtrim('"+cs.nome +"'))), preco = "+cs.preco+" where id_categoria = " + cs.id_categoria +";";
 		else
 			query = "insert into categoria (id_categoria, nome, preco) values " +
				"("+cs.id_categoria+",upper(ltrim(rtrim('"+cs.nome+"'))),"+cs.preco+");";
		
		return executeQuery();
	}
 	public boolean removeReservaFilme(int id_reserva, int posicao)
 	{
 		query = "update reserva set situacao_" + posicao + " ='CONCLUIDO' "+
 		" where id_reserva = " + id_reserva +";";
 		return executeQuery();
 	}
 	public boolean restauraReservaFilme(int id_reserva, int posicao)
 	{
 		query = "update reserva set situacao_" + posicao + " = 'AGUARDANDO', situacao =  'AGUARDANDO' "+
 		"where id_reserva = " + id_reserva +";";
 		return executeQuery();
 	}
 	public boolean insertOrUpdate(RESERVA cs)
	{
 		int id_filmes[] = new int[5];
 		for(int i=0; i<5; i++)
 			id_filmes[i] = -1;
 		for(int i=0; i<cs.filmes.size(); i++)
 			id_filmes[i] = cs.filmes.get(i).id_filmes;
 		cs.data = UTIL.alterMesDiaData(UTIL.getDate());
 		if(existe_id(cs.id_reserva, "reserva"))
 			query = "update reserva set situacao_1 = '"+cs.situacao_individual[0]+"'," +
 					                    "situacao_2 = '"+cs.situacao_individual[1]+"'," +
 							            "situacao_3 = '"+cs.situacao_individual[2]+"'," +
 									    "situacao_4 = '"+cs.situacao_individual[3]+"',"+
 										"situacao_5 = '"+cs.situacao_individual[4]+"'" +
 			" where id_reserva = " + cs.id_reserva +";";
 		else
 			query = "insert into reserva (id_reserva,id_clientes, " +
 			"ID_FILMES_1,ID_FILMES_2,ID_FILMES_3,ID_FILMES_4,ID_FILMES_5," +
 			"situacao_1,situacao_2,situacao_3,situacao_4,situacao_5,data) values " + "(" +
 			cs.id_reserva + "," + cs.cliente.id_clientes+"," + id_filmes[0] + "," + id_filmes[1] + "," +
 			id_filmes[2] + "," +id_filmes[3] + "," +id_filmes[4] + ",'"+cs.situacao_individual[0]+"'," +
 					"'"+cs.situacao_individual[1]+"','"+cs.situacao_individual[2]+"','"+cs.situacao_individual[3]+"'," +
 							"'"+cs.situacao_individual[4]+"','"+cs.data+"');";
		return executeQuery();
	}
 	public boolean insertPedido(PEDIDOLOCACAO pedido)
	{
 		query = "insert into pd_locacao (id_pd_locacao, data, valor_pedido, tipo_pagamento, id_clientes) values " +
				"("+pedido.id_pd_locacao+",'"+pedido.data+"',"+pedido.valor_pedido+", '"+pedido.tipo_pagamento+"', "+pedido.cliente.id_clientes+");";
		
		return executeQuery();
	}
 	public boolean insertOrUpdate(FILMES cs)
	{
 		if(existe_id(cs.id_filmes, "filmes"))
 			query = "update filmes set nome = upper(ltrim(rtrim('"+cs.nome +"'))), qtde = "+cs.qtde+", qtde_disponivel = "+cs.qtde_disponivel+", id_genero = "+cs.id_genero+", id_categoria = "+cs.id_categoria+", sinopse = '"+cs.sinopse+"' where id_filmes = " + cs.id_filmes +";";
 		else
 			query = "insert into filmes (id_filmes, nome,qtde,qtde_disponivel,id_genero,id_categoria,sinopse) values " +
				"("+cs.id_filmes+",upper(ltrim(rtrim('"+cs.nome+"'))),"+cs.qtde+","+ cs.qtde_disponivel + "," + cs.id_genero + "," + cs.id_categoria + ",'" + cs.sinopse + "');";
		return executeQuery();
	}
 	public boolean restauraQtdeDisponivel(FILMES cs)
 	{
 		query ="update filmes set qtde_disponivel =" +cs.qtde_disponivel +
 		" where id_filmes = " + cs.id_filmes + ";";
 		return executeQuery();
 	}
 	public boolean insertOrUpdate(PROMOCAO cs)
	{
 		if(existe_id(cs.id_promocao, "promocao"))
 			query = "update promocao set nome = upper(ltrim(rtrim('"+cs.nome +"'))), preco = "+cs.preco+", qtde_inicial = "+cs.qtde_inicial+", qtde_final = "+cs.qtde_final+", qtde_dias = "+cs.qtde_dias+", id_categoria = "+cs.id_categoria+"  where id_promocao = " + cs.id_promocao +";";
 		else
 			query = "insert into promocao (id_promocao, nome, preco, qtde_inicial, qtde_final, qtde_dias, id_categoria) values " +
				"("+cs.id_promocao+",upper(ltrim(rtrim('"+cs.nome+"'))),"+cs.preco+","+cs.qtde_inicial+", "+cs.qtde_final+", "+cs.qtde_dias+", "+cs.id_categoria+" );";
		
		return executeQuery();
	}
 	public boolean insertItens(FILMES filme,int id_pd_locacao, String total, String data_entrega, String observacao,int id_clientes)
	{
 		query = "insert into itens_pd_locacao (id_itens_pd_locacao, nome, id_filmes, qtde, qtde_disponivel, id_pd_locacao, total, observacao, data_entrega, id_categoria, id_clientes)  values " +
		"("+filme.id_itens_pd_locacao+",upper(ltrim(rtrim('"+filme.nome+"'))),"+filme.id_filmes+","+filme.qtde+", "+filme.qtde_disponivel+", "+id_pd_locacao+", "+total.replace(",", ".")+", '"+observacao+"', '"+data_entrega+"',"+filme.id_categoria+","+id_clientes+" );";
		
		return executeQuery();
	}
 	public boolean insertOrUpdate(CLIENTES cliente)
	{
 		if(existe_id(cliente.id_clientes, "clientes"))
 			query = "update clientes set nome = upper(ltrim(rtrim('"+cliente.nome+"')))," +
 					"endereco = '"+cliente.endereco+"', cep = '"+cliente.cep+"', " +
 					"fone = '"+cliente.fone+"', cpf = trim('"+cliente.cpf+"') " +
 					"where id_clientes = " + cliente.id_clientes +";";
 		else
 			query = "insert into clientes (id_clientes, nome, endereco, fone, cep, cpf) values " +
				"("+cliente.id_clientes+",upper(ltrim(rtrim('"+cliente.nome+"')))," +
				"'"+cliente.endereco+"', '"+cliente.fone+"', '"+cliente.cep+"', " +
				"trim('"+cliente.cpf+"'));";
		
		return executeQuery();
	}
 	public boolean updatePrecosPromocoesFilmes(int id_pd_locacao, int id_categoria,String total, String data_entrega)
 	{
 		query = "update itens_pd_locacao set total ="  + total.replace(",", ".")  + ", data_entrega = '" + UTIL.alterMesDiaData(data_entrega) + "' " +
 				"where id_pd_locacao = " + id_pd_locacao + " and id_categoria = " + id_categoria +";";
 		return executeQuery();
 	}
 	public boolean updatePagamento(int id_devolucao,double valor_a_receber,double valor_recebido,double multa)
	{
 		if(valor_recebido>0)
 		{
 			query = "update devolucao set valor_a_receber = 0 ," +
 			"valor_recebido = " + (valor_recebido) +" , " +
 			"multa = " + multa + " where id_devolucao = " + id_devolucao + ";";
 		}
 		else
 		{
 			query = "update devolucao set valor_a_receber = 0 ," +
 			"valor_recebido = " + (valor_recebido + multa) +" , " +
 			"multa = " + multa + " where id_devolucao = " + id_devolucao + ";";
 		}	
		return executeQuery();
	}
 	public ArrayList<CONTROLERESERVA> getReservaFilme(int id_filmes)//devolve  controle de reservas caso haja reserva
 	{
 		
 		try
 		{
 			ArrayList<CONTROLERESERVA> lsReserva = new ArrayList<CONTROLERESERVA>();
 			for(int i=1; i<6 ; i++)
 			{
	 			query = "select c.NOME, c.ID_CLIENTES, r.ID_RESERVA, f.QTDE_DISPONIVEL " +
	 			"from reserva r " +
	 			"inner join filmes f on r.ID_FILMES_" + i + " = f.ID_FILMES " +
	 			"inner join CLIENTES c on c.ID_CLIENTES = r.ID_CLIENTES " +
	 			"where f.ID_FILMES = " + id_filmes + " and r.SITUACAO_" + i + " = 'AGUARDANDO';";
	 			
	 			rs = statement.executeQuery(query);
				while(rs.next())
				{	
					CONTROLERESERVA res = new CONTROLERESERVA();
					res.nome 			= rs.getString(1);
					res.id_clientes 	= rs.getInt(2);
					res.id_reserva		= rs.getInt(3);
					res.qtde_disponivel = rs.getInt(4);
					res.posicao 		= i;
					lsReserva.add(res);
				}
 			}
 			closeConnection();
 			return lsReserva;
 		}
 		catch (Exception e) 
 		{
			return null;
		}
		
 	}
 	public boolean updateDevolucao(int id_devolucao,int id_filmes,String situacao)
 	{
 		query = "update devolucao set situacao = '" + situacao +
 			"' where id_devolucao = " + id_devolucao +";";
 		if(executeQuery())
 		{
 			connect();
 			if(situacao.compareToIgnoreCase("DEVOLVIDO")==0)
 			{	
 				query = "update filmes set qtde_disponivel = qtde_disponivel + 1 "+
	 			"where id_filmes = " + id_filmes + ";";
 				return executeQuery();
 			}
			return true;
 		}
 		return false;
 	}
 	public boolean insertOrUpdate(GENERO cs)
	{
 		if(existe_id(cs.id_genero, "genero"))
 			query = "update genero set nome = upper(ltrim(rtrim('"+cs.nome+"'))) where id_genero = " + cs.id_genero+";";
 		else
 			query = "insert into genero (id_genero, nome) values " +
				"("+cs.id_genero+",upper(ltrim(rtrim('"+cs.nome+"'))));";
		
		return executeQuery();
	}
 	private boolean executeQuery()
 	{
		try
		{
			statement.execute(query);
			closeConnection();
			return true;
		}
		catch(SQLException e)
		{
			String [] strs = e.getMessage().split("\n");
			if(strs.length>=3)
				JOptionPane.showMessageDialog(null,strs[2].toUpperCase());
			else
				JOptionPane.showMessageDialog(null,e.getMessage().toUpperCase());
		}
		closeConnection();
		return false;
	}
 	private boolean existe_id(int id, String tabela)
	{
		try
		{
			java.sql.CallableStatement cstmt = Conexao.prepareCall("{call EXISTE_ID(?,?)}");
			cstmt.setInt(1, id); 
			cstmt.setString(2,tabela);
			cstmt.registerOutParameter(1,Types.INTEGER);
			cstmt.execute();
			int outParam = cstmt.getInt(1);
			cstmt.close();
			return outParam == 1;
		}
		catch(Exception e)
		{
			closeConnection();
			return false;
		}
	}
 	public PEDIDOLOCACAO selectPedidoFromLocacao(int id_pd_locacao)
 	{
 		try
 		{
	 		query = "select i.id_itens_pd_locacao,"+ 
			       "i.nome,"+
			       "i.qtde,"+
				   "c.nome,"+
				   "i.qtde_disponivel,"+
				   "i.data_entrega,"+
				   "i.observacao,"+
				   "i.total,"+
			       "cli.id_clientes,"+
			       "cli.nome,"+
			       "cli.endereco,"+
			       "cli.fone,"+
			       "cli.cpf,"+
			       "cli.cep, "+
			       "pd.tipo_pagamento,"+
			       "pd.id_pd_locacao " +
			" from pd_locacao pd"+
			" inner join itens_pd_locacao i on i.id_pd_locacao = pd.id_pd_locacao"+
			" inner join filmes f on f.id_filmes = i.id_filmes"+
			" inner join categoria c on c.id_categoria = f.id_categoria"+
			" inner join clientes cli on cli.id_clientes = pd.id_clientes"+
			" where pd.id_pd_locacao = " +id_pd_locacao+
			" order by pd.id_pd_locacao;";
	 		
	 		rs = statement.executeQuery(query.toUpperCase());
	 		PEDIDOLOCACAO pedido 	= new PEDIDOLOCACAO();
	 		pedido.cliente 			= new CLIENTES();
	 		pedido.id_pd_locacao 	= id_pd_locacao;
	 		boolean clienteOk 		= false;
	 		while(rs.next())
			{
				FILMES f 					= new FILMES();
				f.id_itens_pd_locacao		= rs.getInt(1);
				f.nome						= rs.getString(2);
				f.qtde						= rs.getInt(3);
				f.nome_categoria			= rs.getString(4);
				f.qtde_disponivel			= rs.getInt(5);
				f.data_entrega				= rs.getDate(6).toString();
				f.observacao				= rs.getString(7);
				f.preco						= String.valueOf(rs.getDouble(8));
				pedido.filmes.add(f);
				if(!clienteOk)
				{
					pedido.cliente.id_clientes 	= rs.getInt(9);
					pedido.cliente.nome			= rs.getString(10);
					pedido.cliente.endereco		= rs.getString(11);
					pedido.cliente.fone			= rs.getString(12);
					pedido.cliente.cpf			= rs.getString(13);
					pedido.cliente.cep			= rs.getString(14);
					pedido.tipo_pagamento		= ((String)rs.getObject(15)).charAt(0);
					clienteOk = true;
				}
			}
	 		closeConnection();
			return pedido;
 		}
 		catch(Exception exc)
 		{
 			closeConnection();
 			return null;
 		}
 	}
 	
 	public boolean existePedido(int id_pd_locacao)
 	{
 		return existe_id(id_pd_locacao, "pd_locacao");
 	}
 	public boolean existeFilmeDisponivel(int id_filme)
 	{
 		try
		{
 			query = "select qtde_disponivel from filmes where id_filmes = " + id_filme + " ;";
			rs = statement.executeQuery(query);
			int q=0;
			if(rs.next())
				q = rs.getInt(1);
			closeConnection();
			return q>0;
		}
		catch(Exception e)
		{
			closeConnection();
			return false;
		}
 	}
 	public boolean existe_filme(int id_filmes)
	{
		try
		{
			java.sql.CallableStatement cstmt = Conexao.prepareCall("{call EXISTE_ID(?,?)}");
			cstmt.setInt(1, id_filmes); 
			cstmt.setString(2,"filmes");
			cstmt.registerOutParameter(1,Types.INTEGER);
			cstmt.execute();
			int outParam = cstmt.getInt(1);
			cstmt.close();
			closeConnection();
			return outParam == 1;
		}
		catch(Exception e)
		{
			closeConnection();
			return false;
		}
	}
 	public double [] calculaDevolucao(int id_devolucao)
	{
		try
		{
			double valores[] = new double[2];
			java.sql.CallableStatement cstmt = Conexao.prepareCall("{call CALC_DEVOLUCAO(?,?)}");
			cstmt.setInt(1, id_devolucao); 
			cstmt.registerOutParameter(1,Types.DOUBLE);//multa 
			cstmt.registerOutParameter(2,Types.DOUBLE);//valor a receber
			cstmt.execute();
			valores[0] 			= cstmt.getDouble(1);
			valores[1] = cstmt.getDouble(2);
			cstmt.close();
			closeConnection();
			return valores;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
 	public ArrayList<CATEGORIAFILMES> selectBuscaCategoria(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select id_categoria, nome, preco from categoria order by nome;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select id_categoria, nome, preco from categoria where nome like '%"+texto_1.trim()+"%' order by nome;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select id_categoria, nome, preco from categoria where id_categoria like '%"+texto_2.trim()+"%' order by nome;";
			else
				query = "select id_categoria, nome, preco from categoria where nome like '%"+texto_1.trim()+"%' and id_categoria like '%"+texto_2.trim()+"%'  order by nome;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<CATEGORIAFILMES> ls = new ArrayList<CATEGORIAFILMES>();
			while(rs.next())
			{
				CATEGORIAFILMES cf = new CATEGORIAFILMES();
				cf.id_categoria = rs.getInt(1);
				cf.nome = rs.getString(2);
				cf.preco = rs.getDouble(3);
				ls.add(cf);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
 	
 	public ArrayList<RESERVA> selectBuscaReservas(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select r.id_reserva,r.situacao, c.nome ,c.fone,c.cpf,r.data,c.id_clientes " +
						"from reserva  r inner join clientes c on c.id_clientes = r.id_clientes order by r.situacao,r.data;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select r.id_reserva,r.situacao, c.nome ||' - '||f.nome,c.fone,c.cpf,r.data,c.id_clientes " +
				"from reserva  r inner join clientes c on r.id_clientes = c.id_clientes " +
				                "inner join filmes f on f.id_filmes in (r.id_filmes_1,r.id_filmes_2,r.id_filmes_3,r.id_filmes_4,r.id_filmes_5) " +
				"where c.nome like '%"+texto_1.trim()+"%' or f.nome like '%"+texto_1.trim()+"%' order by r.situacao,r.data;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select r.id_reserva,r.situacao, c.nome,c.fone,c.cpf,r.data,c.id_clientes " +
				"from reserva  r inner join clientes c on r.id_clientes = c.id_clientes " +
				"where r.id_reserva like '%"+texto_2.trim()+"%' order by r.situacao,r.data;";
			else
				query = "select distinct r.id_reserva,r.situacao, c.nome,c.fone,c.cpf,r.data,c.id_clientes " +
				"from reserva  r inner join clientes c on r.id_clientes = c.id_clientes " +
				                "inner join filmes f on f.id_filmes in (r.id_filmes_1,r.id_filmes_2,r.id_filmes_3,r.id_filmes_4,r.id_filmes_5) " +
				"where (c.nome like '%"+texto_1.trim()+"%' or f.nome like '%"+texto_1.trim()+"%') and r.id_reserva like '%"+texto_2.trim()+"%'  order by r.situacao,r.data;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<RESERVA> ls = new ArrayList<RESERVA>();
			while(rs.next())
			{
				RESERVA r = new RESERVA();
				r.id_reserva			= rs.getInt(1);
				r.situacao				= rs.getString(2);
				r.cliente.nome			= rs.getString(3);
				r.cliente.fone			= rs.getString(4);
				r.cliente.cpf			= rs.getString(5);
				r.data 					= rs.getString(6);
				r.cliente.id_clientes	= rs.getInt(7);
				ls.add(r);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
 	
 	public ArrayList<FILMES> selectBuscaFilmes(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select f.id_filmes,f.nome, f.qtde,f.qtde_disponivel,f.id_genero,f.id_categoria,f.sinopse,c.nome,g.nome from filmes f inner join categoria c on c.id_categoria = f.id_categoria inner join genero g on g.id_genero = f.id_genero order by f.nome;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select f.id_filmes,f.nome, f.qtde,f.qtde_disponivel,f.id_genero,f.id_categoria,f.sinopse,c.nome,g.nome from filmes f inner join categoria c on c.id_categoria = f.id_categoria inner join genero g on g.id_genero = f.id_genero where f.nome like '%"+texto_1.trim()+"%' order by f.nome;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select f.id_filmes,f.nome, f.qtde,f.qtde_disponivel,f.id_genero,f.id_categoria,f.sinopse,c.nome,g.nome from filmes f inner join categoria c on c.id_categoria = f.id_categoria inner join genero g on g.id_genero = f.id_genero where f.id_filmes like '%"+texto_2.trim()+"%' order by f.nome;";
			else
				query = "select f.id_filmes,f.nome, f.qtde,f.qtde_disponivel,f.id_genero,f.id_categoria,f.sinopse,c.nome,g.nome from filmes f inner join categoria c on c.id_categoria = f.id_categoria inner join genero g on g.id_genero = f.id_genero where f.nome like '%"+texto_1.trim()+"%' and f.id_filmes like '%"+texto_2.trim()+"%'  order by f.nome;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<FILMES> ls = new ArrayList<FILMES>();
			while(rs.next())
			{
				FILMES filme = new FILMES();
				filme.id_filmes			= rs.getInt(1);
				filme.nome 				= rs.getString(2);
				filme.qtde 				= rs.getInt(3);
				filme.qtde_disponivel 	= rs.getInt(4);
				filme.id_genero			= rs.getInt(5);
				filme.id_categoria		= rs.getInt(6);
				filme.sinopse 			= rs.getString(7);
				filme.nome_categoria	= rs.getString(8);
				filme.nome_genero		= rs.getString(9);
				ls.add(filme);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
 	public String selectBuscaDataLocacao(int id_pd_locacao,int id_clientes)
 	{
 		try
 		{
 			query = "select data from pd_locacao where id_pd_locacao = " + 
 			id_pd_locacao + " and id_clientes = " + id_clientes +";";
 			rs = statement.executeQuery(query.toUpperCase());
 			rs.next();
			String data = rs.getString(1);
			closeConnection();
			return UTIL.alterMesDiaData(data);
 		}
 		catch(Exception e)
 		{
 			closeConnection();
			return null;
 		}
 	}
 	public ArrayList<DEVOLUCAO> selectBuscaDevolucao(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select * from devolucao order by situacao desc, data_entrega "; 
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select * from devolucao where nome_clientes like'%" + texto_1 + "%' order by situacao desc, data_entrega;"; 
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select * from devolucao where id_devolucao like'%" + texto_2 + "%' order by situacao desc, data_entrega;";
			else
				query = "select * from devolucao where id_devolucao like'%" + texto_2 + "%' and nome_clientes like '%" +texto_1+ "%' order by situacao desc, data_entrega;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<DEVOLUCAO> ls = new ArrayList<DEVOLUCAO>();
			while(rs.next())
			{
				DEVOLUCAO dev 				= new DEVOLUCAO();
				dev.filme.id_devolucao		= rs.getInt("id_devolucao");
				dev.cliente.id_clientes		= rs.getInt("id_clientes");
				dev.cliente.nome			= rs.getString("nome_clientes");
				dev.qtde_locado				= rs.getInt("qtde_locado");
				dev.filme.id_filmes			= rs.getInt("id_filmes");
				
				dev.filme.nome				= rs.getString("nome_filmes");
				dev.data_entrega			= UTIL.alterMesDiaData(rs.getDate("data_entrega").toString());
				//dev.data_locacao			= rs.getDate("data_locacao").toString();
				dev.valor_unitario			= rs.getDouble("valor_unitario");
				dev.valor_total				= rs.getDouble("valor_total");
				dev.multa					= rs.getDouble("multa");
				dev.valor_recebido			= rs.getDouble("valor_recebido");
				dev.id_pd_locacao			= rs.getInt("id_pd_locacao");
				dev.valor_a_receber			= rs.getDouble("valor_a_receber");
				dev.filme.situacao			= rs.getString("situacao");
				ls.add(dev);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
 	public ArrayList<LOCACAO> selectBuscaLocacao(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select p.id_pd_locacao, c.nome, FloatToStr(p.valor_pedido, '%6g'), p.data, c.fone, c.cpf from pd_locacao p inner join clientes c on c.id_clientes = p.id_clientes order by p.id_pd_locacao desc;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select p.id_pd_locacao, c.nome, FloatToStr(p.valor_pedido, '%6g'), p.data, c.fone, c.cpf from pd_locacao p inner join clientes c on c.id_clientes = p.id_clientes where c.nome like '%"+texto_1.trim()+"%' order by p.id_pd_locacao desc;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select p.id_pd_locacao, c.nome, FloatToStr(p.valor_pedido, '%6g'), p.data, c.fone, c.cpf from pd_locacao p inner join clientes c on c.id_clientes = p.id_clientes where p.id_pd_locacao like '%"+texto_2.trim()+"%' order by p.id_pd_locacao desc;";
			else
				query = "select p.id_pd_locacao, c.nome, FloatToStr(p.valor_pedido, '%6g'), p.data, c.fone, c.cpf from pd_locacao p inner join clientes c on c.id_clientes = p.id_clientes where p.id_pd_locacao like '%"+texto_2.trim()+"%' and c.nome like '%"+texto_1.trim()+"%' order by p.id_pd_locacao desc;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<LOCACAO> ls = new ArrayList<LOCACAO>();
			while(rs.next())
			{
				LOCACAO locacao 		= new LOCACAO();
				locacao.id_pd_locacao	= rs.getInt(1);
				locacao.nome			= rs.getString(2);
				locacao.valor			= rs.getString(3);
				locacao.data			= rs.getString(4);
				locacao.fone			= rs.getString(5);
				locacao.cpf				= rs.getString(6);
				ls.add(locacao);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public ArrayList<PROMOCAO> selectBuscaPromocao(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select p.id_promocao, p.nome, p.preco, p.qtde_inicial, p.qtde_final, p.qtde_dias, p.id_categoria, c.nome, 'de '||p.qtde_inicial||' até '||p.qtde_final from promocao p inner join categoria c on c.id_categoria = p.id_categoria order by p.nome;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select p.id_promocao, p.nome, p.preco, p.qtde_inicial, p.qtde_final, p.qtde_dias, p.id_categoria, c.nome, 'de '||p.qtde_inicial||' até '||p.qtde_final from promocao p inner join categoria c on c.id_categoria = p.id_categoria  where p.nome like '%"+texto_1.trim()+"%' order by p.nome;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select p.id_promocao, p.nome, p.preco, p.qtde_inicial, p.qtde_final, p.qtde_dias, p.id_categoria, c.nome, 'de '||p.qtde_inicial||' até '||p.qtde_final from promocao p inner join categoria c on c.id_categoria = p.id_categoria  where p.id_promocao like '%"+texto_2.trim()+"%' order by p.nome;";
			else
				query = "select p.id_promocao, p.nome, p.preco, p.qtde_inicial, p.qtde_final, p.qtde_dias, p.id_categoria, c.nome, 'de '||p.qtde_inicial||' até '||p.qtde_final from promocao p inner join categoria c on c.id_categoria = p.id_categoria  where p.nome like '%"+texto_1.trim()+"%' and p.id_promocao like '%"+texto_2.trim()+"%'  order by p.nome;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<PROMOCAO> ls = new ArrayList<PROMOCAO>();
			while(rs.next())
			{
				PROMOCAO pr 		= new PROMOCAO();
				pr.id_promocao 		= rs.getInt(1);
				pr.nome 			= rs.getString(2);
				pr.preco 			= rs.getDouble(3);
				pr.qtde_inicial		= rs.getInt(4);
				pr.qtde_final		= rs.getInt(5);
				pr.qtde_dias		= rs.getInt(6);
				pr.id_categoria		= rs.getInt(7);
				pr.nome_categoria	= rs.getString(8);
				pr.qtde_filmes		= rs.getString(9);
				ls.add(pr);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public ArrayList<GENERO> selectBuscaGenero(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select id_genero, nome from genero order by nome;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select id_genero, nome from genero where nome like '%"+texto_1.trim()+"%' order by nome;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select id_genero, nome from genero where id_genero like '%"+texto_2.trim()+"%' order by nome;";
			else
				query = "select id_genero, nome from genero where nome like '%"+texto_1.trim()+"%' and id_genero like '%"+texto_2.trim()+"%'  order by nome;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<GENERO> ls = new ArrayList<GENERO>();
			while(rs.next())
			{
				GENERO gen = new GENERO();
				gen.id_genero = rs.getInt(1);
				gen.nome = rs.getString(2);
				ls.add(gen);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public CLIENTES selectBuscaCliente(int id_cliente)
	{
		try
		{
			query = "select * from clientes where id_clientes = "+id_cliente+";";
			rs = statement.executeQuery(query.toUpperCase());
			CLIENTES gen 	= new CLIENTES();
			rs.next();
			gen.id_clientes = rs.getInt(1);
			gen.nome 		= rs.getString(2);
			gen.endereco 	= rs.getString(3);
			gen.fone    	= rs.getString(4);
			gen.cpf			= rs.getString(5);
			gen.cep			= rs.getString(6);
			closeConnection();
			return gen;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public CLIENTES selectBuscaCliente(String nome)
	{
		try
		{
			query = "select * from clientes where nome = '"+nome+"';";
			rs = statement.executeQuery(query.toUpperCase());
			CLIENTES gen 	= new CLIENTES();
			rs.next();
			gen.id_clientes = rs.getInt(1);
			gen.nome 		= rs.getString(2);
			gen.endereco 	= rs.getString(3);
			gen.fone    	= rs.getString(4);
			gen.cpf			= rs.getString(5);
			gen.cep			= rs.getString(6);
			closeConnection();
			return gen;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	
	public FILMES pedidoDeFilme(int id_filmes,int id_clientes)
	{
		try
		{	java.sql.CallableStatement cstmt = Conexao.prepareCall("{call CHAMA_FILME(?,?,?,?,?,?,?)}");
			cstmt.setInt(1, id_filmes); //id_filmes integer, id_clientes integer
			cstmt.setInt(2,id_clientes);
			/*
			 id_itens_pd_locacao integer,
	        nome varchar(250),
	        qtde integer,
	        qtde_disponivel integer,
	        id_categoria integer,
	        nome_categoria varchar(100),
	        observacao varchar(150)*/
			cstmt.registerOutParameter(1,Types.INTEGER);
			cstmt.registerOutParameter(2,Types.VARCHAR);
			cstmt.registerOutParameter(3,Types.INTEGER);
			cstmt.registerOutParameter(4,Types.INTEGER);
			cstmt.registerOutParameter(5,Types.INTEGER);
			cstmt.registerOutParameter(6,Types.VARCHAR);
			cstmt.registerOutParameter(7,Types.VARCHAR);
			cstmt.execute();
			FILMES filme = new FILMES();
			filme.id_filmes				= id_filmes;
			filme.id_itens_pd_locacao	= cstmt.getInt(1);
			filme.nome 					= cstmt.getString(2);
			filme.qtde 					= cstmt.getInt(3);
			filme.qtde_disponivel 		= cstmt.getInt(4);
			filme.id_categoria			= cstmt.getInt(5);
			filme.nome_categoria		= cstmt.getString(6);
			filme.observacao			= cstmt.getString(7);
			
			cstmt.close();
			closeConnection();
			return filme;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public class PRECOPROMOCIONAL
	{
		public double 	preco;
		public String 	data;
		public int		id_categoria;
	}
	public PRECOPROMOCIONAL getPreco(int qtde,int id_categoria)
	{
		try
		{	java.sql.CallableStatement cstmt = Conexao.prepareCall("{call calc_itens_pd_locacao(?,?)}");
			cstmt.setInt(1, qtde); //id_filmes integer, id_clientes integer
			cstmt.setInt(2,id_categoria);
			/*
			 	preco double precision,
        		data_entrega date
			 */
			cstmt.registerOutParameter(1,Types.DOUBLE);
			cstmt.registerOutParameter(2,Types.VARCHAR);
			cstmt.execute();
			PRECOPROMOCIONAL price = new PRECOPROMOCIONAL();
			price.preco			= cstmt.getDouble(1);
			price.data			= cstmt.getString(2);
			price.id_categoria	= id_categoria;
			cstmt.close();
			closeConnection();
			return price;
		}
		catch(SQLException e)
		{
			String [] strs = e.getMessage().split("\n");
			if(strs.length>=3)
				JOptionPane.showMessageDialog(null,strs[2].toUpperCase());
			else
				JOptionPane.showMessageDialog(null,e.getMessage().toUpperCase());
			closeConnection();
			return null;
		}
	}
	public String[] selectReserva(int id_reserva)
	{
		try
		{
			query = "select id_filmes_1,id_filmes_2,id_filmes_3,id_filmes_4,id_filmes_5,situacao, " +
					"situacao_1,situacao_2,situacao_3,situacao_4,situacao_5 " +
					"from reserva where id_reserva = "+id_reserva+";";
			rs = statement.executeQuery(query.toUpperCase());
			String id_filmes[] = new String[11];
			rs.next();
			id_filmes[0] 		= String.valueOf(rs.getInt(1));
			id_filmes[1] 		= String.valueOf(rs.getInt(2));
			id_filmes[2] 		= String.valueOf(rs.getInt(3));
			id_filmes[3] 		= String.valueOf(rs.getInt(4));
			id_filmes[4] 		= String.valueOf(rs.getInt(5));
			id_filmes[5] 		= rs.getString(6);
			id_filmes[6] 		= rs.getString(7);
			id_filmes[7] 		= rs.getString(8);
			id_filmes[8] 		= rs.getString(9);
			id_filmes[9] 		= rs.getString(10);
			id_filmes[10] 		= rs.getString(11);
			closeConnection();
			return id_filmes;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public FILMES selectBuscaFilme(int id_filme)
	{
		try
		{
			query = "select * from filmes where id_filmes = "+id_filme+";";
			rs = statement.executeQuery(query.toUpperCase());
			FILMES gen 			= new FILMES();
			rs.next();
			gen.id_filmes 		= rs.getInt(1);
			gen.nome 			= rs.getString(2);
			gen.qtde	 		= rs.getInt(3);
			gen.qtde_disponivel = rs.getInt(4);
			gen.id_genero		= rs.getInt(5);
			gen.id_categoria	= rs.getInt(6);
			gen.sinopse			= rs.getString(7);
			closeConnection();
			return gen;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	public int selectBuscaIdFilme(String Nome)
	{
		try
		{
			query = "select id_filmes from filmes where nome = '"+Nome+"';";
			rs = statement.executeQuery(query.toUpperCase());
			int id_filmes=0;
			if(rs.next())
				id_filmes 		= rs.getInt(1);
			closeConnection();
			return id_filmes;
		}
		catch(Exception e)
		{
			closeConnection();
			return 0;
		}
	}
	public ArrayList<CLIENTES> selectBuscaClientes(String texto_1, String texto_2)
	{
		try
		{
			if(texto_1.isEmpty() && texto_2.isEmpty())
				query = "select * from clientes order by nome;";
			else if(!texto_1.isEmpty() && texto_2.isEmpty())
				query = "select * from clientes where nome like '%"+texto_1.trim()+"%' order by nome;";
			else if(!texto_2.isEmpty() && texto_1.isEmpty())
				query = "select * from clientes where id_clientes like '%"+texto_2.trim()+"%' order by nome;";
			else
				query = "select * from clientes where nome like '%"+texto_1.trim()+"%' and id_clientes like '%"+texto_2.trim()+"%'  order by nome;";
			rs = statement.executeQuery(query.toUpperCase());
			ArrayList<CLIENTES> ls = new ArrayList<CLIENTES>();
			while(rs.next())
			{
				CLIENTES gen 	= new CLIENTES();
				gen.id_clientes = rs.getInt(1);
				gen.nome 		= rs.getString(2);
				gen.endereco 	= rs.getString(3);
				gen.fone    	= rs.getString(4);
				gen.cpf			= rs.getString(5);
				gen.cep			= rs.getString(6);
				ls.add(gen);
			}
			closeConnection();
			return ls;
		}
		catch(Exception e)
		{
			closeConnection();
			return null;
		}
	}
	private void closeConnection() 
	{
		try
		{	statement.close();}
		catch (Exception e){};
		try
		{	rs.close();	}
		catch(Exception e){};
		try
		{	Conexao.close();}
		catch (Exception e){};
	}
	private boolean tryConnect()
	{
		try 
		{
			if(statement!=null && !statement.isClosed())
				statement.close();
			if(Conexao!= null && !Conexao.isClosed())
				Conexao.close();
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			Conexao = DriverManager.getConnection(DBURL, USER, PASSW);
			Conexao.setAutoCommit(true);
			
			statement = Conexao.createStatement();
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	public boolean testConnection()
	{
		boolean b = connect();
		closeConnection();
		return b;
	}
	private boolean connect()
	{
		try 
		{
			if(!tryConnect())
				throw (new Exception());
			return true;
		} 
		catch (Exception e) 
		{
			try
			{
				String currentDir = new File("").getAbsolutePath();
				DBURL ="jdbc:firebirdsql:127.0.0.1:" + currentDir + "\\LOCAFACIL.FDB";
				if(!tryConnect())
					throw (new Exception());
				return true;
			}
			catch (Exception ex) 
			{ 
				try 
				{ 
					FileReader fr = new FileReader("locafacil.txt"); 
					BufferedReader br = new BufferedReader(fr);
					String caminhoCompleto = br.readLine(); 
					DBURL ="jdbc:firebirdsql:127.0.0.1:" + caminhoCompleto; 
					if(!tryConnect())
						throw (new Exception());
					return true;
				}
				catch (Exception exc) 
				{
					return false;
				}
			}
		}
	}
	/*
	Contador que funciona!!!!!!!!!! 
	statement = Conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	ResultSet rs = statement.executeQuery(query);
	rs.last(); 
	int rowcount = rs.getRow(); 
	rs.first();
	int count=0;*/
}
