package it.unisa.dia.gas.crypto.jpbc.fe.hve.ip08.engines;

import it.unisa.dia.gas.crypto.jpbc.fe.hve.ip08.params.HHVEIP08SearchKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.hve.ip08.params.HVEIP08EncryptionParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.hve.ip08.params.HVEIP08KeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.hve.ip08.params.HVEIP08PublicKeyParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ParametersWithRandom;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class HHVEIP08Engine implements AsymmetricBlockCipher {

    private HVEIP08KeyParameters key;
    private boolean forEncryption;

    private int n;
    private int inBytes, outBytes;
    private Pairing pairing;

    /**
     * initialise the HVE engine.
     *
     * @param forEncryption true if we are encrypting, false otherwise.
     * @param param         the necessary HVE key parameters.
     */
    public void init(boolean forEncryption, CipherParameters param) {
        if (param instanceof ParametersWithRandom) {
            ParametersWithRandom p = (ParametersWithRandom) param;

            this.key = (HVEIP08KeyParameters) p.getParameters();
        } else {
            this.key = (HVEIP08KeyParameters) param;
        }

        this.forEncryption = forEncryption;
        if (forEncryption) {
            if (!(key instanceof HVEIP08EncryptionParameters)) {
                throw new IllegalArgumentException("HVEIP08EncryptionParameters are required for encryption.");
            }
        } else {
            if (!(key instanceof HHVEIP08SearchKeyParameters)) {
                throw new IllegalArgumentException("HHVEIP08SearchKeyParameters are required for decryption.");
            }
        }

        this.pairing = PairingFactory.getPairing(key.getParameters().getCurveParams());

        this.n = key.getParameters().getN();
        this.inBytes = pairing.getGT().getLengthInBytes();
        this.outBytes = pairing.getGT().getLengthInBytes() + (4 * n + 1) * pairing.getG1().getLengthInBytes();
    }

    /**
     * Return the maximum size for an input block to this engine.
     * For HVE this is always one byte less than the size of P on
     * encryption, and twice the length as the size of P on decryption.
     *
     * @return maximum size for an input block.
     */
    public int getInputBlockSize() {
        if (forEncryption) {
            return inBytes;
        }

        return outBytes;
    }

    /**
     * Return the maximum size for an output block to this engine.
     * For HVE this is always one byte less than the size of P on
     * decryption, and twice the length as the size of P on encryption.
     *
     * @return maximum size for an output block.
     */
    public int getOutputBlockSize() {
        if (forEncryption) {
            return outBytes;
        }

        return 1;
    }

    /**
     * Process a single block using the basic HVE algorithm.
     *
     * @param in    the input array.
     * @param inOff the offset into the input buffer where the data starts.
     * @param inLen the length of the data to be processed.
     * @return the result of the HVE process.
     * @throws org.bouncycastle.crypto.DataLengthException
     *          the input block is too large.
     */
    public byte[] processBlock(byte[] in, int inOff, int inLen) {
        if (key == null) {
            throw new IllegalStateException("HVE engine not initialised");
        }

        int maxLength = forEncryption ? inBytes : getInputBlockSize();

        if (inLen > maxLength) {
            throw new DataLengthException("input too large for HVE cipher.\n");
        }

        if (key instanceof HHVEIP08SearchKeyParameters) {
            // match
            HHVEIP08SearchKeyParameters searchKey = (HHVEIP08SearchKeyParameters) key;

            // Convert bytes to Elements...
            int offset = inOff;

            // load omega
            Element omega = pairing.getGT().newElement();
            offset += omega.setFromBytes(in, offset);

            // load C0...
            Element C0 = pairing.getG1().newElement();
            offset += C0.setFromBytes(in, offset);

            if (searchKey.isAllStar()) {
                return omega.mul(
                        pairing.pairing(C0, searchKey.getK())
                ).toBytes();
            }

            // load Xs, Ws..
            List<Element> X = new ArrayList<Element>();
            List<Element> W = new ArrayList<Element>();
            List<Element> gsm = new ArrayList<Element>();
            List<Element> gs = new ArrayList<Element>();
            for (int i = 0; i < n; i++) {
                Element x = pairing.getG1().newElement();
                offset += x.setFromBytes(in, offset);

                X.add(x);

                Element w = pairing.getG1().newElement();
                offset += w.setFromBytes(in, offset);

                W.add(w);

                Element gsmi = pairing.getG1().newElement();
                offset += gsmi.setFromBytes(in, offset);

                gsm.add(gsmi);

                Element gsi = pairing.getG1().newElement();
                offset += gsi.setFromBytes(in, offset);

                gs.add(gsi);
            }

            Element result = pairing.getGT().newOneElement();
            for (int i = 0; i < searchKey.getParameters().getN(); i++) {
                if (searchKey.isStar(i)) {
                    result.mul(
                            pairing.pairing(gsm.get(i), searchKey.getYAt(i))
                    ).mul(
                            pairing.pairing(gs.get(i), searchKey.getLAt(i))
                    );
                } else {
                    result.mul(
                            pairing.pairing(X.get(i), searchKey.getYAt(i))
                    ).mul(
                            pairing.pairing(W.get(i), searchKey.getLAt(i))
                    );
                }
            }

            return omega.mul(result).toBytes();
        } else {
            // encryption
            HVEIP08EncryptionParameters encKey = (HVEIP08EncryptionParameters) key;

            if (inLen > inBytes || inLen < inBytes)
                throw new DataLengthException("input must be of size " + inBytes);

            byte[] block;
            if (inOff != 0 || inLen != in.length) {
                block = new byte[inLen];
                System.arraycopy(in, inOff, block, 0, inLen);
            } else {
                block = in;
            }
            Element M = pairing.getGT().newElement();
            M.setFromBytes(block);

            int[] attributes = encKey.getAttributes();

            // Encrypt
            HVEIP08PublicKeyParameters pub = encKey.getPublicKey();
            ElementPowPreProcessing powG = pub.getParameters().getPowG();

            Element s = pairing.getZr().newRandomElement().getImmutable();

            // Compute omega = Y^-s
            Element omega = M.mul(pub.getY().powZn(s.negate()));

            // Compute the rest
            Element C0 = powG.powZn(s);
            List<Element> elements = new ArrayList<Element>();
            for (int i = 0; i < n; i++) {
                // Choose randomness
                Element si = pairing.getZr().newElement().setToRandom();
                Element sMinusSi = s.sub(si);

                int j = attributes[i];

                // Compute the elements for the position
                elements.add(pub.getTAt(i, j).powZn(sMinusSi));
                elements.add(pub.getVAt(i, j).powZn(si));
                elements.add(powG.powZn(sMinusSi));
                elements.add(powG.powZn(si));
            }

            // Move to byte array
            ByteArrayOutputStream outputStream;
            try {
                outputStream = new ByteArrayOutputStream(getOutputBlockSize());

                outputStream.write(omega.toBytes());
                outputStream.write(C0.toBytes());
                for (Element element : elements)
                    outputStream.write(element.toBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return outputStream.toByteArray();
        }
    }


}