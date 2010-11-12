package Forms;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Locadora.UTIL;

public class frmSinopse extends JPanel implements FocusListener
{
	private static final long serialVersionUID = 1L;
	private JFrame 		frame;
	private JTextArea	txtArea;
	private static frmSinopse instance = null;
	
	public static frmSinopse getInstance(String titulo,String sinopse)
	{
		if(titulo==null || sinopse==null)
			return null;
		if(instance==null)
			instance = new frmSinopse("SINÓPSE: " + titulo);
		instance.frame.requestFocus();
		instance.txtArea.setText(sinopse);
		instance.txtArea.setEditable(false);
		return instance;
	}
	private frmSinopse(String titulo) 
	{
		setPreferredSize(new Dimension(500, 250));
		setLayout(null);
		txtArea = new JTextArea();
		add(txtArea);
		txtArea.setBounds(15, 15, 470,220);
		frame = new JFrame(titulo);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		UTIL.setCenterScreen(frame);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addFocusListener(this);
		frame.addWindowListener(new WindowAdapter() 
		{  
			public void windowClosed(WindowEvent e) 
			{  
				frmSinopse.instance = null;
			}  
		});
	}
	@Override
	public void focusGained(FocusEvent e) 
	{
	}
	@Override
	public void focusLost(FocusEvent e) 
	{
		frame.dispose();
	}
}
