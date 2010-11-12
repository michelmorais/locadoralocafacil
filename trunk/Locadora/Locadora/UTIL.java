package Locadora;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UTIL 
{
	public static void setCenterScreen(JFrame frame)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    Point p = new Point(screenWidth / 2 - (frame.getWidth()/2), screenHeight / 2-(frame.getHeight()/2));
	    frame.setLocation(p);
	}
	public static void setMaxiSizeScreen(JFrame frame)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    int screenHeight = screenSize.height;
	    int screenWidth = screenSize.width;
	    frame.setSize(new Dimension(screenWidth,screenHeight));
	    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    Point p = new Point(0,0);
	    frame.setLocation(p);
	}
	public static int getWidthWindow()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    return screenSize.width;
	}
	public static boolean isNumericDouble(String str,double maiorQue)
	{
		try
		{
			return Double.parseDouble(str.replace(",",".")) > maiorQue;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public static boolean isNumericInt(String str)
	{
		try
		{
			return Integer.parseInt(str.replace(",",".")) > 0;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	public static int getInt(String str,int onFail)
	{
		try
		{
			return Integer.parseInt(str.replace(",","."));
		}
		catch(Exception e)
		{
			return onFail;
		}
	}
	public static String getMaxString(JTextField txtBox,int Comprimento)
	{
		try
		{
			if(txtBox.getText().length()<Comprimento)
				return txtBox.getText().replace("'", "");
			return txtBox.getText(0,Comprimento).replace("'", "");
		}
		catch(Exception exc)
		{
			JOptionPane.showMessageDialog(null,"Ocooreu um erro! Tente novamente!");
			return null;
		}
	}
	public static String getMaxString(JTextArea txtBox,int Comprimento)
	{
		try
		{
			if(txtBox.getText().length()<Comprimento)
				return txtBox.getText().replace("'", "");
			return txtBox.getText(0,Comprimento).replace("'", "");
		}
		catch(Exception exc)
		{
			JOptionPane.showMessageDialog(null,"Ocooreu um erro! Tente novamente!");
			return null;
		}
	}
	public static String alterMesDiaData(String data)
	{
		String result=null;
		String[] repli =  data.split("/");
		if(repli.length<=3)
		{
			result=repli[1] + "/";
			result+=repli[0] + "/";
			result+=repli[2];
			return result;
		}
		else
			return data;
	}
}
