package it.unisa.dia.gas.plaf.jpbc.mm.clt13.parameters;

import it.unisa.dia.gas.jpbc.PairingParameters;
import it.unisa.dia.gas.plaf.jpbc.mm.clt13.parameters.CTL13MMInstanceParameters;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.3.0
 */
public class CTL13MMInstanceValues {
    private PairingParameters parameters;


    public CTL13MMInstanceValues(PairingParameters parameters) {
        this.parameters = parameters;
    }


    public CTL13MMInstanceParameters getInstanceParameters() {
        return (CTL13MMInstanceParameters) parameters.getObject("params");
    }

    public BigInteger getX0() {
        return parameters.getBigInteger("x0");
    }

    public BigInteger getY() {
        return parameters.getBigInteger("y");
    }

    public BigInteger getZ() {
        return parameters.getBigInteger("z");
    }

    public BigInteger getZInv() {
        return parameters.getBigInteger("zInv");
    }

    public BigInteger getPzt() {
        return parameters.getBigInteger("pzt");
    }

    public BigInteger getXspAt(int index) {
        return ((BigInteger[])parameters.getObject("xsp"))[index];
    }

    public BigInteger getCrtCoefficientAt(int index) {
        return ((BigInteger[])parameters.getObject("crtCoefficients"))[index];
    }

    public BigInteger getXsAt(int index) {
        return ((BigInteger[])parameters.getObject("xs"))[index];
    }

    public BigInteger getGsAt(int index) {
        return ((BigInteger[])parameters.getObject("gs"))[index];
    }

    public BigInteger getPsAt(int index) {
        return ((BigInteger[])parameters.getObject("ps"))[index];
    }
}