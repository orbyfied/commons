package io.orbyfied.commons.tuples;

import io.orbyfied.commons.tuples.internal.ArrayUtils;

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
     * Expands and places values at the
     * back of the tuple.
     * @param objs The values.
     * @return This.
     */
    public Mutuple mergeBack(Object... objs) {
        int l  = objs.length;
        int si = ArrayUtils.endOf(wrapped.arr) + 1;
        int ei = si + l;
        if (ei > wrapped.arr.length - 1) {
            int el = ei - wrapped.arr.length;
            this.resize(wrapped.arr.length + el);
        }
        this.place(si, true, objs);
        return this;
    }

    /**
     * Places the values of the tuple
     * at the back of this one.
     * @see Mutuple#mergeBack(Object...)
     * @return This.
     */
    public Mutuple mergeBack(Tuple tuple) {
        return mergeBack(tuple.toArray());
    }

    /**
     * Places all supplied values at
     * the specified index.
     * @param i The index to place them at.
     * @param overwrite If existent values should be overwritten.
     * @param objs The values.
     * @return This.
     */
    public Mutuple place(int i, boolean overwrite, Object... objs) {
        if (i >= wrapped.arr.length)
            return this;
        if (overwrite) {
            System.arraycopy(objs, 0, wrapped.arr, i, Math.min(wrapped.arr.length - i, objs.length));
        } else {
            for (int f = 0; f < Math.min(wrapped.arr.length - i, objs.length); i++)
                if (wrapped.arr[f + i] == null) wrapped.arr[f + i] = objs[f];
        }
        return this;
    }

    /**
     * Place the values of the tuple
     * at the specified index.
     * @see Mutuple#place(int, boolean, Object...)
     * @return This.
     */
    public Mutuple place(int i, boolean overwrite, Tuple tuple) {
        return place(i, overwrite, tuple.toArray());
    }

    /**
     * Places the values at the specified
     * index. The overwrite flag is defaulted
     * to true.
     * @see Mutuple#place(int, boolean, Object...)
     * @return This.
     */
    public Mutuple placeOver(int i, Object... objs) {
        return place(i, true, objs);
    }

    /**
     * Places the values of the tuple
     * at the specified index. The overwrite
     * flag is defaulted to true.
     * @see Mutuple#place(int, boolean, Tuple)
     * @return This.
     */
    public Mutuple placeOver(int i, Tuple tuple) {
        return place(i, true, tuple);
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
    @Override public Iterator<Object> iterator() { return wrapped.iterator(); }
    @Override public boolean equals(Object o) { return wrapped.equals(o); }
    @Override public int hashCode() { return wrapped.hashCode(); }
    @Override public String toString() { return wrapped.toString(); }

}
