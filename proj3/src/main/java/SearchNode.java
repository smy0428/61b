import java.util.Comparator;

public class SearchNode {

    private long id;
    private SearchNode pre;
    private double distance;
    private double heuristic;


    SearchNode(long id, SearchNode pre, double dis, double heu) {
        this.id = id;
        this.pre = pre;
        this.distance = dis;
        this.heuristic = heu;
    }


    public long getId() {
        return id;
    }


    public SearchNode getPre() {
        return pre;
    }


    public double getDistance() {
        return distance;
    }

    public static class NodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode n1, SearchNode n2) {
            double temp = n1.distance + n1.heuristic - n2.distance - n2.heuristic;
            if (temp > 0) {
                return 1;
            }
            if (temp == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
