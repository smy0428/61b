import static org.junit.Assert.*;

import org.junit.Test;


public class FlikTest {
    @Test
    public void Fliktest() {
    int a = 2;
    int b = 2;
    int c = 3;
    int d = 200;
    int e = 200;
    boolean boo_1 = Flik.isSameNumber(a, b);
    boolean boo_2 = Flik.isSameNumber(a, c);
    boolean boo_3 = Flik.isSameNumber(d, e);
    assertTrue(boo_1);
    assertFalse(boo_2);
    System.out.println(boo_3);
    assertTrue(boo_3);
    }

}
