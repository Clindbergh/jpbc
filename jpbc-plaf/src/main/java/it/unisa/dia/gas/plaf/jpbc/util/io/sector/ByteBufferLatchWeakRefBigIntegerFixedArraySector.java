package it.unisa.dia.gas.plaf.jpbc.util.io.sector;

import java.io.IOException;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.3.0
 */
public class ByteBufferLatchWeakRefBigIntegerFixedArraySector extends ByteBufferLatchWeakRefBigIntegerArraySector {

    public ByteBufferLatchWeakRefBigIntegerFixedArraySector(int recordSize, int numRecords) throws IOException {
        super(recordSize, numRecords);

        this.lengthInBytes = ((recordSize + 4) * numRecords);
        this.offset = 0;
    }

    public ByteBufferLatchWeakRefBigIntegerFixedArraySector(int recordSize, int numRecords, String... labels) throws IOException {
        super(recordSize, numRecords, labels);

        this.lengthInBytes = ((recordSize + 4) * numRecords);
        this.offset = 0;
    }
}
