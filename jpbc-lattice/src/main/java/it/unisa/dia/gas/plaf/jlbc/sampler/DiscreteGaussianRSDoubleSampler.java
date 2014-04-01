package it.unisa.dia.gas.plaf.jlbc.sampler;

import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Matrix;
import it.unisa.dia.gas.plaf.jlbc.util.DoubleUtils;
import it.unisa.dia.gas.plaf.jpbc.field.vector.MatrixField;
import it.unisa.dia.gas.plaf.jpbc.field.z.SymmetricZrField;
import it.unisa.dia.gas.plaf.jpbc.sampler.Sampler;
import org.apfloat.Apfloat;

import java.math.BigInteger;
import java.security.SecureRandom;


/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class DiscreteGaussianRSDoubleSampler implements Sampler<BigInteger> {

    protected SecureRandom random;
    protected double sigma;
    protected double center;

    protected double h, normalization, sigmaTau;
    protected int left, interval;

    protected int tau = 13;

    public DiscreteGaussianRSDoubleSampler(SecureRandom random, double gaussianParameter, double center) {
        if (random == null)
            random = new SecureRandom();

        this.random = random;
        this.sigma = gaussianParameter / DoubleUtils.SQRT_TWO_PI;
        this.center = center;

        this.h = - (Math.PI / (sigma * sigma));

        System.out.println("normalization = " + normalization);
        this.sigmaTau = sigma * tau;

        setCenter(center);
    }

    public DiscreteGaussianRSDoubleSampler(SecureRandom random, double sigma) {
        this(random, sigma, 0.0d);
    }

    public DiscreteGaussianRSDoubleSampler(SecureRandom random, Apfloat sigma) {
        this(random, sigma.doubleValue(), 0.0d);
    }


    public DiscreteGaussianRSDoubleSampler setCenter(double center) {
        this.center = center;
        this.interval = (int) (Math.ceil(center + sigmaTau) -  Math.floor(center - sigmaTau)) + 1;
        this.left = (int) Math.floor(center - sigmaTau);

        return this;
    }

    public BigInteger sample() {
        while (true) {
            int x = left + random.nextInt(interval);

            double rhos = Math.exp(h * ((x * x) - center));
            double sample = random.nextDouble();

            if (sample <= rhos)
                return BigInteger.valueOf(x);
        }
    }
}