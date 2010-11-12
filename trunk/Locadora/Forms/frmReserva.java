package Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Banco.FireBird;
import Locadora.CLIENTES;
import Locadora.FILMES;
import Locadora.RESERVA;
import Locadora.UTIL;

public class frmReserva extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblIdReserva;
	private JLabel lblCodCliente;
	private JLabel lblIdCliente;
	private JLabel lblEnderecoFixo;
	private JLabel lblEndereco;
	private JLabel lblNomeFixo;
	private JLabel lblNome;
	private JLabel lblCpfFixo;
	private JLabel lblCpf;
	private JLabel lblCepFixo;
	private JLabel lblCep;
	private JLabel lblFilmes;
	private JTextField txtIdFilmes;
	private JButton btnAcrescentarFilme;
	private JButton btnExcluirFilme;
	private JLabel lblSituacao;
	private JComboBox cmbSituacao;
	private JTable table;
	private JButton btnPesquisarFilmes;
	private JButton btnCancelar;
	private JButton btnConfirmar;
	private JButton btnPesquisarCodCliente;
	private JFrame frame;
	private RESERVA reserva;
	
	private static frmReserva instance = null;
	
	public static frmReserva getInstance()
	{
		if(instance==null)
			instance = new frmReserva();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmReserva getInstance(RESERVA reserva)
	{
		if(instance==null)
			instance = new frmReserva();
		instance.frame.requestFocus();
		instance.reserva = reserva;
		instance.setCliente(reserva.cliente);
		instance.showValues();
		return instance;
	}
	public static frmReserva retornaClienteSelecionado(CLIENTES cliente)
	{
		if(instance==null)
			instance = new frmReserva();
		instance.frame.requestFocus();
		instance.setCliente(cliente);
		return instance;
	}
	public static frmReserva retornaFilmeSelecionado(int id_filmes)
	{
		if(instance==null)
			instance = new frmReserva();
		instance.frame.requestFocus();
		instance.txtIdFilmes.setText(String.valueOf(id_filmes));
		return instance;
	}
	
	
	private void setCliente(CLIENTES cliente_)
	{
		if(cliente_==null)
			return;
		reserva.cliente = cliente_;
		lblIdCliente.setText(String.valueOf(reserva.cliente.id_clientes));
		lblNome.setText(reserva.cliente.nome);
		lblCpf.setText(reserva.cliente.cpf);
		lblCep.setText(reserva.cliente.cep);
		lblEndereco.setText(reserva.cliente.endereco);
	}

	private frmReserva() 
	{
		reserva = new RESERVA();
		reserva.id_reserva = FireBird.getInstance().getId("reserva");
		String[] cmbSituacaoItems = { "Aguardando", "Locado", "Concluido" };
		lblCodigo = new JLabel("Cod");
		lblIdReserva = new JLabel(String.valueOf(reserva.id_reserva));
		lblCodCliente = new JLabel("C�d Cliente");
		lblIdCliente = new JLabel();
		btnPesquisarCodCliente = new JButton("...");
		lblEnderecoFixo = new JLabel("Endere�o");
		lblEndereco = new JLabel();
		lblNomeFixo = new JLabel("Nome");
		lblNome = new JLabel();
		lblCpfFixo = new JLabel("CPF");
		lblCpf = new JLabel();
		lblCepFixo = new JLabel("CEP");
		lblCep = new JLabel();
		lblFilmes = new JLabel("Filmes");
		txtIdFilmes = new JTextField(5);
		btnAcrescentarFilme = new JButton("+");
		btnExcluirFilme = new JButton("-");
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
		add(lblIdCliente);
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
		add(txtIdFilmes);
		add(btnPesquisarFilmes);
		add(btnCancelar);
		add(btnConfirmar);
		add(lblSituacao);
		add(cmbSituacao);
		add(btnAcrescentarFilme);
		add(btnExcluirFilme);
		
		btnExcluirFilme.addActionListener(this);
		btnAcrescentarFilme.addActionListener(this);

		lblCodigo.setBounds(15, 15, 50, 25);
		lblIdReserva.setBounds(65, 15, 70, 25);
		lblCodCliente.setBounds(15, 45, 75, 25);
		lblIdCliente.setBounds(90, 45, 60, 25);
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
		txtIdFilmes.setBounds(65, 145, 60, 25);
		btnPesquisarFilmes.setBounds(130, 145, 30, 25);
		btnAcrescentarFilme.setBounds(165, 145, 45, 25);
		btnExcluirFilme.setBounds(215, 145, 45, 25);
		btnExcluirFilme.setForeground(new Color(255,0,0));
		btnAcrescentarFilme.setForeground(new Color(255,0,0));
		
		
		table.setBounds(15, 190, 425, 80);
		
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
		else if(e.getSource() == btnAcrescentarFilme)
			onPressed_AcrescentaFilmes();
		else if(e.getSource() == btnExcluirFilme)
			onPressed_ExcluirFilmes();
	}
	private void onPressed_AcrescentaFilmes()
	{
		
		if(reserva.filmes.size()>=5)
		{
			JOptionPane.showMessageDialog(this,"Sua lista de filmes est� cheia!");
			return;
		}
		if(lblNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Cliente n�o selecionado.");
			return;
		}
		int id_filme = UTIL.getInt(txtIdFilmes.getText(),-1);
		if(id_filme == -1)
		{
			JOptionPane.showMessageDialog(null,"Filme n�o encontrado.");
			return;
		}
		if (!FireBird.getInstance().existe_filme(id_filme))
		{
			JOptionPane.showMessageDialog(null,"Filme n�o encontrado.");
			return;
		}
		FILMES filme = FireBird.getInstance().selectBuscaFilme(id_filme);
		if(filme==null)
		{
			JOptionPane.showMessageDialog(null,"Filme n�o encontrado.");
			return;
		}
		reserva.filmes.add(filme);
		showValues();
		txtIdFilmes.setText("");
	}
	private void onPressed_ExcluirFilmes()
	{
		try
		{
			int id = (Integer)table.getModel().getValueAt(table.getSelectionModel().getLeadSelectionIndex(),0);
			FILMES filme = FireBird.getInstance().selectBuscaFilme(id);
			int size = reserva.filmes.size();
			for(int i=0; i<size;i++)
			{
				FILMES f = reserva.filmes.get(i);
				if(filme.id_filmes == f.id_filmes)
				{
					reserva.filmes.remove(i);
					break;
				}
			}
			//if(!FireBird.getInstance().excluir(id_pd_itens_pd_locacao, "itens_pd_locacao"))
			//	JOptionPane.showMessageDialog(null, "erro id_pd_itens_pd_locacao " + id_pd_itens_pd_locacao);
		}
		catch(Exception exc)
		{
			JOptionPane.showMessageDialog(null,"Filme n�o selecionado.");
		}
		showValues();
	}
	private void showValues()
	{
		int size = reserva.filmes.size();
		int rows = table.getRowCount(),totalCol = 2;
		for(int count = 0; count < rows; count++)
		{
			for(int i=0; i < totalCol;i++)
				table.getModel().setValueAt("", count, i);
		}
		for(int i=0;i <size;i++)
		{
			int id_filmes  	= reserva.filmes.get(i).id_filmes;
			String nome  	= reserva.filmes.get(i).nome;
			table.getModel().setValueAt(id_filmes, i, 0);
			table.getModel().setValueAt(nome, i, 1);
		}
	}
	private void onPressed_btnCancelar()
	{
		frame.dispose();
	}
	private void onPressed_btnConfirmar()
	{
		if(lblNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null,"Cliente n�o selecionado.");
			return;
		}
		if(reserva.filmes.size()==0)
		{
			JOptionPane.showMessageDialog(this,"Sua lista de filmes est� vazia!");
			return;
		}
		int i = cmbSituacao.getSelectedIndex();
		String situacao = (String) cmbSituacao.getItemAt(i);
		reserva.situacao = situacao.toUpperCase();
		if(FireBird.getInstance().insertOrUpdate(reserva))
			this.frame.dispose();
	}
	private void onPressed_btnPesquisarCodCliente()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_CLIENTES_PARA_RESERVA);
	}
	private void onPressed_btnPesquisarFilmes()
	{
		frmBuscaGenerica.getInstance(frmBuscaGenerica.BUSCA_FILMES_PARA_RESERVAS);
	}
}
