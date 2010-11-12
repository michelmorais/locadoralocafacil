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
import Locadora.CLIENTES;
import Locadora.CPF;
import Locadora.UTIL;

public class frmCadastroCliente extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCliente;
	private JLabel lblIdCliente;
	private JLabel lblNome;
	private JTextField txtNome;
	private JLabel lblEndereco;
	private JTextField txtEndereco;
	private JLabel lblCpf;
	private JTextField txtCpf;
	private JLabel lblCep;
	private JTextField txtCep;
	private JLabel lblTelefone;
	private JTextField txtTelefone;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private JFrame frame;
	private String strMessage;
	private CLIENTES cliente; 
	
	private static frmCadastroCliente instance = null;
	public static frmCadastroCliente getInstance()
	{
		if(instance==null)
			instance = new frmCadastroCliente();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmCadastroCliente getInstance(CLIENTES cliente)
	{
		if(instance==null)
			instance = new frmCadastroCliente();
		instance.frame.requestFocus();
		instance.cliente = cliente;
		instance.txtNome.setText(cliente.nome);
		instance.txtCep.setText(cliente.cep);
		instance.txtEndereco.setText(cliente.endereco);
		instance.txtCpf.setText(cliente.cpf);
		instance.txtTelefone.setText(cliente.fone);
		instance.lblIdCliente.setText(String.valueOf(cliente.id_clientes));
		return instance;
	}

	private frmCadastroCliente() 
	{
		cliente = new CLIENTES();
		cliente.id_clientes = FireBird.getInstance().getId("clientes");
		lblCliente = new JLabel("Cod Cliente");
		lblIdCliente = new JLabel(String.valueOf(cliente.id_clientes));
		lblNome = new JLabel("Nome");
		txtNome = new JTextField(5);
		
		
		lblEndereco = new JLabel("Endereço");
		txtEndereco = new JTextField(5);
		lblCpf = new JLabel("CPF");
		txtCpf = new JTextField(5);
		lblCep = new JLabel("CEP");
		txtCep = new JTextField(5);
		lblTelefone = new JLabel("Telefone");
		txtTelefone = new JTextField(5);
		btnCancelar = new JButton("Cancelar");
		btnConfirmar = new JButton("Confirmar");

		setPreferredSize(new Dimension(569, 200));
		setLayout(null);
		btnConfirmar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		add(lblCliente);
		add(lblIdCliente);
		add(lblNome);
		add(txtNome);
		add(lblEndereco);
		add(txtEndereco);
		add(lblCpf);
		add(txtCpf);
		add(lblCep);
		add(txtCep);
		add(lblTelefone);
		add(txtTelefone);
		add(btnCancelar);
		add(btnConfirmar);

		lblCliente.setBounds(15, 10, 75, 25);
		lblIdCliente.setBounds(85, 10, 100, 25);
		lblNome.setBounds(15, 40, 65, 25);
		txtNome.setBounds(85, 40, 295, 25);
		lblEndereco.setBounds(15, 75, 65, 25);
		txtEndereco.setBounds(85, 75, 295, 25);
		lblCpf.setBounds(400, 40, 30, 25);
		txtCpf.setBounds(430, 40, 130, 25);
		lblCep.setBounds(400, 75, 30, 25);
		txtCep.setBounds(430, 75, 130, 25);
		lblTelefone.setBounds(15, 115, 55, 25);
		txtTelefone.setBounds(85, 115, 110, 25);
		btnConfirmar.setBounds(125, 160, 100, 25);
		btnCancelar.setBounds(325, 160, 100, 25);
		frmShow();
	}

	private void frmShow() 
	{
		frame = new JFrame("CADASTRO DE CLIENTES");
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
				frmCadastroCliente.instance=null;
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
			cliente.nome		= UTIL.getMaxString(txtNome,100);
			cliente.cep			= UTIL.getMaxString(txtCep,15);
			cliente.cpf			= CPF.getFormatCPF(UTIL.getMaxString(txtCpf,14));
			cliente.endereco	= UTIL.getMaxString(txtEndereco,200);
			cliente.fone		= UTIL.getMaxString(txtTelefone,15);
			if(FireBird.getInstance().insertOrUpdate(cliente))
			{
				frame.dispose();
				frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_CLIENTES);
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
		if(txtCpf.getText().isEmpty())
		{
			strMessage = "Campo 'CPF' não informado.";
			return false;
		}
		if(!CPF.ehValido(txtCpf.getText()))
		{
			strMessage = "CPF  inválido.";
			return false;
		}
		if(txtEndereco.getText().isEmpty())
		{
			strMessage = "Campo 'endereço' não informado.";
			return false;
		}
		if(txtTelefone.getText().isEmpty())
		{
			strMessage = "Campo 'telefone' não informado.";
			return false;
		}
		return true;
	}
}
