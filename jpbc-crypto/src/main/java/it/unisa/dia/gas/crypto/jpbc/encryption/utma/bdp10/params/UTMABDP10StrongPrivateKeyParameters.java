package it.unisa.dia.gas.crypto.jpbc.encryption.utma.bdp10.params;

import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.CipherParameters;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class UTMABDP10StrongPrivateKeyParameters extends UTMABDP10StrongKeyParameters {
    private Element D0, D1, D2, D3;
    private CipherParameters rPrivateKey;


    public UTMABDP10StrongPrivateKeyParameters(UTMABDP10StrongPublicParameters publicParameters,
                                               Element d0, Element d1, Element d2, Element d3,
                                               CipherParameters rPrivateKey) {
        super(true, publicParameters);

        D0 = d0;
        D1 = d1;
        D2 = d2;
        D3 = d3;

        this.rPrivateKey = rPrivateKey;
    }

    public Element getD0() {
        return D0;
    }

    public Element getD1() {
        return D1;
    }

    public Element getD2() {
        return D2;
    }

    public Element getD3() {
        return D3;
    }

    public CipherParameters getRPrivateKey() {
        return rPrivateKey;
    }
}