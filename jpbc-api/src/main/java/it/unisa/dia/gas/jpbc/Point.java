package it.unisa.dia.gas.jpbc;

/**
 * This interface represents an element with two coordinates. (A point over an elliptic curve).
 *
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 */
public interface Point<E extends Element> extends Element {

    /**              
     * Returns the x-coordinate.
     *
     * @return the x-coordinate.
     */
    E getX();

    /**
     * Returns the y-coordinate.
     *
     * @return the y-coordinate.
     */
    E getY();

    int getLengthInBytesCompressed();

    /**
     * Converts this element to bytes. The number of bytes it will write can be determined calling getLengthInBytesCompressed().
     *
     * @return the bytes written.
     */
    byte[] toBytesCompressed();

    /**
     * Reads this element from the buffer source. staring from the passed offset.
     *
     * @param source the source of bytes.
     * @return the number of bytes read.
     */
    int setFromBytesCompressed(byte[] source);

    /**
     * Reads the x-coordinate from the buffer source staring from the passed offset. The y-coordinate is then recomputed.
     * Pay attention. the y-coordinate could be different from the element which originates the buffer source.
     *
     * @param source the source of bytes.
     * @param offset the starting offset.
     * @return the number of bytes read.
     */
    int setFromBytesCompressed(byte[] source, int offset);

    int getLengthInBytesX();

    /**
     * Converts the x-coordinate to bytes. The number of bytes it will write can be determined calling getLengthInBytesX().
     *
     * @return the bytes written.
     */
    byte[] toBytesX();

    /**
     * Reads the x-coordinate from the buffer source. The y-coordinate is then recomputed.
     * Pay attention. the y-coordinate could be different from the element which originates the buffer source.
     *
     * @param source the source of bytes.
     * @return the number of bytes read.
     */
    int setFromBytesX(byte[] source);

    /**
     * Reads the x-coordinate from the buffer source staring from the passed offset. The y-coordinate is then recomputed.
     * Pay attention. the y-coordinate could be different from the element which originates the buffer source.
     *
     * @param source the source of bytes.
     * @param offset the starting offset.
     * @return the number of bytes read.
     */
    int setFromBytesX(byte[] source, int offset);

}
