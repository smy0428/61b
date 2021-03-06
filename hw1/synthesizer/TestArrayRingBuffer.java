package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue(2);
        arb.enqueue(3);
        arb.enqueue(4);
        assertEquals(2, arb.peek());
        arb.dequeue();
        assertEquals(3, arb.peek());
        arb.dequeue();
        assertEquals(4, arb.peek());
        assertEquals(1, arb.fillCount);
        arb.dequeue();
        assertTrue(arb.isEmpty());
    }

    @Test
    public void someOtherTest() {
        ArrayRingBuffer<Integer> arbOther = new ArrayRingBuffer(10);
        arbOther.enqueue(6);
        arbOther.enqueue(7);
        arbOther.enqueue(8);
        for (int i: arbOther) {
            System.out.println(i);
        }
    }


    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
