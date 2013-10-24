package it.unisa.dia.gas.plaf.jpbc.field.z;

import it.unisa.dia.gas.jpbc.Element;

import java.math.BigInteger;

/**
 * @author Angelo De Caro (jpbclib@gmail.com)
 */
public class ImmutableZrElement extends ZrElement {

    public ImmutableZrElement(ZrElement zrElement) {
        super(zrElement);
        this.immutable = true;
    }

    @Override
    public ZrElement duplicate() {
        return super.duplicate();
    }

    @Override
    public ZrElement set(Element value) {
        return (ZrElement) super.duplicate().set(value).getImmutable();
    }

    @Override
    public ZrElement set(int value) {
        return (ZrElement) super.duplicate().set(value).getImmutable();
    }

    @Override
    public ZrElement set(BigInteger value) {
        return (ZrElement) super.duplicate().set(value).getImmutable();
    }

    @Override
    public ZrElement twice() {
        return (ZrElement) super.duplicate().twice().getImmutable();
    }

    @Override
    public ZrElement mul(int z) {
        return (ZrElement) super.duplicate().mul(z).getImmutable();
    }

    @Override
    public ZrElement setToZero() {
        return (ZrElement) super.duplicate().setToZero().getImmutable();
    }

    @Override
    public ZrElement setToOne() {
        return (ZrElement) super.duplicate().setToOne().getImmutable();
    }

    @Override
    public ZrElement setToRandom() {
        return (ZrElement) super.duplicate().setToRandom().getImmutable();
    }

    @Override
    public ZrElement setFromHash(byte[] source, int offset, int length) {
        return (ZrElement) super.duplicate().setFromHash(source, offset, length).getImmutable();
    }

    @Override
    public int setFromBytes(byte[] source) {
        throw new IllegalStateException("Invalid call on an immutable element");
    }

    @Override
    public int setFromBytes(byte[] source, int offset) {
        throw new IllegalStateException("Invalid call on an immutable element");
    }

    @Override
    public ZrElement square() {
        return (ZrElement) super.duplicate().square().getImmutable();
    }

    @Override
    public ZrElement invert() {
        return (ZrElement) super.duplicate().invert().getImmutable();
    }

    @Override
    public ZrElement halve() {
        return (ZrElement) super.duplicate().halve().getImmutable();
    }

    @Override
    public ZrElement negate() {
        return (ZrElement) super.duplicate().negate().getImmutable();
    }

    @Override
    public ZrElement add(Element element) {
        return (ZrElement) super.duplicate().add(element).getImmutable();
    }

    @Override
    public ZrElement sub(Element element) {
        return (ZrElement) super.duplicate().sub(element).getImmutable();
    }

    @Override
    public ZrElement div(Element element) {
        return (ZrElement) super.duplicate().div(element).getImmutable();
    }

    @Override
    public ZrElement mul(Element element) {
        return (ZrElement) super.duplicate().mul(element).getImmutable();
    }

    @Override
    public ZrElement mul(BigInteger n) {
        return (ZrElement) super.duplicate().mul(n).getImmutable();
    }

    @Override
    public ZrElement mulZn(Element z) {
        return (ZrElement) super.duplicate().mulZn(z).getImmutable();
    }

    @Override
    public ZrElement sqrt() {
        return (ZrElement) super.duplicate().sqrt().getImmutable();
    }

    @Override
    public ZrElement pow(BigInteger n) {
        return (ZrElement) super.duplicate().pow(n).getImmutable();
    }

    @Override
    public ZrElement powZn(Element n) {
        return (ZrElement) super.duplicate().powZn(n).getImmutable();
    }

}
