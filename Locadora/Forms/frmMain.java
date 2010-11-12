package Forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Banco.FireBird;
import Locadora.UTIL;

public class frmMain extends JPanel implements ActionListener,ItemListener 
{
	private static final long serialVersionUID = 1L;
	private JMenuBar mnuMenu;
	private JMenu mnuCadastroMenu;
	private JMenu mnuSobreMenu;
	private JMenu mnuOperacoesMenu;
	private JMenuItem mnuFilmes;
	private JMenuItem mnuCategorias;
	private JMenuItem mnuGeneros;
	private JMenuItem mnuPromocao;
	private JMenuItem mnuClientes;
	private JMenuItem mnuLocacao;
	private JMenuItem mnuReserva;
	private JMenuItem mnuEntreEmContato;
	private JMenuItem mnuControleDeDevolucao;
	
	public frmBuscaGenerica frmBuscaFilmes;
	public frmBuscaGenerica frmBuscaCategoriaFilmes;
	public frmBuscaGenerica frmBuscaGenerosFilmes;
	public frmBuscaGenerica frmBuscaCliente;
	public frmBuscaGenerica frmBuscaLocacao;
	public frmBuscaGenerica frmBuscaDevolucao;
	public frmBuscaGenerica frmBuscaReserva;
	public frmBuscaGenerica frmBuscaPromocao;
	
	private static FireBird fireBird;
	
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame("LOCAFACIL");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new frmMain());
		frame.pack();
		UTIL.setMaxiSizeScreen(frame);
		frame.setVisible(true);
		fireBird = FireBird.getInstance();
		if(!fireBird.connect())
			JOptionPane.showMessageDialog(null, "Falha na conexão com o banco de dados");
		fireBird.closeConnection();
	}
	public frmMain() 
	{
		mnuCadastroMenu = new JMenu("Cadastro");
		mnuFilmes = new JMenuItem("Filmes");
		mnuCadastroMenu.add(mnuFilmes);
		mnuCategorias = new JMenuItem("Categorias");
		mnuCadastroMenu.add(mnuCategorias);
		mnuGeneros = new JMenuItem("Gêneros");
		mnuCadastroMenu.add(mnuGeneros);
		mnuCadastroMenu.add(mnuCategorias);
		mnuClientes = new JMenuItem("Clientes");
		mnuCadastroMenu.add(mnuClientes);
		mnuPromocao = new JMenuItem("Promoções");
		mnuCadastroMenu.add(mnuPromocao);
		mnuOperacoesMenu = new JMenu("Operações");
		mnuLocacao = new JMenuItem("Locação");
		mnuOperacoesMenu.add(mnuLocacao);
		mnuControleDeDevolucao = new JMenuItem("Controle de devolução");
		mnuOperacoesMenu.add(mnuControleDeDevolucao);
		mnuReserva = new JMenuItem("Reserva");
		mnuOperacoesMenu.add(mnuReserva);
		mnuSobreMenu = new JMenu("Sobre");
		mnuEntreEmContato = new JMenuItem("Entre em contato");
		mnuSobreMenu.add(mnuEntreEmContato);
		mnuMenu = new JMenuBar();
		
		mnuFilmes.addActionListener(this);
		mnuCategorias.addActionListener(this);
		mnuGeneros.addActionListener(this);
		mnuClientes.addActionListener(this);
		mnuPromocao.addActionListener(this);
		mnuLocacao.addActionListener(this);
		mnuReserva.addActionListener(this);
		mnuEntreEmContato.addActionListener(this);
		mnuControleDeDevolucao.addActionListener(this);
		
		mnuMenu.add(mnuCadastroMenu);
		mnuMenu.add(mnuOperacoesMenu);
		mnuMenu.add(mnuSobreMenu);
		add(mnuMenu);
		setPreferredSize(new Dimension(800, 650));
		setLayout(null);
		mnuMenu.setBounds(0, 0, UTIL.getWidthWindow(), 25);
	}

	@Override
	public void itemStateChanged(ItemEvent e) 
	{
		if(e.getSource()==mnuFilmes)
			System.out.println("FilmesItens ItemEvent");
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource()==mnuFilmes)
			frmBuscaFilmes = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_FILMES);
		else if(arg0.getSource()==mnuCategorias)
			frmBuscaCategoriaFilmes = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_CATEGORIA_FILMES);
		else if(arg0.getSource()==mnuGeneros)
			frmBuscaGenerosFilmes = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_GENEROS_FILMES);
		else if(arg0.getSource()==mnuClientes)
			frmBuscaCliente = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_CLIENTES);
		else if(arg0.getSource()==mnuPromocao)
			frmBuscaPromocao = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_PROMOCAO);
		else if(arg0.getSource()==mnuLocacao)
			frmBuscaLocacao = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_LOCACAO);
		else if(arg0.getSource()==mnuReserva)
			frmBuscaReserva = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_RESERVA);
		else if(arg0.getSource()==mnuControleDeDevolucao)
			frmBuscaDevolucao = frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_DEVOLUCAO);
		else if(arg0.getSource()==mnuEntreEmContato)
		{
			JOptionPane.showMessageDialog(this,"" +
					"TRABALHO BSI 4SA\n\n" +
					"ALUNOS:\n" +
					"CALANDRINE MAXIMILIANO\n" +
					"JULIANO DE MELO\n" +
					"MICHEL BRAZ DE MORAIS");
		}
	}
}
