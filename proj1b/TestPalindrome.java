import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    static Palindrome palindrome = new Palindrome();


    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }


    @Test
    public void testIsPalindrome() {
        assertFalse(palindrome.isPalindrome("dog"));
        assertFalse(palindrome.isPalindrome("dogtod"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("adccda"));
        assertTrue(palindrome.isPalindrome("idwswdi"));

        assertTrue(palindrome.isPalindrome("acb", new OffByOne()));
        assertFalse(palindrome.isPalindrome("acegdb", new OffByOne()));
    }
}
