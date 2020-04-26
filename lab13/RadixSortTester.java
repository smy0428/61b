import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {

    private static String[] someString = {"abbc", "accb", "aa", "bcad", "cads", "iegh", "lkhs", "zsdf", "y9123", "u71ba", "jad", "yy", "swed"};
    private static String[] easyString = {"casdf", "bwqef", "as"};

    public static void main(String[] args) {
        String[] sorted = RadixSort.sort(someString);
        String[] sorted2 = RadixSort.sort(easyString);
        for (String s: sorted2) {
            System.out.println(s);
        }
    }
}
