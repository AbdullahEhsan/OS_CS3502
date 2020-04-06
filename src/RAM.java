import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//By Abdullah

public class RAM {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int RAMSize = 1024; //1024 words in RAM

    private int[][] data;
    private boolean[] used;

    //constructor initializes register array
    public RAM(){
        data = new int[RAMSize][wordSize];
        used = new boolean[RAMSize];
        Arrays.fill(used, false);
    }

    //writes data to given address, converting hex string to int array of bits
    public void write(int address, String data){
        long decoded = Long.decode(data);
        this.data[address] = Convert.LongToBinArray(decoded, wordSize);
        used[address] = true;
    }

    //reads data from given address, returns int array of bits
    public int[] read(int address){
        return data[address];
    }

    //reads data from given address, returns string of hex
    public String readAsString(int address){
        long decimal = Long.parseLong(Convert.BinArrayToBinString(this.data[address]),2);
        return "0x"+Long.toString(decimal,16);
    }

    //checks whether RAM is full
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

    //returns largest chunk of memory
    public int[] getFreeMemory(){
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

    //frees up space in RAM and updates 'used' array
    public void freeSpace(int beginningAddress, int length){
        for (int i = 0; i <length ; i++) {
            //data[beginningAddress+i] = new int[0];
            used[beginningAddress+i] = false;
        }
    }

    public double percentRAMUsed(){
        int usedNum = 0;
        for(boolean b: used){
            if(b) usedNum++;
        }
        return 100*usedNum/RAMSize;
    }
}
