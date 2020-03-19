import java.util.ArrayList;
import java.util.Arrays;

//By Abdullah

public class RAM {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int RAMSize = 1024; //1024 words in RAM

    private int[][] registers;
    private boolean[] used;

    //constructor initializes register array
    public RAM(){
        registers = new int[RAMSize][wordSize];
        used = new boolean[RAMSize];
        Arrays.fill(used, false);
    }

    //writes data to given address, converting hex string to int array of bits
    public void write(int address, String data){
        String bin = Long.toBinaryString(Long.decode(data));
        int[] dataArr = new int[wordSize];
        for(int i = 0; i < bin.length(); i++){
            dataArr[i] = Character.getNumericValue(bin.charAt(i));
        }
        registers[address] = dataArr;
        used[address] = true;
    }

    //reads data from given address, returns int array of bits
    public int[] read(int address){
        return registers[address];
    }

    //reads data from given address, returns string of hex
    public String readAsString(int address){
        String data="";
        for (int i = 0; i <registers[address].length ; i++) {
            data = data+registers[address][i];
        }
        long decimal = Long.parseLong(data,2);
        return "0x"+Long.toString(decimal,16);
    }

    //checks whether RAM is full
    public boolean isFull(){
        boolean full = true;
        for (int i = 0; i < used.length; i++) {
            if(used[i]==false){
                full = false;
                break;
            }
        }
        return full;
    }

    //returns largest chunk of memory
    public int[] getFreeMemory(){
        int len=0;
        ArrayList<Integer> beginning = new ArrayList<>(), length = new ArrayList<>();
        boolean free=false;
        for (int i = 0; i < registers.length; i++) {
            if(!used[i]){
                if(!free) {
                    beginning.add(i);
                    len=0;
                    free=true;
                }
                len++;
            }
            else{
                if (len>0) length.add(len);
                free=false;
            }
        }
        if(length.isEmpty()){
            return new int[]{0, 0};
        }
        else{
            int max=0;
            for (int i = 0; i < length.size(); i++) {
                if(length.get(i)>max) max = i;
            }
            return new int[]{beginning.get(max),length.get(max)};
        }
    }

    //frees up space in RAM and updates 'used' array
    public void freeSpace(int beginningAddress, int length){
        for (int i = 0; i <length ; i++) {
            registers[beginningAddress+i] = new int[0];
            used[beginningAddress+i] = false;
        }
    }
}
