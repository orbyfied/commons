package io.orbyfied.commons.tuples;

import java.util.Iterator;

/**
 * Mutable tuple wrapper.
 */
public class Mutuple extends Tuple {

    /**
     * Wraps an existent tuple in
     * a mutable to make it mutable.
     * @param t The tuple.
     * @return The mutable.
     */
    public static Mutuple wrap(Tuple t) {
        return new Mutuple(t);
    }

    ////////////////////////////////////////////

    /** Wrapping Constructor. */
    private Mutuple(Tuple wrap) {
        super(0);
        this.arr = null;
        this.wrapped = wrap;
    }

    ///////////////////////////////////////////

    /**
     * The tuple that is being wrapped.
     */
    Tuple wrapped;
    public Tuple wrapped() {
        return wrapped;
    }

    /**
     * Sets the value at the given index
     * in the tuple.
     * @param i The index.
     * @param o The value.
     * @return This.
     */
    public Mutuple set(int i, Object o) {
        if (i >= wrapped.arr.length)
            return this;
        wrapped.arr[i] = o;
        return this;
    }

    /**
     * Resizes the internal array
     * to the specified size.
     * @param s The target size.
     * @return This.
     */
    public Mutuple resize(int s) {
        if (s < 0) throw new IllegalArgumentException();
        Object[] tmp = new Object[s];
        System.arraycopy(wrapped.arr, 0, tmp, 0, Math.min(wrapped.arr.length, s));
        wrapped.arr = tmp;
        return this;
    }

    /* Wrap Methods. */

    @Override public Object[] toArray() { return wrapped.toArray(); }
    @Override public <T> T[] toArray(Class<T> tClass) { return wrapped.toArray(tClass); }
    @Override public Object get(int i) { return wrapped.get(i); }
    @Override public <T> T get(int i, Class<T> tClass) { return wrapped.get(i, tClass); }
    @Override public <T> T unpack(T layout) { return wrapped.unpack(layout); }
    @Override public <T> T unpack(Class<T> layout) { return wrapped.unpack(layout); }
    @Override public Mutuple toMutable() { return wrapped.toMutable(); }
    @Override public Object clone() { return wrap((Tuple) wrapped.clone()); }
    @Override public Iterator<Object> iterator() {
        return wrapped.iterator();
    }
    @Override public boolean equals(Object o) {
        return wrapped.equals(o);
    }
    @Override public int hashCode() {
        return wrapped.hashCode();
    }
    @Override public String toString() {
        return wrapped.toString();
    }

}
