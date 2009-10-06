package it.unisa.dia.gas.jpbc.benchmark;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.jpbc.PairingPreProcessing;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import it.unisa.dia.gas.plaf.jpbc.pbc.PBCPairing;

import java.io.File;
import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PBCPairingBenchmark {

    protected Pairing pairing;
    protected String curveParamsFileName;
    protected int times;


    public PBCPairingBenchmark(String curveParamsFileName, int times) {
        this.curveParamsFileName = curveParamsFileName;
        this.times = times;
    }


    public void setUp() {
        pairing = new PBCPairing(getCurveParams());
    }


    public void testPairingPreProcessing() {
        if (pairing == null)
            return;

        long t1 = 0;
        long t2 = 0;

        for (int i = 0; i < times; i++) {
            Element g = pairing.getG1().newElement().setToRandom();
            PairingPreProcessing ppp = pairing.pairing(g);
            Element h = pairing.getG2().newElement().setToRandom();

            long start = System.currentTimeMillis();
            Element r1 = ppp.pairing(h);
            long end = System.currentTimeMillis();
            t1 += (end - start);

            start = System.currentTimeMillis();
            Element r2 = pairing.pairing(g, h);
            end = System.currentTimeMillis();
            t2 += (end - start);
        }

        System.out.println("t1 = " + t1);
        System.out.println("t2 = " + t2);
    }


    protected CurveParams getCurveParams() {
        CurveParams curveParams = new CurveParams();

        try {
            File curveFile = new File(curveParamsFileName);
            if (curveFile.exists()) {
                curveParams.load(curveFile.toURI().toURL().openStream());
            } else
                curveParams.load(getClass().getClassLoader().getResourceAsStream(curveParamsFileName));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        return curveParams;
    }



    public static void main(String[] args) {
        PBCPairingBenchmark test = new PBCPairingBenchmark(args[0],
                                                           Integer.parseInt(args[1]));

        System.out.printf("PBCPairingBenchmark{%s %s}\n", args[0], args[1]);
        test.setUp();
        test.testPairingPreProcessing();
        System.out.printf("PBCPairingBenchmark. Finished.\n");
    }

}