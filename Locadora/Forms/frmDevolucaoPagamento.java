package Forms;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import Banco.FireBird;
import Locadora.CONTROLERESERVA;
import Locadora.DEVOLUCAO;
import Locadora.UTIL;

public class frmDevolucaoPagamento extends JPanel implements ActionListener
{
    private static final long serialVersionUID = 1L;
	private JLabel lblCliente;
    private JLabel lblTotalDeLocacaoAtual;
    private JLabel lblNomeDoFilme;
    private JLabel lblDataDeEntrega;
    private JLabel lblValorParcial;
    private JLabel lblVisualizacaoValorAtual;
    private JLabel lblVisualizacaoValorTotal;
    private JLabel lblValorTotal;
    private JLabel lblVisualizacaoValorPago;
    private JLabel lblValorPago;
    private JLabel lblVisualizarMulta;
    private JLabel lblMulta;
    private JButton btnCancelarMulta;
    private JTextField txtReceber;
    private JLabel lblReceber;
    private JButton btnConfirmarPagamento;
    private JLabel lblStatus;
    private JButton btnDevolver;
    private JButton btnAnterior;
    private JButton btnProximo;
    private JButton btnSair;
    private JFrame frame;
    private ArrayList<DEVOLUCAO> lsDevolucao;
    private DEVOLUCAO Devolucao;
    private int registro;
    private boolean next;
    
    private static frmDevolucaoPagamento instance = null;
	public static frmDevolucaoPagamento getInstance(ArrayList<DEVOLUCAO> devolucao_,int id_devolucao)
	{
		if(devolucao_.size()==0)
			return null;
		if(instance==null)
			instance = new frmDevolucaoPagamento();
		instance.frame.requestFocus();
		instance.lsDevolucao = devolucao_;
		for(int i=0; i <devolucao_.size(); i++)
		{
			if(devolucao_.get(i).filme.id_devolucao==id_devolucao)
			{
				instance.registro = i;
				break;
			}
		}
		instance.updateForm();
		
		return instance;
	}
	private boolean updateForm()
	{
		try
		{
			Devolucao = lsDevolucao.get(registro);
			if(Devolucao.filme.situacao.compareToIgnoreCase("LOCADO")==0)
			{
				
				double[] valores = FireBird.getInstance().calculaDevolucao(Devolucao.filme.id_devolucao);
				if(valores==null)
					JOptionPane.showMessageDialog(null, "Erro ao calcular a multa");
				else
				{
					Devolucao.multa 			= valores[0];
					Devolucao.valor_a_receber	= valores[1];
					if(Devolucao.multa > 0.0)
						lblDataDeEntrega.setForeground(new Color(255, 0,0));
					else
						lblDataDeEntrega.setForeground(lblCliente.getForeground());
				}
			}
			else
			{
				Devolucao.multa 			= 0;
				Devolucao.valor_a_receber	= 0;
				lblDataDeEntrega.setForeground(lblCliente.getForeground());
			}
			lblCliente.setText("Cód: " + Devolucao.filme.id_devolucao + "      Cliente:  "  + Devolucao.cliente.nome);
			lblTotalDeLocacaoAtual.setText("Total:  "+ String.valueOf(registro + 1) + " / " +  String.valueOf(lsDevolucao.size()));
			lblNomeDoFilme.setText("Filme: " + Devolucao.filme.nome);
			lblDataDeEntrega.setText("Data locação: " + Devolucao.data_locacao+ "    Data entrega: " +Devolucao.data_entrega );
			lblStatus.setText("Situação: " +  Devolucao.filme.situacao);
			lblValorParcial.setText("R$ " + Devolucao.filme.preco);
			
			lblValorTotal.setText("R$ " + UTIL.formateRS(Devolucao.valor_total));
			lblValorParcial.setText("R$ " + UTIL.formateRS(Devolucao.valor_unitario));
			lblValorPago.setText("R$ " + UTIL.formateRS(Devolucao.valor_recebido));
			
			lblMulta.setText("R$ " + UTIL.formateRS(Devolucao.multa));
			
			txtReceber.setText(UTIL.formateRS(Devolucao.valor_a_receber));
			if(Devolucao.valor_a_receber>0)
				btnConfirmarPagamento.setEnabled(true);
			else
				btnConfirmarPagamento.setEnabled(false);
			
			if(Devolucao.filme.situacao.compareToIgnoreCase("LOCADO")==0)
				btnDevolver.setEnabled(true);
			else
				btnDevolver.setEnabled(false);
			return true;
		}
		catch (Exception e) 
		{
			if(next)
			{
				JOptionPane.showMessageDialog(null,"Não há mais registros seguintes!");
				registro--;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Não há mais registros anteriores!");
				registro++;
			}
			return false;
		}
	}
	
