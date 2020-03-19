
//By Abdullah

import java.io.IOException;
import java.util.ArrayList;

public class OSDriver {

    public static void main(String[] args){
        RAM ram = new RAM();
        Disk disk = new Disk();
        CPU cpu = new CPU();

        ArrayList<PCB> pcbList = new ArrayList<>();
        ArrayList<PCB> readyQueue = new ArrayList<>();

        String jobFile = "/Users/abdeeaamir/Downloads/Operating Systems Section 04 Spring Semester 2020 CO - 3162020 - 118 AM/Program-File-Wordversion-30-JOBS.doc";

        Loader loader = new Loader(jobFile, disk, pcbList);
        LongTermScheduler lts = new LongTermScheduler(disk, ram,pcbList, readyQueue);
        ShortTermScheduler sts = new ShortTermScheduler(ram, cpu, readyQueue, lts);

        try {
            loader.load();
        } catch (IOException e) {

        }

        lts.schedule();


    }
}
