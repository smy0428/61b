import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        ArrayList<Long> results = new ArrayList<>();
        PriorityQueue<SearchNode> pq = new PriorityQueue<>(new SearchNode.NodeComparator());
        Set<Long> mark = new HashSet<>();

        long start = g.closest(stlon, stlat);
        long end = g.closest(destlon, destlat);

        SearchNode root = new SearchNode(start, null, 0, 0);
        pq.add(root);

        while (!pq.isEmpty()) {
            SearchNode currN = pq.poll();
            long currL = currN.getId();
            mark.add(currL);

            if (currL == end) {
                SearchNode nn = currN;
                while (nn != null) {
                    results.add(nn.getId());
                    nn = nn.getPre();
                }
                Collections.reverse(results);
                return results;
            }

            for (long nextL : g.adjacent(currL)) {
                if (!mark.contains(nextL)) {
                    double dis = currN.getDistance() + g.distance(currL, nextL);
                    double heu = g.distance(nextL, end);

                    if (currN == root) {
                        SearchNode nextN = new SearchNode(nextL, currN, dis, heu);
                        pq.add(nextN);
                    } else if (nextL != currN.getPre().getId()) {
                        SearchNode nextN = new SearchNode(nextL, currN, dis, heu);
                        pq.add(nextN);
                    }
                }
            }
        }
        return results;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {

        List<NavigationDirection> directions = new ArrayList<>();

        if (route.size() < 1) {
            return directions;
        }

        if (route.size() == 1) {
            NavigationDirection arrive = new NavigationDirection();

            String name = g.getStreet(route.get(0));
            arrive.way = name;
            directions.add(arrive);
            return directions;
        }

        int size = route.size();
        int direction = 0;
        double dist = g.distance(route.get(0), route.get(1));
        double prevBearing = g.bearing(route.get(0), route.get(1));
        String prevName = g.getStreet(route.get(0));


        for (int i = 1; i < size - 1; i += 1) {
            double currBearing = g.bearing(route.get(i), route.get(i + 1));
            double diff = currBearing - prevBearing;
            double currDist = g.distance(route.get(i), route.get(i + 1));
            String currName = g.getStreet(route.get(i));
            boolean change = g.changeRoad(route.get(i));

            if (!change) {
                dist += currDist;

            } else {

                NavigationDirection nd = new NavigationDirection();
                nd.distance = dist;
                nd.direction = direction;
                nd.way = prevName;
                directions.add(nd);


                if (diff >= -15 && diff <= 15) {
                    direction = NavigationDirection.STRAIGHT;
                } else if (diff < -15 && diff >= -30) {
                    direction = NavigationDirection.SLIGHT_LEFT;
                } else if (diff > 15 && diff <= 30) {
                    direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (diff < -30 && diff > -100) {
                    direction = NavigationDirection.LEFT;
                } else if (diff > 30 && diff <= 100) {
                    direction = NavigationDirection.RIGHT;
                } else if (diff < -100) {
                    direction = NavigationDirection.SHARP_LEFT;
                } else if (diff > 100) {
                    direction = NavigationDirection.SHARP_RIGHT;
                }

                dist = 0;

            }
            prevName = currName;
            prevBearing = currBearing;
        }

        NavigationDirection last = new NavigationDirection();
        last.way = prevName;
        last.distance = dist;
        last.direction = direction;
        directions.add(last);

        return directions;
    }


    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
