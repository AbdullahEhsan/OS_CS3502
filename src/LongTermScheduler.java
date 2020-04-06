import java.util.ArrayList;

//By Abdullah

public class LongTermScheduler {

    private Disk disk;
    private RAM ram;
    private ArrayList<ProcessControlBlock> pcbArrayList;
    private ArrayList<ProcessControlBlock> readyQueue;

    public LongTermScheduler(Disk d, RAM r, ArrayList<ProcessControlBlock> pcblist, ArrayList<ProcessControlBlock> readyq){
        pcbArrayList=pcblist;
        ram=r;
        disk=d;
        readyQueue=readyq;
    }

    public void schedule(){
        fcfsScheduler();
    }

    private void fcfsScheduler(){
        ProcessControlBlock pcb;
        while(true){
            if (pcbArrayList.isEmpty()){
                return;
            }
            pcb = pcbArrayList.get(0);
            if(ram.getFreeMemory()[1]<(pcb.getJobLength()+pcb.getTotalBufferSize())){
                return;
            }

            int ramAddress = ram.getFreeMemory()[0], diskAddress = pcb.getJobAddress();
            pcb.setJobAddress(ramAddress);
            for (int i = 0; i < pcb.getJobLength(); i++) {
                ram.write(ramAddress++, disk.readAsString(diskAddress + i));
            }
            int bufferAddress = pcb.getBufferAddress();
            pcb.setBufferAddress(ramAddress);
            for (int i = 0; i < pcb.getTotalBufferSize(); i++) {
                ram.write(ramAddress++, disk.readAsString(bufferAddress + i));
            }
            pcb.setStartTime(System.currentTimeMillis());
            readyQueue.add(pcbArrayList.remove(0));
        }


    }

}
