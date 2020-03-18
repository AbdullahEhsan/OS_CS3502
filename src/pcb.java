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
	char[] state={"ready","running","waiting","halted"};
	double[] registers={};
	double memory_limit;
	double [] resources={};

	public PCB()
	{
		cpu_id=0;
		prog_num=0;
		prog_count=0;
		code_size=0;
		registers={0};
		memory_limit=0;
		resources={0};
		
	}


}
