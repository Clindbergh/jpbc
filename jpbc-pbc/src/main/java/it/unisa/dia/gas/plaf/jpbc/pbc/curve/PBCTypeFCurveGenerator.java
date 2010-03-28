package it.unisa.dia.gas.plaf.jpbc.pbc.curve;

import it.unisa.dia.gas.plaf.jpbc.pbc.jna.PBCLibraryProvider;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PBCTypeFCurveGenerator extends PBCCurveGenerator {
    protected int rbits;


    public PBCTypeFCurveGenerator(int rbits) {
        this.rbits = rbits;
    }


    protected void pbcGenerate(String fileName) {
        PBCLibraryProvider.getPbcLibrary().pbc_curvegen_f(fileName, rbits);
    }
}