package byog.Core;

import java.util.Random;

public class World {
    private int floorTiles;
    private int wallTiles;
    private String[][] propertyMap;
    private int width;
    private int height;
    private Random random;


    public World(int w, int h, Random r) {
        this.width = w;
        this.height = h;
        random = r;

        propertyMap = new String[width][height];
        for (int i = 0; i < width; i += 1) {
            for (int k = 0; k < height; k += 1) {
                propertyMap[i][k] = "exterior";
            }
        }
    }


    public void addWall(Position p) {
        if (propertyMap[p.getX()][p.getY()].equals("exterior")) {
            propertyMap[p.getX()][p.getY()] = "wall";
            wallTiles += 1;
        }
    }

    public void addFloor(Position p) {
        if (propertyMap[p.getX()][p.getY()].equals("exterior")) {
            propertyMap[p.getX()][p.getY()] = "floor";
            floorTiles += 1;
        }
        if (propertyMap[p.getX()][p.getY()].equals("wall")) {
            propertyMap[p.getX()][p.getY()] = "floor";
            floorTiles += 1;
            wallTiles -= 1;
        }
    }

    public void addDoor(int k) {
        for (int i = 0; i < k; i += 1) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            while (!propertyMap[x][y].equals("wall") && !propertyMap[x][y].equals("door")) {
                x = random.nextInt(width);
                y = random.nextInt(height);
            }
            propertyMap[x][y] = "door";
        }
    }

    public double getUsage() {
        double usage = 1.0 * (floorTiles + wallTiles) / (width * height);
        return usage;
    }

    public String[][] getPropertyMap() {
        return propertyMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
