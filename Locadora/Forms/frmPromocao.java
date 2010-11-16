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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Banco.FireBird;
import Locadora.CATEGORIAFILMES;
import Locadora.PROMOCAO;
import Locadora.UTIL;

public class frmPromocao extends JPanel implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	private JLabel lblCodigo;
	private JLabel lblIdPromocao;
	private JLabel lblNome;
	private JLabel lblPreco;
	private JLabel lblQuantidadeFilmesDe;
	private JLabel lblQuantidadeFilmesAte;
	private JLabel lblQuantidadeDias;
	private JLabel lblCategoria;
	private JTextField txtNome;
	private JTextField txtPreco;
	private JSpinner nudQuantidadeFilmesDe;
	private JSpinner nudQuantidadeFilmesAte;
	private JSpinner nudQuantidadeDias;
	private JButton btnConfirmar;
	private JButton btnCancelar;
	private JFrame frame;
	private JComboBox cmbCategoria;
	private String strMessage;
	private PROMOCAO prom;
	private ArrayList<CATEGORIAFILMES> lsCategoria; 

	private static frmPromocao instance = null;

	public static frmPromocao getInstance() 
	{
		if (instance == null)
			instance = new frmPromocao();
		instance.frame.requestFocus();
		return instance;
	}
	public static frmPromocao getInstance(PROMOCAO promocao) 
	{
		if (instance == null)
			instance = new frmPromocao();
		instance.frame.requestFocus();
		instance.prom = promocao;
		instance.lblIdPromocao.setText(String.valueOf(promocao.id_promocao));
		instance.txtNome.setText(promocao.nome);
		instance.txtPreco.setText(String.valueOf(promocao.preco));
		instance.nudQuantidadeDias.setValue(promocao.qtde_dias);
		instance.nudQuantidadeFilmesAte.setValue(promocao.qtde_final);
		instance.nudQuantidadeFilmesDe.setValue(promocao.qtde_inicial);
		//(String)cmb.getSelectedItem();
		int size = instance.cmbCategoria.getItemCount();
		for(int i =0; i < size ;i++)
		{
			String n = (String) instance.cmbCategoria.getItemAt(i);
			if(n.compareToIgnoreCase(promocao.nome_categoria)==0)
			{
				instance.cmbCategoria.setSelectedIndex(i);
				break;
			}
		}
		return instance;
	}

	private frmPromocao() 
	{	
		prom = new PROMOCAO();
		lsCategoria = new ArrayList<CATEGORIAFILMES>();
		lsCategoria = FireBird.getInstance().selectBuscaCategoria("", "");
		prom.id_promocao = FireBird.getInstance().getId("promocao");
		
		lblCodigo = new JLabel("Código");
		lblNome = new JLabel("Nome");
		txtNome = new JTextField(5);
		lblPreco = new JLabel("Preço");
		txtPreco = new JTextField(5);
		lblQuantidadeFilmesDe = new JLabel("Quantidade de filmes, de");
		lblQuantidadeFilmesAte = new JLabel("até");
		SpinnerModel model1 = new SpinnerNumberModel(1, 1, 100, 1);
		SpinnerModel model2 = new SpinnerNumberModel(1, 1, 100, 1);
		SpinnerModel model3 = new SpinnerNumberModel(1, 1, 100, 1);
		nudQuantidadeFilmesDe = new JSpinner(model1);
		nudQuantidadeFilmesAte = new JSpinner(model3);
		lblQuantidadeDias = new JLabel("Quantidade de dias ganho na promoção");
		nudQuantidadeDias = new JSpinner(model2);
		btnConfirmar = new JButton("Confirmar");
		btnCancelar = new JButton("Cancelar");
		lblIdPromocao = new JLabel(String.valueOf(prom.id_promocao));
		lblCategoria = new JLabel("Categoria");
		btnConfirmar.setToolTipText("Clique aqui para confirmar!");
		btnCancelar.setToolTipText("Clique aqui para cancelar!");
		cmbCategoria = new JComboBox();
		int size = lsCategoria.size();
		for(int i = 0; i < size; i++)
		{
			CATEGORIAFILMES cf = lsCategoria.get(i);
			cmbCategoria.addItem(cf.nome);
		}
		btnConfirmar.addActionListener(this);
		btnCancelar.addActionListener(this);
		
		setPreferredSize(new Dimension(410, 350));
		setLayout(null);
		
		add(lblCodigo);
		add(txtPreco);
		add(lblPreco);
		add(lblNome);
		add(txtNome);
		add(lblQuantidadeFilmesDe);
		add(lblQuantidadeFilmesAte);
		add(nudQuantidadeFilmesDe);
		add(nudQuantidadeFilmesAte);
		add(btnConfirmar);
		add(btnCancelar);
		add(lblIdPromocao);
		add(lblCategoria);
		add(cmbCategoria);
		add(lblQuantidadeDias);
		add(nudQuantidadeDias);
		
		lblCodigo.setBounds(15, 20, 45, 25);
		lblIdPromocao.setBounds(65, 20, 100, 25);
		lblNome.setBounds(15, 55, 50, 25);
		txtNome.setBounds(65, 55, 325, 25);
		
		lblPreco.setBounds(15, 95, 300, 25);
		txtPreco.setBounds(280, 95, 109, 25);
		txtPreco.setBounds(65, 95, 109, 25);
		
		lblQuantidadeFilmesDe.setBounds(15, 135, 150, 25);
		nudQuantidadeFilmesDe.setBounds(165, 135, 80, 25);
		lblQuantidadeFilmesAte.setBounds(265, 135, 50, 25);
		nudQuantidadeFilmesAte.setBounds(310, 135, 80, 25);
		
		lblQuantidadeDias.setBounds(15, 175, 300, 25);
		nudQuantidadeDias.setBounds(280, 175, 109, 25);
		
		lblCategoria.setBounds(15, 215, 100, 25);
		cmbCategoria.setBounds(100, 215, 290, 25);
		
		btnCancelar.setBounds(235, 280, 100, 25);
		btnConfirmar.setBounds(115, 280, 100, 25);
		
		txtNome.setToolTipText("Insira o nome da promoção!");
		frmShow("CADASTRO DE PROMOÇÕES DE FILMES");
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
				frmPromocao.instance = null;
				
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
			prom.nome  			= UTIL.getMaxString(txtNome,60);
			if(txtPreco.getText().isEmpty())
				prom.preco 		= 0;
			else
				prom.preco 		= Double.parseDouble(txtPreco.getText().replace(",","."));
			prom.qtde_inicial	= (Integer) nudQuantidadeFilmesDe.getValue();
			prom.qtde_final		= (Integer) nudQuantidadeFilmesAte.getValue();
			prom.qtde_dias		= (Integer) nudQuantidadeDias.getValue();
			int index=0;
			int size = lsCategoria.size();
			for(index=0; index < size; index++)
			{
				CATEGORIAFILMES ct = lsCategoria.get(index);
				if(ct.nome == cmbCategoria.getSelectedItem())
				{
					prom.id_categoria = ct.id_categoria;
					break;
				}
			}
			if(FireBird.getInstance().insertOrUpdate(prom))
			{
				frame.dispose();
				frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_PROMOCAO);
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
		if(!txtPreco.getText().isEmpty() && !UTIL.isNumericDouble(txtPreco.getText(),-0.00000001))
		{
			strMessage = "Campo 'preço' não é numérico ou não é válido.";
			return false;
		}	
		try
		{
			strMessage = "Quantidade inicial inválida.";
			if((Integer) nudQuantidadeFilmesDe.getValue() <=0)
				return false;
			strMessage = "Quantidade final inválida.";
			if((Integer) nudQuantidadeFilmesAte.getValue() <=0)
				return false;
			strMessage = "Quantidade de dias inválida.";
			if((Integer) nudQuantidadeDias.getValue() <=0)
				return false;
		}
		catch(Exception exc)
		{
			return false;
		}
		return true;
	}
}
