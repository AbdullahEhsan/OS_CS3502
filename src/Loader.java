import java.io.*;

public class Loader {

    private BufferedReader file;
    private Disk disk;

    public Loader(String fileLocation, Disk diskInUse){
        try{
           file = new BufferedReader(new FileReader(fileLocation));
           disk = diskInUse;
        }
        catch (Exception e) {}
    }
    public void load() throws IOException {
        String line;
        int diskIndex = 0;

        while((line = file.readLine()) != null){
            if(line.contains("JOB")){
                String[] jobInfo = line.split(" ");
                int jobID = Integer.parseInt(jobInfo[2], 16);
                int jobNum = Integer.parseInt(jobInfo[3], 16);
                int jobPriority = Integer.parseInt(jobInfo[4], 16);
                for(int i = 0; i<jobNum; i++){
                    disk.write(diskIndex, file.readLine());
                    diskIndex++;
                }
            }
            else if(line.contains("Data")){
                String[] jobInfo = line.split(" ");
                int inputBufferSize = Integer.parseInt(jobInfo[2], 16);
                int outputBufferSize = Integer.parseInt(jobInfo[3], 16);
                int tempBufferSize = Integer.parseInt(jobInfo[4], 16);
                int bufferNum = inputBufferSize+outputBufferSize+tempBufferSize;
                for(int i = 0; i<bufferNum; i++){
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
