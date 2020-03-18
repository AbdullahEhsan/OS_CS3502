import java.util.Arrays;

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

    //frees up space in RAM and updates 'used' array
    public void freeSpace(int beginningAddress, int length){
        for (int i = 0; i <length ; i++) {
            registers[beginningAddress+i] = new int[0];
            used[beginningAddress+i] = false;
        }
    }
}
