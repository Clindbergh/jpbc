package it.unisa.dia.gas.crypto.jpbc.encryption.utma.bdp10.params;

import org.bouncycastle.crypto.KeyGenerationParameters;

import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class UTMAWeakKeyGenerationParameters extends KeyGenerationParameters {

    private UTMAWeakParameters params;

    public UTMAWeakKeyGenerationParameters(SecureRandom random, UTMAWeakParameters params) {
        super(random, params.getPublicParameters().getG().getField().getLengthInBytes());

        this.params = params;
    }

    public UTMAWeakParameters getParameters() {
        return params;
    }

}
