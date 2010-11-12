package Locadora;

public class CPF 
{
	public static String validaria=null;
	public static boolean ehValido(String num)
	{	int Valor , Fator, Dig1, Dig2, i;
		num = num.replace(" ", "");
		num = num.replace(",", "");
		num = num.replace(".", "");
		num = num.replace("-", "");
		if(num.length() !=11)
			return false;
		
		Valor = 0;
		/* PRIMEIRO DIGITO VERIFICADOR */
		for( i=0; i<9; i++ )//48 corresponde ao ascii do caracter "0"
			Valor += (num.charAt(i) - 48) * (10 - i);
		Fator = (Valor/11);
		Fator *= 11;
		Valor = Valor - Fator;
		// Se Valor igual a 0 ou 1 Dig1 recebe 0 senao
		// Dig1 recebe 11-Dig1
		Dig1 = Valor < 2 ? 0 : 11-Valor;
		Valor = 0;
		/* SEGUNDO DIGITO VERIFICADOR */
		for( i=0; i<9; i++ )
			Valor += (num.charAt(i) - 48) * (11-i);
		Valor += Dig1 * 2;
		Fator = (Valor/11);
		Fator *= 11;
		Valor = Valor - Fator;
		// Se Valor igual a 0 ou 1 Dig2 recebe 0 senao
		// Dig2 recebe 11-Dig2
		Dig2 = Valor < 2 ? 0 : 11-Valor;
		int res = (Dig1==(num.charAt(9) - 48) && Dig2==(num.charAt(10) - 48) ? 0 :Dig1*10+Dig2);
		validaria = String.valueOf(res);
		return res == 0;
	}
	public static String getFormatCPF(String num)
	{
		num = num.replace(" ", "");
		num = num.replace(",", "");
		num = num.replace(".", "");
		num = num.replace("-", "");
		if(num.length() <11)
			return null;
		String cpf = num.substring(0,3);
		cpf+=".";
		cpf+=num.substring(3,6);
		cpf+=".";
		cpf+=num.substring(6,9);
		cpf+="-";
		cpf+=num.substring(9,11);
		return cpf;
	}
}
