import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

//By Abdullah

public class Disk {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int diskSize = 2048; //2048 words in Disk

    private int[][] data;
    private boolean[] used;

    //constructor initializes register array
    public Disk(){
        data = new int[diskSize][wordSize];
        used = new boolean[diskSize];
        Arrays.fill(used, false);
    }

    //writes data to given address, converting hex string to int array of bits
    public void write(int address, String data){
        String formatted = data.split(" ")[0];
        long decoded = Long.decode(formatted);

        int[] dataArr = Convert.LongToBinArray(decoded, wordSize);


        this.data[address] = dataArr;
        used[address] = true;
    }

    //reads data from given address, returns int array of bits
    public int[] read(int address){
        return data[address];
    }

    //reads data from given address, returns string of hex
    public String readAsString(int address){
        String data="";
        for (int i = 0; i < this.data[address].length ; i++) {
            data += this.data[address][i];
        }
        long decimal = Long.parseLong(data,2);
        return "0x"+Long.toString(decimal,16);
    }

    //checks whether Disk is full
    public boolean isFull(){
        boolean full = true;
        for (boolean b : used) {
            if (!b) {
                full = false;
                break;
            }
        }
        return full;
    }

    //returns largest chunk of disk space
    public int[] getFreeDiskSpace(){
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> l = new ArrayList<>();
        boolean wasFree = false;
        int len = 0;
        for(int addr = 0; addr<used.length;addr++){
            if(!used[addr]){
                if(!wasFree) a.add(addr);
                len++;
                wasFree = true;
                if(addr+1==used.length) l.add(len);
            }
            else{
                if(len>0){
                    l.add(len);
                    len = 0;
                }
                wasFree=false;
            }
        }
        if(l.isEmpty()){
            int[] none = {0, 0};
            return none;
        }
        else{
            int maxElement = Collections.max(l);
            int maxIndex = l.indexOf(maxElement);
            int[] some = {a.get(maxIndex), l.get(maxIndex)};
            return some;
        }
    }

    //frees up space in Disk and updates 'used' array
    public void freeSpace(int beginningAddress, int length){
        for (int i = 0; i <length ; i++) {
            //data[beginningAddress+i] = new int[0];
            used[beginningAddress+i] = false;
        }
    }
}
