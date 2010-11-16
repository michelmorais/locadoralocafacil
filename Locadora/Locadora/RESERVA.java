package Locadora;

import java.util.ArrayList;

public class RESERVA 
{
	public int 					id_reserva;
	public String 				situacao;
	public String[]				situacao_individual;
	public String				data;
	public ArrayList<FILMES> 	filmes;
	public CLIENTES 			cliente;
	public RESERVA()
	{
		filmes  			= new ArrayList<FILMES>();
		cliente 			= new CLIENTES();
		situacao_individual = new String[5];
	}
	
}
