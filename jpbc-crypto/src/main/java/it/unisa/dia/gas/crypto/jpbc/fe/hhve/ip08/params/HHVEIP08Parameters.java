package it.unisa.dia.gas.crypto.jpbc.fe.hhve.ip08.params;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import org.bouncycastle.crypto.CipherParameters;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class HHVEIP08Parameters implements CipherParameters, Serializable {
    private CurveParams curveParams;
    private Element g;
    private int[] attributeLengths;
    private int n;

    private int length;


    public HHVEIP08Parameters(CurveParams curveParams, Element g, int[] attributeLengths) {
        this.curveParams = curveParams;
        this.g = g;
        this.attributeLengths = Arrays.copyOf(attributeLengths, attributeLengths.length);
        this.n = attributeLengths.length;

        this.length = 0;
        for (int attributeLength : attributeLengths) {
            length += Math.ceil((Math.log(attributeLength)/Math.log( 2 )+1) / 8);
        }
    }

    public CurveParams getCurveParams() {
        return curveParams;
    }

    public Element getG() {
        return g;
    }

    public int getN() {
        return n;
    }

    public int[] getAttributeLengths() {
        return attributeLengths;
    }

    public int getAttributeLengthAt(int index) {
        return attributeLengths[index];
    }

    public int getLength() {
        return length;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HHVEIP08Parameters that = (HHVEIP08Parameters) o;

        if (n != that.n) return false;
        if (!Arrays.equals(attributeLengths, that.attributeLengths)) return false;
        if (!curveParams.equals(that.curveParams)) return false;
        if (!g.equals(that.g)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = curveParams.hashCode();
        result = 31 * result + g.hashCode();
        result = 31 * result + Arrays.hashCode(attributeLengths);
        result = 31 * result + n;
        return result;
    }
}