package Forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Banco.FireBird;
import Locadora.CATEGORIAFILMES;
import Locadora.FILMES;
import Locadora.GENERO;
import Locadora.UTIL;

public class frmCadastroFilmes extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblGenero;
	private JComboBox cmbGenero;
	private JTextArea txtSinopse;
	private JLabel lblSinopse;
	private JTextField txtQuantidade;
	private JLabel lblQuantidade;
	private JLabel lblCategoria;
	private JComboBox cmbCategoria;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblQuantidadeDisponivel;
	private JLabel lblQtdeDisponivel;
	private JLabel lblIdFilmes;
	private JFrame frame;
	private String strMessage;
	private FILMES filmes;
	private ArrayList<GENERO> generos;
	private ArrayList<CATEGORIAFILMES> categorias;
	
	private static frmCadastroFilmes instance = null;
	public static frmCadastroFilmes getInstance()
	{
		if(instance==null)
			instance = new frmCadastroFilmes();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmCadastroFilmes getInstance(FILMES filme)
	{
		if(instance==null)
			instance = new frmCadastroFilmes();
		instance.frame.requestFocus();
		instance.filmes = filme;
		instance.txtNome.setText(filme.nome);
		instance.txtQuantidade.setText(String.valueOf(filme.qtde));
		instance.lblIdFilmes.setText(String.valueOf(filme.id_filmes));
		int qtde_disponicel = FireBird.getInstance().getQtdeDisponivel(filme.id_filmes);
		instance.lblQtdeDisponivel.setText(String.valueOf(qtde_disponicel));
		instance.txtSinopse.setText(filme.sinopse);
		//(String)cmb.getSelectedItem();
		int size = instance.cmbGenero.getItemCount();
		for(int i =0; i < size ;i++)
		{
			String n = (String) instance.cmbGenero.getItemAt(i);
			if(n.compareToIgnoreCase(filme.nome_genero)==0)
			{
				instance.cmbGenero.setSelectedIndex(i);
				break;
			}
		}
		size = instance.cmbCategoria.getItemCount();
		for(int i =0; i < size ;i++)
		{
			String n = (String) instance.cmbCategoria.getItemAt(i);
			if(n.compareToIgnoreCase(filme.nome_categoria)==0)
			{
				instance.cmbCategoria.setSelectedIndex(i);
				break;
			}
		}
		return instance;
	}

	private frmCadastroFilmes() 
	{
		filmes = new FILMES();
		filmes.id_filmes = FireBird.getInstance().getId("filmes");
		
		lblCodigo = new JLabel("Código");
		lblGenero = new JLabel("Gênero");
		
		cmbGenero = new JComboBox();
		generos = new ArrayList<GENERO>();
		generos = FireBird.getInstance().selectBuscaGenero("","");
		int size = generos.size();
		for(int i =0; i < size ;i++)
		{
			GENERO gen = generos.get(i);
			cmbGenero.addItem(gen.nome);
		}
		
		cmbCategoria = new JComboBox();
		categorias = new ArrayList<CATEGORIAFILMES>();
		categorias = FireBird.getInstance().selectBuscaCategoria("","");
		size = categorias.size();
		for(int i =0; i < size ;i++)
		{
			CATEGORIAFILMES categoria = categorias.get(i);
			cmbCategoria.addItem(categoria.nome);
		}
		txtSinopse = new JTextArea(5, 5);
		lblSinopse = new JLabel("Sinópse");
		txtQuantidade = new JTextField(5);
		lblQuantidade = new JLabel("Quantidade");
		lblCategoria = new JLabel("Categoria");
		
		btnConfirmar = new JButton("Confirmar");
		btnCancelar = new JButton("Cancelar");
		lblNome = new JLabel("Nome");
		txtNome = new JTextField(5);
		lblQuantidadeDisponivel = new JLabel("Quantidade Disponivel");
		lblQtdeDisponivel = new JLabel("0");
		lblIdFilmes = new JLabel(String.valueOf(filmes.id_filmes));
		
		btnCancelar.addActionListener(this);
		btnConfirmar.addActionListener(this);
		cmbGenero.setToolTipText("Escolha a categoria do filme desejado");
		txtSinopse.setToolTipText("Sinópse do filme, não obrigatório!");
		lblSinopse.setToolTipText("Sinópse do filme, não obrigatório!");
		txtQuantidade
				.setToolTipText("Insira aqui a quantidade total de filmes.");
		cmbCategoria.setToolTipText("Escolha o tipo do filme");
		btnConfirmar.setToolTipText("Pressione aqui para confirmar o filme!");
		btnCancelar.setToolTipText("Pressione aqui para retornar!");

		setPreferredSize(new Dimension(393, 433));
		setLayout(null);

		add(lblCodigo);
		add(lblGenero);
		add(cmbGenero);
		add(txtSinopse);
		add(lblSinopse);
		add(txtQuantidade);
		add(lblQuantidade);
		add(lblCategoria);
		add(cmbCategoria);
		add(btnConfirmar);
		add(btnCancelar);
		add(lblNome);
		add(txtNome);
		add(lblQuantidadeDisponivel);
		add(lblQtdeDisponivel);
		add(lblIdFilmes);

		lblCodigo.setBounds(15, 25, 70, 25);
		lblGenero.setBounds(15, 135, 70, 25);
		cmbGenero.setBounds(90, 130, 285, 25);
		txtSinopse.setBounds(15, 240, 365, 135);
		lblSinopse.setBounds(15, 215, 100, 30);
		txtQuantidade.setBounds(90, 90, 60, 25);
		lblQuantidade.setBounds(15, 90, 75, 25);
		lblCategoria.setBounds(15, 175, 70, 25);
		cmbCategoria.setBounds(90, 175, 285, 25);
		btnCancelar.setBounds(270, 390, 110, 25);
		btnConfirmar.setBounds(15, 390, 110, 25);
		lblNome.setBounds(15, 55, 70, 25);
		txtNome.setBounds(90, 55, 265, 25);
		lblQuantidadeDisponivel.setBounds(170, 90, 140, 25);
		lblQtdeDisponivel.setBounds(300, 90, 100, 25);
		lblIdFilmes.setBounds(90, 25, 100, 25);
		frmShow();
	}

	private void frmShow() 
	{
		frame = new JFrame("CADASTRO DE FILMES");
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
				frmCadastroFilmes.instance=null;
			}  
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==btnConfirmar)
			onPressed_btnConfirmar();
		else if(e.getSource()==btnCancelar)
			onPressed_btnCancelar();
	}
	private void onPressed_btnCancelar()
	{
		frame.dispose();
	}
	private void onPressed_btnConfirmar()
	{
		if(!CamposValidos())
			JOptionPane.showMessageDialog(null,strMessage);
		else
		{
			filmes.nome 			= UTIL.getMaxString(txtNome,100);
			filmes.qtde				= Integer.parseInt(txtQuantidade.getText());
			filmes.qtde_disponivel 	= filmes.qtde;
			filmes.sinopse			= UTIL.getMaxString(txtSinopse,500);
			
			int size = generos.size();
			for(int i =0; i < size ;i++)
			{
				GENERO gen = generos.get(i);
				if(gen.nome == cmbGenero.getSelectedItem())
				{
					filmes.id_genero = gen.id_genero;
					break;
				}
			}
			size = categorias.size();
			for(int i =0; i < size ;i++)
			{
				CATEGORIAFILMES categoria = categorias.get(i);
				if(categoria.nome == cmbCategoria.getSelectedItem())
				{
					filmes.id_categoria = categoria.id_categoria;
					break;
				}
			}
			if(FireBird.getInstance().insertOrUpdate(filmes))
			{
				frame.dispose();
				frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_FILMES);
			}
		}
	}
	private boolean CamposValidos()
	{
		if(txtNome.getText().isEmpty())
		{
			strMessage = "Campo 'nome' não informado.";
			return false;
		}
		if(txtQuantidade.getText().isEmpty())
		{
			strMessage = "Campo 'quantidade' não informado.";
			return false;
		}
		if(!UTIL.isNumericInt(txtQuantidade.getText()))
		{
			strMessage = "Campo 'quantidade' somente números.";
			return false;
		}
		return true;
	}
}
