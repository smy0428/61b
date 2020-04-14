import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private double rootLonDPP;
    private double ROOT_ULLAT;
    private double ROOT_ULLON;
    private double ROOT_LRLAT;
    private double ROOT_LRLON;

    public Rasterer() {
        ROOT_ULLAT = MapServer.ROOT_ULLAT;
        ROOT_ULLON = MapServer.ROOT_ULLON;
        ROOT_LRLAT = MapServer.ROOT_LRLAT;
        ROOT_LRLON = MapServer.ROOT_LRLON;
        rootLonDPP = (ROOT_LRLON - ROOT_ULLON) / MapServer.TILE_SIZE;
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        //System.out.println(params);
        Map<String, Object> results = new HashMap<>();
        //System.out.println("Since you haven't implemented getMapRaster, nothing is displayed in "
                          // + "your browser.");

        Boolean query = querySuccess(params);
        results.put("query_success", query);

        if (query) {
            double lon = lonDPP(params);
            int depth = depth(lon);

            String [][] render = renderGrid(params, depth);
            double ullon = rasterUllon(params, depth);
            double ullat = rasterUllat(params, depth);
            double lrlon = rasterLrlon(params, depth);
            double lrlat = rasterLrlat(params, depth);

            results.put("render_grid", render);
            results.put("raster_ul_lon", ullon);
            results.put("raster_ul_lat", ullat);
            results.put("raster_lr_lon", lrlon);
            results.put("raster_lr_lat", lrlat);
            results.put("depth", depth);


        // check the corner case
        } else {

            double ullat = MapServer.ROOT_ULLAT;
            double ullon = MapServer.ROOT_ULLON;
            double lrlat = MapServer.ROOT_LRLAT;
            double lrlon = MapServer.ROOT_LRLON;

            results.put("render_grid", new String[1][1]);
            results.put("raster_ul_lon", ullon);
            results.put("raster_ul_lat", ullat);
            results.put("raster_lr_lon", lrlon);
            results.put("raster_lr_lat", lrlat);

        }
        return results;
    }


    // calculate the expected longitudinal distance per pixel (LonDPP)
    public double lonDPP(Map<String, Double> params) {
        double ullonX = params.get("ullon");
        double lrlonX = params.get("lrlon");
        double w = params.get("w");
        double lon = (lrlonX - ullonX) / w;
        return lon;
    }


    // calculate the depth from the expected LonDPP
    public int depth(double lon) {
        double currLonDPP = rootLonDPP;
        int depth = 0;

        // highest depth is 7
        while (lon < currLonDPP && depth < 7) {
            currLonDPP /= 2;
            depth += 1;
        }
        return depth;
    }


    public double lonUnit(int depth) {
        double u = (ROOT_LRLON - ROOT_ULLON) / Math.pow(2, depth);
        return u;
    }


    public double latUnit(int depth) {
        double u = (ROOT_ULLAT - ROOT_LRLAT) / Math.pow(2, depth);
        return u;
    }

    public int getulX(Map<String, Double> params, int depth) {
        double ullonX = params.get("ullon");
        double unit = lonUnit(depth);

        int x = (int) ((ullonX - ROOT_ULLON) / unit);
        return x;
    }


    public int getlrX(Map<String, Double> params, int depth) {
        double lrlonX = params.get("lrlon");
        double unit = lonUnit(depth);

        int x = (int) ((lrlonX - ROOT_ULLON) / unit);
        return x;
    }


    public int getulY(Map<String, Double> params, int depth) {
        double ullatY = params.get("ullat");
        double unit = latUnit(depth);

        int y = (int) ((ROOT_ULLAT - ullatY) / unit);
        return y;
    }


    public int getlrY(Map<String, Double> params, int depth) {
        double lrlatY = params.get("lrlat");
        double unit = latUnit(depth);

        int y = (int) ((ROOT_ULLAT - lrlatY) / unit);
        return y;
    }


    public double rasterUllon(Map<String, Double> params, int depth) {
        int x = getulX(params, depth);
        double unit = lonUnit(depth);

        double lon = ROOT_ULLON + x * unit;
        return lon;
    }

    public double rasterUllat(Map<String, Double> params, int depth) {
        int y = getulY(params, depth);
        double unit = latUnit(depth);

        double lat = ROOT_ULLAT - y * unit;
        return lat;
    }

    public double rasterLrlon(Map<String, Double> params, int depth) {
        int x = getlrX(params, depth);
        double unit = lonUnit(depth);

        double lon = ROOT_ULLON + (x + 1) * unit;
        return lon;
    }


    public double rasterLrlat(Map<String, Double> params, int depth) {
        int y = getlrY(params, depth);
        double unit = latUnit(depth);

        double lat = ROOT_ULLAT - (y + 1) * unit;
        return lat;
    }


    public String[][] renderGrid(Map<String, Double> params, int depth) {
        if (querySuccess(params)) {
            int ux = getulX(params, depth);
            int uy = getulY(params, depth);
            int lx = getlrX(params, depth);
            int ly = getlrY(params, depth);
            int maX = (int) Math.pow(2, depth) - 1;

            int startX = Math.max(0, ux);
            int endX = Math.min(lx, maX);
            int startY = Math.max(0, uy);
            int endY = Math.min(ly, maX);

            int xx = endX - startX + 1;
            int yy = endY - startY + 1;

            //System.out.println("xx is:" + xx);
            //System.out.println("uy is:" + uy);
            //System.out.println("ly is:" + ly);
            //System.out.println("yy is:" + yy);


            String[][] str = new String[yy][xx];
            for (int i = 0; i < yy; i += 1) {
                for (int k = 0; k < xx; k += 1) {
                    int currY = i + startY;
                    int currX = k + startX;
                    str[i][k] = "d" + depth + "_x" + currX + "_y" + currY + ".png";
                    //System.out.println(str[i][k]);
                }
            }
            return str;
        }
        return null;
    }


    public boolean querySuccess(Map<String, Double> params) {
        double ux = params.get("ullon");
        double uy = params.get("ullat");
        double lx = params.get("lrlon");
        double ly = params.get("lrlat");

        // query box doesn't make any sense
        if (ux > lx || uy < ly) {
            return false;
        }
        // query box out side of our map
        if (ux < ROOT_ULLON && lx < ROOT_ULLON) {
            return false;
        }
        if (ux > ROOT_LRLON && lx > ROOT_LRLON) {
            return false;
        }
        if (uy < ROOT_LRLAT && ly < ROOT_LRLAT) {
            return false;
        }
        if (uy > ROOT_ULLAT && ly > ROOT_ULLAT) {
            return false;
        }
        return true;
    }
}
