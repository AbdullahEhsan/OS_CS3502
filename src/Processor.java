//By Abdullah

import jdk.internal.cmm.SystemResourcePressureImpl;

import java.util.ArrayList;
import java.util.Arrays;

public class Processor implements Runnable{

    Thread t;

    public void start(){
        t=new Thread(this, "CPU "+processorID);
        t.start();
    }

    private int processorID;
    private boolean isAvailable;

    private int registerCount = 16;
    private int registerLength = 32;

    private int[][] registers;
    private int[] instr;
    private ArrayList<int[]> cache;
    private int programCounter;


    private RAM ram;
    private ProcessControlBlock pcb;

    private ArrayList<long[]> statsList = new ArrayList<>();


    private int IOcount;

    public void getJobStats(){
        long[] stats = {pcb.getJobID(), IOcount, pcb.getWaitTime(), pcb.getCompletionTime()};

        String output ="Job ID:"+stats[0]+"\n"+
                        "Job Length:"+pcb.getJobLength()+"\n"+
                        "CPU ID:"+processorID+"\n"+
                        "IO Count:"+stats[1]+"\n"+
                        "Wait:"+stats[2]+"ms"+"\n"+
                        "Complete:"+stats[3]+"ms"+"\n"+
                        "Accumulator:"+Long.parseLong(Convert.BinArrayToBinString(registers[0]),2)+"\n"+
                        "Cache % used: 100\n";
        System.out.println(output);
        statsList.add(stats);
    }

    public double[] getAverageStats(){
        double[] avgStats = {0, 0, 0};
        for (long[] l: statsList) {
            avgStats[0]+=l[1];
            avgStats[1]+=l[2];
            avgStats[2]+=l[3];
        }
        avgStats[0] = avgStats[0]/statsList.size();
        avgStats[1] = avgStats[1]/statsList.size();
        avgStats[2] = avgStats[2]/statsList.size();

        String output ="Averages for CPU "+processorID+":"+"\n"+
                        "IO Count:"+avgStats[0]+"\n"+
                        "Wait:"+avgStats[1]+"ms"+"\n"+
                        "Complete:"+avgStats[2]+"ms"+"\n"+
                        "Cache % used: 100\n";
        System.out.println(output);

        return avgStats;

    }


    public Processor(RAM r, int id) {
        ram = r;

        registers = new int[registerCount][registerLength];
        registers[0]=new int[registerLength]; //accumulator
        Arrays.fill(registers[0], 0);
        registers[1]=new int[registerLength];
        Arrays.fill(registers[1], 0);

        processorID = id;
        isAvailable=true;
    }

    public void run(){
        while (programCounter < pcb.getJobLength()) {
            fetch();
            decodeAndExecute();
            programCounter++;
        }
        getJobStats();
        isAvailable = true;
    }

    //accepts pcb from dispatcher
    public void assignPCB(ProcessControlBlock p) {
        isAvailable=false;
        pcb = p;
        cache = new ArrayList();
        for (int i = 0; i<pcb.getJobLength()+pcb.getTotalBufferSize(); i++){
            cache.add(ram.read(pcb.getJobAddress()+i));
        }

        programCounter=0;
        IOcount=0;
    }

    //fetches next instruction
    private void fetch()
    {
        if(programCounter<pcb.getJobLength()) {
            instr = cache.get(programCounter);
        }
    }

    //decodes and executes instruction
    private void decodeAndExecute()
    {



        String instructionType = ""+instr[0]+instr[1];

        String opCode = "";
        for(int i=2; i<8;i++){
            opCode += instr[i];
        }

        if(opCode.equals("010011"))//NOP
            ;
        else {
            switch (instructionType) {
                case "00":
                    decodeAndExecuteArithmetic(opCode);
                    break;
                case "01":
                    decodeAndExecuteConditionalBranchAndImmediate(opCode);
                    break;
                case "10":
                    decodeAndExecuteUnconditionalJump(opCode);
                    break;
                case "11":
                    decodeAndExecuteIO(opCode);
                    break;
            }
        }
    }

