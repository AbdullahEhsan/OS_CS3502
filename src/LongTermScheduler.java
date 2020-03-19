import java.util.ArrayList;

//By Abdullah

public class LongTermScheduler {

    private Disk disk;
    private RAM ram;
    private ArrayList<PCB> pcbArrayList;
    private ArrayList<PCB> readyQueue;

    public LongTermScheduler(Disk d, RAM r, ArrayList<PCB> p, ArrayList<PCB> q){
        pcbArrayList=p;
        ram=r;
        disk=d;
        readyQueue=q;
    }

    public void schedule(){
        fcfsScheduler();
    }

    private void fcfsScheduler(){
        PCB pcb;
        while(true){
            if (pcbArrayList.isEmpty()){
                return;
            }
            pcb = pcbArrayList.get(0);
            if(ram.getFreeMemory()[1]<(pcb.jobLength+pcb.totalBufferSize)){
                return;
            }

            int ramAddress = ram.getFreeMemory()[0], diskAddress = pcb.jobAddress;
            pcb.jobAddress =ramAddress;
            for (int i = 0; i < pcb.jobLength; i++) {
                ram.write(ramAddress++, disk.readAsString(diskAddress + i));
            }
            pcb.bufferAddress=ramAddress;
            for (int i = 0; i < pcb.totalBufferSize; i++) {
                ram.write(ramAddress++, disk.readAsString(diskAddress + i));
            }
            readyQueue.add(pcb);
            pcbArrayList.remove(0);
        }

    }

}
