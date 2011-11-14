package it.unisa.dia.gas.plaf.jpbc.wrapper.jna;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PBCPairingPPType extends Memory {

    public PBCPairingPPType() {
        super(WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_pp_sizeof());
    }


    public PBCPairingPPType(Pointer element, Pointer pairing) {
        this();
        WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_pp_init(this, element, pairing);
    }

    public PBCPairingPPType(byte[] source, Pointer pairing) {
        this();

        if (WrapperLibraryProvider.getWrapperLibrary().pbc_is_pairing_pp_io_available())
            WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_pp_init_from_bytes(this, source, pairing);
        else
            throw new IllegalStateException("Initialization not supported.");
    }

    @Override
    protected void finalize() {
        if (isValid()) {
            WrapperLibraryProvider.getWrapperLibrary().pbc_pairing_pp_clear(this);
            super.finalize();
        }
    }
}