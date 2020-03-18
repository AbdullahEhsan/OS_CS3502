import java.util.Arrays;

public class Disk {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int diskSize = 2048; //2048 words in Disk

    private int[][] registers;
    private boolean[] used;

    //constructor initializes register array
    public Disk(){
        registers = new int[diskSize][wordSize];
        used = new boolean[diskSize];
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

    //checks whether Disk is full
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

    //frees up space in Disk and updates 'used' array
    public void freeSpace(int beginningAddress, int length){
        for (int i = 0; i <length ; i++) {
            registers[beginningAddress+i] = new int[0];
            used[beginningAddress+i] = false;
        }
    }
}
