package io.orbyfied.commons.tuples;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Simple tuple class.
 * @author orbyfied
 */
public class Tuple implements
        Iterable<Object>, Cloneable {

    /**
     * Marks a field in a layout class as
     * a unpackable field.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target   (ElementType.FIELD)
    public @interface Packed { }

    //////////////////////////////////////////

    /**
     * Unpacks all tuples into the supplied
     * layout object in-order.
     * @param layout The layout object.
     * @param tuples The array of tuples.
     * @param <T> The layout type.
     * @return The layout object. (gives back)
     */
    public static <T> T unpackAll(T layout, Tuple... tuples) {
        if (layout == null)
            throw new NullPointerException();
        for (Tuple t : tuples)
            t.unpack(layout);
        return layout;
    }

    /**
     * @see Tuple#unpackAll(Object, Tuple...)
     */
    public static <T> T unpackAll(Class<T> layout, Tuple... tuples) {
        if (layout == null)
            throw new NullPointerException();

        T instance;
        try {
            instance = layout.newInstance();
        } catch (Exception e) { e.printStackTrace(); throw new IllegalArgumentException(); }
        return unpackAll(instance, tuples);
    }

    /**
     * Creates a new tuple containing the
     * supplied values.
     * @param o The values.
     * @return The tuple.
     */
    public static Tuple of(Object... o) {
        return new Tuple(o);
    }

    /**
     * Used to create a tuple of an array, not
     * a varargs.
     * @param o The values.
     * @param <V> The type of the values.
     * @return The tuple.
     */
    public static <V> Tuple ofArray(V[] o) {
        return new Tuple(o);
    }

    /**
     * Copies and converts the supplied
     * tuple to a generic one. FOR EXAMPLE:
     * <code>Pair(x, y) -> Tuple({x, y})</code>
     * @param t The tuple to convert.
     * @return The converted tuple.
     */
    public static Tuple from(Tuple t) {
        return new Tuple(t.toArray());
    }

    @SafeVarargs
    public static <T> StrictTuple<T> strictOf(T... objs) {
        return new StrictTuple<>(objs);
    }

    public static <T> StrictTuple<T> strictOfArray(T[] objs) {
        return new StrictTuple<>(objs);
    }

    /**
     * Creates a new tuple with all integers
     * between the supplied start and end (inclusive)
     * @param s The start number.
     * @param e The end number.
     * @return The tuple.
     */
    public static Tuple range(int s, int e) {
        int l = (e - s) + 1;
        Integer[] ints = new Integer[l];
        for (int i = 0; i < l; i++) {
            ints[i] = i + s;
        }
        return Tuple.ofArray(ints);
    }

    /**
     * 'Cleans' (sets marked fields to null) the
     * supplied layout instance.
     * @param instance The layout instance.
     * @param <T> The layout type.
     * @return The (same) layout instance.
     */
    public static <T> T cleanLayout(T instance) {
        try {
            Class<?> klass = instance.getClass();
            for (Field f : klass.getDeclaredFields()) {
                if (!f.isAnnotationPresent(Packed.class)) continue;
                f.setAccessible(true);
                f.set(instance, null);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return instance;
    }

    //////////////////////////////////////////

    /**
     * The internal array storing the objects.
     */
    Object[] arr;

    /* Constructors. */

    public Tuple(int s) { this.arr = new Object[s]; }
    public Tuple(Object[] objs) { this.arr = objs; }
    public Tuple(int s, Object[] objs) {
        this(s);
        System.arraycopy(objs, 0, arr, 0, objs.length);
    }

    /**
     * Returns the internal array.
     * @return The array.
     */
    public Object[] toArray() {
        return arr;
    }

    /**
     * Returns the internal array as a specific type.
     * @return The array.
     */
    public <T> T[] toArray(Class<T> tClass) {
        return (T[]) arr;
    }

    /**
     * Returns the object at position i.
     * @param i The index.
     * @return The object.
     */
    public Object get(int i) {
        if (i >= arr.length)
            return null;
        return arr[i];
    }

    /**
     * Returns the object at position i with type T.
     * @param i The index.
     * @return The object.
     */
    public <T> T get(int i, Class<T> tClass) {
        return (T) arr[i];
    }

    /**
     * Unpacks this tuple into a layout instance. Only
     * puts values in fields marked with <code>@Tuple.Packed</code>
     * and are empty.
     * @param layout The layout instance.
     * @param <T> The layout type.
     * @return Gives back the layout instance.
     */
    public <T> T unpack(T layout) {
        try {
            // get and iterate over fields
            Class<T> lc = (Class<T>) layout.getClass();
            int i = 0;
            int l = arr.length;
            for (Field f : lc.getDeclaredFields()) {
                // validate index
                if (i == l) break;
                // validate field
                if (!f.isAnnotationPresent(Packed.class)) continue;
                if (f.get(layout) != null)                continue;

                // set field
                f.setAccessible(true);
                f.set(layout, arr[i]);

                // advance
                i++;
            }

            return layout;
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    /**
     * Unpacks the tuple into a new instance of
     * class layout. Returns the new instance.
     * @see Tuple#unpack(Object)
     */
    public <T> T unpack(Class<T> layout) {
        try {
            T l = layout.newInstance();
            return unpack(l);
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    /**
     * Converts this tuple to a
     * mutable tuple (mutuple)
     * @return
     */
    public Mutuple toMutable() {
        return Mutuple.wrap(this);
    }

    /* --------------------------------------- */

    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) { e.printStackTrace(); return null; }
    }

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<>() { int i = 0; final int l = arr.length;
            @Override public boolean hasNext() {
                return i < l - 1;
            }

            @Override public Object next() {
                return arr[i++];
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple tuple = (Tuple) o;
        return Arrays.equals(arr, tuple.arr);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arr);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("(");
        int l = arr.length;
        for (int i = 0; i < l; i++) {
            b.append(arr[i]);
            if (i < l - 1) b.append(", ");
        }
        return b.append(")").toString();
    }
}
