package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private static final long SEED = 28042007;
    private static final Random RANDOM = new Random(SEED);



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


    /**
     * not so simple class to present the world map of exterior/wall/floor
     */



    public void addFirst(Room first, World w) {
        for (Position pp: first.getWallTiles()) {
            w.addWall(pp);
        }
        for (Position pp: first.getFloorTiles()) {
            w.addFloor(pp);
        }
    }


    public void addNext(Room first, World w) {
        Room next = first.nextRoom();
        addFirst(next, w);
    }


    public void addNextN(Room first, World w) {
        Room next = first.nextRoom();
        addFirst(next, w);
        while (w.getUsage() < 0.5) {
            addNextN(next, w);
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
        World w = new World(WIDTH, HEIGHT);
        Game game = new Game();
        Room first = new Room(RANDOM, 9, w);
        first.drawRoom(7, 4, start);
        first.inside();
        first.outside();

        game.addFirst(first, w);
        game.addNextN(first, w);


        for (int i = 0; i < w.getPropertyMap().length; i += 1) {
            for (int k = 0; k < w.getPropertyMap()[i].length; k += 1) {
                if (w.getPropertyMap()[i][k].equals("wall")) {
                    finalWorldFrame[i][k] = Tileset.WALL;
                }
                if (w.getPropertyMap()[i][k].equals("floor")) {
                    finalWorldFrame[i][k] = Tileset.MOUNTAIN;
                }
            }
        }


        ter.renderFrame(finalWorldFrame);
    }
}
