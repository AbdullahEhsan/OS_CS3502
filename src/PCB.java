import java.io.*;
import java.util.*;


/*Created by Leeford 
*
*
*
*/

public class PCB
{

	double cpu_id,prog_num,prog_count,code_size;
	String[] state={"ready","running","waiting","halted"};
	double[] registers={};
	double memory_limit;
	double [] resources={};


	int jobID, jobLength, jobPriority, jobAddress;
	int inputBufferSize, outputBufferSize, tempBufferSize, totalBufferSize, bufferAddress;

	public PCB()
	{
		cpu_id=0;
		prog_num=0;
		prog_count=0;
		code_size=0;
		registers= new double[]{0};
		memory_limit=0;
		resources= new double[]{0};
		
	}


}
