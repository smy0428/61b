package hw4.puzzle;
import java.util.Comparator;

public class SearchNode {
    private WorldState currState;
    private int moves;
    private SearchNode prevNode;
    private int distanceToGoal;

    public SearchNode(WorldState curr, int i, SearchNode prev) {
        currState = curr;
        moves = i;
        prevNode = prev;
        distanceToGoal = curr.estimatedDistanceToGoal();
    }

    public WorldState world() {
        return currState;
    }

    public SearchNode prev() {
        return prevNode;
    }

    public int moveNum() {
        return moves;
    }

    public int moveAndDistance() {
        return moves + distanceToGoal;
    }

    public static class MoveComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return node1.moveAndDistance() - node2.moveAndDistance();
        }
    }
}
