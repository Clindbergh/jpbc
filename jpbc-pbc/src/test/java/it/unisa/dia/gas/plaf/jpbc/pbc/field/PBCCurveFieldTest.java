package it.unisa.dia.gas.plaf.jpbc.pbc.field;

import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveFieldTest;
import it.unisa.dia.gas.plaf.jpbc.pbc.PBCPairing;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.WrapperLibraryProvider;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PBCCurveFieldTest extends CurveFieldTest {

    @Override
    protected void setUp() throws Exception {
        if (WrapperLibraryProvider.isAvailable()) {
            pairing = new PBCPairing(getCurveParams());
        } else
            pairing = null;
    }
}
