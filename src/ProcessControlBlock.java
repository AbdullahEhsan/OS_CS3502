//By Abdullah


public class ProcessControlBlock {

    private int jobID, jobLength, jobPriority, jobAddress;
    private int inputBufferSize, outputBufferSize, tempBufferSize, totalBufferSize, bufferAddress;

    private long startTime, endWaitTime, jobCompletedTime;

    public ProcessControlBlock()
    {

    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getJobLength() {
        return jobLength;
    }

    public void setJobLength(int jobLength) {
        this.jobLength = jobLength;
    }

    public int getJobPriority() {
        return jobPriority;
    }

    public void setJobPriority(int jobPriority) {
        this.jobPriority = jobPriority;
    }

    public int getJobAddress() {
        return jobAddress;
    }

    public void setJobAddress(int jobAddress) {
        this.jobAddress = jobAddress;
    }

    public int getInputBufferSize() {
        return inputBufferSize;
    }

    public void setInputBufferSize(int inputBufferSize) {
        this.inputBufferSize = inputBufferSize;
    }

    public int getOutputBufferSize() {
        return outputBufferSize;
    }

    public void setOutputBufferSize(int outputBufferSize) {
        this.outputBufferSize = outputBufferSize;
    }

    public int getTempBufferSize() {
        return tempBufferSize;
    }

    public void setTempBufferSize(int tempBufferSize) {
        this.tempBufferSize = tempBufferSize;
    }

    public int getTotalBufferSize() {
        return inputBufferSize+outputBufferSize+tempBufferSize;
    }

    public void setTotalBufferSize(int totalBufferSize) {
        this.totalBufferSize = totalBufferSize;
    }

    public int getBufferAddress() {
        return bufferAddress;
    }

    public int getInputBufferAddress(){
        return getBufferAddress();
    }

    public int getOutputBufferAddress(){
        return getBufferAddress()+getInputBufferSize();
    }

    public int getTempBufferAddress(){
        return getBufferAddress()+getInputBufferSize()+getOutputBufferSize();
    }

    public void setBufferAddress(int bufferAddress) {
        this.bufferAddress = bufferAddress;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndWaitTime() {
        return endWaitTime;
    }

    public void setEndWaitTime(long endWaitTime) {
        this.endWaitTime = endWaitTime;
    }

    public long getJobCompletedTime() {
        return jobCompletedTime;
    }

    public void setJobCompletedTime(long jobCompletedTime) {
        this.jobCompletedTime = jobCompletedTime;
    }

    public long getWaitTime(){
        return getEndWaitTime()-getStartTime();
    }

    public long getCompletionTime(){
        return getJobCompletedTime()-getEndWaitTime();
    }
}
