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
        int[] dec = {1, 2, 6, 9, 10, 3};
        int minterms = 5;
        String[] binaryStrings = new String[dec.length];

        for(int i = 0; i < dec.length; i++){
            binaryStrings[i] = decToBinary(dec[i], minterms);
        }

        // Print original Decimal and Binary table
        System.out.printf("%-10s %-15s%n", "Decimal", "Binary");
        for (int i = 0; i < binaryStrings.length; i++) {
            System.out.printf("%-10d %-15s%n", dec[i], binaryStrings[i]);
        }

        HashMap<Integer, List<String>> grouping = new HashMap<>();
        for(String binary : binaryStrings){
            int onesCount = countOnes(binary);
            grouping.putIfAbsent(onesCount, new ArrayList<>());
            grouping.get(onesCount).add(binary);
        }

        System.out.println("\nGrouped by number of 1's:");
        for (Map.Entry<Integer, List<String>> entry : grouping.entrySet()) {
            System.out.println("Group " + entry.getKey() + " 1's:");
            for (String binary : entry.getValue()) {
                System.out.println("binary: " + binary);
            }
        }

        // Map to hold merged terms and their corresponding decimal values
        HashMap<String, List<Integer>> mergedTermsMap = new HashMap<>();

        // Merging terms that differ by one bit
        System.out.println("\nMerging terms that differ by one bit:");
        for (int i = 0; i < binaryStrings.length; i++) {
            for (int j = i + 1; j < binaryStrings.length; j++) {
                // Use compareMinterms to check if they can be merged
                if (compareMinterms(binaryStrings[i], binaryStrings[j])) {
                    // Merge the two terms
                    String merged = mergeMinterms(binaryStrings[i], binaryStrings[j]);

                    // Add decimal values to merged terms map
                    mergedTermsMap.putIfAbsent(merged, new ArrayList<>());
                    mergedTermsMap.get(merged).add(dec[i]);
                    mergedTermsMap.get(merged).add(dec[j]);

                    System.out.println("Merged: " + binaryStrings[i] + " (" + dec[i] + ")" +
                            " and " + binaryStrings[j] + " (" + dec[j] + ") -> " + merged);
                }
            }
        }

        // Print all merged terms with their corresponding decimal values
        System.out.println("\nFinal Merged Terms with Decimals:");
        for (Map.Entry<String, List<Integer>> entry : mergedTermsMap.entrySet()) {
            System.out.println("Binary: " + entry.getKey() + ", Decimals: " + entry.getValue());
        }
    }
}

