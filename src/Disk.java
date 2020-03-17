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

    //writes data to given address
    public void write(int address, String data){
        String bin = Long.toBinaryString(Long.decode(data));
        int[] dataArr = new int[wordSize];
        for(int i = 0; i < bin.length(); i++){
            dataArr[i] = Character.getNumericValue(bin.charAt(i));
        }
        registers[address] = dataArr;
        used[address] = true;
    }

    //reads data from given address
    public int[] read(int address){
        return registers[address];
    }
}
