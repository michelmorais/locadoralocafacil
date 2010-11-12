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
import Locadora.GENERO;
import Locadora.UTIL;

public class frmGenero extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JTextField txtNome;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JLabel lblIdGenero;
	private JFrame frame;
	private String strMessage;
	private GENERO gen;

	private static frmGenero instance = null;
	
	public static frmGenero getInstance()
	{
		if(instance==null)
			instance = new frmGenero();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmGenero getInstance(GENERO gen)
	{
		if(instance==null)
			instance = new frmGenero();
		instance.frame.requestFocus();
		instance.gen = gen;
		instance.lblIdGenero.setText(String.valueOf(gen.id_genero));
		instance.txtNome.setText(gen.nome);
		return instance;
	}
	
	private frmGenero() 
	{
		gen = new GENERO();
		gen.id_genero = FireBird.getInstance().getId("genero");
		lblCodigo = new JLabel("Código");
		lblNome = new JLabel("Nome");
		txtNome = new JTextField(5);
		btnConfirmar = new JButton("Confirmar");
		btnCancelar = new JButton("Cancelar");
		lblIdGenero = new JLabel(String.valueOf(gen.id_genero));
		btnConfirmar.setToolTipText("Clique aqui para confirmar!");
		btnCancelar.setToolTipText("Clique aqui para cancelar!");
		btnConfirmar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		setPreferredSize(new Dimension(409, 138));
		setLayout(null);

		add(lblCodigo);
		add(lblNome);
		add(txtNome);
		add(btnConfirmar);
		add(btnCancelar);
		add(lblIdGenero);

		lblCodigo.setBounds(15, 20, 100, 25);
		lblNome.setBounds(15, 55, 50, 25);
		txtNome.setBounds(65, 55, 325, 25);
		btnCancelar.setBounds(235, 95, 100, 25);
		btnConfirmar.setBounds(115, 95, 100, 25);
		lblIdGenero.setBounds(65, 20, 100, 25);
		lblNome.setToolTipText("Insira o nome da gênero na caixa de dialogo");
		txtNome.setToolTipText("Insira o nome da gênero do filme aqui!");
		frmShow();
	}
	private void frmShow() 
	{
		frame = new JFrame("CADASTRO DE GÊNERO");
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
				frmGenero.instance = null;
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
			gen.nome = UTIL.getMaxString(txtNome,50);
			if(FireBird.getInstance().insertOrUpdate(gen))
			{
				frame.dispose();
				frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_GENEROS_FILMES);
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
		return true;
	}
}
