package it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params;

import it.unisa.dia.gas.crypto.jpbc.utils.ElementUtil;
import it.unisa.dia.gas.jpbc.Element;
import org.bouncycastle.crypto.CipherParameters;

import java.util.Arrays;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class AHIBEDIP10EncryptionParameters implements CipherParameters {

    private AHIBEDIP10PublicKeyParameters publicKey;
    private Element[] ids;


    public AHIBEDIP10EncryptionParameters(AHIBEDIP10PublicKeyParameters publicKey,
                                          Element[] ids) {
        this.publicKey = publicKey;
        this.ids = ElementUtil.cloneImmutably(ids);
    }


    public AHIBEDIP10PublicKeyParameters getPublicKey() {
        return publicKey;
    }

    public Element[] getIds() {
        return Arrays.copyOf(ids, ids.length);
    }

    public Element getIdAt(int index) {
        return ids[index];
    }

    public int getLength() {
        return ids.length;
    }
}