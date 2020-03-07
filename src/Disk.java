public class Disk {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int diskSize = 20148; //2048 words in Disk

    private int[] registers;

    //constructor initializes register array
    public Disk(){
        registers = new int[diskSize];
    }

    //writes data to given address
    public void write(int address, int data){
        registers[address] = data;
    }

    //reads data from given address
    public int read(int address){
        return registers[address];
    }
}
