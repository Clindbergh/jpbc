package it.unisa.dia.gas.plaf.jpbc.pairing.map;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingPreProcessing;
import it.unisa.dia.gas.jpbc.Point;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public abstract class AbstractPairingMap implements PairingMap {

    protected Point in1;


    public PairingPreProcessing pairingPreProcessing(Point in1) {
        this.in1 = in1;

        return this;
    }

    public Element pairing(Element in2) {
        return pairing(in1, (Point) in2);
    }

    
    protected final void pointToAffine(Element Vx, Element Vy, Element z, Element z2, Element e0) {
        // Vx = Vx * z^-2
        Vx.mul(e0.set(z.invert()).square());
        // Vy = Vy * z^-3
        Vy.mul(e0.mul(z));

        z.setToOne();
        z2.setToOne();
    }
}