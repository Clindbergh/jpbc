package it.unisa.dia.gas.plaf.jpbc.field.quadratic;

import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.plaf.jpbc.field.generic.GenericFieldOver;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class QuadraticEvenField<F extends Field, E extends QuadraticEvenElement> extends GenericFieldOver<F, E> {
    protected BigInteger order;
    protected int fixedLengthInBytes;

    public QuadraticEvenField(F targetField) {
        super(targetField);

        this.order = targetField.getOrder().multiply(targetField.getOrder());

        if (targetField.getFixedLengthInBytes() < 0) {
            //f->length_in_bytes = fq_length_in_bytes;
            fixedLengthInBytes = -1;
        } else {
            fixedLengthInBytes = 2 * targetField.getFixedLengthInBytes();
        }
    }


    public E newElement() {
        return (E) new QuadraticEvenElement(this);
    }

    public BigInteger getOrder() {
        return order;
    }

    public E getNqr() {
        throw new IllegalStateException("Not Implemented yet!!!");
    }

    public int getFixedLengthInBytes() {
        return fixedLengthInBytes;
    }
}