
//By Abdullah

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.LoggingMXBean;

public class OSDriver {

    private static Disk disk;
    private static RAM ram;
    private static Processor cpu;
    private static ArrayList<Processor> cpuList;

    private static LongTermScheduler lts;
    private static ShortTermScheduler sts;

    private static ArrayList<ProcessControlBlock> pcbList;
    private static ArrayList<ProcessControlBlock> readyQueue;

    private static Loader loader;


    public static void main(String[] args){
        disk = new Disk();
        ram = new RAM();
        cpu = new Processor(ram, 0);

        cpuList = new ArrayList<>();

        for(int i=1; i<=4;i++) {
            cpuList.add(new Processor(ram, i));
        }

        pcbList = new ArrayList<>();
        readyQueue = new ArrayList<>();

        String jobFile = "/Users/abdeeaamir/IdeaProjects/OS_CS3502/src/jobs.doc";

        Scanner read = new Scanner(System.in);
        String input;
        int code;
        while(true){
            System.out.println("Enter job file path:");
            jobFile = read.next();
            System.out.println("Enter 1 for single processor,");
            System.out.println("Enter 4 for quad processor,");
            System.out.println("Enter -1 to exit");
            input = read.next();
            try{
                code = Integer.parseInt(input);
                loader = new Loader(jobFile,disk,pcbList);
                lts = new LongTermScheduler(disk,ram,pcbList,readyQueue);
                if(code == 1){
                    singleProcessor();
                }
                else if(code == 4){
                    quadProcessor();
                }
                else if(code == -1){
                    System.out.println("Goodbye!");
                    break;
                }
                else{
                    System.out.println("Oh no! Invalid input. Starting over...");
                }
            }
            catch (NumberFormatException e){
                System.out.println("Invalid input. Starting over...");
            }
        }
    }

    private static void singleProcessor(){
        sts = new ShortTermScheduler(ram, cpu, readyQueue, lts);

        try {
            loader.load();
        } catch (IOException e) {
            System.out.println("ERROR");
        }

        lts.schedule();
        sts.scheduleSP();

        cpu.getAverageStats();
        System.out.println("RAM % used: "+ram.percentRAMUsed());
        System.out.println();
    }

    private static void quadProcessor(){
        sts = new ShortTermScheduler(ram, cpuList, readyQueue, lts);

        try {
            loader.load();
        } catch (IOException e) {
            System.out.println("ERROR");
        }

        lts.schedule();
        sts.scheduleQP();

        double[] avgStats = {0, 0, 0};

        for(Processor processor: cpuList){
            double[] stats = processor.getAverageStats();
            avgStats[0]+=stats[0];
            avgStats[1]+=stats[1];
            avgStats[2]+=stats[2];
        }

        avgStats[0] = avgStats[0]/4;
        avgStats[1] = avgStats[1]/4;
        avgStats[2] = avgStats[2]/4;

        String output ="Overall CPU Averages:"+"\n"+
                        "IO Count:"+avgStats[0]+"\n"+
                        "Wait:"+avgStats[1]+"ms"+"\n"+
                        "Complete:"+avgStats[2]+"ms"+"\n"+
                        "Cache % used: 100"+"\n"+
                        "RAM % used: "+ram.percentRAMUsed()+"\n"+
                        "\n";
        System.out.println(output);
    }
}
