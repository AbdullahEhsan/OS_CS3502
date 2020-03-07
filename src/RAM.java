public class RAM {

    private int wordSize = 32; //32 bits/8 hex characters/4 bytes in a word
    private int RAMSize = 1024; //1024 words in RAM

    private int[] registers;

    //constructor initializes register array
    public RAM(){
        registers = new int[RAMSize];
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