    //decodes and executes arithmetic instructions
    private void decodeAndExecuteArithmetic(String opCode){
        String sReg1Str="",sReg2Str="", dRegStr="";
        for(int i=8; i<12;i++){
            sReg1Str += instr[i];
        }
        for(int i=12; i<16;i++){
            sReg2Str += instr[i];
        }
        for(int i=16; i<20;i++){
            dRegStr += instr[i];
        }

        int sReg1 = Integer.parseInt(sReg1Str,2);
        int sReg2 = Integer.parseInt(sReg2Str,2);
        int dReg = Integer.parseInt(dRegStr,2);

        switch(opCode){
            //04-0A,10
            case "000100": //MOV
                registers[sReg1] = registers[sReg2];
                break;
            case "000101": //ADD
                long resultAdd = Long.parseLong(Convert.BinArrayToBinString(registers[sReg2]),2)
                        +Long.parseLong(Convert.BinArrayToBinString(registers[sReg1]),2);
                registers[dReg] = Convert.LongToBinArray(resultAdd, registerLength);
                break;
            case "000110": //SUB
                long s2SUB = Long.parseLong(Convert.BinArrayToBinString(registers[sReg2]),2);
                long s1SUB = Long.parseLong(Convert.BinArrayToBinString(registers[sReg1]),2);;
                long resultSub;
                if (s2SUB>s1SUB) resultSub = s2SUB-s1SUB;
                else resultSub = s1SUB-s2SUB;
                registers[dReg] = Convert.LongToBinArray(resultSub, registerLength);
                break;
            case "000111": //MUL
                long resultMul = Long.parseLong(Convert.BinArrayToBinString(registers[sReg2]),2)
                        *Long.parseLong(Convert.BinArrayToBinString(registers[sReg1]),2);
                registers[dReg] = Convert.LongToBinArray(resultMul, registerLength);
                break;
            case "001000": //DIV
                long s2DIV = Long.parseLong(Convert.BinArrayToBinString(registers[sReg2]),2);
                long s1DIV = Long.parseLong(Convert.BinArrayToBinString(registers[sReg1]),2);;
                long resultDiv;
                if(s2DIV>s1DIV) resultDiv=s2DIV/s1DIV;
                else resultDiv=s1DIV/s2DIV;
                registers[dReg] = Convert.LongToBinArray(resultDiv, registerLength);
                break;
            case "001001": //AND
                for(int i = 0; i<registerLength;i++){
                    if(registers[sReg1][i]==1&&registers[sReg2][i]==1){
                        registers[dReg][i]=1;
                    }
                    else registers[dReg][i]=0;
                }
                break;
            case "001010": //OR
                for(int i = 0; i<registerLength;i++){
                    if(registers[sReg1][i]==1||registers[sReg2][i]==1){
                        registers[dReg][i]=1;
                    }
                    else registers[dReg][i]=0;
                }
                break;
            case "010000": //SLT
                if(Long.parseLong(Convert.BinArrayToBinString(registers[sReg1]),2)
                        <Long.parseLong(Convert.BinArrayToBinString(registers[sReg2]),2)){
                    Arrays.fill(registers[dReg],0);
                    registers[dReg][registerLength-1]=1;
                }
                else Arrays.fill(registers[dReg],0);
                break;
        }
    }

