package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import Banco.FireBird;
import Banco.FireBird.PRECOPROMOCIONAL;
import Locadora.CATEGORIAFILMES;
import Locadora.CLIENTES;
import Locadora.CONTACATEGORIA;
import Locadora.FILMES;
import Locadora.PEDIDOLOCACAO;
import Locadora.UTIL;

public class frmPedidoLocacao extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel lblNumero;
	private JLabel lblidPdLocacao;
	private JLabel lblDataCadastro;
	private JLabel lblData;
	private JLabel lblValor;
	private JLabel lblValorPedido;
	private JLabel lblCliente;
	private JLabel lblIdCliente;
	private JTextField txtIdFilme;
	private JButton btnPesquisarFilme;
	private JButton btnAcrescentarFilme;
	private JButton btnExcluirFilme;
	private JButton btnPesquisarCliente;
	private JLabel lblNomeFixo;
	private JLabel lblNome;
	private JLabel lblEnderecoFixo;
	private JLabel lblEndereco;
	private JLabel lblCpfFixo;
	private JLabel lblCpf;
	private JLabel lblCEPFixo;
	private JLabel lblCep;
	private JLabel lblFilmes;
	private JLabel lblTipoPagamento;
	private JRadioButton chkAvista;
	private JRadioButton chkAprazo;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	
	private JTable table;
	private DefaultTableModel model;
	private JFrame frame;
	private PEDIDOLOCACAO pedido;
	private boolean habilitaProcuraCliente;
	
	
	private static frmPedidoLocacao instance = null;
	public static void inibeBotaoConfirmar()
	{
		if(instance==null)
			return;
		instance.btnConfirmar.setEnabled(false);
	}
	public static frmPedidoLocacao getInstance()
	{
		if(instance==null)
			instance = new frmPedidoLocacao();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmPedidoLocacao getInstance(int id_pd_locacao)
	{
		if(id_pd_locacao<=0)
			return null;
		PEDIDOLOCACAO pd = FireBird.getInstance().selectPedidoFromLocacao(id_pd_locacao);
		if(pd==null)
		{
			JOptionPane.showMessageDialog(null,"Pedido de loca��o n�o encontrado!");
			return null;
		}
		if(instance==null)
			instance = new frmPedidoLocacao();
		instance.frame.requestFocus();
		instance.btnConfirmar.setEnabled(false);
		instance.btnAcrescentarFilme.setEnabled(false);
		instance.btnExcluirFilme.setEnabled(false);
		instance.btnPesquisarCliente.setEnabled(false);
		instance.btnPesquisarFilme.setEnabled(false);
		
		instance.lblIdCliente.setText(String.valueOf(pd.cliente.id_clientes));
		instance.lblidPdLocacao.setText(String.valueOf(id_pd_locacao));
		instance.lblNome.setText(pd.cliente.nome);
		instance.lblCpf.setText(pd.cliente.cpf);
		instance.lblCep.setText(pd.cliente.cep);
		instance.lblEndereco.setText(pd.cliente.endereco);
		instance.chkAprazo.setEnabled(false);
		instance.chkAvista.setEnabled(false);
		instance.pedido = pd;
		int size = pd.filmes.size();
		double valor=0;
		for (int i =0; i < size ; i ++)
		{
			FILMES f = pd.filmes.get(i);
			f.id_filmes = FireBird.getInstance().selectBuscaIdFilme(f.nome);
			instance.model.setValueAt(f.id_filmes, i, 0);
			instance.model.setValueAt(f.nome, i, 1);
			instance.model.setValueAt(f.qtde, i, 2);
			instance.model.setValueAt(f.nome_categoria, i, 3);
			instance.model.setValueAt(f.qtde_disponivel, i, 4);
			instance.model.setValueAt(f.data_entrega, i, 5);
			instance.model.setValueAt(f.observacao, i, 6);
			instance.model.setValueAt(f.preco.replace(".",","), i, 7);
			valor+=Double.parseDouble(f.preco.replace(",","."));
		}
		if(pd.tipo_pagamento == 'P')
			instance.chkAprazo.setSelected(true);
		instance.lblValorPedido.setText(String.valueOf(valor).replace(".",","));
		instance.btnCancelar.setText("Sair");
		return instance;
	}
	public static frmPedidoLocacao retornaClienteSelecionado(CLIENTES cliente)
	{
		if(instance==null)
			instance = new frmPedidoLocacao();
		instance.frame.requestFocus();
		instance.habilitaProcuraCliente = false;
		instance.lblIdCliente.setText(String.valueOf(cliente.id_clientes));
		instance.lblNome.setText(cliente.nome);
		instance.lblCpf.setText(cliente.cpf);
		instance.lblCep.setText(cliente.cep);
		instance.lblEndereco.setText(cliente.endereco);
		instance.pedido.cliente = cliente;
		instance.habilitaProcuraCliente = true;
		return instance;
	}
	public static frmPedidoLocacao retornaFilmeSelecionado(FILMES filme)
	{
		if(instance==null)
			instance = new frmPedidoLocacao();
		instance.frame.requestFocus();
		instance.habilitaProcuraCliente = false;
		instance.txtIdFilme.setText(String.valueOf(filme.id_filmes));
		instance.habilitaProcuraCliente = true;
		return instance;
	}
	
	public void addFilme(FILMES filme)
	{
		if(filme==null)
			return;
		pedido.filmes.add(filme);
		int sizeFilmes = pedido.filmes.size();
		int sizeCat = pedido.ListaCategorias.size();
		if(sizeCat==0)
			pedido.ListaCategorias.add(new CONTACATEGORIA(filme.id_categoria));
		else
		{
			boolean encontrouCategoria = false;
			for(int i=0; i< sizeCat;i++)
			{
				CONTACATEGORIA t = pedido.ListaCategorias.get(i);
				if(t.id_categoria == filme.id_categoria)
				{
					t.total+=1;
					encontrouCategoria =true;
					break;
				}
			}
			if(!encontrouCategoria)
				pedido.ListaCategorias.add(new CONTACATEGORIA(filme.id_categoria));
		}
		int rows = table.getRowCount(),totalCol = 8;
		for(int count = 0; count < rows; count++)
		{
			for(int i=0; i < totalCol;i++)
				table.getModel().setValueAt("", count, i);
		}
		for(int count=0; count < sizeFilmes; count++)
		{
			FILMES filmeAtual = pedido.filmes.get(count);
			model.setValueAt(filmeAtual.id_filmes, count, 0);
			model.setValueAt(filmeAtual.nome, count, 1);
			model.setValueAt(filmeAtual.qtde, count, 2);
			model.setValueAt(filmeAtual.nome_categoria, count, 3);
			model.setValueAt(filmeAtual.qtde_disponivel, count, 4);
			model.setValueAt("", count, 5);
			model.setValueAt(filmeAtual.observacao, count, 6);
			model.setValueAt("", count, 7);
		}
	}
	public void removeFilme(FILMES filme)
	{
		int size = pedido.filmes.size();
		for(int i= 0; i <size; i++)
		{
			FILMES f = pedido.filmes.get(i);
			if(f.id_itens_pd_locacao == filme.id_itens_pd_locacao)
			{
				pedido.filmes.remove(i);
				int sizeLsCat = pedido.ListaCategorias.size();
				for(int j= 0; j < sizeLsCat; j++)
				{
					CONTACATEGORIA cc = pedido.ListaCategorias.get(j);
					if(cc.id_categoria == f.id_categoria)
					{
						if(cc.total == 1)
							pedido.ListaCategorias.remove(j);
						else
							cc.total-=1;
						break;
					}
				}
				break;
			}
		}
	}

	private frmPedidoLocacao() 
	{
		habilitaProcuraCliente = true;
		pedido = new PEDIDOLOCACAO();
		lblNumero = new JLabel("N�mero da loca��o");
		lblidPdLocacao = new JLabel();
		pedido.data = UTIL.alterMesDiaData(getDate());
		pedido.id_pd_locacao = FireBird.getInstance().getId("pd_locacao");
		lblidPdLocacao.setText(String.valueOf(pedido.id_pd_locacao));
		lblDataCadastro = new JLabel(getDate());
		lblData = new JLabel("Data");
		lblValor = new JLabel("Valor R$");
		lblValorPedido = new JLabel("00,00");
		lblCliente = new JLabel("C�d Cliente");
		lblIdCliente = new JLabel();
		btnPesquisarCliente = new JButton("...");
		lblNomeFixo = new JLabel("Nome");
		lblNome = new JLabel();
		lblEnderecoFixo = new JLabel("Endere�o");
		lblEndereco = new JLabel();
		lblCpfFixo = new JLabel("CPF");
		lblCpf = new JLabel();
		lblCEPFixo = new JLabel("CEP");
		lblCep = new JLabel();
		lblFilmes = new JLabel("Filme:");
		txtIdFilme = new JTextField();
		btnPesquisarFilme = new JButton("...");
		btnAcrescentarFilme = new JButton("+");
		btnExcluirFilme = new JButton("-");
		btnExcluirFilme.setForeground(new Color(0,255,0));
		lblTipoPagamento = new JLabel("Tipo de pagamento");
		chkAvista = new JRadioButton("A vista",true);
		chkAprazo = new JRadioButton("A prazo");
		btnConfirmar = new JButton("Confirmar");
		btnCancelar = new JButton("Cancelar");
		ButtonGroup group = new ButtonGroup ();
		
		model = new DefaultTableModel(19,8);
	    table = new JTable(model);
	    JTextField editor = new JTextField();
	    editor.setEditable(false);
	    table.setDefaultEditor(Object.class, new DefaultCellEditor(editor));
		table.setPreferredScrollableViewportSize(new Dimension(700, 275));
		table.getColumnModel().getColumn(0).setHeaderValue("C�d");
		table.getColumnModel().getColumn(1).setHeaderValue("Nome");
		table.getColumnModel().getColumn(2).setHeaderValue("Qtde");
		table.getColumnModel().getColumn(3).setHeaderValue("Categoria");
		table.getColumnModel().getColumn(4).setHeaderValue("Dsp");
		table.getColumnModel().getColumn(5).setHeaderValue("Entrega");
		table.getColumnModel().getColumn(6).setHeaderValue("Observa��o");
		table.getColumnModel().getColumn(7).setHeaderValue("Total");
		table.setPreferredScrollableViewportSize(new Dimension(490, 50));
		table.getColumnModel().getColumn(0).setMaxWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(150);
		table.getColumnModel().getColumn(3).setPreferredWidth(150);
		table.getColumnModel().getColumn(4).setMaxWidth(50);
		table.getColumnModel().getColumn(5).setMaxWidth(90);
		table.getColumnModel().getColumn(6).setPreferredWidth(70);
		table.getColumnModel().getColumn(7).setMaxWidth(50);
		
		table.setFillsViewportHeight(true);
        table.setSelectionMode(WHEN_FOCUSED);
        JScrollPane jp = new JScrollPane(table);  
        jp.setViewportView(table);  
        jp.setAutoscrolls(true);  
        jp.setEnabled(true);
        jp.setWheelScrollingEnabled(true);
        table.setAutoscrolls(true);
        table.getTableHeader().setBounds(15, 160, 850,15);
        
        add(jp);
		add(table);
		add(table.getTableHeader());
		table.setBounds(15, 175, 850, 305);
		table.setOpaque(true);
		
		btnConfirmar.addActionListener(this);
		btnCancelar.addActionListener(this);
		btnPesquisarCliente.addActionListener(this);

		chkAvista.addActionListener(this);
		chkAprazo.addActionListener(this);
		btnAcrescentarFilme.addActionListener(this);
		btnExcluirFilme.addActionListener(this);
		btnPesquisarFilme.addActionListener(this);
		group.add(chkAprazo);
		group.add(chkAvista);
		
		setPreferredSize(new Dimension(900, 600));
		setLayout(null);

		add(lblNumero);
		add(lblidPdLocacao);
		add(lblDataCadastro);
		add(lblData);
		add(lblValor);
		add(lblValorPedido);
		add(lblCliente);
		add(lblIdCliente);
		add(btnPesquisarCliente);
		add(lblNomeFixo);
		add(lblNome);
		add(lblEnderecoFixo);
		add(lblEndereco);
		add(lblCpfFixo);
		add(lblCpf);
		add(lblCEPFixo);
		add(lblCep);
		add(lblFilmes);
		add(txtIdFilme);
		add(btnPesquisarFilme);
		add(btnAcrescentarFilme);
		add(btnExcluirFilme);
		add(lblTipoPagamento);
		add(chkAvista);
		add(chkAprazo);
		add(btnConfirmar);
		add(btnCancelar);

		lblNumero.setBounds(15, 15, 115, 25);
		lblidPdLocacao.setBounds(130, 15, 70, 25);
		lblDataCadastro.setBounds(275, 15, 100, 25);
		lblData.setBounds(220, 15, 35, 25);
		lblValor.setBounds(390, 15, 50, 25);
		lblValorPedido.setBounds(450, 15, 50, 25);
		lblCliente.setBounds(15, 45, 65, 25);
		lblIdCliente.setBounds(85, 45, 60, 25);
		btnPesquisarCliente.setBounds(150, 45, 25, 25);
		lblNomeFixo.setBounds(15, 80, 65, 25);
		lblNome.setBounds(85, 80, 280, 25);
		lblEnderecoFixo.setBounds(15, 105, 65, 25);
		lblEndereco.setBounds(85, 105, 280, 25);
		lblCpfFixo.setBounds(365, 80, 25, 25);
		lblCpf.setBounds(405, 80, 120, 25);
		lblCEPFixo.setBounds(365, 105, 35, 25);
		lblCep.setBounds(405, 105, 80, 25);
		
		lblFilmes.setBounds(15, 135, 70, 25);
		txtIdFilme.setBounds(90, 135, 70, 25);
		btnPesquisarFilme.setBounds(170, 135, 25, 25);
		btnAcrescentarFilme.setBounds(200, 135, 45, 25);
		btnExcluirFilme.setBounds(250, 135, 45, 25);
		btnExcluirFilme.setForeground(new Color(255,0,0));
		btnAcrescentarFilme.setForeground(new Color(255,0,0));
		
		lblTipoPagamento.setBounds(20, 475, 140, 25);
		chkAvista.setBounds(20, 500, 100, 25);
		chkAprazo.setBounds(20, 525, 100, 25);
		btnCancelar.setBounds(390, 500, 105, 55);
		btnConfirmar.setBounds(205, 500, 105, 55);
		frmShow();
	}
	private String getDate()
	{
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		return day + "/" + month + "/"+year;
	}
	private void frmShow() 
	{
		frame = new JFrame("PEDIDO DE LOCA��O");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				if (!FireBird.getInstance().existePedido(instance.pedido.id_pd_locacao)  && instance.pedido.filmes.size()>0)
				{
					FireBird.getInstance().insertPedido(instance.pedido);
					if (!instance.ConfirmaPedidoAoFechar())
						FireBird.getInstance().excluir(instance.pedido.id_pd_locacao, "pd_locacao");
					
				}
				frmPedidoLocacao.instance=null;
			}  
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==btnConfirmar)
			onPressed_btnConfirmar();
		else if(e.getSource()==btnPesquisarCliente)
			onPressed_btnPesquisarCliente();
		else if(e.getSource()==chkAprazo)
			onPressed_chkAprazo();
		else if(e.getSource()==chkAvista)
			onPressed_chkAvista();
		else if(e.getSource() == btnPesquisarFilme)
			onPressed_PesquisaFilmes();
		else if(e.getSource() == btnAcrescentarFilme)
			onPressed_AcrescentaFilmes();
		else if(e.getSource() == btnExcluirFilme)
			onPressed_ExcluirFilmes();
		else if(e.getSource()==btnCancelar)
		{   
			frame.dispose();
		}
	}
	private void onPressed_btnConfirmar()
	{
		if(lblNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Cliente n�o selecionado.");
			return;
		}
		if(pedido.filmes.size()==0)
		{
			JOptionPane.showMessageDialog(null, "N�o h� filmes para finalizar o pedido.");
			return;
		}
		if (ConfirmaPedido())
		{
			if(FireBird.getInstance().insertPedido(pedido))
			{
				this.frame.dispose();
			}
		}
	}
	private boolean ConfirmaPedido()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Deseja realmente confirmar esse pedido?",  
		"LOCAF�CIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
	private boolean ConfirmaPedidoAoFechar()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Esse pedido foi alterado, deseja salva-lo?",  
		"LOCAF�CIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
	private void onPressed_btnPesquisarCliente()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_CLIENTES_PARA_LOCACAO);
	}
	private void onPressed_chkAprazo()
	{
		pedido.tipo_pagamento = 'P'; 
	}
	private void onPressed_chkAvista()
	{
		pedido.tipo_pagamento = 'V';
	}
	private void onPressed_PesquisaFilmes()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_FILMES_PARA_LOCACAO);
	}
	private void showValues()
	{
		int size = pedido.filmes.size();
		double valor=0;
		for(int x=0;x<size;x++)
		{
			String str = (String)model.getValueAt(x,7);
			str = str.replace(",", ".");
			valor+=  Double.parseDouble(str);
		}
		lblValorPedido.setText(String.valueOf(valor));
		pedido.valor_pedido = valor;
		size = pedido.ListaCategorias.size();
		for(int i=0;i <size;i++)
		{
			int id_categoria  	= pedido.ListaCategorias.get(i).id_categoria;
			int sizeFilmes = pedido.filmes.size();
			String total="";
			String  data ="";
			for(int j=0; j<sizeFilmes; j++)//acha o pre�o e data de entrega do filme
			{
				if(pedido.filmes.get(j).id_categoria == id_categoria)
				{
					total = pedido.filmes.get(j).preco;
					data  = pedido.filmes.get(j).data_entrega;
					break;
				}
			}
			FireBird.getInstance().updatePrecosPromocoesFilmes(pedido.id_pd_locacao, id_categoria, total, data);
		}
	}
	private void onPressed_ExcluirFilmes()
	{
		try
		{
			int id = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			FILMES filme = FireBird.getInstance().selectBuscaFilme(id);
			int size = pedido.filmes.size();
			int id_pd_itens_pd_locacao=0;
			FILMES f=null;
			for(int i=0; i<size;i++)
			{
				f = pedido.filmes.get(i);
				if(filme.id_filmes == f.id_filmes)
				{
					id_pd_itens_pd_locacao = f.id_itens_pd_locacao;
					break;
				}
			}
			removeFilme(f);
			updateFilme();
			if(!FireBird.getInstance().excluir(id_pd_itens_pd_locacao, "itens_pd_locacao"))
				JOptionPane.showMessageDialog(null, "erro id_pd_itens_pd_locacao " + id_pd_itens_pd_locacao);
		}
		catch(Exception exc)
		{
			JOptionPane.showMessageDialog(null,"Filme n�o selecionado.");
		}
		showValues();
	}
	public void updateFilme()
	{
		int sizeFilmes = pedido.filmes.size();
		int rows = table.getRowCount(),totalCol = 8;
		for(int count = 0; count < rows; count++)
		{
			for(int i=0; i < totalCol;i++)
				table.getModel().setValueAt("", count, i);
		}
		for(int count=0; count < sizeFilmes; count++)
		{
			FILMES filmeAtual = pedido.filmes.get(count);
			model.setValueAt(filmeAtual.id_filmes, count, 0);
			model.setValueAt(filmeAtual.nome, count, 1);
			model.setValueAt(filmeAtual.qtde, count, 2);
			model.setValueAt(filmeAtual.nome_categoria, count, 3);
			model.setValueAt(filmeAtual.qtde_disponivel, count, 4);
			model.setValueAt("", count, 5);
			model.setValueAt(filmeAtual.observacao, count, 6);
			model.setValueAt("", count, 7);
		}
		int size = pedido.ListaCategorias.size();
		for(int i=0; i< size; i++)
		{
			CONTACATEGORIA cc =  pedido.ListaCategorias.get(i);
			PRECOPROMOCIONAL pp = FireBird.getInstance().getPreco(cc.total, cc.id_categoria);
			setPrecoPromocional(pp);
		}
	}
	private void onPressed_AcrescentaFilmes()
	{
		if(pedido.filmes.size()>=19)
		{
			JOptionPane.showMessageDialog(this,"Sua lista de filmes est� cheia!");
			return;
		}
		if(lblNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Cliente n�o selecionado.");
			return;
		}
		int id_filme = UTIL.getInt(txtIdFilme.getText(),-1);
		if(id_filme == -1)
		{
			JOptionPane.showMessageDialog(null,"Filme n�o encontrado.");
			return;
		}
		txtIdFilme.setText("");
		if (!FireBird.getInstance().existe_filme(id_filme))
		{
			JOptionPane.showMessageDialog(null,"Filme n�o encontrado.");
			return;
		}
		FILMES filme = FireBird.getInstance().pedidoDeFilme(id_filme, pedido.cliente.id_clientes);
		if(filme!=null)
		{
			addFilme(filme);
			int size = pedido.ListaCategorias.size();
			for(int i=0; i< size; i++)
			{
				CONTACATEGORIA cc =  pedido.ListaCategorias.get(i);
				PRECOPROMOCIONAL pp = FireBird.getInstance().getPreco(cc.total, cc.id_categoria);
				if(!setPrecoPromocional(pp))
				{
					removeFilme(filme);
					updateFilme();
				}
			}
			try
			{
				for(int i=0; i< 19; i++)
				{
					int id_filmes = (Integer)model.getValueAt(i,0);
					if(filme.id_filmes == id_filmes)
					{
						String data_entrega = (String)model.getValueAt(i,5);
						String observacao = (String)model.getValueAt(i,6);
						String preco_filme = (String)model.getValueAt(i,7);
						if(!FireBird.getInstance().insertItens(filme, pedido.id_pd_locacao, preco_filme, UTIL.alterMesDiaData(data_entrega), observacao))
						{
							removeFilme(filme);
							updateFilme();
						}
						break;
					}
				}
			}
			catch(Exception exc)
			{
				JOptionPane.showMessageDialog(null,"Nop nop.");
			}
		}
		showValues();
	}
	public boolean setPrecoPromocional(PRECOPROMOCIONAL pp)
	{
		try
		{
			ArrayList<CATEGORIAFILMES> lsCat;
			lsCat = FireBird.getInstance().selectBuscaCategoria("","");
			int sizeLsCat = lsCat.size();
			int size = pedido.filmes.size();
			for(int i=0; i< size;i++)
			{
				String NomeCat = (String)model.getValueAt(i,3);
				for(int j=0; j < sizeLsCat ; j++)
				{
					CATEGORIAFILMES cf = lsCat.get(j);
					if(NomeCat.compareToIgnoreCase(cf.nome)==0)
					{
						if(cf.id_categoria == pp.id_categoria)
						{
							model.setValueAt(pp.data, i, 5);
							model.setValueAt(String.valueOf(pp.preco).replace(".",","), i, 7);
							for(int k=0;k < size;k++)
							{
								FILMES f = pedido.filmes.get(k);
								if(f.id_categoria == pp.id_categoria)
								{
									f.preco = String.valueOf(pp.preco);
									f.data_entrega = pp.data;
								}
							}
							break;
						}
					}
				}
			}
			return true;
		}
		catch(Exception ex)
		{
			return false;
		}
	}
	public void procuraClientePeloId(int id_cliente)
	{
		if(!habilitaProcuraCliente || id_cliente == -1)
			return;
		CLIENTES cliente =  FireBird.getInstance().selectBuscaCliente(id_cliente);
		if(cliente!=null)
		{
			lblIdCliente.setText(String.valueOf(cliente.id_clientes));
			lblNome.setText(cliente.nome);
			lblCpf.setText(cliente.cpf);
			lblCep.setText(cliente.cep);
			lblEndereco.setText(cliente.endereco);
			pedido.cliente = cliente;
		}
	}
}
