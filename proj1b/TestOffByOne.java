import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testEqualChars() {
        assertFalse(offByOne.equalChars('a', 'c'));
        assertFalse(offByOne.equalChars('a', 'B'));
        assertFalse(offByOne.equalChars('c', 'c'));
        assertFalse(offByOne.equalChars('?', '!'));
        assertFalse(offByOne.equalChars(' ', '"'));
        assertTrue(offByOne.equalChars('A', 'B'));
        assertTrue(offByOne.equalChars('@', 'A'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('b', 'c'));
        assertTrue(offByOne.equalChars('y', 'z'));
    }
}
