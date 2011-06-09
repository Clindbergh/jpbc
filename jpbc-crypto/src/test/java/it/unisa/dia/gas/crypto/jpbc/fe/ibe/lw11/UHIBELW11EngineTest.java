package it.unisa.dia.gas.crypto.jpbc.fe.ibe.lw11;

import it.unisa.dia.gas.crypto.engines.MultiBlockAsymmetricBlockCipher;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.lw11.engines.UHIBELW11Engine;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.lw11.generators.UHIBELW11KeyPairGenerator;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.lw11.generators.UHIBELW11SecretKeyGenerator;
import it.unisa.dia.gas.crypto.jpbc.fe.ibe.lw11.params.*;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import junit.framework.TestCase;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;

/**
 * @author Angelo De Caro
 */
public class UHIBELW11EngineTest extends TestCase {

    public void testUHIBE() {
        // Setup
        AsymmetricCipherKeyPair keyPair = setup(32);

        // KeyGen
        Pairing pairing = PairingFactory.getPairing(((UHIBELW11PublicKeyParameters) keyPair.getPublic()).getCurveParameters());
        Element[] ids = map(pairing, "angelo", "de caro", "unisa");

        CipherParameters sk0 = keyGen(keyPair, ids[0]);
        CipherParameters sk01 = keyGen(keyPair, ids[0], ids[1]);
        CipherParameters sk012 = keyGen(keyPair, ids[0], ids[1], ids[2]);
        CipherParameters sk1 = keyGen(keyPair, ids[1]);
        CipherParameters sk10 = keyGen(keyPair, ids[1], ids[0]);
        CipherParameters sk021 = keyGen(keyPair, ids[0], ids[2], ids[1]);

        // Encrypt
        String message = "HW!";
        byte[] ciphertext0 = encrypt(keyPair.getPublic(), message, ids[0]);
        byte[] ciphertext01 = encrypt(keyPair.getPublic(), message, ids[0], ids[1]);
        byte[] ciphertext012 = encrypt(keyPair.getPublic(), message, ids[0], ids[1], ids[2]);

        // Decrypt - Delegate/Decrypt
        assertEquals(message, decrypt(sk0, ciphertext0));
        assertEquals(message, decrypt(sk01, ciphertext01));
        assertEquals(message, decrypt(sk012, ciphertext012));

        assertNotSame(message, decrypt(sk1, ciphertext0));
        assertNotSame(message, decrypt(sk10, ciphertext01));
        assertNotSame(message, decrypt(sk021, ciphertext012));

        assertEquals(message, decrypt(delegate(keyPair, sk0, ids[1]), ciphertext01));
        assertEquals(message, decrypt(delegate(keyPair, sk01, ids[2]), ciphertext012));
        assertEquals(message, decrypt(delegate(keyPair, delegate(keyPair, sk0, ids[1]), ids[2]), ciphertext012));

        assertNotSame(message, decrypt(delegate(keyPair, sk0, ids[2]), ciphertext01));
        assertNotSame(message, decrypt(delegate(keyPair, sk01, ids[1]), ciphertext012));
        assertNotSame(message, decrypt(delegate(keyPair, delegate(keyPair, sk0, ids[2]), ids[1]), ciphertext012));
    }

    protected AsymmetricCipherKeyPair setup(int bitLength) {
        UHIBELW11KeyPairGenerator setup = new UHIBELW11KeyPairGenerator();
        setup.init(new UHIBELW11KeyPairGenerationParameters(bitLength));

        return setup.generateKeyPair();
    }

    protected Element[] map(Pairing pairing, String... ids) {
        Element[] elements = new Element[ids.length];
        for (int i = 0; i < elements.length; i++) {
            elements[i] = pairing.getZr().newRandomElement();
        }
        return elements;
    }


    protected CipherParameters keyGen(AsymmetricCipherKeyPair masterKey, Element... ids) {
        UHIBELW11SecretKeyGenerator generator = new UHIBELW11SecretKeyGenerator();
        generator.init(new UHIBELW11SecretKeyGenerationParameters(
                (UHIBELW11MasterSecretKeyParameters) masterKey.getPrivate(),
                (UHIBELW11PublicKeyParameters) masterKey.getPublic(),
                ids
        ));

        return generator.generateKey();
    }

    protected CipherParameters delegate(AsymmetricCipherKeyPair masterKey, CipherParameters secretKey, Element id) {
        UHIBELW11SecretKeyGenerator generator = new UHIBELW11SecretKeyGenerator();
        generator.init(new UHIBELW11DelegateGenerationParameters(
                (UHIBELW11PublicKeyParameters) masterKey.getPublic(),
                (UHIBELW11SecretKeyParameters) secretKey,
                id
        ));

        return generator.generateKey();
    }

    protected byte[] encrypt(CipherParameters publicKey, String message, Element... ids) {
        byte[] bytes = message.getBytes();
        byte[] ciphertext = new byte[0];

        try {
            AsymmetricBlockCipher engine = new MultiBlockAsymmetricBlockCipher(
                    new UHIBELW11Engine(),
                    new ZeroBytePadding()
            );
            engine.init(true, new UHIBELW11EncryptionParameters((UHIBELW11PublicKeyParameters) publicKey, ids));
            ciphertext = engine.processBlock(bytes, 0, bytes.length);

            assertNotNull(ciphertext);
            assertNotSame(0, ciphertext.length);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        return ciphertext;
    }

    protected String decrypt(CipherParameters secretKey, byte[] cipherText) {
        byte[] plainText = new byte[0];
        try {
            AsymmetricBlockCipher engine = new MultiBlockAsymmetricBlockCipher(
                    new UHIBELW11Engine(),
                    new ZeroBytePadding()
            );
            // Decrypt
            engine.init(false, secretKey);
            plainText = engine.processBlock(cipherText, 0, cipherText.length);

            assertNotNull(plainText);
            assertNotSame(0, plainText.length);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        return new String(plainText).trim();
    }
}

