package Forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Banco.FireBird;
import Locadora.CATEGORIAFILMES;
import Locadora.UTIL;

public class frmCategoria extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblPreco;
	private JTextField txtPreco;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JLabel lblIdCategoria;
	private JFrame frame;
	private String strMessage;
	private CATEGORIAFILMES cf;

	private static frmCategoria instance = null;

	public static frmCategoria getInstance() 
	{
		if (instance == null)
			instance = new frmCategoria();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmCategoria getInstance(CATEGORIAFILMES oldCf) 
	{
		if (instance == null)
			instance = new frmCategoria();
		instance.frame.requestFocus();
		instance.cf = oldCf;
		instance.lblIdCategoria.setText(String.valueOf(oldCf.id_categoria));
		instance.txtNome.setText(oldCf.nome);
		instance.txtPreco.setText(String.valueOf(oldCf.preco));
		return instance;
	}

	private frmCategoria() 
	{
		cf = new CATEGORIAFILMES();
		cf.id_categoria = FireBird.getInstance().getId("categoria");
		lblCodigo = new JLabel("Código");
		lblNome = new JLabel("Nome");
		txtNome = new JTextField(5);
		lblPreco = new JLabel("Preço");
		txtPreco = new JTextField(5);
		btnConfirmar = new JButton("Confirmar");
		btnCancelar = new JButton("Cancelar");
		lblIdCategoria = new JLabel(String.valueOf(cf.id_categoria));
		btnConfirmar.setToolTipText("Clique aqui para confirmar!");
		btnCancelar.setToolTipText("Clique aqui para cancelar!");
		btnConfirmar.addActionListener(this);
		btnCancelar.addActionListener(this);

		setPreferredSize(new Dimension(409, 190));
		setLayout(null);

		add(lblCodigo);
		add(lblNome);
		add(txtNome);
		add(lblPreco);
		add(txtPreco);
		add(btnConfirmar);
		add(btnCancelar);
		add(lblIdCategoria);

		lblCodigo.setBounds(15, 20, 45, 25);
		lblNome.setBounds(15, 55, 50, 25);
		txtNome.setBounds(65, 55, 325, 25);
		lblPreco.setBounds(15, 85, 50, 25);
		txtPreco.setBounds(65, 85, 100, 25);
		btnCancelar.setBounds(235, 125, 100, 25);
		btnConfirmar.setBounds(115, 125, 100, 25);
		lblIdCategoria.setBounds(65, 20, 100, 25);
		lblNome
				.setToolTipText("Insira o nome da categoria na caixa de dialogo.");
		txtNome.setToolTipText("Insira o nome da categoria!");
		frmShow("CADASTRO DE CATEGORIA DE FILMES");
	}

	private void frmShow(String nome) 
	{
		frame = new JFrame(nome);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		UTIL.setCenterScreen(frame);
		//frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosed(WindowEvent e)
{
				frmCategoria.instance = null;
				
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnConfirmar)
			onPressed_btnConfirmar();
		else if (e.getSource() == btnCancelar)
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
			cf.nome = UTIL.getMaxString(txtNome,100);
			cf.preco = Double.parseDouble(txtPreco.getText().replace(",","."));
			if(FireBird.getInstance().insertOrUpdate(cf))
			{
				frame.dispose();
				frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_CATEGORIA_FILMES);
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
		if(txtPreco.getText().isEmpty())
		{
			strMessage = "Campo 'preço' não informado.";
			return false;
		}	
		if(!UTIL.isNumericDouble(txtPreco.getText(),0))
		{
			strMessage = "Campo 'preço' não é numérico ou não é válido.";
			return false;
		}	
		return true;
	}
}
