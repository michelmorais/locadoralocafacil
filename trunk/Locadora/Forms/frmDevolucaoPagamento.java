package Forms;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

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
    
    private static frmDevolucaoPagamento instance = null;
	public static frmDevolucaoPagamento getInstance()
	{
		if(instance==null)
			instance = new frmDevolucaoPagamento();
		instance.frame.requestFocus();
		return instance;
	}

    private frmDevolucaoPagamento() 
    {
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
        lblDataDeEntrega.setBounds (15, 130, 165, 25);
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
        lblStatus.setBounds (15, 165, 165, 25);
        btnDevolver.setBounds (180, 165, 160, 25);
        btnAnterior.setBounds (20, 305, 100, 25);
        btnProximo.setBounds (125, 305, 100, 25);
        btnSair.setBounds (395, 305, 100, 25);
        frmShow();
    }
    private void frmShow() 
    {
        frame = new JFrame ("CONTROLE DE DEVOLUÇÃO / PAGAMENTO");
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
	private void onPressed_btnConfirmarPagamento()
	{

	}
	private void onPressed_btnCancelarMulta()
	{

	}
	private void onPressed_btnDevolver()
	{

	}
	private void onPressed_btnSair()
	{
		frame.dispose();
	}
	private void onPressed_btnProximo()
	{

	}
	private void onPressed_btnAnterior()
	{

	}
}
