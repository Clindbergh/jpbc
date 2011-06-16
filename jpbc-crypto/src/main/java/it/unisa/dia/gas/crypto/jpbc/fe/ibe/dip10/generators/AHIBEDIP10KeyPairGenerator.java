package it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.generators;

import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10KeyPairGenerationParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10MasterSecretKeyParameters;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.dip10.params.AHIBEDIP10PublicKeyParameters;
import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import it.unisa.dia.gas.plaf.jpbc.pairing.a1.TypeA1CurveGenerator;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class AHIBEDIP10KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private AHIBEDIP10KeyPairGenerationParameters parameters;


    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.parameters = (AHIBEDIP10KeyPairGenerationParameters) keyGenerationParameters;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        // Generate curve parameters
        CurveParams curveParameters = generateCurveParameters();

        // Get the generators of the subgroups

        Pairing pairing = PairingFactory.getPairing(curveParameters);

        Element gen1 = pairing.getG1().newElement();
        gen1.setFromBytes(curveParameters.getBytes("gen1"));

        Element gen3 = pairing.getG1().newElement();
        gen3.setFromBytes(curveParameters.getBytes("gen3"));

        Element gen4 = pairing.getG1().newElement();
        gen4.setFromBytes(curveParameters.getBytes("gen4"));


        // Construct Public Key and Master Secret Key

        Element Y1 = gen1.powZn(pairing.getZr().newRandomElement()).getImmutable();
        Element X1 = gen1.powZn(pairing.getZr().newRandomElement()).getImmutable();

        Element[] uElements = new Element[parameters.getLength()];
        for (int i = 0; i < uElements.length; i++) {
            uElements[i] = gen1.powZn(pairing.getZr().newRandomElement()).getImmutable();
        }

        Element Y3 = gen3.powZn(pairing.getZr().newRandomElement()).getImmutable();

        Element X4 = gen4.powZn(pairing.getZr().newRandomElement()).getImmutable();
        Element Y4 = gen4.powZn(pairing.getZr().newRandomElement()).getImmutable();

        Element alpha = pairing.getZr().newRandomElement().getImmutable();

        // Remove factorization from curveParameters
        curveParameters.remove("gen2");

        // Return the keypair

        return new AsymmetricCipherKeyPair(
                new AHIBEDIP10PublicKeyParameters(
                        curveParameters,
                        Y1, Y3, Y4,
                        X1.mul(X4),
                        uElements,
                        pairing.pairing(Y1,Y1).powZn(alpha).getImmutable()
                ),
                new AHIBEDIP10MasterSecretKeyParameters(
                        curveParameters,
                        X1, alpha
                )
        );
    }


    private CurveParams generateCurveParameters() {
        CurveGenerator curveGenerator = new TypeA1CurveGenerator(4, parameters.getBitLength());

        return (CurveParams) curveGenerator.generate();
    }
}
