package it.unisa.dia.gas.crypto.jpbc.tor.gvw13.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingParameters;
import org.bouncycastle.crypto.CipherParameters;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class TORGVW13Parameters implements CipherParameters {
    private PairingParameters parameters;
    private Element g1;
    private Element g2;

    private Element g1a;
    private Element g2a;

    public TORGVW13Parameters(PairingParameters parameters, Element g1, Element g2, Element g1a, Element g2a) {
        this.parameters = parameters;
        this.g1 = g1;
        this.g2 = g2;
        this.g1a = g1a;
        this.g2a = g2a;
    }


    public PairingParameters getParameters() {
        return parameters;
    }

    public Element getG1() {
        return g1;
    }

    public Element getG2() {
        return g2;
    }

    public Element getG1a() {
        return g1a;
    }

    public Element getG2a() {
        return g2a;
    }
}