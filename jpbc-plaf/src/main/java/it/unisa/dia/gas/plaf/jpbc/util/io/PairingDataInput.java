package it.unisa.dia.gas.plaf.jpbc.util.io;

import it.unisa.dia.gas.jpbc.*;

import java.io.DataInput;
import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public class PairingDataInput implements DataInput {

    private DataInput dataInput;
    private PairingParameters parameters;
    private Pairing pairing;


    public PairingDataInput(DataInput dataInput) {
        this.dataInput = dataInput;
    }


    public void readFully(byte[] b) throws IOException {
        dataInput.readFully(b);
    }

    public void readFully(byte[] b, int off, int len) throws IOException {
        dataInput.readFully(b, off, len);
    }

    public int skipBytes(int n) throws IOException {
        return dataInput.skipBytes(n);
    }

    public boolean readBoolean() throws IOException {
        return dataInput.readBoolean();
    }

    public byte readByte() throws IOException {
        return dataInput.readByte();
    }

    public int readUnsignedByte() throws IOException {
        return dataInput.readUnsignedByte();
    }

    public short readShort() throws IOException {
        return dataInput.readShort();
    }

    public int readUnsignedShort() throws IOException {
        return dataInput.readUnsignedShort();
    }

    public char readChar() throws IOException {
        return dataInput.readChar();
    }

    public int readInt() throws IOException {
        return dataInput.readInt();
    }

    public long readLong() throws IOException {
        return dataInput.readLong();
    }

    public float readFloat() throws IOException {
        return dataInput.readFloat();
    }

    public double readDouble() throws IOException {
        return dataInput.readDouble();
    }

    public String readLine() throws IOException {
        return dataInput.readLine();
    }

    public String readUTF() throws IOException {
        return dataInput.readUTF();
    }

    public Pairing getPairing() {
        return pairing;
    }

    public Field readField() throws IOException {
        Pairing.PairingFieldIdentifier identifier = Pairing.PairingFieldIdentifier.values()[readInt()];
        switch (identifier) {
            case G1:
                return pairing.getG1();
            case G2:
                return pairing.getG2();
            case GT:
                return pairing.getGT();
            case Zr:
                return pairing.getZr();
            default:
                throw new IllegalArgumentException("Illegal Field Identifier.");
        }
    }


    public Element readElement(Pairing.PairingFieldIdentifier fieldIdentifier) throws IOException {
        Element e;
        switch (fieldIdentifier) {
            case G1:
                e = pairing.getG1().newElement();
                break;
            case G2:
                e = pairing.getG2().newElement();
                break;
            case GT:
                e = pairing.getGT().newElement();
                break;
            case Zr:
                e = pairing.getZr().newElement();
                break;
            default:
                throw new IllegalArgumentException("Invalid Field Type.");
        }

        byte[] buffer = new byte[readInt()];
        readFully(buffer);
        e.setFromBytes(buffer);

        return e;
    }

    public Element[] readElements(Pairing.PairingFieldIdentifier identifier) throws IOException{
        int num = readInt();
        Element[] elements = new Element[num];
        for (int i = 0; i < num; i++) {
            elements[i] = readElement(identifier);
        }

        return elements;
    }

    public PairingPreProcessing readPairingPreProcessing() throws IOException {
        int size = readInt();
        byte[] buffer = new byte[size];
        readFully(buffer);

        return getPairing().pairing(buffer, 0);
    }

    public ElementPowPreProcessing readElementPowPreProcessing() throws IOException {
        // Read field identifier
        Field field = readField();

        // read the preprocessing information
        int size = readInt();
        byte[] buffer = new byte[size];
        readFully(buffer);

        return field.pow(buffer, 0);
    }

    public int[] readInts() throws IOException {
        int num = readInt();
        int[] elements = new int[num];
        for (int i = 0; i < num; i++) {
            elements[i] = readInt();
        }

        return elements;
    }

    public byte[] readBytes() throws IOException {
        int length = readInt();
        byte[] buffer = new byte[length];
        readFully(buffer);

        return buffer;
    }

    public BigInteger readBigInteger() throws IOException {
        return new BigInteger(readBytes());
    }

    public BigInteger[] readBigIntegers() throws IOException {
        int num = readInt();
        BigInteger[] bigIntegers = new BigInteger[num];
        for (int i = 0; i < bigIntegers.length; i++) {
            bigIntegers[i] = readBigInteger();
        }

        return bigIntegers;
    }

}