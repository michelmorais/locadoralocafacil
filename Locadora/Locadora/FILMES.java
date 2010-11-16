package Locadora;

public class FILMES 
{
	public int 		id_filmes;
	public String 	nome;
	public int 		qtde;
	public int 		qtde_disponivel;
	public int 		id_genero;
	public int 		id_categoria;
	public String	sinopse;
	public String	nome_genero;
	public String	nome_categoria;
	public String 	observacao;
	public int 		id_itens_pd_locacao;
	public String	data_entrega;//usado somente para consulta (inner join)
	public String	preco;//usado somente para consulta (inner join)
	public String   situacao;//usado para devolucao
	public int 		id_devolucao;//usado para devolucao
}
