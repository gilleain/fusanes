package test.fusanes;

import java.io.IOException;

import layout.OuterplanarLayout;
import layout.Refiner;
import model.Graph;

import org.junit.Test;

import planar.BlockEmbedding;
import planar.DualTreeEmbedder;
import test.planar.AbstractDrawingTest;
import draw.Colorer;
import draw.ParameterSet;
import draw.SignatureColorer;
import fusanes.FusaneGenerator;

public class OuterplanarDrawingTest extends AbstractDrawingTest {
    
    public static final String OUTPUT_DIR = "output/planar/outer";
    
    @Override
    public String getOutputDir() {
        return OUTPUT_DIR;
    }
    
    public ParameterSet getParameterSet() {
        ParameterSet params = new ParameterSet();
        params.set("lineWidth", 2);
        params.set("pointRadius", 5);
        params.set("drawNumberLabels", 0);
        return params;
    }
    
    public void allTreesOnNVerticesTest(int n) throws IOException {
        int counter = 0;
        for (Graph g : FusaneGenerator.generate(n)) {
            test(g, "f" + n + "_" + counter);
            counter++;
        }
    }
    
    @Test
    public void allTreesOn4VerticesTest() throws IOException {
        allTreesOnNVerticesTest(4);
    }
    
    @Test
    public void allTreesOn7VerticesTest() throws IOException {
        allTreesOnNVerticesTest(7);
    }
    
    public void test(Graph graph, String filename) throws IOException {
        BlockEmbedding em = DualTreeEmbedder.embed(graph);
        double radius = 40;
        Colorer colorer = new SignatureColorer();
        Refiner refiner = null;
        draw(em, WIDTH, HEIGHT, filename + ".png", colorer, 
                new OuterplanarLayout(radius), refiner, getParameterSet());
    }
    
    @Test
    public void napthalene() throws IOException {
        Graph graph = new Graph("0:1,0:5,1:2,2:3,3:4,4:5,4:6,5:9,6:7,7:8,8:9");
        test(graph, "naptha");
    }
    
    @Test
    public void f3s() throws IOException {
        test(new Graph("0:1, 1:2, 2:3, 3:4, 4:5, 5:0, 0:6, 6:7, 7:8, 8:9, 9:1, 2:10, 10:11, 11:12, 12:13, 13:3"), "f3_0");
        test(new Graph("0:1, 1:2, 2:3, 3:4, 4:5, 5:0, 0:6, 6:7, 7:8, 8:9, 9:1, 4:10, 10:11, 11:12, 12:13, 13:5"), "f3_1");
        test(new Graph("0:1, 1:2, 2:3, 3:4, 4:5, 5:0, 0:6, 6:7, 7:8, 8:9, 9:1, 3:10, 10:11, 11:12, 12:13, 13:4"), "f3_2");
    }
    
    @Test
    public void f4s() throws IOException {
        test(new Graph("0:1, 0:5, 0:6, 1:2, 1:9, 2:3, 2:14, 3:4, 3:17, 4:5, 6:7, 6:10, 7:8, 7:13, 8:9, 10:11, 11:12, 12:13, 14:15, 15:16, 16:17"), "f4_0");
        test(new Graph("0:1, 0:5, 0:6, 1:2, 1:9, 2:3, 3:4, 4:5, 4:14, 5:17, 6:7, 6:10, 7:8, 7:13, 8:9, 10:11, 11:12, 12:13, 14:15, 15:16, 16:17"), "f4_1");
        test(new Graph("0:1, 0:5, 0:6, 1:2, 1:9, 2:3, 3:4, 3:14, 4:5, 4:17, 6:7, 6:10, 7:8, 7:13, 8:9, 10:11, 11:12, 12:13, 14:15, 15:16, 16:17"), "f4_2");
        test(new Graph("0:1, 0:5, 0:6, 1:2, 1:9, 2:3, 2:10, 3:4, 3:13, 4:5, 4:14, 5:17, 6:7, 7:8, 8:9, 10:11, 11:12, 12:13, 14:15, 15:16, 16:17"), "f4_3");
    }
    
    @Test
    public void fusane3() throws IOException {
        Graph graph = new Graph("0:1,0:5,0:9,1:2,1:13,2:3,2:10,3:4,4:5,5:6,6:7,7:8,8:9,10:11,11:12,12:13");
        test(graph, "fus3");
    }
    
    @Test
    public void fusane6_0_Test() throws IOException {
        Graph g = new Graph("0:1,0:5,0:6,10:11,11:12,12:13,14:15,15:16,16:17,18:19,18:22," +
                            "19:20,19:25,1:2,1:9,20:21,22:23,23:24,24:25,2:18,2:3,3:21,3:4," +
                            "4:5,6:10,6:7,7:13,7:8,8:14,8:9,9:17");
        test(g, "fus6_0");
    }
    
    @Test
    public void fusane6_1_Test() throws IOException {
        Graph g = new Graph("0:1,0:5,0:6,1:2,1:9,2:3,3:4,4:5,4:18,5:21,6:7,6:10,7:8,7:13,8:9,8:14," +
                            "9:17,10:11,11:12,12:13,14:15,15:16,16:17,18:19,18:22,19:20,19:25," +
                            "20:21,22:23,23:24,24:25");
        test(g, "fus6_1");
    }
    
    @Test
    public void fusane6_2_Test() throws IOException {
        Graph g = new Graph("0:1,0:5,0:6,1:2,1:9,2:3,3:4,3:18,4:5,4:21,6:7,6:10,7:8,7:13,8:9,8:14," +
                            "9:17,10:11,11:12,12:13,14:15,15:16,16:17,18:19,18:22,19:20,19:25,20:21," +
                            "22:23,23:24,24:25");
        test(g, "fus6_2");
    }

}
