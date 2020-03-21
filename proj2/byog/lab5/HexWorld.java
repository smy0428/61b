package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.awt.*;
import java.util.*;

import java.util.List;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;


    //** simple class to present the lower left corner of the hexagon */
    public static class Position {
        public int x;
        public int y;
        public Position(int pX, int pY) {
            x = pX;
            y = pY;
        }
    }


    public static Position rightDown(Position p, int len) {
        Position q = new Position(p.x + 2 * len - 1, p.y - len);
        return q;
    }


    public static Position rightUp(Position p, int len) {
        Position q = new Position(p.x + 2 * len - 1, p.y + len);
        return q;
    }


    public static int start(int h, int l) {
        int w = l - h - 1;
        if (w >= 0) {
            return w;
        }
        return -(1 + w);
    }

    public static int end(int h, int l) {
        int w = 3 * l - 2 - start(h, l);
        return w;
    }


    //** draw a simple hexagon */
    public static void addHexagon(TETile[][] world, int length, Position p, TETile t) {
        List<Position> drawPosition = new ArrayList<Position>();

        //** get the right positions to draw the hexagon */
        for (int h = 0; h < 2 * length; h += 1) {
            for (int w = start(h, length); w < end(h, length); w += 1) {
                Position q = new Position(w + p.x, h + p.y);
                drawPosition.add(q);
            }
        }

        for (Position pp : drawPosition) {
            world[pp.x][pp.y] = t;
        }
    }


    public static void drawRandomVerticalHexes(TETile[][] world, int length, int num, Position p, TETile t) {
        for (int i = 0; i < num; i += 1) {
            addHexagon(world, length, p, t);
            p = new Position(p.x, p.y + 2 * length);
        }
    }


    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexagonTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexagonTiles[x][y] = Tileset.NOTHING;
            }
        }

        Position start1 = new Position(0, 10);
        TETile t1 = new TETile('@', Color.white, Color.black, "@");
        drawRandomVerticalHexes(hexagonTiles, 3, 3, start1, t1);

        Position start2 = rightDown(start1, 3);
        TETile t2 = new TETile('%', Color.blue, Color.black, "%");
        drawRandomVerticalHexes(hexagonTiles, 3, 4, start2, t2);


        Position start3 = rightDown(start2, 3);
        TETile t3 = new TETile('&', Color.red, Color.black, "%");
        drawRandomVerticalHexes(hexagonTiles, 3, 5, start3, t3);


        Position start4 = rightUp(start3, 3);
        TETile t4 = new TETile('#', Color.green, Color.black, "%");
        drawRandomVerticalHexes(hexagonTiles, 3, 4, start4, t4);

        Position start5 = rightUp(start4, 3);
        TETile t5 = new TETile('*', Color.yellow, Color.black, "%");
        drawRandomVerticalHexes(hexagonTiles, 3, 3, start5, t5);


        // addHexagon(hexagonTiles, 8, start, t);
        ter.renderFrame(hexagonTiles);
    }
}
