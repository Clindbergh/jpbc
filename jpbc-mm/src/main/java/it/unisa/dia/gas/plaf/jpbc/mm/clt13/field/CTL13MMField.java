package it.unisa.dia.gas.plaf.jpbc.mm.clt13.field;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jpbc.mm.clt13.parameters.CTL13MMInstance;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.3.0
 */
public class CTL13MMField implements Field<CTL13MMElement> {

    private SecureRandom random;
    private CTL13MMInstance instance;
    private int index;


    public CTL13MMField(SecureRandom random, CTL13MMInstance instance) {
        this(random, instance, 0);
    }

    public CTL13MMField(SecureRandom random, CTL13MMInstance instance, int index) {
        this.random = random;
        this.instance = instance;
        this.index = index;
    }


    public CTL13MMElement newElement() {
        return new CTL13MMElement(this, index);
    }

    public CTL13MMElement newElement(int value) {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public CTL13MMElement newElement(BigInteger value) {
        return new CTL13MMElement(this, instance.encodeAt(value, index), index);
    }

    public CTL13MMElement newZeroElement() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public CTL13MMElement newOneElement() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public CTL13MMElement newRandomElement() {
        BigInteger value = instance.sampleAtZero();
        if (index > 0)
            return new CTL13MMElement(this, value, index);

        return newElement(value);
    }

    public BigInteger getOrder() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public boolean isOrderOdd() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public CTL13MMElement getNqr() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public int getLengthInBytes() {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public Element[] twice(Element[] elements) {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public Element[] add(Element[] a, Element[] b) {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public ElementPowPreProcessing pow(byte[] source) {
        throw new IllegalStateException("Not Implemented yet!");
    }

    public ElementPowPreProcessing pow(byte[] source, int offset) {
        throw new IllegalStateException("Not Implemented yet!");
    }


    public CTL13MMInstance getInstance() {
        return instance;
    }

}
