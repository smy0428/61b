import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestRasterLocal {

    public static void main(String[] args) {
        Rasterer r = new Rasterer();

        Map<String, Double> para1 = new HashMap<>();
        para1.put("ullon", -122.23995662778569);
        para1.put("ullat", 37.877266154010954);
        para1.put("lrlon", -122.22275132672245);
        para1.put("lrlat", 37.85829260830337);
        para1.put("w", 613.0);
        para1.put("h", 676.0);

        Map<String, Double> para2 = new HashMap<>();
        para2.put("ullon", -122.29288796055374);
        para2.put("ullat", 37.88362657285339);
        para2.put("lrlon", -122.2756847672312);
        para2.put("lrlat", 37.85601498428901);
        para2.put("w", 557.0);
        para2.put("h", 894.0);

        Map<String, Double> para3 = new HashMap<>();
        para3.put("ullon", -122.2325986736921);
        para3.put("ullat", 37.840256238827735);
        para3.put("lrlon", -122.23108224034448);
        para3.put("lrlat", 37.83815211143175);
        para3.put("w", 498.0);
        para3.put("h", 691.0);

        Map<String, Double> para8 = new HashMap<>();
        para8.put("ullon", -122.23326874429999);
        para8.put("ullat", 37.846094782003604);
        para8.put("lrlon", -122.2325964987882);
        para8.put("lrlat", 37.84560520035915);
        para8.put("w", 633.0);
        para8.put("h", 461.0);



        double l1 = r.lonDPP(para1);
        double l2 = r.lonDPP(para2);
        double l3 = r.lonDPP(para3);
        double l8 = r.lonDPP(para8);

        int d1 = r.depth(l1);
        int d2 = r.depth(l2);
        int d3 = r.depth(l3);
        int d8 = r.depth(l8);



        int ulX1 = r.getulX(para1, d1);
        int ulY1 = r.getulY(para1, d1);
        int lrX1 = r.getlrX(para1, d1);
        int lrY1 = r.getlrY(para1, d1);

        int ulX2 = r.getulX(para2, d2);

        double ullon1 = r.rasterUllon(para1, d1);
        double ullon2 = r.rasterUllon(para2, d2);
        double ullon3 = r.rasterUllon(para3, d3);
        double ullon8 = r.rasterUllon(para8, d8);

        double lrlat1 = r.rasterLrlat(para1, d1);
        double lrlat2 = r.rasterLrlat(para2, d2);
        double lrlat3 = r.rasterLrlat(para3, d3);
        double lrlat8 = r.rasterLrlat(para8, d8);

        String[][] str1 = r.renderGrid(para1, d1);
        String[][] str2 = r.renderGrid(para2, d2);
        String[][] str3 = r.renderGrid(para3, d3);
        String[][] str8 = r.renderGrid(para8, d8);


        //assertEquals("Did not match the 1st expected depth", 4, d1);
        //assertEquals("Did not match the 2nd expected depth", 4, d2);
        //assertEquals("Did not match the 3rd expected depth", 7, d3);
        //assertEquals("Did not match the 8th expected depth", 7, d8);

        //assertEquals("Did not match the 1st expected ulX", 10, ulX1);
        //assertEquals("Did not match the 1st expected ulY", 3, ulY1);
        //assertEquals("Did not match the 1st expected lrX", 14, lrX1);
        //assertEquals("Did not match the 1st expected lrY", 7, lrY1);
        //assertEquals("Did not match the 2nd expected ulX", 1, ulX2);

        //assertEquals(-122.244873046875, ullon1, 0.0f);
        //assertEquals(-122.244873046875, ullon1, 0.0f);
        //assertEquals(-122.2943115234375, ullon2, 0.0f);
        //assertEquals(-122.23320007324219, ullon3, 0.0f);
        //assertEquals(-122.23388671875, ullon8, 0.0f);

        assertEquals(37.85749899038596, lrlat1, 0.0f);
        assertEquals(37.85316192077866, lrlat2, 0.0f);
        assertEquals(37.83798217715311, lrlat3, 0.0f);
        assertEquals(37.845572048965884, lrlat8, 0.0f);

        assertEquals("d4_x10_y3.png", str1[0][0]);
        assertEquals(5, str1.length);
        assertEquals(5, str1[0].length);

        assertEquals("d4_x1_y1.png", str2[0][0]);
        assertEquals(8, str2.length);
        assertEquals(4, str2[0].length);

        assertEquals("d7_x97_y95.png", str3[0][0]);
        assertEquals(5, str3.length);
        assertEquals(4, str3[0].length);

        assertEquals("d7_x96_y85.png", str8[0][0]);
        assertEquals(1, str8.length);
        assertEquals(2, str8[0].length);
    }
}
