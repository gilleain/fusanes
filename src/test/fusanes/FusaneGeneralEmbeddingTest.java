package test.fusanes;

import java.io.IOException;

import layout.OuterplanarLayout;
import layout.Refiner;
import model.Graph;

import org.junit.Test;

import planar.BlockEmbedding;
import planar.PlanarBlockEmbedder;
import test.planar.AbstractDrawingTest;
import draw.Colorer;
import draw.ParameterSet;
import draw.SignatureColorer;
import fusanes.FusaneGenerator;

public class FusaneGeneralEmbeddingTest extends AbstractDrawingTest {
    
    public static final String OUTPUT_DIR = "output/planar/general";
    
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
    
    public void test(BlockEmbedding em, String filename) throws IOException {
        double radius = 40;
        Colorer colorer = new SignatureColorer();
        Refiner refiner = null;
        draw(em, WIDTH, HEIGHT, filename + ".png", colorer, 
                new OuterplanarLayout(radius), refiner, getParameterSet());
    }
    
    public void testN(int n) throws IOException {
        int counter = 0;
        for (Graph fusane : FusaneGenerator.generate(n)) {
            BlockEmbedding em = PlanarBlockEmbedder.embed(fusane);
            test(em, n + "_" + counter);
            System.out.println("drawing " + counter);
            counter++;
        }
    }
    
    @Test
    public void test4() throws IOException {
        testN(4);
    }
    
}
