package it.unisa.dia.gas.plaf.jpbc.pairing.a;

import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Point;
import it.unisa.dia.gas.plaf.jpbc.field.curve.CurveField;
import it.unisa.dia.gas.plaf.jpbc.field.gt.GTFiniteField;
import it.unisa.dia.gas.plaf.jpbc.field.naive.NaiveField;
import it.unisa.dia.gas.plaf.jpbc.field.quadratic.DegreeTwoExtensionQuadraticField;
import it.unisa.dia.gas.plaf.jpbc.pairing.AbstractPairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class TypeAPairing extends AbstractPairing {
    public static final String MILLER_PROJECTTIVE_METHOD = "miller-projective";
    public static final String MILLER_AFFINE_METHOD = "miller-affine";


    protected int exp2;
    protected int exp1;
    protected int sign1;
    protected int sign0;

    protected BigInteger r;
    protected BigInteger q; 
    protected BigInteger h;

    protected BigInteger phikOnr;

    protected BigInteger genNoCofac;

    protected Field Fq;
    protected Field<? extends Point> Fq2;
    protected Field<? extends Point> Eq;


    public TypeAPairing(CurveParams params) {
        initParams(params);
        initMap(params);
        initFields();
    }



    protected void initParams(CurveParams curveParams) {
        // validate the type
        String type = curveParams.get("type");
        if (type == null || !"a".equalsIgnoreCase(type))
            throw new IllegalArgumentException("Type not valid. Found '" + type + "'. Expected 'a'.");

        // load params
        exp2 = curveParams.getInt("exp2");
        exp1 = curveParams.getInt("exp1");
        sign1 = curveParams.getInt("sign1");
        sign0 = curveParams.getInt("sign0");

        r = curveParams.getBigInteger("r"); // r = 2^exp2 + sign1 * 2^exp1 + sign0 * 1
        q = curveParams.getBigInteger("q"); // we work in E(F_q) (and E(F_q^2))
        h = curveParams.getBigInteger("h");  // r * h = q + 1

        genNoCofac = curveParams.getBigInteger("genNoCofac", null);
    }


    protected void initFields() {
        // Init Zr
        Zr = initFp(r);

        // Init Fq
        Fq = initFp(q);

        // Init Eq
        Eq = initEq();

        // Init Fq2
        Fq2 = initFi();

        // k=2, hence phi_k(q) = q + 1, phikOnr = (q+1)/r
        phikOnr = h;

        // Init G1, G2, GT
        G1 = Eq;
        G2 = G1;
        GT = initGT();
    }


    protected Field initFp(BigInteger order) {
        return new NaiveField(order);
    }

    protected Field<? extends Point> initEq() {
        // Remember the curve is: y^2 = x^3 + ax
        return new CurveField<Field>(Fq.newOneElement(),   // a
                                     Fq.newZeroElement(),  // b
                                     r,                    // order
                                     h,                    // cofactor  (r*h)=q+1=#E(F_q)
                                     genNoCofac);
    }

    protected Field<? extends Point> initFi() {
        return new DegreeTwoExtensionQuadraticField<Field>(Fq);
    }

    protected Field initGT() {
        return new GTFiniteField(r, pairingMap, Fq2);
    }


    protected void initMap(CurveParams curveParams) {
        String method = curveParams.getString("method", MILLER_PROJECTTIVE_METHOD);

        if (MILLER_PROJECTTIVE_METHOD.equals(method))
            pairingMap = new TypeATateProjectiveMillerPairingMap(this);
        else if (MILLER_AFFINE_METHOD.equals(method))
            pairingMap = new TypeATateAffineMillerPairingMap(this);
        else
            throw new IllegalArgumentException("Pairing method not recognized. Method = " + method);
    }
}
