import java.io*;
import java.util.*;

/*Created by Leeford 
*
*
*
*/



public class CPU extends PCB
{
	int cpu_id=0;
	int prog_num;
	int mem=0;
	int opCode;
	public PCB table;


	public String fetch(int prog_count)
	{

		String instr=table.registers[prog_count];
		return instr; //returns a HEX string of the instructions

	}

	public int decode(String instruction)
	{
		String instrc;

		switch()
		{
			case 00: //Arithmetic instruction format
			{
				break;
			}
			case 01: //Conditional Branch and Immediate format
			{
				break;
			}
			case 10: //Unconditional Jump format
			{
				break;
			}
			case 11: //Input and Output instruction format
			{
				break;
			}
			default:
			{
				System.out.println("INVALID!!!");
			}
		}
		return opCode;
	}

	public execute()
	{

	}

	public CPU(double count)
	{
		table.prog_count=count;
		table.registers[1]=0;

	}

	public static void main(String[]args)
	{

	}


}