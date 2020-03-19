import java.io.*;
import java.util.ArrayList;

public class Loader {

    private BufferedReader file;
    private Disk disk;
    private ArrayList<PCB> pcbArrayList;
    private PCB pcb;

    public Loader(String fileLocation, Disk diskInUse, ArrayList<PCB> pcbList){
        try{
           file = new BufferedReader(new FileReader(fileLocation));
           disk = diskInUse;
           pcbArrayList = pcbList;
        }
        catch (Exception e) {}
    }
    public void load() throws IOException {
        String line;
        int diskIndex = 0;
        while((line = file.readLine()) != null){
            if(line.contains("JOB")){
                pcb=new PCB();
                pcbArrayList.add(pcb);

                String[] jobInfo = line.split(" ");
                //info on job
                pcb.jobID = Integer.parseInt(jobInfo[2], 16);
                pcb.jobLength = Integer.parseInt(jobInfo[3], 16);
                pcb.jobPriority = Integer.parseInt(jobInfo[4], 16);
                pcb.jobAddress = diskIndex;

                //writes to disk
                for(int i = 0; i<pcb.jobLength; i++){
                    disk.write(diskIndex, file.readLine());
                    diskIndex++;
                }
            }
            else if(line.contains("Data")){
                String[] dataInfo = line.split(" ");

                //info on data
                pcb.inputBufferSize = Integer.parseInt(dataInfo[2], 16);
                pcb.outputBufferSize = Integer.parseInt(dataInfo[3], 16);
                pcb.tempBufferSize = Integer.parseInt(dataInfo[4], 16);
                pcb.totalBufferSize = pcb.inputBufferSize+pcb.outputBufferSize+pcb.tempBufferSize;
                pcb.bufferAddress=diskIndex;

                //writes to disk
                for(int i = 0; i<pcb.totalBufferSize; i++){
                    disk.write(diskIndex, file.readLine());
                    diskIndex++;
                }
            }
            else if(line.contains("END")){
                //do nothing
            }
            else{
                //do nothing
            }
        }
    }

}
