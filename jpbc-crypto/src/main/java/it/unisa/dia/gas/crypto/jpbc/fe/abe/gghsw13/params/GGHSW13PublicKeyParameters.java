package it.unisa.dia.gas.crypto.jpbc.fe.abe.gghsw13.params;

import it.unisa.dia.gas.crypto.jpbc.utils.ElementUtils;
import it.unisa.dia.gas.jpbc.Element;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class GGHSW13PublicKeyParameters extends GGHSW13KeyParameters {

    private Element H;
    private Element[] hs;

    public GGHSW13PublicKeyParameters(GGHSW13Parameters parameters, Element H, Element[] hs) {
        super(false, parameters);

        this.H = H.getImmutable();
        this.hs = ElementUtils.cloneImmutable(hs);
    }

    public Element getH() {
        return H;
    }

    public Element getHAt(int index) {
        return hs[index];
    }
}