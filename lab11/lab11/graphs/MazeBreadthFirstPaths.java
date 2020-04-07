package lab11.graphs;

import java.util.ArrayDeque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private ArrayDeque<Integer> pq;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new ArrayDeque<>();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        pq.add(s);
        while (!pq.isEmpty()) {
            int curr = pq.poll();
            marked[curr] = true;
            announce();

            if (curr == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }

            for (int i: maze.adj(curr)) {
                if (!marked[i]) {
                    distTo[i] = distTo[curr] + 1;
                    edgeTo[i] = curr;
                    announce();
                    pq.add(i);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

