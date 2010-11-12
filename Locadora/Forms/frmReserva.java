package Forms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import Locadora.UTIL;

public class frmReserva extends JPanel implements ActionListener,TableModelListener,FocusListener
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblIdReserva;
	private JLabel lblCodCliente;
	private JTextField txtIdCliente;
	private JLabel lblEnderecoFixo;
	private JLabel lblEndereco;
	private JLabel lblNomeFixo;
	private JLabel lblNome;
	private JLabel lblCpfFixo;
	private JLabel lblCpf;
	private JLabel lblCepFixo;
	private JLabel lblCep;
	private JLabel lblFilmes;
	private JLabel lblSituacao;
	private JComboBox cmbSituacao;
	private JTable table;
	private JButton btnPesquisarFilmes;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private JButton btnPesquisarCodCliente;
	private JFrame frame;
	
	private static frmReserva instance = null;
	
	public static frmReserva getInstance()
	{
		if(instance==null)
			instance = new frmReserva();
		instance.frame.requestFocus();
		return instance;
	}

	private frmReserva() 
	{
		String[] cmbSituacaoItems = { "Item 1", "Item 2", "Item 3" };
		lblCodigo = new JLabel("C�digo");
		lblIdReserva = new JLabel("15");
		lblCodCliente = new JLabel("C�d Cliente");
		txtIdCliente = new JTextField(5);
		btnPesquisarCodCliente = new JButton(".");
		lblEnderecoFixo = new JLabel("Endere�o");
		lblEndereco = new JLabel("dsfjsdafhbsajfhsabchjsa");
		lblNomeFixo = new JLabel("Nome");
		lblNome = new JLabel("ajsbsajchschshfcs");
		lblCpfFixo = new JLabel("CPF");
		lblCpf = new JLabel("12121245154");
		lblCepFixo = new JLabel("CEP");
		lblCep = new JLabel("2154545");
		lblFilmes = new JLabel("Filmes");
		table = new JTable(9,2);
		btnPesquisarFilmes = new JButton("p");
		btnCancelar = new JButton("Cancelar");
		btnConfirmar = new JButton("Confirmar");
		lblSituacao = new JLabel("Situa��o");
		cmbSituacao = new JComboBox(cmbSituacaoItems);
		
		table.getColumnModel().getColumn(0).setHeaderValue("C�digo");
		table.getColumnModel().getColumn(1).setHeaderValue("Nome");
		table.setPreferredScrollableViewportSize(new Dimension(490, 50));
		
        table.setFillsViewportHeight(true);
        table.setSelectionMode(WHEN_FOCUSED);
        JScrollPane jp = new JScrollPane(table);  
        jp.setViewportView(table);  
        jp.setAutoscrolls(true);  
        jp.setEnabled(true);
        jp.setWheelScrollingEnabled(true);
        table.setAutoscrolls(true);
        table.getTableHeader().setBounds(15, 175, 490,15);
        table.getColumnModel().getColumn(0).setMaxWidth(70);
        add(jp);
		add(table);
		add(table.getTableHeader());
		table.getModel().addTableModelListener(this);
		table.addFocusListener(this);
		table.setOpaque(true);

		setPreferredSize(new Dimension(461, 312));
		setLayout(null);
		
		btnCancelar.addActionListener(this);
		btnConfirmar.addActionListener(this);
		btnPesquisarCodCliente.addActionListener(this);
		btnPesquisarFilmes.addActionListener(this);

		add(lblCodigo);
		add(lblIdReserva);
		add(lblCodCliente);
		add(txtIdCliente);
		add(btnPesquisarCodCliente);
		add(lblEnderecoFixo);
		add(lblEndereco);
		add(lblNomeFixo);
		add(lblNome);
		add(lblCpfFixo);
		add(lblCpf);
		add(lblCepFixo);
		add(lblCep);
		add(lblFilmes);
		add(btnPesquisarFilmes);
		add(btnCancelar);
		add(btnConfirmar);
		add(lblSituacao);
		add(cmbSituacao);

		lblCodigo.setBounds(15, 15, 50, 25);
		lblIdReserva.setBounds(65, 15, 70, 25);
		lblCodCliente.setBounds(15, 45, 75, 25);
		txtIdCliente.setBounds(90, 45, 60, 25);
		btnPesquisarCodCliente.setBounds(155, 45, 30, 25);
		lblEnderecoFixo.setBounds(15, 110, 65, 25);
		lblEndereco.setBounds(85, 110, 225, 25);
		lblNomeFixo.setBounds(15, 75, 45, 25);
		lblNome.setBounds(85, 80, 230, 25);
		lblCpfFixo.setBounds(325, 80, 30, 25);
		lblCpf.setBounds(360, 80, 95, 25);
		lblCepFixo.setBounds(325, 110, 30, 25);
		lblCep.setBounds(360, 110, 95, 25);
		lblFilmes.setBounds(15, 145, 60, 25);
		table.setBounds(15, 190, 425, 80);
		btnPesquisarFilmes.setBounds(80, 145, 30, 25);
		btnConfirmar.setBounds(85, 280, 100, 25);
		btnCancelar.setBounds(255, 280, 100, 25);
		lblSituacao.setBounds(140, 15, 65, 25);
		cmbSituacao.setBounds(210, 15, 150, 25);
		frmShow();
	}

	private void frmShow() 
	{
		frame = new JFrame("CONTROLE DE RESERVA");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setVisible(true);
		UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				frmReserva.instance=null;
			}  
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == btnCancelar)
			onPressed_btnCancelar();
		else if(e.getSource() == btnConfirmar)
			onPressed_btnConfirmar();
		else if(e.getSource() == btnPesquisarCodCliente)
			onPressed_btnPesquisarCodCliente();
		else if(e.getSource() == btnPesquisarFilmes)
			onPressed_btnPesquisarFilmes();
	}
	private void onPressed_btnCancelar()
	{
		frame.dispose();
	}
	private void onPressed_btnConfirmar()
	{
		System.out.println("onPressed_btnConfirmar");
	}
	private void onPressed_btnPesquisarCodCliente()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_CLIENTES);
	}
	private void onPressed_btnPesquisarFilmes()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_FILMES);
	}
	@Override
	public void tableChanged(TableModelEvent e) 
	{
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        //String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        System.out.println(data);
	}
	@Override
	public void focusGained(FocusEvent arg0) 
	{
		System.out.println("Focus");
	}
	@Override
	public void focusLost(FocusEvent arg0) 
	{
		System.out.println("Lost Focus");
	}
}
