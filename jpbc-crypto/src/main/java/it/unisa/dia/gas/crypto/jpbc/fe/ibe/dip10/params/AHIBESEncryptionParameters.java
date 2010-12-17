package it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params;

import it.unisa.dia.gas.crypto.jpbc.utils.ElementUtil;
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.CipherParameters;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class AHIBESEncryptionParameters implements CipherParameters {

    private AHIBEPublicKeyParameters ahivePublicKeyParameters;
    private Element[] ids;


    public AHIBESEncryptionParameters(AHIBEPublicKeyParameters AHIBEPublicKeyParameters,
                                      Element[] ids) {
        this.ahivePublicKeyParameters = AHIBEPublicKeyParameters;
        this.ids = ElementUtil.cloneImmutably(ids);
    }


    public AHIBEPublicKeyParameters getPublicKey() {
        return ahivePublicKeyParameters;
    }

    public Element[] getIds() {
        return ids;
    }
}
