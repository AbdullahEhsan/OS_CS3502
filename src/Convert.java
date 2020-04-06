public class Convert {

    public static int[] LongToBinArray(long l, int arraySize){
        String bin = Long.toBinaryString(l);
        int[] dataArr = new int[arraySize];
        for(int i = 0; i < bin.length(); i++){
            dataArr[arraySize-1-i] = Character.getNumericValue(bin.charAt(bin.length()-1-i));
        }
        return dataArr;
    }

    public static String BinArrayToBinString(int[] arr){
        String data="";
        for (int value : arr) {
            data += value;
        }
        return data;
    }


}
