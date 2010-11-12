package Locadora;

import java.util.ArrayList;

public class RESERVA 
{
	public int id_reserva;
	public int id_clientes;
	public int id_filmes_1;
	public int id_filmes_2;
	public int id_filmes_3;
	public int id_filmes_4;
	public int id_filmes_5;
	public String situacao; 
	public ArrayList<FILMES> filmes;
	public CLIENTES cliente;
	public RESERVA()
	{
		filmes  = new ArrayList<FILMES>();
		cliente = new CLIENTES();
	}
	
}