    private frmDevolucaoPagamento() 
    {
    	next = false;
    	registro = 0;
    	lblCliente = new JLabel ("Cliente: José maria dos santos");
        lblTotalDeLocacaoAtual = new JLabel ("Total/locação atual: 1/2 filmes");
        lblNomeDoFilme = new JLabel ("Titulo: Principe da percia");
        lblDataDeEntrega = new JLabel ("Data de entrega: 22/10/2010");
        lblValorParcial = new JLabel ("R$ 4,00");
        lblVisualizacaoValorAtual = new JLabel ("Valor da atual Locação");
        lblVisualizacaoValorTotal = new JLabel ("Valor total da locação");
        lblValorTotal = new JLabel ("R$ 8,00");
        lblVisualizacaoValorPago = new JLabel ("Valor Pago");
        lblValorPago = new JLabel ("R$ 0,00");
        lblVisualizarMulta = new JLabel ("Multa");
        lblMulta = new JLabel ("R$ 0,00");
        btnCancelarMulta = new JButton ("...");
        txtReceber = new JTextField (5);
        txtReceber.setEditable(false);
        lblReceber = new JLabel ("Receber           R$");
        btnConfirmarPagamento = new JButton ("Confirmar pagamento");
        lblStatus = new JLabel ("Situação: Locado");
        btnDevolver = new JButton ("Confirmar devolução");
        btnAnterior = new JButton ("Anterior");
        btnProximo = new JButton ("Próximo");
        btnSair = new JButton ("Sair");

        lblCliente.setToolTipText ("Nome do cliente desta locação");
        lblNomeDoFilme.setToolTipText ("Nome do filme da atual locação");
        lblValorParcial.setToolTipText ("Valor da atual locação");
        lblValorTotal.setToolTipText ("Valor Total da locação");
        btnCancelarMulta.setToolTipText ("Ação permetida somente pelo administrador");
        txtReceber.setToolTipText ("Valor a receber apenas números");
        lblReceber.setToolTipText ("Preencha o valor a receber se houver");
        btnConfirmarPagamento.setToolTipText ("Clique aqui para confirmar pagamento");
        btnAnterior.setToolTipText ("Permite navegar até o filme anterior");
        btnProximo.setToolTipText ("Permite navegar até o próximo filme");
        btnSair.setToolTipText ("Clique aqui para sair!");
        btnCancelarMulta.addActionListener(this);
        btnConfirmarPagamento.addActionListener(this);
        btnDevolver.addActionListener(this);
        btnSair.addActionListener(this);
        btnProximo.addActionListener(this);
        btnAnterior.addActionListener(this);
        btnCancelarMulta.setVisible(false);
        lblValorTotal.setForeground(new Color(0,128, 255));
		lblValorParcial.setForeground(new Color(0,128,255));
		lblValorPago.setForeground(new Color(0,128,255));
		lblMulta.setForeground(new Color(255,0, 0));

        setPreferredSize (new Dimension (544, 377));
        setLayout (null);

        add (lblCliente);
        add (lblTotalDeLocacaoAtual);
        add (lblNomeDoFilme);
        add (lblDataDeEntrega);
        add (lblValorParcial);
        add (lblVisualizacaoValorAtual);
        add (lblVisualizacaoValorTotal);
        add (lblValorTotal);
        add (lblVisualizacaoValorPago);
        add (lblValorPago);
        add (lblVisualizarMulta);
        add (lblMulta);
        add (btnCancelarMulta);
        add (txtReceber);
        add (lblReceber);
        add (btnConfirmarPagamento);
        add (lblStatus);
        add (btnDevolver);
        add (btnAnterior);
        add (btnProximo);
        add (btnSair);

        lblCliente.setBounds (15, 25, 300, 25);
        lblTotalDeLocacaoAtual.setBounds (15, 60, 300, 25);
        lblNomeDoFilme.setBounds (15, 95, 300, 25);
        lblDataDeEntrega.setBounds (15, 130, 500, 25);
        lblValorParcial.setBounds (20, 230, 100, 25);
        lblVisualizacaoValorAtual.setBounds (20, 205, 145, 25);
        lblVisualizacaoValorTotal.setBounds (165, 205, 145, 25);
        lblValorTotal.setBounds (170, 230, 100, 25);
        lblVisualizacaoValorPago.setBounds (310, 205, 95, 25);
        lblValorPago.setBounds (315, 230, 100, 25);
        lblVisualizarMulta.setBounds (405, 205, 40, 25);
        lblMulta.setBounds (405, 230, 100, 25);
        btnCancelarMulta.setBounds (445, 210, 15, 15);
        txtReceber.setBounds (120, 270, 100, 25);
        lblReceber.setBounds (20, 270, 100, 25);
        btnConfirmarPagamento.setBounds (230, 270, 160, 25);
        btnDevolver.setBounds (230, 305, 160, 25);
        lblStatus.setBounds (15, 165, 165, 25);
        btnAnterior.setBounds (20, 305, 100, 25);
        btnProximo.setBounds (125, 305, 100, 25);
        btnSair.setBounds (395, 305, 100, 25);
        frmShow();
    }
    private void frmShow() 
    {
        frame = new JFrame ("CONTROLE DE DEVOLUÇÃO / PAGAMENTO   " + UTIL.getDate());
        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add (this);
        frame.pack();
        frame.setVisible (true);
        UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				frmDevolucaoPagamento.instance=null;
			}  
		});
    }
    public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==btnCancelarMulta)
			onPressed_btnCancelarMulta();
		else if(e.getSource()==btnConfirmarPagamento)
			onPressed_btnConfirmarPagamento();
		else if(e.getSource()==btnDevolver)
			onPressed_btnDevolver();
		else if(e.getSource()==btnSair)
			onPressed_btnSair();
		else if(e.getSource()==btnProximo)
			onPressed_btnProximo();
		else if(e.getSource()==btnAnterior)
			onPressed_btnAnterior();
	}
    private boolean ConfirmaDevolucao()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Deseja realmente devolver o filme: " + Devolucao.filme.nome + "?",  
		"LOCAFÁCIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
    private boolean ConfirmaPagamento()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Deseja realmente pagar o filme: " + Devolucao.filme.nome + "?",  
		"LOCAFÁCIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
    private boolean CancelarMulta()
	{
		int i =JOptionPane.showConfirmDialog(this,  
		"Deseja realmente cancelar a multa?",  
		"LOCAFÁCIL",  
		JOptionPane.YES_NO_OPTION,  
		JOptionPane.QUESTION_MESSAGE);
		return i !=1;
	}
	private void onPressed_btnConfirmarPagamento()
	{
		if(!ConfirmaPagamento())
			return;
		Devolucao.valor_recebido = Double.parseDouble(txtReceber.getText().replace(",","."));
		if(FireBird.getInstance().updatePagamento(Devolucao.filme.id_devolucao, Devolucao.valor_a_receber, Devolucao.valor_recebido, Devolucao.multa))
		{
			btnConfirmarPagamento.setEnabled(false);
			txtReceber.setText("0,00");
			Devolucao.valor_a_receber = 0;
			frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_DEVOLUCAO);
		}
	}
	private void onPressed_btnCancelarMulta()
	{
		if(!CancelarMulta())
			return;
	}
	private void onPressed_btnDevolver()
	{
		if(!ConfirmaDevolucao())
			return;
		if(FireBird.getInstance().updateDevolucao(Devolucao.filme.id_devolucao, Devolucao.filme.id_filmes, "DEVOLVIDO"))
		{
			btnDevolver.setEnabled(false);
			Devolucao.filme.situacao = "DEVOLVIDO";
			lblStatus.setText("DEVOLVIDO");
			frmBuscaGenerica.RefreshPesquisa(frmBuscaGenerica.BUSCA_DEVOLUCAO);
			ArrayList<CONTROLERESERVA> reserva = FireBird.getInstance().getReservaFilme(Devolucao.filme.id_filmes);
			if(reserva!=null && reserva.size()>0)
				JOptionPane.showMessageDialog(null,"O cliente: " + reserva.get(0).nome + " ID: " + reserva.get(0).id_clientes+ " possui reserva para o Filme: " + Devolucao.filme.nome);
		}
	}
	private void onPressed_btnSair()
	{
		frame.dispose();
	}
	private void onPressed_btnProximo()
	{
		next = true;
		registro++;
		if(!updateForm())
			updateForm();
	}
	private void onPressed_btnAnterior()
	{
		next = false;
		registro--;
		if(!updateForm())
			updateForm();
	}
}
