/*Created by Leeford
*
*
*
*/



public class CPU
{
	int cpu_id=0;
	int prog_num;
	int mem=0;
	int opCode;
	public PCB table;

    public CPU() {

    }


    public String fetch(int prog_count)
	{

		String instr= ""+table.registers[prog_count];
		return instr; //returns a HEX string of the instructions

	}

	public int decode(String instruction)
	{
		String instrc;

		switch()
		{
			/*Arithmetic instruction format
			*First two bits are always 00
			*Two first 4-bits interval are S-reg(Source Register)
			*Last 4-bits interval is the D-reg(Destination register)
			*The last 12 bits are always 0 <not used>
			*/
			case 00: //Arithmetic instruction format
			{
				break;
			}
			/*Conditional Branch and Immediate format
			*First two bits are always 01
			*The next 6-bits indicate the immediate type of instruction
			*First 4-bits are for the Base register
			*Next 4-bits are the Destination register
			*The last 16-bits may be an address or an immediate data
			*/
			case 01: //Conditional Branch and Immediate format
			{
				break;
			}
			/*Unconditional Jump format
			*First 2-bits are always 10, with a jump to the specified address.
			*
			*/
			case 10: //Unconditional Jump format
			{
				break;
			}
			/*Input and Output instruction format
			*First 2-bits are always 11
			*The instruction may read the content of Address/Reg 2 into Reg1
			*The instruction may write the content of Reg 1 into a specified Address/Reg 2.
			*/
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

	public void execute()
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
