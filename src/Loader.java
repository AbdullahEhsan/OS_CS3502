import java.io.*;
import java.util.ArrayList;

public class Loader {

    private BufferedReader file;
    private Disk disk;
    private ArrayList<ProcessControlBlock> pcbArrayList;
    private ProcessControlBlock pcb;

    public Loader(String fileLocation, Disk diskInUse, ArrayList<ProcessControlBlock> pcbList){
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
                pcb=new ProcessControlBlock();
                pcbArrayList.add(pcb);

                String[] jobInfo = line.split(" ");
                //info on job
                pcb.setJobID(Integer.parseInt(jobInfo[2], 16));
                pcb.setJobLength(Integer.parseInt(jobInfo[3], 16));
                pcb.setJobPriority(Integer.parseInt(jobInfo[4], 16));
                pcb.setJobAddress(diskIndex);

                //writes to disk
                for(int i = 0; i<pcb.getJobLength(); i++){
                    disk.write(diskIndex, file.readLine());
                    diskIndex++;
                }
            }
            else if(line.contains("Data")){
                String[] dataInfo = line.split(" ");

                //info on data
                pcb.setInputBufferSize(Integer.parseInt(dataInfo[2], 16));
                pcb.setOutputBufferSize(Integer.parseInt(dataInfo[3], 16));
                pcb.setTempBufferSize(Integer.parseInt(dataInfo[4], 16));
                pcb.setBufferAddress(diskIndex);

                //writes to disk
                for(int i = 0; i<pcb.getTotalBufferSize(); i++){
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
