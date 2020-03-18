/*Created by Leeford 
*
*Consist of the converstion helpers
*Hex to binary
*
*/

public class helper
{
	public static int convertFromHexToDecimal(String Hex)
	{
		return Integer.parseInt(Hex,16);
	}

	public static String convertFromDecimalToHex(int Deci)
	{
		return Integer.toHexString(Deci);
	}

	public static String convertFromHexStringToBinString(String Hex)
	{
		long a=Long.parseLong(Hex,16);
		String binary=Long.toBinaryString(a);

		while(binary.length()!=32)
		{
			binary+="0";
		}

		return binary;
	}

	public static int convertFromBinaryStringToDecimalInteger(String binary)
	{
		int a=Integer.parseInt(bin,2);
		return i;
	}
}