package byog.Core;
import java.util.Random;

public class Room {
    private Position[] floorTiles;
    private Position[] wallTiles;
    private Position outsideP;
    private Position insideP;
    private Position bottomLeft;
    private Position topRight;
    private Random random;
    private int roomMaxSize;
    private World world;


    public Room(Random r, int maxSize, World w) {
        if (maxSize < 5) {
            throw new IllegalArgumentException("size must be larger than 4: we got " + maxSize);
        }
        random = r;
        roomMaxSize = maxSize;
        world = w;
    }

    // use width, height and left bottom corner to generate a square
    public void drawRoom(int w, int h, Position p) {

        System.out.println("The width is " + w + ", the height is " + h + ", position is " + p.getX() + ", " + p.getY());

        wallTiles = new Position[2 * (w + h - 2)];
        floorTiles = new Position[(w - 2) * (h - 2)];

        bottomLeft = new Position(p.getX(), p.getY());
        topRight = new Position(p.getX() + w - 1, p.getY() + h - 1);

        // wall bottom line
        for (int i = 0; i < w; i += 1) {
            wallTiles[i] = new Position(p.getX() + i, p.getY());
        }
        // wall top line
        for (int i = 0; i < w; i += 1) {
            wallTiles[w + i] = new Position(p.getX() + i, p.getY() + h - 1);
        }
        // wall left line
        for (int i = 0; i < h - 2; i += 1) {
            wallTiles[2 * w + i] = new Position(p.getX(), p.getY() + i + 1);
        }
        // wall right line
        for (int i = 0; i < h - 2; i += 1) {
            wallTiles[2 * w + h - 2 + i] = new Position(p.getX() + w - 1, p.getY() + i + 1);
        }

        // floor square
        int m = 0;
        for (int i = 1; i < w - 1; i += 1) {
            for (int k = 1; k < h - 1; k += 1) {
                floorTiles[m] = new Position(p.getX() + i, p.getY() + k);
                //floorTiles[(i - 1) * (h - 2) + k - 1] = new Position(p.x + i, p.y + k);
                m += 1;
            }
        }
    }


    // random point inside the room, except the corners
    public void inside() {
        int x = RandomUtils.uniform(random, bottomLeft.getX() + 1, topRight.getX());
        int y = RandomUtils.uniform(random, bottomLeft.getY() + 1, topRight.getY());
        System.out.println("bottomLeft X is " + bottomLeft.getX() + ", bottomLeft Y is " + bottomLeft.getY());
        System.out.println("topRight X is " + topRight.getX() + ", topRight Y is " + topRight.getY());

        while ((x == bottomLeft.getX() + 1 && y == bottomLeft.getY() + 1)
                || (x == bottomLeft.getX() + 1 && y == topRight.getY() - 1)
                || (x == topRight.getX() - 1 && y == bottomLeft.getY() + 1)
                || (x == topRight.getX() - 1 && y == topRight.getY() - 1)) {
            x = RandomUtils.uniform(random, bottomLeft.getX() + 1, topRight.getX());
            y = RandomUtils.uniform(random, bottomLeft.getY() + 1, topRight.getY());
        }
        insideP = new Position(x, y);
    }

    public Position getInsideP() {
        return insideP;
    }


    //(x, y) random point outside the room
    public void outside() {
        int x = random.nextInt(world.getWidth());
        int y = random.nextInt(world.getHeight());
        int dX = Math.abs(x - insideP.getX());
        int dY = Math.abs(y - insideP.getY());
        while (dX < 2 || dY < 2 || dX * dY < 2 * roomMaxSize || dX * dY > 4 * roomMaxSize
                || (x >= bottomLeft.getX() && x <= topRight.getX()
                && y >= bottomLeft.getY() && y <= topRight.getY())) {
        //while (dX < 2 || dY < 2 || dX * dY < 3 * roomMaxSize || dX * dY > 4 * roomMaxSize) {
            x = random.nextInt(world.getWidth());
            y = random.nextInt(world.getHeight());
            dX = Math.abs(x - insideP.getX());
            dY = Math.abs(y - insideP.getY());
            System.out.println(" x is " + x + ", y is " + y);
            System.out.println("dX is " + dX + ", dY is " + dY);
        }
        outsideP = new Position(x, y);
    }


    public Position getOutsideP() {
        return outsideP;
    }



    public Room nextRoom() {
        Room next = new Room(random, roomMaxSize, world);

        int width = Math.abs(outsideP.getX() - insideP.getX()) + 1;
        int height = Math.abs(outsideP.getY() - insideP.getY()) + 1;
        int xMin = Math.min(insideP.getX(), outsideP.getX());
        int yMin = Math.min(insideP.getY(), outsideP.getY());
        Position p = new Position(xMin, yMin);
        next.drawRoom(width, height, p);

        next.inside();
        next.outside();
        return next;
    }


    public Position[] getFloorTiles() {
        return floorTiles;
    }


    public Position[] getWallTiles() {
        return wallTiles;
    }

    public Random getRandom() {
        return random;
    }

    public int getRoomMaxSize() {
        return roomMaxSize;
    }

    public World getWorld() {
        return world;
    }
}
