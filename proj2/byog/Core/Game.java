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


    /**
     * simple class to present the position with x and y
     */
    public static class Position {
        private int x;
        private int y;
        public Position(int pX, int pY) {
            x = pX;
            y = pY;
        }
    }


    /**
     * not so simple class to present the world map of exterior/wall/floor
     */
    public static class Worldmap {
        private int floorTiles;
        private int wallTiles;
        private String[][] propertyMap;

        public Worldmap() {
            propertyMap = new String[WIDTH][HEIGHT];
            for (int i = 0; i < WIDTH; i += 1) {
                for (int k = 0; k < HEIGHT; k += 1) {
                    propertyMap[i][k] = "exterior";
                }
            }
        }


        public void addWall(Position p) {
            if (propertyMap[p.x][p.y].equals("exterior")) {
                propertyMap[p.x][p.y] = "wall";
                wallTiles += 1;
            }
        }

        public void addFloor(Position p) {
            if (propertyMap[p.x][p.y].equals("exterior")) {
                propertyMap[p.x][p.y] = "floor";
                floorTiles += 1;
            }
            if (propertyMap[p.x][p.y].equals("wall")) {
                propertyMap[p.x][p.y] = "floor";
                floorTiles += 1;
                wallTiles -= 1;
            }
        }

        public double usageRatio(){
            double usage = 1.0 * (floorTiles + wallTiles) / (WIDTH * HEIGHT);
            return usage;
        }
    }



    public static class Room {
        private Position randomP;
        private Position[] floorTiles;
        private Position[] wallTiles;
        // use width, height and left bottom corner to generate a square
        public void drawRoom (int w, int h, Position p) {
            wallTiles = new Position[2 * (w + h - 2)];
            floorTiles = new Position[(w - 2) * (h - 2)];

            // wall bottom line
            for (int i = 0; i < w; i += 1) {
                wallTiles[i] = new Position(p.x + i, p.y);
            }
            // wall top line
            for (int i = 0; i < w; i += 1) {
                wallTiles[w + i] = new Position(p.x + i, p.y + h - 1);
            }
            // wall left line
            for (int i = 0; i < h - 2; i += 1) {
                wallTiles[2 * w + i] = new Position(p.x, p.y + i + 1);
            }
            // wall right line
            for (int i = 0; i < h - 2; i += 1) {
                wallTiles[2 * w + h - 2 + i] = new Position(p.x + w - 1, p.y + i + 1);
            }

            // floor square
            int m = 0;
            for (int i = 1; i < w - 1; i += 1) {
                for (int k = 1; k < h - 1; k += 1) {
                    floorTiles[m] = new Position(p.x + i, p.y + k);
                    m += 1;
                }
            }
            int i = RandomUtils.uniform(RANDOM, 0, floorTiles.length);
            randomP = floorTiles[i];
        }
    }



    public void addFirstSquare(Room first, Worldmap m) {
        for (Position pp: first.wallTiles) {
            m.addWall(pp);
        }
        for (Position pp: first.floorTiles) {
            m.addFloor(pp);
        }
    }


    public void addNextSquare(int width, int height, Room pre, Worldmap m) {
        Room next = new Room();
        next.drawRoom(width, height, pre.randomP);
        for (Position pp: next.wallTiles) {
            m.addWall(pp);
        }
        for (Position pp: next.floorTiles) {
            m.addFloor(pp);
        }
    }


    public void addNextNSquare(Room pre, Worldmap m) {
        while (m.usageRatio() < 0.5) {
            //(x, y) random point in the screen (actually is x > pre.randomP.x or y > pre.randomP.y)
            int x = RANDOM.nextInt(WIDTH);
            int y = RANDOM.nextInt(HEIGHT);

            int dX = Math.abs(pre.randomP.x - x);
            int dY = Math.abs(pre.randomP.y - y);
            while (dX < 3 || dY < 3 || dX * dY > 81 || (x < pre.randomP.x && y < pre.randomP.y)) {
                x = RANDOM.nextInt(WIDTH);
                y = RANDOM.nextInt(HEIGHT);
                dX = Math.abs(pre.randomP.x - x);
                dY = Math.abs(pre.randomP.y - y);
            }

            // get the right width, height, start point of new room
            int newX = Math.min(pre.randomP.x, x);
            int newY = Math.min(pre.randomP.y, y);
            System.out.println("newX is " + newX + " . newY is " + newY + " .");
            Position newP = new Position(newX, newY);
            int width = Math.abs(pre.randomP.x - x);
            int height = Math.abs(pre.randomP.y - y);

            pre.drawRoom(width, height, newP);
            for (Position pp : pre.wallTiles) {
                m.addWall(pp);
            }
            for (Position pp : pre.floorTiles) {
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
        Worldmap m = new Worldmap();
        Game g = new Game();
        Room first = new Room();
        first.drawRoom(7, 4, start);
        g.addFirstSquare(first, m);
        g.addNextNSquare(first, m);


        for (int i = 0; i < m.propertyMap.length; i += 1) {
            for (int k = 0; k < m.propertyMap[i].length; k += 1) {
                if (m.propertyMap[i][k].equals("floor")) {
                    finalWorldFrame[i][k] = Tileset.MOUNTAIN;
                }
                if (m.propertyMap[i][k].equals("wall")) {
                    finalWorldFrame[i][k] = Tileset.WALL;
                }
            }
        }

        ter.renderFrame(finalWorldFrame);

    }
}
