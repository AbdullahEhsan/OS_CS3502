
//By Abdullah

import java.util.ArrayList;

public class ShortTermScheduler {

    private RAM ram;
    private Processor cpu;
    private ArrayList<Processor> cpuList;
    private ArrayList<ProcessControlBlock> readyQueue;
    private LongTermScheduler lts;

    public ShortTermScheduler(RAM r, Processor c, ArrayList<ProcessControlBlock> readyq, LongTermScheduler l){
        ram = r;
        cpu = c;
        readyQueue = readyq;
        lts = l;
    }
    public ShortTermScheduler(RAM r, ArrayList<Processor> c, ArrayList<ProcessControlBlock> readyq, LongTermScheduler l){
        ram = r;
        cpuList = c;
        readyQueue = readyq;
        lts = l;
    }

    private void dispatchSP(ProcessControlBlock pcb){
        cpu.assignPCB(pcb);
        cpu.run();

        if(readyQueue.size()<5){
            lts.schedule();
        }
        if(readyQueue.isEmpty()){
            //TODO //stats
        }
    }

    private void dispatchQP(Processor processor, ProcessControlBlock pcb){
        processor.assignPCB(pcb);
        processor.start();

        if(readyQueue.size()<5){
            lts.schedule();
        }
        if(readyQueue.isEmpty()){
            //TODO //stats

        }
    }

    public void scheduleSP(){
        fcfsScheduleSP();
    }

    public void scheduleQP(){
        fcfsScheduleQP();
    }

    private void fcfsScheduleSP(){
        while(!readyQueue.isEmpty()){
            ProcessControlBlock pcb = readyQueue.remove(0);

            pcb.setEndWaitTime(System.currentTimeMillis());
            dispatchSP(pcb);

        }
    }

    private void fcfsScheduleQP(){
        while(!readyQueue.isEmpty()) {
            for (Processor p : cpuList) {
                if (p.isAvailable()) {
                    ProcessControlBlock pcb = readyQueue.remove(0);

                    pcb.setEndWaitTime(System.currentTimeMillis());
                    dispatchQP(p, pcb);
                    break;
                }
            }
        }
    }



}
