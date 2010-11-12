package Locadora;

public class BUSCAGENERICA 
{
	public String nome_btnNovo; 
	public String nome_lblBuscatexto;
	public String nome_lblBuscaNumero;
	public String nome_btnPesquisar;
	public String nome_btnEditar;
	public String[] header;
	BUSCAGENERICA()
	{
		nome_btnNovo = "Novo"; 
		nome_lblBuscatexto = "Busca textual";
		nome_lblBuscaNumero = "Número";
		nome_btnPesquisar = "Pesquisar";
		nome_btnEditar = "Editar";
		header = new String[3];
		header[0]="Cliente";
		header[1]="Nome do filme";
		header[2]="Tipo";
	}
}
