package Locadora;

import java.util.ArrayList;

public class RESERVA 
{
	public int 					id_reserva;
	public String 				situacao; 
	public ArrayList<FILMES> 	filmes;
	public CLIENTES 			cliente;
	public RESERVA()
	{
		filmes  = new ArrayList<FILMES>();
		cliente = new CLIENTES();
	}
	
}
