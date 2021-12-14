package io.orbyfied.commons.tuples;

/**
 * Stores two values with two strict
 * types.
 * @param <X> First value type.
 * @param <Y> Second value type.
 */
public class Pair<X, Y> extends Tuple {

    /** First value. */
    X x;

    /** Second value. */
    Y y;

    /** Constructor. */
    public Pair(X x, Y y) {
        super(2);

        this.x = x;
        this.y = y;
    }

    /* Getters. */
    public X x() { return x; }
    public Y y() { return y; }

    /* -------------------------------- */

    @Override
    public Object[] toArray() {
        return new Object[] { x, y };
    }

    @Override
    public Mutuple toMutable() {
        throw new UnsupportedOperationException();
    }
}
