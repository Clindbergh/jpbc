package it.unisa.dia.gas.plaf.jlbc.lattice.trapdoor.mp12.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jlbc.sampler.Sampler;
import it.unisa.dia.gas.plaf.jpbc.field.vector.MatrixElement;
import it.unisa.dia.gas.plaf.jpbc.field.vector.VectorElement;
import it.unisa.dia.gas.plaf.jpbc.field.vector.VectorField;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class MP12PLP2PublicKeyParameters extends MP12KeyParameters {

    protected int k;
    protected Sampler<BigInteger> sampler;

    protected VectorElement g; // primitiv vector
    protected MatrixElement G; // parity-check matrix

    protected Field syndromeField;

    protected Field Zq;
    protected VectorField<Field> preimageField;


    public MP12PLP2PublicKeyParameters(MP12Parameters parameters,
                                       int k, Sampler<BigInteger> sampler,
                                       VectorElement g, MatrixElement G,
                                       Field syndromeField,
                                       Field Zq,
                                       VectorField<Field> preimageField) {
        super(false, parameters);

        this.k = k;
        this.sampler = sampler;
        this.g = g;
        this.G = G;
        this.syndromeField = syndromeField;
        this.Zq = Zq;
        this.preimageField = preimageField;
    }

    public int getK() {
        return k;
    }

    public VectorField<Field> getPreimageField() {
        return preimageField;
    }

    public Sampler<BigInteger> getSampler() {
        return sampler;
    }

    public Field getSyndromeField() {
        return syndromeField;
    }

    public Element getG() {
        return G;
    }

    public Field getZq() {
        return Zq;
    }
}
