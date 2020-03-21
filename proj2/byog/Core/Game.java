package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final long SEED = 28042007;
    private static final Random RANDOM = new Random(SEED);
    Position start;
    public static int a;
    public static int b;



    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        return;
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().

        // TETile[][] finalWorldFrame = null;

        /** first generate a empty world. */
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }
        return finalWorldFrame;
    }




    public void addFirstSquare(Room first, Worldmap m) {
        for (Position pp: first.getWallTiles()) {
            m.addWall(pp);
        }
        for (Position pp: first.getFloorTiles()) {
            m.addFloor(pp);
        }
    }


    public void addNextSquare(int width, int height, Room pre, Worldmap m) {
        Room next = new Room(RANDOM);
        next.drawRoom(width, height, pre.getRandomP());
        for (Position pp: next.getWallTiles()) {
            m.addWall(pp);
        }
        for (Position pp: next.getFloorTiles()) {
            m.addFloor(pp);
        }
    }


    public void addNextNSquare(Room pre, Worldmap m) {
        while (m.usageRatio() < 0.5) {
            int randomX = pre.getRandomP().getX();
            int randomY = pre.getRandomP().getY();
            //(x, y) random point in the screen (actually is x > randomX or y > randomY)
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);

            int dX = Math.abs(randomX - x);
            int dY = Math.abs(randomY - y);
            while (dX < 3 || dY < 3 || dX * dY > 81 || (x < randomX && y < randomY)) {
                x = RANDOM.nextInt(WIDTH);
                y = RANDOM.nextInt(HEIGHT);
                dX = Math.abs(randomX - x);
                dY = Math.abs(randomY - y);
            }

            // get the right width, height, start point of new room
            int newX = Math.min(randomX, x);
            int newY = Math.min(randomY, y);
            System.out.println("newX is " + newX + ". newY is " + newY + ".");
            Position newP = new Position(newX, newY);
            int width = Math.abs(randomX - x);
            int height = Math.abs(randomY - y);

            pre.drawRoom(width, height, newP);
            for (Position pp : pre.getWallTiles()) {
                m.addWall(pp);
            }
            for (Position pp : pre.getFloorTiles()) {
                m.addFloor(pp);
            }
        }
    }


    /**
     * quick test for the code
     */
    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                finalWorldFrame[x][y] = Tileset.NOTHING;
            }
        }

        Position start = new Position(0, 0);
        Worldmap m = new Worldmap(WIDTH, HEIGHT);
        Game g = new Game();
        Room first = new Room(RANDOM);
        first.drawRoom(7, 4, start);
        g.addFirstSquare(first, m);
        g.addNextNSquare(first, m);


        String[][] propertyMap = m.getPropertyMap();
        for (int i = 0; i < propertyMap.length; i += 1) {
            for (int k = 0; k < propertyMap[i].length; k += 1) {
                if (propertyMap[i][k].equals("floor")) {
                    finalWorldFrame[i][k] = Tileset.MOUNTAIN;
                }
                if (propertyMap[i][k].equals("wall")) {
                    finalWorldFrame[i][k] = Tileset.WALL;
                }
            }
        }

        ter.renderFrame(finalWorldFrame);

    }
}
