
//By Abdullah

import java.util.ArrayList;

public class ShortTermScheduler {

    private RAM ram;
    private CPU cpu;
    private ArrayList<PCB> readyQueue;
    private LongTermScheduler lts;

    public ShortTermScheduler(RAM r, CPU c, ArrayList<PCB> readyq, LongTermScheduler l){
        ram = r;
        cpu = c;
        readyQueue = readyq;
        lts = l;
    }



}
