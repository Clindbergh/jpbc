package it.unisa.dia.gas.plaf.jpbc.util.concurrent;

import java.util.concurrent.Callable;

/**
 * @author Angelo De Caro (angelo.decaro@gmail.com)
 * @since 1.3.0
 */
public interface Pool<T> {

    Pool<T> submit(Callable<T> callable);

    Pool<T> submit(Runnable runnable);

    Pool<T> process();

}