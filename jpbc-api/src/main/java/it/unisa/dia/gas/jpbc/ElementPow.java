package it.unisa.dia.gas.jpbc;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public interface ElementPow {

    /**
     * Compute the power to n.
     *
     * @param n the exponent of the power.
     * @return the computed power.
     * @since 1.0.0
     */
    Element pow(BigInteger n);

    /**
     * Compute the power to n, where n is an element of a ring Z_N for some N.
     *
     * @param n the exponent of the power.
     * @return the computed power.
     * @since 1.0.0
     */
    Element powZn(Element n);

}
