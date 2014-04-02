package it.unisa.dia.gas.crypto.jlbc.trapdoor.mp12.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Matrix;
import it.unisa.dia.gas.jpbc.Vector;
import it.unisa.dia.gas.plaf.jpbc.sampler.Sampler;
import it.unisa.dia.gas.plaf.jpbc.field.vector.MatrixElement;
import it.unisa.dia.gas.plaf.jpbc.field.vector.VectorElement;
import it.unisa.dia.gas.plaf.jpbc.field.vector.VectorField;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class MP12HLP2PublicKeyParameters extends MP12PLP2PublicKeyParameters {

    protected Element A;
    protected Element barA;
    protected int m;

    public MP12HLP2PublicKeyParameters(MP12Parameters parameters, int k, int m,
                                       Sampler<BigInteger> sampler,
                                       Vector g, Matrix G,
                                       Field syndromeField, Field Zq,
                                       VectorField<Field> preimageField,
                                       Element A, Element barA) {
        super(parameters, k, sampler, g, G, syndromeField, Zq, preimageField);

        this.A = A;
        this.barA = barA;
        this.m = m;
    }

    public Element getA() {
        return A;
    }

    public Element getBarA() {
        return barA;
    }

    public int getM() {
        return m;
    }

}
