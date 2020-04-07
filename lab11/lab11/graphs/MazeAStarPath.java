package lab11.graphs;
import edu.princeton.cs.algs4.MinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return  Math.abs(maze.toX(t) - maze.toX(v)) + Math.abs(maze.toY(t) - maze.toY(v));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int ss) {
        MinPQ<Node> pq = new MinPQ<>();
        pq.insert(new Node(ss));
        announce();

        while (!pq.isEmpty()) {
            Node n = pq.delMin();
            int i = n.getX();
            marked[i] = true;
            announce();

            if (i == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }

            for (int j: maze.adj(i)) {
                if (!marked[j]) {
                    distTo[j] = distTo[i] + 1;
                    edgeTo[j] = i;
                    announce();
                    pq.insert(new Node(j));
                }
            }
        }
    }

    private class Node implements Comparable<Node> {
        private int x;
        private int y;
        Node(int i) {
            x = i;
            y = h(i) + distTo[i];
        }

        public int getX() {
            return x;
        }

        @Override
        public int compareTo(Node n) {
            return this.y - n.y;
        }
    }

    @Override
    public void solve() {
        astar(s);
    }
}
