public class RAM {

    private int wordSize = 8; //8 hex characters in a word
    private int RAMSize = 1024; //1024 words in RAM

    private int[] registers;

    public RAM(){
        registers = new int[wordSize];
    }

    public void write(int address, int data){
        registers[address] = data;
    }

    public int read(int address){
        return registers[address];
    }
}
