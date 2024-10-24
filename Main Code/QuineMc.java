package up.edu;

import java.util.*;

public class QuineMc{
    private static String decToBinary(int dec, int minterms){
        int binary[] = new int[minterms];
        int curr = minterms - 1;
        while(dec > 0){
            binary[curr--] = dec % 2;
            dec = dec/2;
        }

        StringBuilder binaryString = new StringBuilder();
        for(int i = 0; i < minterms; i++){
            binaryString.append(binary[i]);
        }
        return binaryString.toString();
    }

    private static int countOnes(String binary){
        int count = 0;
        for(int i = 0; i < binary.length(); i++){
            if(binary.charAt(i) == '1'){
                count += 1;
            }
        }
        return count;
    }

    private static boolean compareMinterms(String a, String b){
        int temp = 0;
        for(int i = 0; i < a.length(); i++){
            if( a.charAt(i) != b.charAt(i)){
                temp += 1;

                if (temp > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int mismatchedIndex(String a, String b){
        int index = -1;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                index = i;
                return index;
            }
        }
        return index;
    }

    private static String mergeMinterms(String a, String b){
        int index = mismatchedIndex(a, b);
        StringBuilder merged = new StringBuilder(a);
        merged.setCharAt(index, '-');
        return merged.toString();
    }

    public static void main(String[] args){
        int[] dec = {1,2,6,9,10,3};
        int minterms = 5;
        String[] binaryStrings = new String[dec.length];

        for(int i = 0; i < dec.length; i++){
            binaryStrings[i] = decToBinary(dec[i],minterms);
        }

        for (int i = 0; i < binaryStrings.length; i++) {
            System.out.println("Decimal: " + dec[i] + ", Binary: " + binaryStrings[i]);
        }

        HashMap<Integer, List<String>> grouping = new HashMap<>();

        for(String binary : binaryStrings){
            int onesCount = countOnes(binary);
            grouping.putIfAbsent(onesCount, new ArrayList<>());
            grouping.get(onesCount).add(binary);
        }

        System.out.println("Grouped by number of 1's:");
        for (Map.Entry<Integer, List<String>> entry : grouping.entrySet()) {
            System.out.println("Group " + entry.getKey() + " 1's:");
            for (String binary : entry.getValue()) {
                System.out.println("binary: " + binary);
            }
        }

    }
}