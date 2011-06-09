package it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.engines;

import it.unisa.dia.gas.crypto.engines.PredicateOnlyPairingAsymmetricBlockCipher;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10EncryptionParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10KeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10PublicKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10SecretKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.utils.ElementUtil;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class AHIBEDIP10PredicateOnlyEngine extends PredicateOnlyPairingAsymmetricBlockCipher {

    public void initialize() {
        if (forEncryption) {
            if (!(key instanceof AHIBEDIP10EncryptionParameters))
                throw new IllegalArgumentException("AHIBEDIP10EncryptionParameters are required for encryption.");
        } else {
            if (!(key instanceof AHIBEDIP10SecretKeyParameters))
                throw new IllegalArgumentException("AHIBEDIP10SecretKeyParameters are required for decryption.");
        }

        this.pairing = PairingFactory.getPairing(((AHIBEDIP10KeyParameters) key).getCurveParameters());
        this.inBytes = 0;
        this.outBytes = (pairing.getGT().getLengthInBytes() + (2 * pairing.getG1().getLengthInBytes()));
    }

    public byte[] process(byte[] in, int inOff, int inLen) {
        if (key instanceof AHIBEDIP10SecretKeyParameters) {
            // Convert bytes to Elements...

            int offset = inOff;

            // Load C0...
            Element C0 = pairing.getGT().newElement();
            offset += C0.setFromBytes(in, offset);

            // Load C1...
            Element C1 = pairing.getG1().newElement();
            offset += C1.setFromBytes(in, offset);

            // Load C2...
            Element C2 = pairing.getG1().newElement();
            offset += C2.setFromBytes(in, offset);

            // Run the test
            AHIBEDIP10SecretKeyParameters sk = (AHIBEDIP10SecretKeyParameters) key;
            Element M = C0.mul(pairing.pairing(sk.getK12(), C2).mul(pairing.pairing(sk.getK11(), C1).invert()).invert());

            return new byte[]{(byte) (M.isOne() ? 0 : 1)};
        } else {
            // Encrypt
            AHIBEDIP10EncryptionParameters encParams = (AHIBEDIP10EncryptionParameters) key;
            AHIBEDIP10PublicKeyParameters pk = encParams.getPublicKey();

            // Encrypt the attributes
            Element s = pairing.getZr().newRandomElement();

            Element C0 = pk.getOmega().powZn(s);

            Element C1 = pairing.getG1().newOneElement();
            for (int i = 0; i < encParams.getLength(); i++) {
                C1.mul(pk.getUAt(i).powZn(encParams.getIdAt(i)));
            }
            C1.mul(pk.getT()).powZn(s).mul(ElementUtil.randomIn(pairing, pk.getY4()));

            Element C2 = pk.getY1().powZn(s).mul(ElementUtil.randomIn(pairing, pk.getY4()));

            // Convert the Elements to byte arrays
            ByteArrayOutputStream bytes = new ByteArrayOutputStream(getOutputBlockSize());
            try {
                bytes.write(C0.toBytes());
                bytes.write(C1.toBytes());
                bytes.write(C2.toBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return bytes.toByteArray();
        }
    }

}