import static edu.princeton.cs.algs4.StdRandom.uniform;
import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {

    StudentArrayDeque<Integer> stuAd = new StudentArrayDeque<>();
    ArrayDequeSolution<Integer> realAd = new ArrayDequeSolution<>();

    @Test
    public void testStudentArrayDeque() {
        int kinds = 5;
        String message = "";
        for (int i = 0; true; i += 1) {
            int k = uniform(kinds);
            if (k == 0) {
                stuAd.addFirst(i);
                realAd.addFirst(i);
                message += "addFirst(" + i + ")\n";
            }
            if (k == 1) {
                stuAd.addLast(i);
                realAd.addLast(i);
                message += "addLast(" + i + ")\n";
            }
            if (k == 2 && !stuAd.isEmpty() && !realAd.isEmpty()) {
                Integer s = stuAd.removeFirst();
                Integer r = realAd.removeFirst();
                message += "removeFirst()\n";
                assertEquals(message, s, r);
            }
            if (k == 3 && !stuAd.isEmpty() && !realAd.isEmpty()) {
                Integer s = stuAd.removeLast();
                Integer r = realAd.removeLast();
                message += "removeLast()\n";
                assertEquals(message, s, r);
            }
            if (k == 4) {
                Integer s = stuAd.size();
                Integer r = realAd.size();
                message += "size()\n";
                assertEquals(message, s, r);
            }
        }
    }
}
