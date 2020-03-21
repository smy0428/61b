package byog.Core;

import java.util.Random;

public class Room {
    private Position randomP;
    private Position[] floorTiles;
    private Position[] wallTiles;
    private Random random;

    public Room(Random r) {
        random = r;
    }

    // use width, height and left bottom corner to generate a square
    public void drawRoom (int w, int h, Position p) {
        int x = p.getX();
        int y = p.getY();
        wallTiles = new Position[2 * (w + h - 2)];
        floorTiles = new Position[(w - 2) * (h - 2)];

        // wall bottom line
        for (int i = 0; i < w; i += 1) {
            wallTiles[i] = new Position(x + i, y);
        }
        // wall top line
        for (int i = 0; i < w; i += 1) {
            wallTiles[w + i] = new Position(x + i, y + h - 1);
        }
        // wall left line
        for (int i = 0; i < h - 2; i += 1) {
            wallTiles[2 * w + i] = new Position(x, y + i + 1);
        }
        // wall right line
        for (int i = 0; i < h - 2; i += 1) {
            wallTiles[2 * w + h - 2 + i] = new Position(x + w - 1, y + i + 1);
        }

        // floor square
        int m = 0;
        for (int i = 1; i < w - 1; i += 1) {
            for (int k = 1; k < h - 1; k += 1) {
                floorTiles[m] = new Position(x + i, y + k);
                m += 1;
            }
        }
        int i = RandomUtils.uniform(random, 0, floorTiles.length);
        randomP = floorTiles[i];
    }

    public Position getRandomP() {
        return randomP;
    }

    public Position[] getWallTiles() {
        return wallTiles;
    }

    public Position[] getFloorTiles() {
        return floorTiles;
    }

}
