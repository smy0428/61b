package byog.Core;

/**
 * not so simple class to present the world map of exterior/wall/floor
 */
public class Worldmap {
    private int floorTiles;
    private int wallTiles;
    private String[][] propertyMap;
    private int width;
    private int height;

    public Worldmap(int width, int height) {
        this.width = width;
        this.height = height;

        propertyMap = new String[width][height];
        for (int i = 0; i < width; i += 1) {
            for (int k = 0; k < height; k += 1) {
                propertyMap[i][k] = "exterior";
            }
        }
    }


    public void addWall(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (propertyMap[x][y].equals("exterior")) {
            propertyMap[x][y] = "wall";
            wallTiles += 1;
        }
    }

    public void addFloor(Position p) {
        int x = p.getX();
        int y = p.getY();
        if (propertyMap[x][y].equals("exterior")) {
            propertyMap[x][y] = "floor";
            floorTiles += 1;
        }
        if (propertyMap[x][y].equals("wall")) {
            propertyMap[x][y] = "floor";
            floorTiles += 1;
            wallTiles -= 1;
        }
    }

    public double usageRatio(){
        double usage = 1.0 * (floorTiles + wallTiles) / (width * height);
        return usage;
    }

    public String[][] getPropertyMap() {
        return propertyMap;
    }

}
