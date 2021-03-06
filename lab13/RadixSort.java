/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // Implement LSD Sort
        int len = asciis.length;
        String[] sorted = new String[len];
        int i = 0;
        int maxLength = 0;
        for (String str: asciis) {
            maxLength = Math.max(maxLength, str.length());
            sorted[i] = str;
            i += 1;
        }

        for (int k = maxLength - 1; k >= 0; k -= 1) {
            sortHelperLSD(sorted, k);
        }

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort

        int size = 257;

        int[] counts = new int[size];
        int[] starts = new int[size];
        int len = asciis.length;
        String[] sorted = new String[len];
        System.arraycopy(asciis, 0, sorted, 0, len);

        int temp;
        for (String str: asciis) {
            temp = getInt(str, index);
            counts[temp] += 1;
        }

        int curr = 0;
        for (int i = 0; i < size; i += 1) {
            starts[i] += curr;
            curr += counts[i];
        }

        int num;
        int pos;
        for (String str: asciis) {
            num = getInt(str, index);
            pos = starts[num];
            starts[num] += 1;
            sorted[pos] = str;
        }

        System.arraycopy(sorted, 0, asciis, 0, len);
    }


    public static int getInt(String str, int index) {
        if (str.length() <= index) {
            return 0;
        } else {
            char c = str.charAt(index);
            int i = (int) c;
            i += 1;
            return i;
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }
}
