import java.io.*;

public class Loader {

    BufferedReader file;

    public Loader(String fileLocation){
        try{
           file = new BufferedReader(new FileReader(fileLocation));
        }
        catch (Exception e) {}
    }
    public void load() throws IOException {
        String line;
        while((line = file.readLine()) != null){

        }
    }

}
