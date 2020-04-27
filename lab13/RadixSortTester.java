import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {

    private static String[] someString = {"abbc", "accb", "aa", "bcad", "cads", "iegh", "lkhs", "zsdf", "y9123", "u71ba", "jad", "yy", "swed"};
    private static String[] easyString = {"200", "aa", "as"};

    public static void main(String[] args) {
        String[] sorted = RadixSort.sort(someString);
        String[] sorted2 = RadixSort.sort(easyString);
        int i = (char) 255;
        String stt = "a";
        int[] ab = new int[0];
        for (int l: ab) {
            System.out.println(l);
        }
        //System.out.println(ab.length);
        //int j = RadixSort.getInt(stt, 0);
       // int k = (int)stt.charAt(0);
       // System.out.println(j);
       // System.out.println(k);
       // for (String s: sorted) {
           // System.out.println(s);
       // }
    }
}
