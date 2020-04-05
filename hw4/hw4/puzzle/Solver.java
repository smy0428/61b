package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;

public class Solver {
    private int minMoves;
    private ArrayList<WorldState> worldStates = new ArrayList<>();

    public Solver(WorldState initial) {
        SearchNode root = new SearchNode(initial, 0, null);
        HashSet<WorldState> record = new HashSet<>();
        MinPQ<SearchNode> queue = new MinPQ<>(new SearchNode.MoveComparator());
        queue.insert(root);


        while (!queue.isEmpty()) {
            SearchNode currNode = queue.delMin();
            WorldState currWorld = currNode.world();
            record.add(currWorld);
            int m  = currNode.moveNum();
            SearchNode prevNode = currNode.prev();
            WorldState prevWorld = null;
            if (prevNode != null) {
                prevWorld = prevNode.world();
            }

            // check if it is done and return
            if (currWorld.isGoal()) {
                minMoves = m;
                worldStates.add(currWorld);
                while (currNode != root) {
                    currNode = currNode.prev();
                    prevWorld = currNode.world();
                    worldStates.add(prevWorld);
                }
                Collections.reverse(worldStates);
                return;
            }

            // add appropriate worlds to the PQ
            for (WorldState nextWorld: currWorld.neighbors()) {
                if (nextWorld != prevWorld && !record.contains(nextWorld)) {
                    SearchNode nextNode = new SearchNode(nextWorld, m + 1, currNode);
                    queue.insert(nextNode);
                }
            }
        }
    }

    public int moves() {
        return minMoves;
    }

    public Iterable<WorldState> solution() {
        return worldStates;
    }
}
