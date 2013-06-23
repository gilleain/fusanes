package test.fusanes;

import fusanes.FusaneGenerator;
import graph.model.Edge;
import graph.model.Graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import layout.OuterplanarLayout;

import org.junit.Test;

import planar.BlockEmbedding;
import planar.DualTreeEmbedder;
import planar.Vertex;
import coloring.AugmentingEdgeColorer;
import coloring.EdgeColorer;
import coloring.SimpleExhaustiveEdgeColorer;
import draw.ParameterSet;
import draw.Representation;

public class FusaneTest {
    
    public static final String OUT_DIR = "output/edgeColors";
    
    public File getFileHandle(String dirString, String filename) {
        File dir = new File(dirString);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, filename);
    }
    
    public void test(int fusaneDualSize, EdgeColorer edgeColorer) {
        int fusaneCount = 0;
        for (Graph fusane : FusaneGenerator.generate(fusaneDualSize)) {
            int colorCount = 0;
            Map<Integer, List<Graph>> hist = new HashMap<Integer, List<Graph>>();
            for (Graph cFusane : edgeColorer.color(fusane)) {
//                System.out.println(colorCount + "\t" + cFusane.getSortedEdgeStringWithEdgeOrder());
                int cSize = getColorCount(cFusane);
                List<Graph> sizeBin;
                if (hist.containsKey(cSize)) {
                    sizeBin = hist.get(cSize);
                } else {
                    sizeBin = new ArrayList<Graph>();
                    hist.put(cSize, sizeBin);
                }
                sizeBin.add(cFusane);
                colorCount++;
            }
            System.out.print("Fusane " + fusaneCount + " has " + colorCount + " colorings\t");
            for (int key : hist.keySet()) {
                System.out.print(key + " : " + hist.get(key).size() + "\t");
            }
            System.out.println();
            fusaneCount++;
        }
    }
    
    public void draw(int fusaneDualSize, int colorSize, String filePrefix, EdgeColorer edgeColorer) throws IOException {
        int w = 400;
        int h = 400;
        int fusaneCount = 0;
        for (Graph fusane : FusaneGenerator.generate(fusaneDualSize)) {
            int colorCount = 0;
            draw(fusane, w, h, filePrefix + "_" + fusaneCount + "_base.png");
            for (Graph cFusane : edgeColorer.color(fusane)) {
                // XXX -expensive : throwing away all the others!
                if (getColorCount(cFusane) == colorSize) {
                    String filename = filePrefix + "_" + fusaneCount + "_" + colorCount + ".png";
                    draw(cFusane, w, h, filename);
                    colorCount++;
                }
            }
            fusaneCount++;
        }
    }
    
    public void draw(Graph graph, int w, int h, String filename) throws IOException {
        int border = 20;
        int ww = w + (2 * border);
        int hh = h + (2 * border);
        BlockEmbedding em = DualTreeEmbedder.embed(graph);
        BufferedImage image = new BufferedImage(ww, hh, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, ww, hh);
        g.setColor(Color.BLACK);
        
        Representation rep = new OuterplanarLayout(50).layout(em, new Rectangle2D.Double(0, 0, w, h));
        rep.draw(g, getParameterSet(), null, getEdgeColorMap(graph, rep));
        
        File outfile = getFileHandle(OUT_DIR, filename);
        ImageIO.write(image, "PNG", outfile);
    }
    
    private Map<planar.Edge, Color> getEdgeColorMap(Graph cFusane, Representation rep) {
        Map<planar.Edge, Color> map = new HashMap<planar.Edge, Color>();
        for (Edge e : cFusane.edges) {
            if (e.o > 1) {
                Vertex a = rep.getVertex(e.a);
                Vertex b = rep.getVertex(e.b);
                map.put(new planar.Edge(a, b), Color.RED);
            }
        }
        return map;
    }

    public ParameterSet getParameterSet() {
        ParameterSet params = new ParameterSet();
        params.set("lineWidth", 2);
        params.set("pointRadius", 5);
        params.set("drawNumberLabels", 0);
        return params;
    }
    
    public int getColorCount(Graph g) {
        int count = 0;
        for (Edge e : g.edges) {
            if (e.o > 1) {
                count++;
            }
        }
        return count;
    }
    
    @Test
    public void simpleColorerOn4() {
        test(4, new SimpleExhaustiveEdgeColorer());
    }
    
    @Test
    public void augmentingColorerOn4() {
        test(4, new AugmentingEdgeColorer());
    }
    
    @Test
    public void drawFourNines() throws IOException {
        draw(4, 9, "four", new AugmentingEdgeColorer());
    }
    
}
