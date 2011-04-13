package it.unisa.dia.gas.plaf.jpbc.pbc;

import com.sun.jna.Pointer;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.PairingPreProcessing;
import it.unisa.dia.gas.plaf.jpbc.pairing.AbstractPairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.CurveParams;
import it.unisa.dia.gas.plaf.jpbc.pairing.map.AbstractMillerPairingPreProcessing;
import it.unisa.dia.gas.plaf.jpbc.pbc.field.PBCG1Field;
import it.unisa.dia.gas.plaf.jpbc.pbc.field.PBCG2Field;
import it.unisa.dia.gas.plaf.jpbc.pbc.field.PBCGTField;
import it.unisa.dia.gas.plaf.jpbc.pbc.field.PBCZrField;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCElementPPType;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCPairingPPType;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.PBCPairingType;
import it.unisa.dia.gas.plaf.jpbc.wrapper.jna.WrapperLibraryProvider;

import java.util.Arrays;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PBCPairing extends AbstractPairing {

    protected PBCPairingType pairing;


    public PBCPairing(CurveParams curveParams) {
        if (!WrapperLibraryProvider.isAvailable())
            throw new IllegalStateException("PBC support not available.");

        // Init pairing...
        pairing = new PBCPairingType(curveParams.toString(" "));

        // Init fields
        initFields();
    }


    public boolean isSymmetric() {
        return WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_is_symmetric(pairing) == 1;
    }

    public Element pairing(Element in1, Element in2) {
        PBCElement out = (PBCElement) GT.newElement();

        WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_apply(
                out.getValue(),
                ((PBCElement) in1).getValue(),
                ((PBCElement) in2).getValue(),
                pairing
        );
//        WrapperLibraryProvider.getWrapperLibrary().pbc_element_pairing(
//                out.getValue(),
//                ((PBCElement) in1).getValue(),
//                ((PBCElement) in2).getValue()
//        );

        return out;
    }

    public Element pairing(Element[] in1, Element[] in2) {
        PBCElement out = (PBCElement) GT.newElement();

        Pointer[] in1Pointers = new Pointer[in1.length];
        for (int i = 0; i <in1.length; i++) {
            in1Pointers[i] = ((PBCElement) in1[i]).getValue();
        }

        Pointer[] in2Pointers = new Pointer[in2.length];
        for (int i = 0; i <in2.length; i++) {
            in2Pointers[i] = ((PBCElement) in2[i]).getValue();
        }

        WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_prod(
                out.getValue(),
                in1Pointers,
                in2Pointers,
                in1.length);

        return out;
    }

    public PairingPreProcessing pairing(Element in1) {
        return new PBCPairingPreProcessing(in1);
    }

    public PairingPreProcessing pairing(byte[] source) {
        return new PBCPairingPreProcessing(source);
    }

    public boolean isAlmostCoddh(Element a, Element b, Element c, Element d) {
        return WrapperLibraryProvider.getWrapperLibrary().pbc_is_almost_coddh(
                ((PBCElement) a).getValue(),
                ((PBCElement) b).getValue(),
                ((PBCElement) c).getValue(),
                ((PBCElement) d).getValue(),
                pairing
        ) == 1;
    }


    protected void initFields() {
        G1 = new PBCG1Field(pairing);
        G2 = new PBCG2Field(pairing);
        GT = new PBCGTField(pairing);
        Zr = new PBCZrField(pairing);
    }


    public class PBCPairingPreProcessing implements PairingPreProcessing {
        protected PBCPairingPPType pairingPPType;

        protected Pointer in1;


        public PBCPairingPreProcessing(Element in1) {
            this.in1 = ((PBCElement) in1).value;
            this.pairingPPType = new PBCPairingPPType(this.in1, pairing);
        }

        public PBCPairingPreProcessing(byte[] source) {
            fromBytes(source);
        }

        public Element pairing(Element in2) {
            PBCElement out = (PBCElement) GT.newElement();
            WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_pp_apply(
                    out.value,
                    ((PBCElement) in2).value,
                    pairingPPType
            );

            return out;
        }

        public byte[] toBytes() {
            byte[] bytes = new byte[WrapperLibraryProvider.getWrapperLibrary().pbc_element_length_in_bytes(this.in1)];
            WrapperLibraryProvider.getWrapperLibrary().pbc_element_to_bytes(bytes, this.in1);
            return bytes;
        }

        public void fromBytes(byte[] source) {
            PBCElement tmep = (PBCElement) getG1().newElement();
            tmep.setFromBytes(source);

            this.in1 = tmep.value;
            this.pairingPPType = new PBCPairingPPType(this.in1, pairing);
        }

    }
}
