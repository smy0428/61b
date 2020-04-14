import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestGraphDBLocal {
    private static GraphDB graph;
    private static GraphDB graphSmall;

    private static final String OSM_DB_PATH = "../library-sp18/data/berkeley-2018.osm.xml";
    private static final String OSM_DB_PATH_SMALL =
            "../library-sp18/data/berkeley-2018-small.osm.xml";
    private static boolean initialized = false;

    /**
     * Initializes the student graphs.
     * You should not need to modify this code. If you do, then the Autograder
     * may not work with your code.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        if (initialized) {
            return;
        }
        graphSmall = new GraphDB(OSM_DB_PATH_SMALL);
        initialized = true;
    }

    @Test
    public void testNodeCountSmall() {
        Iterable<Long> ids = graphSmall.vertices();
        int numberOfNodes = countIterableItems(ids);
        assertEquals("Your graph should have 21 nodes after cleaning. Consider removing the call"
                + " to clean and seeing if you get 250 nodes as expected as a sanity check on"
                + "  your results before calling clean.", 21, numberOfNodes);
    }

    static <Item> int countIterableItems(Iterable<Item> it) {
        int N = 0;
        for (Item x : it) {
            N += 1;
        }
        return N;
    }
}
