package io.orbyfied.commons.tuples;

public class Pair<X, Y> extends Tuple {

    X x;
    Y y;

    public Pair(X x, Y y) {
        super(2);
        this.arr = new Object[] {
                x, y
        };

        this.x = x;
        this.y = y;
    }

    public X x() { return x; }
    public Y y() { return y; }

}
