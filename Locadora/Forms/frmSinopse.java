package Forms;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import Locadora.FILMES;
import Locadora.PEDIDOLOCACAO;
import Locadora.UTIL;

public class frmSinopse extends JPanel implements FocusListener
{
	private static final long serialVersionUID = 1L;
	private JFrame 		frame;
	private JTextArea	txtArea;
	private static frmSinopse instance = null;
	private boolean impressao;
	
	public static frmSinopse getInstance(String titulo,String sinopse)
	{
		if(titulo==null || sinopse==null)
			return null;
		if(instance==null)
			instance = new frmSinopse("SINÓPSE: " + titulo);
		instance.impressao = false;
		instance.frame.requestFocus();
		instance.txtArea.setText(sinopse);
		instance.txtArea.setEditable(false);
		return instance;
	}
	private String getStringLenthFix(String str,int size,String caracter)
	{
		while(str.length() < size)
		{
			str+=caracter;
		}
		if(str.length() > size)
		{
			str=str.substring(0, size);
		}
		return str.toUpperCase();
	}
	public static frmSinopse impressao(PEDIDOLOCACAO pedido)
	{
		if(instance==null)
			instance = new frmSinopse("LOCAFÁCIL IMPRESÃO");
		instance.setPreferredSize(new Dimension(500, 600));
		instance.txtArea.setBounds(15, 15, 430,500);
		instance.frame.setBounds(15, 15, 470,570);
		UTIL.setCenterScreen(instance.frame);
		int lengthLines =60;
		instance.frame.requestFocus();
		String str =null; 
		str = instance.getStringLenthFix("\t\tLOCAFÁCIL\n",lengthLines," ");
		//instance.txtArea.a
		instance.txtArea.append(str+ "\n");
		
		str = instance.getStringLenthFix("\tCliente: " + pedido.cliente.nome,lengthLines," ");
		instance.txtArea.append(str+ "\n");
		
		str = instance.getStringLenthFix("\tEndereço: " + pedido.cliente.endereco,lengthLines," ");
		instance.txtArea.append(str+ "\n");
		
		str = instance.getStringLenthFix("\tTelefone: " + pedido.cliente.fone,lengthLines," ");
		instance.txtArea.append(str+ "\n");
		
		str = instance.getStringLenthFix("____________________________________________________________________",lengthLines,"_");
		instance.txtArea.append(str+ "\n");
		
		str = instance.getStringLenthFix("    Filme\t\t\t\tPreço",lengthLines," ");
		instance.txtArea.append(str+ "\n");
		
		int sizeFilmes=pedido.filmes.size();
		for(int i=0;i< sizeFilmes;i++)
		{
			FILMES f = pedido.filmes.get(i);
			str = instance.getStringLenthFix(" " + f.nome,lengthLines-8,"_");
			str=instance.getStringLenthFix(str + UTIL.formateRS(Double.parseDouble(f.preco.replace(",","."))),lengthLines," ");
			instance.txtArea.append(str+ "\n");
		}
		if(pedido.tipo_pagamento=='V')
			str = instance.getStringLenthFix("\n\tTipo de pagamento: A vista",lengthLines," ");
		else
			str = instance.getStringLenthFix("\n\tTipo de pagamento: A prazo",lengthLines," ");
		instance.txtArea.append(str+ "\n");
		instance.txtArea.setEditable(false);
		instance.impressao =true;
		return instance;
	}
	private frmSinopse(String titulo) 
	{
		setPreferredSize(new Dimension(500, 250));
		setLayout(null);
		txtArea = new JTextArea();
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
	    JScrollPane scroll = new JScrollPane(txtArea);
	    
		add(txtArea);
		add(scroll, java.awt.BorderLayout.CENTER);
		txtArea.setBounds(15, 15, 470,220);
		
		frame = new JFrame(titulo);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addFocusListener(this);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				frmSinopse.instance = null;
			}  
		});
	}
	@Override
	public void focusGained(FocusEvent e) 
	{
	}
	@Override
	public void focusLost(FocusEvent e) 
	{
		if(!impressao)
			frame.dispose();
	}
}
