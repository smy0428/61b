/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 *
 * @author Akhil Batra, Alexander Hwang
 *
 **/
public class CountingSort {
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     * Does not touch original array (non-destructive method).
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
     */
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = max > i ? max : i;
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // however, below is a more proper, generalized implementation of
        // counting sort that uses start position calculation
        int[] starts = new int[max + 1];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += counts[i];
        }

        int[] sorted2 = new int[arr.length];
        for (int i = 0; i < arr.length; i += 1) {
            int item = arr[i];
            int place = starts[item];
            sorted2[place] = item;
            starts[item] += 1;
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     * Does not touch original array (non-destructive method).
     *
     * @param arr int array that will be sorted
     */


    public static int[] betterCountingSort(int[] arr) {

        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i: arr) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }

        int[] counts = new int[max - min + 1];
        for (int i: arr) {
            counts[i - min] += 1;
        }

        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i + min;
            }
        }
        return sorted;
    }

    /*
    public static int[] betterCountingSort(int[] arr) {
        // make counting sort work with arrays containing negative numbers.

        int a = 0;
        int len = arr.length;

        for (int i: arr) {
            if (i < 0) {
                a += 1;
            }
        }

        // none negative, use helper approach
        if (a == 0) {
            return helperSort(arr);
        }

        // only negative, use helper approach
        if (a == len) {
            int[] temp = new int[len];
            int index = 0;
            for (int i: arr) {
                temp[index] = -i;
                index += 1;
            }
            int[] temp2 = helperSort(temp);
            for (int j = 0; j < len; j += 1) {
                temp[j] = -1 * temp2[len - j];
            }
            return temp;
        }


        // split the negative and positive integer
        int[] neg = new int[a];
        int[] pos = new int[len - a];

        int aIndex = 0;
        int bIndex = 0;
        for (int i: arr) {
            if (i < 0) {
                neg[aIndex] = -i;
                aIndex += 1;
            } else {
                pos[bIndex] = i;
                bIndex += 1;
            }
        }

        int[] sortNeg = helperSort(neg);
        int[] sortPos = helperSort(pos);
        int[] sorted = new int[len];

        int indexNeg = a - 1;
        for (int i: sortNeg) {
            sorted[indexNeg] = -i;
            indexNeg -= 1;
        }

        int indexPos = a;
        for (int i: sortPos) {
            sorted[indexPos] = i;
            indexPos += 1;
        }
        return sorted;
    }
    */


    private static int[] helperSort(int[] arr) {
        // find max
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            max = Math.max(max, i);
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i]++;
        }

        // when we're dealing with ints, we can just put each value
        // count number of times into the new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }
        // return the sorted array
        return sorted;
    }
}