    //decodes and executes conditional branch and immediate instructions
    private void decodeAndExecuteConditionalBranchAndImmediate(String opCode){
        String bRegStr="",dRegStr="", addressStr="";
        for(int i=8; i<12;i++){
            bRegStr += instr[i];
        }
        for(int i=12; i<16;i++){
            dRegStr += instr[i];
        }
        for(int i=16; i<32;i++){
            addressStr += instr[i];
        }

        int bReg = Integer.parseInt(bRegStr,2);
        int dReg = Integer.parseInt(dRegStr,2);
        int address = Integer.parseInt(addressStr,2);

        switch(opCode){
            //02-03,0B-0F,11,15-1A
            case "000010": //ST
                long decimalST = Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2);
                ram.write(Integer.parseInt(Convert.BinArrayToBinString(registers[dReg]),2), "0x"+Long.toString(decimalST,16));
                break;
            case "000011": //LW
                registers[dReg] = ram.read(Integer.parseInt(Convert.BinArrayToBinString(registers[bReg]),2));
                break;
            case "001011": //MOVI
                registers[dReg] = Convert.LongToBinArray(address, registerLength);
                break;
            case "001100": //ADDI
                long decimalADDI = Long.parseLong(Convert.BinArrayToBinString(registers[dReg]),2);
                if(address%4==0)
                    registers[dReg] = Convert.LongToBinArray(decimalADDI+(address/4),registerLength);
                else registers[dReg] = Convert.LongToBinArray(decimalADDI+(address),registerLength);
                break;
            case "001101": //MULI
                long decimalMULI = Long.parseLong(Convert.BinArrayToBinString(registers[dReg]),2);
                if(address%4==0)
                    registers[dReg] = Convert.LongToBinArray(decimalMULI*(address/4),registerLength);
                else registers[dReg] = Convert.LongToBinArray(decimalMULI*(address),registerLength);
                break;
            case "001110": //DIVI
                long decimalDIVI = Long.parseLong(Convert.BinArrayToBinString(registers[dReg]),2);
                if(address%4==0)
                    registers[dReg] = Convert.LongToBinArray(decimalDIVI/(address/4),registerLength);
                else registers[dReg] = Convert.LongToBinArray(decimalDIVI/(address),registerLength);
                break;
            case "001111": //LDI
                if((address/4) == pcb.getJobLength())
                    registers[dReg] = Convert.LongToBinArray(pcb.getInputBufferAddress(), registerLength);
                else if((address/4) == (pcb.getJobLength()+pcb.getInputBufferSize()))
                    registers[dReg] = Convert.LongToBinArray(pcb.getOutputBufferAddress(), registerLength);
                else if((address/4) == (pcb.getJobLength()+pcb.getInputBufferSize()+pcb.getOutputBufferSize()))
                    registers[dReg] = Convert.LongToBinArray(pcb.getTempBufferAddress(), registerLength);
                break;
            case "010001": //SLTI
                if(Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2)
                        <(address/4)){ //TODO //check [replaced all addresses with (address/4)]
                    Arrays.fill(registers[dReg],0);
                    registers[dReg][registerLength-1]=1;
                }
                else Arrays.fill(registers[dReg],0);
                break;
            case "010101": //BEQ
                long bEQ = Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2);
                long dEQ = Long.parseLong(Convert.BinArrayToBinString(registers[dReg]),2);
                if(bEQ==dEQ){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
            case "010110": //BNE
                long bNE = Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2);
                long dNE = Long.parseLong(Convert.BinArrayToBinString(registers[dReg]),2);
                if(bNE!=dNE){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
            case "010111": //BEZ
                if(Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2)==0){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
            case "011000": //BNZ
                if(Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2)!=0){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
            case "011001": //BGZ
                if(Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2)>0){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
            case "011010": //BLZ
                if(Long.parseLong(Convert.BinArrayToBinString(registers[bReg]),2)<0){
                    //jumps to given instruction - 1, adjusted by increment
                    programCounter=(address/4)-1;
                }
                break;
        }


    }

    //decodes and executes unconditional jump instructions
    private void decodeAndExecuteUnconditionalJump(String opCode){

        switch(opCode){
            case "010010": //HLT
                //end of job, stats, free up memory
                ram.freeSpace(pcb.getJobAddress(),
                        pcb.getJobLength()+pcb.getTotalBufferSize());
                //TODO //stats
                pcb.setJobCompletedTime(System.currentTimeMillis());
                break;
            case "010100": //JMP
                String addressStr ="";
                for(int i=8; i<32;i++){
                    addressStr += instr[i];
                }
                int address = Integer.parseInt(addressStr,2);

                //jumps to given instruction - 1, adjusted by increment
                programCounter=(address/4)-1;
                //TODO //check
                break;
        }

    }

    //decodes and executes I/O instructions
    private void decodeAndExecuteIO(String opCode){
        String reg1Str="",reg2Str="", addressStr="";
        for(int i=8; i<12;i++){
            reg1Str += instr[i];
        }
        for(int i=12; i<16;i++){
            reg2Str += instr[i];
        }
        for(int i=16; i<32;i++){
            addressStr += instr[i];
        }

        int reg1 = Integer.parseInt(reg1Str,2);
        int reg2 = Integer.parseInt(reg2Str,2);
        int address = Integer.parseInt(addressStr,2);

        IOcount++;

        switch(opCode){
            case "000000": //RD
                if(address>0)
                    registers[reg1] = ram.read(pcb.getInputBufferAddress()); //reads from RAM address into reg1 (DMA)
                else{
                    registers[reg1] = ram.read(Integer.parseInt(Convert.BinArrayToBinString(registers[reg2]),2)); //reads from reg2 into reg1
                  }
                break;
            case "000001": //WR
                if(address>0){
                    long decimal = Long.parseLong(Convert.BinArrayToBinString(registers[reg1]),2);
                    ram.write(pcb.getOutputBufferAddress(), "0x"+Long.toString(decimal,16)); //writes to RAM from reg1 (DMA)
                }
                else{
                    long decimal = Long.parseLong(Convert.BinArrayToBinString(registers[reg1]),2);
                    ram.write(Integer.parseInt(Convert.BinArrayToBinString(registers[reg2]),2), "0x"+Long.toString(decimal,16)); //writes to reg2 from reg1
                }
                break;
        }

    }

    public boolean isAvailable(){
        return isAvailable;
    }



}
