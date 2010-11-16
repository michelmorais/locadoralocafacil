package Locadora;

public class DEVOLUCAO 
{
	public double	valor_unitario;//usado somente para devolucao de filme
	public double	valor_total;//usado somente para devolucao de filme
	public double	valor_recebido;//usado somente para devolucao de filme
	public double   valor_a_receber;//usado somente para devolucao de filme
	public double	multa;//usado somente para devolucao de filme
	public int		qtde_locado;
	public String	data_entrega;
	public String	data_locacao;
	public int		id_pd_locacao;
	public CLIENTES cliente;
	public FILMES	filme;	
	public DEVOLUCAO()
	{
		filme		= new FILMES();
		cliente 	= new CLIENTES();
	}
	
}
