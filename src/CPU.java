/*Created by Leeford
*
*
*
*/



public class CPU extends Helper, PCB
{
	public int cpu_id=0;
	public int prog_num;
	public int mem=0;
	public int opCode;
	public PCB table;

	public int InstructType;
	public int opCode;

	public int DReg,Breg,Sreg1,Sreg2;
	public int tempReg1,tempReg2;
	public int Address;

	public CPU() 
	{

    }


    public String fetch(int prog_count)
	{

		String instr= ""+table.registers[prog_count];
		return instr; //returns a HEX string of the instructions

	}

	public int decode(String instruction)
	{
		String tempInstruct=Helper.convertFromHexStringToBinString(instruction.substring(2));
		InstructType=Integer.parseInt(tempInstruct.substring(0,2));

		switch(InstructType)
		{
			/*Arithmetic instruction format
			*First two bits are always 00
			*Two first 4-bits interval are S-reg(Source Register)
			*Last 4-bits interval is the D-reg(Destination register)
			*The last 12 bits are always 0 <not used>
			*/
			case 00: //Arithmetic instruction format
			{
				Sreg1=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(8,12));
				Sreg2=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(12,16));
				DReg=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(16,20));
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
				Breg=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(8,12));
				DReg=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(12,16));
				Address=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(16));
				break;
			}
			/*Unconditional Jump format
			*First 2-bits are always 10, with a jump to the specified address.
			*
			*/
			case 10: //Unconditional Jump format
			{
				Address=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(8));
				break;
			}
			/*Input and Output instruction format
			*First 2-bits are always 11
			*The instruction may read the content of Address/Reg 2 into Reg1
			*The instruction may write the content of Reg 1 into a specified Address/Reg 2.
			*/
			case 11: //Input and Output instruction format
			{
				tempReg1=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(8,12));
				tempReg2=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(12,16));
				Address=Helper.convertFromBinaryStringToDecimalInteger(tempInstruct.substring(16));
				break;
			}
			default:
			{
				System.out.println("INVALID!!!");
			}
		}
		return opCode;
	}

	public void execute(int opCode)
	{
		int instr=opCode;

		switch(instr)
		{
			case RD:
			{
				break;
			}
	
			case WR:
			{
				break;
			}
	
			case ST:
			{
				break;
			}
	
			case LW:
			{
				break;
			}
	
			case MOV:
			{
				break;
			}
	
			case ADD:
			{

	
				break;
			}
	
			case SUB:
			{

	
				break;
			}
	
			case MUL:
			{

	
				break;
			}
	
			case DIV:
			{
	
				break;
			}
	
			case AND:
			{
	
				break;
			}
	
			case OR:
			{
	
				break;
			}
	
			case MOVI:
			{
				break;
			}
	
			case ADDI:
			{
				
				break;
			}
	
			case MULI:
			{
				
				break;
			}
	
			case DIVI:
			{
				
				break;
			}
	
			case LDI:
			{
				
				break;
			}
	
			case SLT:
			{
				
				break;
			}
	
			case SLTI:
			{
				
				break;
			}
	
			case HLT:
			{
				break;
			}
	
			case NOP:
			{
				break;
			}
	
			case JMP:
			{
				
				break;
			}
	
			case BEQ:
			{
				
	
				break;
			}
	
			case BNE:
			{
				
				break;
			}
	
			case BEZ:
			{
				
	
				break;
			}
	
			case BNZ:
			{
				
				break;
			}
	
			case BGZ:
			{
	
				break;
			}
	
			case BLZ:
			{
	
				break;
			}
		}

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
