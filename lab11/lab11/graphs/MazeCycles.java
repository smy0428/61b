package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        helper(0);
    }

    private void helper(int i) {
        Stack stack = new Stack<Integer>();
        int[] parent = new int[maze.V()];
        parent[i] = i;
        stack.push(i);

        while (!stack.isEmpty()) {
            int v = (int) stack.pop();
            marked[v] = true;
            for (int w: maze.adj(v)) {
                if (w != parent[v]) {
                    if (marked[w]) {
                        edgeTo[w] = v;
                        while (v != w) {
                            edgeTo[v] = parent[v];
                            v = parent[v];
                            announce();
                        }
                        return;
                    }
                    if (!marked[w]) {
                        parent[w] = v;
                        stack.push(w);
                    }
                }
            }
        }
    }
}
