package it.unisa.dia.gas.plaf.jpbc.mm.clt13.parameters;

import it.unisa.dia.gas.jpbc.PairingParameters;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 2.0.0
 */
public class CTL13MMInstanceParameters {

    public static CTL13MMInstanceParameters TOY =
            new CTL13MMInstanceParameters(
                    757, // eta
                    165, // n
                    10, // alpha
                    160, // ell
                    27, //rho
                    12, // delta
                    5, // kappa
                    80, //beta
                    16, // theta
                    160 // bound
            );

    public static CTL13MMInstanceParameters SMALL =
            new CTL13MMInstanceParameters(
                    1779, // eta
                    540, // n
                    80, // alpha
                    160, // ell
                    41, //rho
                    23, // delta
                    6, // kappa
                    80, //beta
                    16, // theta
                    160 // bound
            );

    protected int eta, n, alpha, ell, rho, delta, deltaSquare, kappa, beta, theta, bound;

    public CTL13MMInstanceParameters(int eta, int n, int alpha, int ell,
                                     int rho, int delta, int kappa, int beta,
                                     int theta, int bound) {
        this.eta = eta;
        this.n = n;
        this.alpha = alpha;
        this.ell = ell;
        this.rho = rho;
        this.delta = delta;
        this.deltaSquare = delta * delta;
        this.kappa = kappa;
        this.beta = beta;
        this.theta = theta;
        this.bound = bound;
    }


    public CTL13MMInstanceParameters(PairingParameters parameters) {
        this.eta = parameters.getInt("eta");
        this.n = parameters.getInt("n");
        this.alpha = parameters.getInt("alpha");
        this.ell = parameters.getInt("ell");
        this.rho = parameters.getInt("rho");
        this.delta = parameters.getInt("delta");
        this.deltaSquare = delta * delta;
        this.kappa = parameters.getInt("kappa");
        this.beta = parameters.getInt("beta");
        this.theta = parameters.getInt("theta");
        this.bound = parameters.getInt("bound");
    }


    public int getEta() {
        return eta;
    }

    public int getN() {
        return n;
    }

    public int getAlpha() {
        return alpha;
    }

    public int getEll() {
        return ell;
    }

    public int getRho() {
        return rho;
    }

    public int getDelta() {
        return delta;
    }

    public int getDeltaSquare() {
        return deltaSquare;
    }

    public int getKappa() {
        return kappa;
    }

    public int getBeta() {
        return beta;
    }

    public int getTheta() {
        return theta;
    }

    public int getBound() {
        return bound;
    }

    public CTL13MMInstanceParameters setKappa(int kappa) {
        this.kappa = kappa;

        return this;
    }

    @Override
    public String toString() {
        return "CTL13MMInstanceParameters{" +
                "eta=" + eta +
                ", n=" + n +
                ", alpha=" + alpha +
                ", ell=" + ell +
                ", rho=" + rho +
                ", delta=" + delta +
                ", deltaSquare=" + deltaSquare +
                ", kappa=" + kappa +
                ", beta=" + beta +
                ", theta=" + theta +
                ", bound=" + bound +
                '}';
    }
}
