package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Banco.FireBird;
import Locadora.CATEGORIAFILMES;
import Locadora.CLIENTES;
import Locadora.FILMES;
import Locadora.GENERO;
import Locadora.LOCACAO;
import Locadora.PROMOCAO;
import Locadora.RESERVA;
import Locadora.UTIL;

public class frmBuscaGenerica extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JButton btnNovo;
	private JLabel lblBuscatexto;
	private JLabel lblBuscaNumero;
	private JTextField txtBuscaTexto;
	private JTextField txtBuscaNumero;
	private JButton btnPesquisar;
	private JButton btnSelecionar;
	private JButton btnExcluir;
	private JButton btnCancelar;
	private JButton btnEditar;
	private JTable table;
	private JTextField editor;
	private DefaultTableModel model;
	private JFrame frame;
	
	private int tipoForm;
	private boolean foiPressionadoEditar;
	private ArrayList<CATEGORIAFILMES> lsCatFilmes;
	private ArrayList<FILMES> lsFilmes;
	private ArrayList<RESERVA> lsReserva;
	private ArrayList<GENERO> lsGenFilmes;
	private ArrayList<CLIENTES> lsClientes;
	private ArrayList<PROMOCAO> lsPromocao;
	private ArrayList<LOCACAO> lsLocacao;
	
	public static final int BUSCA_FILMES = 1;
	public static final int BUSCA_FILMES_PARA_LOCACAO = 2;
	public static final int BUSCA_FILMES_PARA_RESERVAS = 3;
	public static final int BUSCA_CATEGORIA_FILMES = 4;
	public static final int BUSCA_GENEROS_FILMES = 5;
	public static final int BUSCA_CLIENTES = 6;
	public static final int BUSCA_CLIENTES_PARA_LOCACAO = 7;
	public static final int BUSCA_CLIENTES_PARA_RESERVA = 8;
	public static final int BUSCA_PROMOCAO = 9;
	public static final int BUSCA_LOCACAO = 10;
	public static final int BUSCA_DEVOLUCAO = 11;
	public static final int BUSCA_RESERVA = 12;
	
	
	private static frmBuscaGenerica instanceBuscaFilmes = null;
	private static frmBuscaGenerica instanceBuscaCategoriaFilmes = null;
	private static frmBuscaGenerica instanceBuscaGenerosFilmes = null;
	private static frmBuscaGenerica instanceBuscaClientes = null;
	private static frmBuscaGenerica instanceBuscaLocacao = null;
	private static frmBuscaGenerica instanceBuscaDevolocao = null;
	private static frmBuscaGenerica instanceBuscaReserva = null;
	private static frmBuscaGenerica instanceBuscaPromocao = null;
	
	public static void RefreshPesquisa(int tipo)
	{
		switch(tipo)
		{
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				if(instanceBuscaFilmes!=null && instanceBuscaFilmes.foiPressionadoEditar)
					instanceBuscaFilmes.onPressed_btnPesquisar();
			break;
			case BUSCA_CATEGORIA_FILMES:
				if(instanceBuscaCategoriaFilmes!=null && instanceBuscaCategoriaFilmes.foiPressionadoEditar)
					instanceBuscaCategoriaFilmes.onPressed_btnPesquisar();
				break;
			case BUSCA_GENEROS_FILMES:
				if(instanceBuscaGenerosFilmes!=null && instanceBuscaGenerosFilmes.foiPressionadoEditar)
					instanceBuscaGenerosFilmes.onPressed_btnPesquisar();
				break;			
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				if(instanceBuscaClientes!=null && instanceBuscaClientes.foiPressionadoEditar)
					instanceBuscaClientes.onPressed_btnPesquisar();
				break;			
			case BUSCA_PROMOCAO:
				if(instanceBuscaPromocao!=null && instanceBuscaPromocao.foiPressionadoEditar)
					instanceBuscaPromocao.onPressed_btnPesquisar();
				break;			
			case BUSCA_LOCACAO:
				if(instanceBuscaLocacao!=null && instanceBuscaLocacao.foiPressionadoEditar)
					instanceBuscaLocacao.onPressed_btnPesquisar();
				break;			
			case BUSCA_DEVOLUCAO:
				if(instanceBuscaDevolocao != null && instanceBuscaDevolocao.foiPressionadoEditar)
					instanceBuscaDevolocao.onPressed_btnPesquisar();
				break;			
			case BUSCA_RESERVA:
				if(instanceBuscaReserva != null && instanceBuscaReserva.foiPressionadoEditar)
					instanceBuscaReserva.onPressed_btnPesquisar();
				break;
			}
	}
	
	public static frmBuscaGenerica getInstance(int tipo)
	{
		switch(tipo)
		{
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				if(instanceBuscaFilmes==null)
					instanceBuscaFilmes = new frmBuscaGenerica(tipo);
				instanceBuscaFilmes.frame.requestFocus();
				return instanceBuscaFilmes;
			case BUSCA_CATEGORIA_FILMES:
				if(instanceBuscaCategoriaFilmes==null)
					instanceBuscaCategoriaFilmes = new frmBuscaGenerica(tipo);
				instanceBuscaCategoriaFilmes.frame.requestFocus();
				return instanceBuscaCategoriaFilmes;
			case BUSCA_GENEROS_FILMES:
				if(instanceBuscaGenerosFilmes==null)
					instanceBuscaGenerosFilmes = new frmBuscaGenerica(tipo);
				instanceBuscaGenerosFilmes.frame.requestFocus();
				return instanceBuscaGenerosFilmes;
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				if(instanceBuscaClientes==null)
					instanceBuscaClientes = new frmBuscaGenerica(tipo);
				instanceBuscaClientes.frame.requestFocus();
				return instanceBuscaClientes;
			case BUSCA_PROMOCAO:
				if(instanceBuscaPromocao==null)
					instanceBuscaPromocao = new frmBuscaGenerica(tipo);
				instanceBuscaPromocao.frame.requestFocus();
				return instanceBuscaPromocao;
			case BUSCA_LOCACAO:
				if(instanceBuscaLocacao==null)
					instanceBuscaLocacao = new frmBuscaGenerica(tipo);
				instanceBuscaLocacao.frame.requestFocus();
				instanceBuscaLocacao.btnEditar.setText("Consultar");
				return instanceBuscaLocacao;
			case BUSCA_DEVOLUCAO:
				if(instanceBuscaDevolocao == null)
					instanceBuscaDevolocao = new frmBuscaGenerica(tipo);
				instanceBuscaDevolocao.frame.requestFocus();
				return instanceBuscaDevolocao;
			case BUSCA_RESERVA:
				if(instanceBuscaReserva == null)
					instanceBuscaReserva = new frmBuscaGenerica(tipo);
				instanceBuscaReserva.frame.requestFocus();
				return instanceBuscaReserva;
		}
		return null;
	}
	private frmBuscaGenerica(int tipo) 
	{
		foiPressionadoEditar = false;
		txtBuscaTexto = new JTextField(5);
		txtBuscaNumero = new JTextField(5);
		btnPesquisar = new JButton("Pesquisar");
		btnExcluir = new JButton("Excluir");
		btnCancelar = new JButton("Cancelar");
		btnEditar = new JButton("Editar");
		btnSelecionar = new JButton("Selecionar");
		editor = new JTextField();
		editor.setEditable(false);
		switch(tipo)
		{
		case BUSCA_FILMES:
			tipoForm = BUSCA_FILMES;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do filme");
			lblBuscaNumero = new JLabel("Código do filme");
			model = new DefaultTableModel(17,7);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("FILMES");
		break;
		case BUSCA_FILMES_PARA_LOCACAO:
			tipoForm = BUSCA_FILMES_PARA_LOCACAO;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do filme");
			lblBuscaNumero = new JLabel("Código do filme");
			model = new DefaultTableModel(17,7);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("FILMES");
		break;
		case BUSCA_FILMES_PARA_RESERVAS:
			tipoForm = BUSCA_FILMES_PARA_RESERVAS;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do filme");
			lblBuscaNumero = new JLabel("Código do filme");
			model = new DefaultTableModel(17,7);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("FILMES");
		break;
		case BUSCA_CATEGORIA_FILMES:
			tipoForm = BUSCA_CATEGORIA_FILMES;
			model = new DefaultTableModel(17,3);
		    table = new JTable(model);
		    btnNovo = new JButton("Nova");
			lblBuscatexto = new JLabel("Nome da categoria");
			lblBuscaNumero = new JLabel("Código da categoria");
			ajusteColunas();
			mountForm();
			frmShow("CATEGORIA DE FILMES");
		break;
		case BUSCA_CLIENTES:
			tipoForm = BUSCA_CLIENTES;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("Código do cliente");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
		    ajusteColunas();
			mountForm();
			frmShow("CLIENTES");
		break;
		case BUSCA_CLIENTES_PARA_LOCACAO:
			tipoForm = BUSCA_CLIENTES_PARA_LOCACAO;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("Código do cliente");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
		    ajusteColunas();
			mountForm();
			frmShow("CLIENTES");
		break;
		case BUSCA_CLIENTES_PARA_RESERVA:
			tipoForm = BUSCA_CLIENTES_PARA_RESERVA;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("Código do cliente");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
		    ajusteColunas();
			mountForm();
			frmShow("CLIENTES");
		break;
		case BUSCA_GENEROS_FILMES:
			tipoForm = BUSCA_GENEROS_FILMES;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do gênero");
			lblBuscaNumero = new JLabel("Código do gênero");
			model = new DefaultTableModel(17,2);
		    table = new JTable(model);
		    ajusteColunas();
			mountForm();
			frmShow("GÊNERO DE FILMES");
		break;
		case BUSCA_LOCACAO:
			tipoForm = BUSCA_LOCACAO;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("Número da locação");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("LOCAÇÃO");
		break;
		case BUSCA_PROMOCAO:
			tipoForm = BUSCA_PROMOCAO;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome da promoção");
			lblBuscaNumero = new JLabel("Código da promoção");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("PROMOÇÕES");
		break;
		case BUSCA_DEVOLUCAO:
			tipoForm = BUSCA_DEVOLUCAO;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("N° locação / CPF");
			model = new DefaultTableModel(17,8);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("DEVOLUÇÃO");
			btnNovo.setVisible(false);
			btnEditar.setBounds(15, 15, 92, 50);
		break;
		case BUSCA_RESERVA:
			tipoForm = BUSCA_RESERVA;
			btnNovo = new JButton("Novo");
			lblBuscatexto = new JLabel("Nome do cliente");
			lblBuscaNumero = new JLabel("Número da reserva");
			model = new DefaultTableModel(17,6);
		    table = new JTable(model);
			ajusteColunas();
			mountForm();
			frmShow("RESERVA");
		break;
		}
	}
	private void ajusteColunas()
	{
		switch(tipoForm)
		{
			case BUSCA_CATEGORIA_FILMES:
				table.getColumnModel().getColumn(0).setHeaderValue("Cód");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome da categoria");
				table.getColumnModel().getColumn(2).setHeaderValue("Preço");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(300);
				table.getColumnModel().getColumn(2).setMaxWidth(90);
			break;
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				table.getColumnModel().getColumn(0).setHeaderValue("Cód");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome");
				table.getColumnModel().getColumn(2).setHeaderValue("Categoria");
				table.getColumnModel().getColumn(3).setHeaderValue("Gênero");
				table.getColumnModel().getColumn(4).setHeaderValue("Qtde");
				table.getColumnModel().getColumn(5).setHeaderValue("Qtde Disp");
				table.getColumnModel().getColumn(6).setHeaderValue("Sinópse");
				table.getColumnModel().getColumn(0).setMaxWidth(30);
				table.getColumnModel().getColumn(1).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setPreferredWidth(70);
				table.getColumnModel().getColumn(3).setPreferredWidth(70);
				table.getColumnModel().getColumn(4).setMaxWidth(70);
				table.getColumnModel().getColumn(5).setMaxWidth(70);
				table.getColumnModel().getColumn(6).setPreferredWidth(70);
			break;
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				table.getColumnModel().getColumn(0).setHeaderValue("Cód");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome");
				table.getColumnModel().getColumn(2).setHeaderValue("Endereço");
				table.getColumnModel().getColumn(3).setHeaderValue("CPF");
				table.getColumnModel().getColumn(4).setHeaderValue("CEP");
				table.getColumnModel().getColumn(5).setHeaderValue("Telefone");
				table.getColumnModel().getColumn(0).setMaxWidth(30);
				table.getColumnModel().getColumn(1).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(3).setPreferredWidth(70);
				table.getColumnModel().getColumn(4).setPreferredWidth(70);
				table.getColumnModel().getColumn(5).setPreferredWidth(70);
			break;
			case BUSCA_GENEROS_FILMES:
				table.getColumnModel().getColumn(0).setHeaderValue("Cód");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome do gênero");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
			break;
			case BUSCA_PROMOCAO:
				table.getColumnModel().getColumn(0).setHeaderValue("Cód");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome da promoção");
				table.getColumnModel().getColumn(2).setHeaderValue("Categoria");
				table.getColumnModel().getColumn(3).setHeaderValue("Preço");
				table.getColumnModel().getColumn(4).setHeaderValue("Qtde filmes");
				table.getColumnModel().getColumn(5).setHeaderValue("Qtde Dias");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(250);
				table.getColumnModel().getColumn(2).setPreferredWidth(150);
				table.getColumnModel().getColumn(2).setMaxWidth(150);
				table.getColumnModel().getColumn(3).setMaxWidth(100);
				table.getColumnModel().getColumn(4).setMaxWidth(70);
				table.getColumnModel().getColumn(5).setMaxWidth(70);
				
			break;
			case BUSCA_LOCACAO:
				table.getColumnModel().getColumn(0).setHeaderValue("Número");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome do cliente");
				table.getColumnModel().getColumn(2).setHeaderValue("Valor");
				table.getColumnModel().getColumn(3).setHeaderValue("Data");
				table.getColumnModel().getColumn(4).setHeaderValue("Telefone");
				table.getColumnModel().getColumn(5).setHeaderValue("CPF");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(1).setMaxWidth(200);
				table.getColumnModel().getColumn(1).setPreferredWidth(120);
				table.getColumnModel().getColumn(2).setMaxWidth(100);
				table.getColumnModel().getColumn(3).setMaxWidth(120);
				table.getColumnModel().getColumn(4).setMaxWidth(120);
				table.getColumnModel().getColumn(5).setMaxWidth(120);
			break;
			case BUSCA_DEVOLUCAO:
				table.getColumnModel().getColumn(0).setHeaderValue("Número");
				table.getColumnModel().getColumn(1).setHeaderValue("Nome do cliente");
				table.getColumnModel().getColumn(2).setHeaderValue("Valor");
				table.getColumnModel().getColumn(3).setHeaderValue("Data locação");
				table.getColumnModel().getColumn(4).setHeaderValue("Data entrega");
				table.getColumnModel().getColumn(5).setHeaderValue("Telefone");
				table.getColumnModel().getColumn(6).setHeaderValue("CPF");
				table.getColumnModel().getColumn(7).setHeaderValue("Situação");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(1).setPreferredWidth(200);
				table.getColumnModel().getColumn(2).setMaxWidth(80);
				table.getColumnModel().getColumn(3).setMaxWidth(90);
				table.getColumnModel().getColumn(4).setMaxWidth(90);
				table.getColumnModel().getColumn(5).setMaxWidth(90);
			break;
			case BUSCA_RESERVA:
				table.getColumnModel().getColumn(0).setHeaderValue("Número");
				table.getColumnModel().getColumn(1).setHeaderValue("Situação");
				table.getColumnModel().getColumn(2).setHeaderValue("Nome do cliente");
				table.getColumnModel().getColumn(3).setHeaderValue("Data");
				table.getColumnModel().getColumn(4).setHeaderValue("Telefone");
				table.getColumnModel().getColumn(5).setHeaderValue("CPF");
				table.getColumnModel().getColumn(0).setMaxWidth(50);
				table.getColumnModel().getColumn(1).setMaxWidth(90);
				table.getColumnModel().getColumn(2).setPreferredWidth(250);
				table.getColumnModel().getColumn(3).setMaxWidth(90);
				table.getColumnModel().getColumn(4).setMaxWidth(90);
				table.getColumnModel().getColumn(5).setMaxWidth(120);
				table.getColumnModel().getColumn(5).setPreferredWidth(120);
			break;
		}
	}
		
	
	private void mountForm()
	{
		table.setDefaultEditor(Object.class, new DefaultCellEditor(editor));
		table.setPreferredScrollableViewportSize(new Dimension(700, 275));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(WHEN_FOCUSED);
        JScrollPane jp = new JScrollPane(table);  
        jp.setViewportView(table);  
        //jp.getVerticalScrollBar().setValue(100);  
        jp.setAutoscrolls(true);  
        //jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //jp.setMinimumSize(new Dimension(10, 10));
        jp.setEnabled(true);
        jp.setWheelScrollingEnabled(true);
        table.setAutoscrolls(true);
        //jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //jp.setBounds(500, 105, 20,65);
        setPreferredSize(new Dimension(725, 400));
		setLayout(null);
		table.getTableHeader().setBounds(15, 95, 700,15);
		
		add(jp);
		add(table);
		add(table.getTableHeader());
		add(btnNovo);
		add(lblBuscatexto);
		add(lblBuscaNumero);
		add(txtBuscaTexto);
		add(txtBuscaNumero);
		add(btnPesquisar);
		add(btnExcluir);
		add(btnCancelar);
		add(btnEditar);
		
		btnEditar.addActionListener(this);
		btnNovo.addActionListener(this);
		btnPesquisar.addActionListener(this);
		btnExcluir.addActionListener(this);
		btnCancelar.addActionListener(this);
		//table.getModel().addTableModelListener(this);
		
		//table.addFocusListener(this);
		btnNovo.setBounds(15, 15, 92, 25);
		btnEditar.setBounds(15, 45, 92, 25);
		lblBuscatexto.setBounds(130, 15, 130, 25);
		lblBuscaNumero.setBounds(130, 45, 130, 25);
		txtBuscaTexto.setBounds(250, 15, 360, 25);
		txtBuscaNumero.setBounds(250, 45, 175, 25);
		btnExcluir.setBounds(620, 15, 92, 25);
		btnPesquisar.setBounds(468, 45, 140, 25);
		btnCancelar.setBounds(620, 45, 92, 25);
		if(tipoForm == BUSCA_FILMES_PARA_LOCACAO || 
				tipoForm == BUSCA_CLIENTES_PARA_LOCACAO || 
				tipoForm == BUSCA_CLIENTES_PARA_RESERVA ||
				tipoForm == BUSCA_FILMES_PARA_RESERVAS)
		{
			add(btnSelecionar);
			btnSelecionar.addActionListener(this);
			txtBuscaNumero.setBounds(250, 45, 130, 25);
			btnSelecionar.setBounds(388, 45, 103, 25);
			btnSelecionar.setForeground(new Color(255, 0, 0));
			btnPesquisar.setBounds(502, 45, 107, 25);
		}
		table.setBounds(15, 110,700, 275);//290
		//table.setPreferredSize( new Dimension(490, 50));//15, 115, 490, 50);//290
		table.setOpaque(true);
	}
	private void frmShow(String nomeForm) 
	{
		table.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseClicked(MouseEvent arg0){}
			@Override
			public void mouseEntered(MouseEvent e){}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) 
			{
				if(instanceBuscaFilmes!=null)
				{
					verificaSinopse();
				}
			}
			
		});
		
		frame = new JFrame(nomeForm);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				switch(tipoForm)
				{
					case BUSCA_FILMES:
					case BUSCA_FILMES_PARA_LOCACAO:
					case BUSCA_FILMES_PARA_RESERVAS:
						instanceBuscaFilmes = null;
					break;
					case BUSCA_CATEGORIA_FILMES:
						instanceBuscaCategoriaFilmes = null;
					case BUSCA_GENEROS_FILMES:
						instanceBuscaGenerosFilmes = null;
					break;
					case BUSCA_CLIENTES:
					case BUSCA_CLIENTES_PARA_LOCACAO:
					case BUSCA_CLIENTES_PARA_RESERVA:
						instanceBuscaClientes = null;
					break;
					case BUSCA_PROMOCAO:
						instanceBuscaPromocao = null;
					break;
					case BUSCA_LOCACAO:
						instanceBuscaLocacao = null;
					break;
					case BUSCA_DEVOLUCAO:
						instanceBuscaDevolocao = null;
					break;
					case BUSCA_RESERVA:
						instanceBuscaReserva = null;
					break;
				} 
			}  
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnNovo)
			onPressed_btnNovo();
		else if(e.getSource() == btnPesquisar)
			onPressed_btnPesquisar();
		else if(e.getSource() == btnEditar)
			onPressed_btnEditar();
		else if(e.getSource() == btnExcluir)
			onPressed_btnExcluir();
		else if(e.getSource() == btnSelecionar)
			onPressed_btnSelecionar();
		else if(e.getSource() == btnCancelar)
			frame.dispose();
	}
	private boolean ConfirmaExclusao()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Deseja realmente excluir o registro?",  
		"LOCAFÁCIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
	public void onPressed_btnExcluir()
	{
		foiPressionadoEditar = false;
		switch(tipoForm)
		{
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				FILMES filme =  getFilmeFromTable();
				if(filme != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(filme.id_filmes, "filmes"))
						onPressed_btnPesquisar();
					}
				}
			break;
			case BUSCA_CATEGORIA_FILMES:
				CATEGORIAFILMES cf =  getCategoriaFromTable();
				if(cf != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(cf.id_categoria, "categoria"))
						onPressed_btnPesquisar();
					}
				}
			break;
			case BUSCA_GENEROS_FILMES:
				GENERO gen =  getGeneroFromTable();
				if(gen != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(gen.id_genero, "genero"))
							onPressed_btnPesquisar();
					}
				}
			break;
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				CLIENTES cliente =  getClientesFromTable();
				if(cliente != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(cliente.id_clientes, "clientes"))
						onPressed_btnPesquisar();
					}
				}
			break;
			case BUSCA_LOCACAO:
				int id_pd_locacao = getIdPdLocacaoFromTable();  
				if(id_pd_locacao != - 1)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(id_pd_locacao, "pd_locacao"));
						onPressed_btnPesquisar();
					}
				}
				
			break;
			case BUSCA_PROMOCAO:
				PROMOCAO promocao =  getPromocaoFromTable();
				if(promocao != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(promocao.id_promocao, "promocao"))
						onPressed_btnPesquisar();
					}
				}
			break;
			case BUSCA_DEVOLUCAO:
				
			break;
			case BUSCA_RESERVA:
				RESERVA reserva =  getReservaFromTable();
				if(reserva != null)
				{
					if(ConfirmaExclusao())
					{
						if(FireBird.getInstance().excluir(reserva.id_reserva, "reserva"))
						onPressed_btnPesquisar();
					}
				}
			break;
		}
	}
	public void onPressed_btnNovo()
	{
		foiPressionadoEditar = false;
		switch(tipoForm)
		{
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				frmCadastroFilmes.getInstance();
				this.frame.dispose();
			break;
			case BUSCA_CATEGORIA_FILMES:
				frmCategoria.getInstance();
			break;
			case BUSCA_GENEROS_FILMES:
				frmGenero.getInstance();
			break;
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				frmCadastroCliente.getInstance();
			break;
			case BUSCA_LOCACAO:
				frmPedidoLocacao.getInstance();
				this.frame.dispose();
			break;
			case BUSCA_PROMOCAO:
				frmPromocao.getInstance();
			break;
			case BUSCA_DEVOLUCAO:
				frmDevolucaoPagamento.getInstance();
			break;
			case BUSCA_RESERVA:
				frmReserva.getInstance();
				this.frame.dispose();
			break;
		}
		//this.frame.dispose();
	}
	private void onPressed_btnPesquisar()
	{
		btnPesquisar.setEnabled(false);
		switch(tipoForm)
		{
			case BUSCA_CATEGORIA_FILMES://3 totalCol
				pesquisaCategoriaDeFilmes();
				break;
			case BUSCA_FILMES://6 totalCol
			case BUSCA_FILMES_PARA_LOCACAO:
			case BUSCA_FILMES_PARA_RESERVAS:
				pesquisaDeFilmes();
				break;
			case BUSCA_GENEROS_FILMES://2 totalCol
				pesquisaGenerosDeFilmes();
				break;
			case BUSCA_CLIENTES://6 totalCol
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				pesquisaClientes();
				break;
			case BUSCA_LOCACAO://6 totalCol
				pesquisaLocacao();
				break;
			case BUSCA_DEVOLUCAO://8 totalCol
				break;
			case BUSCA_RESERVA://6 totalCol
				pesquisaDeReserva();
				break;
			case BUSCA_PROMOCAO://6
				pesquisaPromocao();
				break;
		}
		btnPesquisar.setEnabled(true);
	}
	private void pesquisaLocacao()
	{
		FireBird fireBird = FireBird.getInstance();
		lsLocacao = fireBird.selectBuscaLocacao(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsLocacao==null)
			return;
		int size = lsLocacao.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,6);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 6;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			LOCACAO locacao = lsLocacao.get(count);
			model.setValueAt(locacao.id_pd_locacao, count, 0);
			model.setValueAt(locacao.nome, count, 1);
			model.setValueAt(locacao.valor, count, 2);
			model.setValueAt(locacao.data, count, 3);
			model.setValueAt(locacao.fone, count, 4);
			model.setValueAt(locacao.cpf, count, 5);
		}
	}
	private void pesquisaDeReserva()
	{
		FireBird fireBird = FireBird.getInstance();
		lsReserva = fireBird.selectBuscaReservas(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsReserva==null)
			return;
		int size = lsReserva.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,7);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 7;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			RESERVA reserva = lsReserva.get(count);
			model.setValueAt(reserva.id_reserva, count, 0);
			model.setValueAt(reserva.situacao, count, 1);
			model.setValueAt(reserva.cliente.nome, count, 2);
			//model.setValueAt(reserva.nome_genero, count, 3);
			model.setValueAt(reserva.cliente.fone, count, 4);
			model.setValueAt(reserva.cliente.cpf, count, 5);
		}
	}
	private void pesquisaDeFilmes()
	{
		FireBird fireBird = FireBird.getInstance();
		lsFilmes = fireBird.selectBuscaFilmes(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsFilmes==null)
			return;
		int size = lsFilmes.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,7);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 7;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			FILMES filme = lsFilmes.get(count);
			model.setValueAt(filme.id_filmes, count, 0);
			model.setValueAt(filme.nome, count, 1);
			model.setValueAt(filme.nome_categoria, count, 2);
			model.setValueAt(filme.nome_genero, count, 3);
			model.setValueAt(filme.qtde, count, 4);
			model.setValueAt(filme.qtde_disponivel, count, 5);
			model.setValueAt(filme.sinopse, count, 6);
		}
	}
	
	private void pesquisaCategoriaDeFilmes()
	{
		FireBird fireBird = FireBird.getInstance();
		lsCatFilmes = fireBird.selectBuscaCategoria(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsCatFilmes==null)
			return;
		int size = lsCatFilmes.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,3);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 3;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			CATEGORIAFILMES cf = lsCatFilmes.get(count);
			model.setValueAt(cf.id_categoria, count, 0);
			model.setValueAt(cf.nome, count, 1);
			model.setValueAt(cf.preco, count, 2);
		}
	}
	private void pesquisaPromocao()
	{
		FireBird fireBird = FireBird.getInstance();
		lsPromocao = fireBird.selectBuscaPromocao(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsPromocao==null)
			return;
		int size = lsPromocao.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,6);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 6;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			PROMOCAO gen = lsPromocao.get(count);
			model.setValueAt(gen.id_promocao, count, 0);
			model.setValueAt(gen.nome, count, 1);
			model.setValueAt(gen.nome_categoria,count,2);
			model.setValueAt(gen.preco, count, 3);
			model.setValueAt(gen.qtde_filmes, count, 4);
			model.setValueAt(String.valueOf(gen.qtde_dias), count, 5);
		}
	}
	private void pesquisaClientes()
	{
		FireBird fireBird = FireBird.getInstance();
		lsClientes = fireBird.selectBuscaClientes(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsClientes==null)
			return;
		int size = lsClientes.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,6);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 6;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			CLIENTES gen = lsClientes.get(count);
			model.setValueAt(gen.id_clientes , count, 0);
			model.setValueAt(gen.nome, count, 1);
			model.setValueAt(gen.endereco, count, 2);
			model.setValueAt(gen.cpf, count, 3);
			model.setValueAt(gen.cep, count, 4);
			model.setValueAt(gen.fone, count, 5);
		}
	}
	private void pesquisaGenerosDeFilmes()
	{
		FireBird fireBird = FireBird.getInstance();
		lsGenFilmes = fireBird.selectBuscaGenero(txtBuscaTexto.getText(), txtBuscaNumero.getText());
		if(lsGenFilmes==null)
			return;
		int size = lsGenFilmes.size();
		if(size >= 17)
		{
			model = new DefaultTableModel(size,2);
			table.setModel(model);
			ajusteColunas();
		}
		else
		{
			int rows = table.getRowCount(),totalCol = 2;
			for(int count = 0; count < rows; count++)
			{
				for(int i=0; i < totalCol;i++)
					table.getModel().setValueAt("", count, i);
			}
		}
		for(int count=0; count < size; count++)
		{
			GENERO gen = lsGenFilmes.get(count);
			model.setValueAt(gen.id_genero, count, 0);
			model.setValueAt(gen.nome, count, 1);
		}
	}
	private CATEGORIAFILMES getCategoriaFromTable()
	{
		CATEGORIAFILMES cf = new CATEGORIAFILMES();
		try
		{
			cf.id_categoria = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			cf.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
			cf.preco = (Double)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),2);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return cf;
	}
	private FILMES getFilmeFromTable()
	{
		FILMES filme = new FILMES();
		try
		{
			filme.id_filmes = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			filme.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
			filme.qtde = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),4);
			filme.qtde_disponivel = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),5);
			int size = lsFilmes.size();
			for(int i=0; i< size; i++)
			{
				FILMES f = lsFilmes.get(i);
				if(f.id_filmes == filme.id_filmes)
				{
					filme.id_categoria 		= f.id_categoria;
					filme.id_genero			= f.id_genero;
					filme.nome_categoria	= f.nome_categoria;
					filme.nome_genero		= f.nome_genero;
					filme.sinopse			= f.sinopse;
					break;
				}
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return filme;
	}
	private GENERO getGeneroFromTable()
	{
		GENERO gen = new GENERO();
		try
		{
			gen.id_genero = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			gen.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return gen;
	}
	private CLIENTES getClientesFromTable()
	{
		CLIENTES gen = new CLIENTES();
		try
		{
			gen.id_clientes = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			gen.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
			gen.endereco = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),2);
			gen.cpf = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),3);
			gen.cep = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),4);
			gen.fone = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),5);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return gen;
	}
	private int getIdPdLocacaoFromTable()
	{
		try
		{
			return  (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return -1;
		}
	}
	private RESERVA getReservaFromTable()
	{
		RESERVA gen = new RESERVA();
		try
		{
			gen.id_reserva = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			gen.situacao = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
			gen.cliente.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),3);
			gen.cliente.fone = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),5);
			gen.cliente.cpf = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),6);
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return gen;
	}
	private PROMOCAO getPromocaoFromTable()
	{
		PROMOCAO gen = new PROMOCAO();
		try
		{
			gen.id_promocao = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			gen.nome = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
			gen.preco = (Double)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),3);
			int size = lsPromocao.size();
			for(int i=0; i< size; i++)
			{
				PROMOCAO temp = lsPromocao.get(i);
				if(gen.id_promocao == temp.id_promocao)
				{
					gen.id_categoria 	= temp.id_categoria;
					gen.qtde_dias		= temp.qtde_dias;
					gen.qtde_filmes		= temp.qtde_filmes;
					gen.qtde_final		= temp.qtde_final;
					gen.qtde_inicial	= temp.qtde_inicial;
					gen.nome_categoria	= temp.nome_categoria;
					break;
				}
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(this,"Nenhum registro foi selecionado.");
			return null;
		}
		return gen;
	}
	private void verificaSinopse()
	{
		try
		{	//int out = table.getSelectionModel().getLeadSelectionIndex();
			int out2 = table.getColumnModel().getSelectionModel().getLeadSelectionIndex();
			if(out2==6)
			{
				String titulo = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),1);
				String sinopse = (String)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),6);
				frmSinopse.getInstance(titulo, sinopse);
			}
		}
		catch(Exception exc)
		{}
	}
	public void onPressed_btnSelecionar()
	{
		
		try
		{
			if(tipoForm == BUSCA_FILMES_PARA_LOCACAO)
			{
				Integer id_filme = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
				int size = lsFilmes.size();
				for(int i = 0 ; i < size; i++)
				{
					FILMES filme = lsFilmes.get(i);
					if(id_filme == filme.id_filmes)
					{
						frmPedidoLocacao.retornaFilmeSelecionado(filme);
						this.frame.dispose();
						break;
					}
				}
			}
			else if(tipoForm == BUSCA_CLIENTES_PARA_LOCACAO)
			{
				Integer id_cliente = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
				int size = lsClientes.size();
				for(int i = 0 ; i < size; i++)
				{
					CLIENTES cliente = lsClientes.get(i);
					if(id_cliente == cliente.id_clientes)
					{
						frmPedidoLocacao.retornaClienteSelecionado(cliente);
						this.frame.dispose();
						break;
					}
				}
			}
			else if(tipoForm == BUSCA_CLIENTES_PARA_RESERVA)
			{
				Integer id_cliente = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
				int size = lsClientes.size();
				for(int i = 0 ; i < size; i++)
				{
					CLIENTES cliente = lsClientes.get(i);
					if(id_cliente == cliente.id_clientes)
					{
						frmReserva.retornaClienteSelecionado(cliente);
						this.frame.dispose();
						break;
					}
				}
			}
			else if(tipoForm == BUSCA_FILMES_PARA_RESERVAS)
			{
				Integer id_filme = (Integer)model.getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
				frmReserva.retornaFilmeSelecionado(id_filme);
				this.frame.dispose();
			}
		}
		catch(Exception exc)
		{}
	}
	public void onPressed_btnEditar()
	{
		foiPressionadoEditar = true;
		switch(tipoForm)
		{
			case BUSCA_FILMES:
			case BUSCA_FILMES_PARA_LOCACAO:
				FILMES filme = getFilmeFromTable(); 
				if(filme!=null)
					frmCadastroFilmes.getInstance(filme);
			break;
			case BUSCA_CATEGORIA_FILMES:
				CATEGORIAFILMES cf = getCategoriaFromTable(); 
				if(cf!=null)
					frmCategoria.getInstance(cf);
			break;
			case BUSCA_GENEROS_FILMES:
				GENERO gen = getGeneroFromTable(); 
				if(gen!=null)
					frmGenero.getInstance(gen);
			break;
			case BUSCA_CLIENTES:
			case BUSCA_CLIENTES_PARA_LOCACAO:
			case BUSCA_CLIENTES_PARA_RESERVA:
				CLIENTES clientes = getClientesFromTable(); 
				if(clientes!=null)
					frmCadastroCliente.getInstance(clientes);
			break;
			case BUSCA_LOCACAO://editar vira consultar
				int id_pd_locacao = getIdPdLocacaoFromTable();
				frmPedidoLocacao.getInstance(id_pd_locacao);
			break;
			case BUSCA_PROMOCAO:
				PROMOCAO pr = getPromocaoFromTable(); 
				if(pr!=null)
					frmPromocao.getInstance(pr);
			break;
			case BUSCA_DEVOLUCAO:
				frmDevolucaoPagamento.getInstance();
			break;
			case BUSCA_RESERVA:
				RESERVA reserva = getReservaFromTable();
				if(reserva!=null)
					frmReserva.getInstance(reserva);
			break;
		}
	}
}
