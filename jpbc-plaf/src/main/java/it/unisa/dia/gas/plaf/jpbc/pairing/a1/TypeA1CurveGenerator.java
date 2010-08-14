package it.unisa.dia.gas.plaf.jpbc.pairing.a1;

import it.unisa.dia.gas.jpbc.CurveGenerator;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import it.unisa.dia.gas.plaf.jpbc.util.BigIntegerUtils;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class TypeA1CurveGenerator implements CurveGenerator {
    protected int numPrimes, bits;


    public TypeA1CurveGenerator(int numPrimes, int bits) {
        this.numPrimes = numPrimes;
        this.bits = bits;
    }


    public Map generate() {
        SecureRandom secureRandom = new SecureRandom();

        BigInteger[] primes = new BigInteger[numPrimes];
        BigInteger order, n, p;
        long l;

        while (true) {
            while (true) {
                order = BigInteger.ONE;
                for (int i = 0; i < numPrimes; i++) {
                    primes[i] = BigIntegerUtils.generateSolinasPrime(bits, secureRandom);
                    order = order.multiply(primes[i]);
                }

                if ((order.bitLength() + 7) / 8 == order.bitLength() / 8)
                    break;
            }

            // If order is even, ideally check all even l, not just multiples of 4
            l = 4;
            n = order.multiply(BigIntegerUtils.FOUR);

            p = n.subtract(BigInteger.ONE);
            while (!p.isProbablePrime(10)) {
                p = p.add(n);
                l += 4;
            }
            if ((p.bitLength() + 7) / 8 == p.bitLength() / 8)
                break;
        }

        CurveParams params = new CurveParams();
        params.put("type", "a1");
        params.put("p", p.toString());
        params.put("n", order.toString());
        for (int i = 0; i < primes.length; i++) {
            params.put("n" + i, primes[i].toString());

        }
        params.put("l", String.valueOf(l));

        return params;
    }

    public static void main(String[] args) {
        TypeA1CurveGenerator generator = new TypeA1CurveGenerator(3, 16);
        CurveParams curveParams = (CurveParams) generator.generate();

        System.out.println(curveParams.toString(" "));
    }

}
