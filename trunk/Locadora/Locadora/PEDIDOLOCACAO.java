package Locadora;

import java.util.ArrayList;

public class PEDIDOLOCACAO 
{
	public CLIENTES						cliente;
	public ArrayList<FILMES>			filmes;
	public ArrayList<CONTACATEGORIA>	ListaCategorias;
	public int 							id_pd_locacao;
	public String 						data;
	public double 						valor_pedido;
	public char							tipo_pagamento;
	public PEDIDOLOCACAO()
	{
		filmes 				= new ArrayList<FILMES>();
		ListaCategorias 	= new ArrayList<CONTACATEGORIA>();
		tipo_pagamento		= 'V';
	}
	
}
