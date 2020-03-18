import java.io.*;
import java.util.*;


public class PCB
{

	double cpu_id,prog_num,prog_count,code_size;
	char[] state={"ready","running","waiting","halted"};
	double[] registers={};
	double memory_limit;
	double [] resources={};

	public PCB(double a,char b,double[]c, double d, double[]e)
	{
		prog_num=a;
		state=b;
		registers=c;
		memory_limit=d;
		resources=e;
	}


}
