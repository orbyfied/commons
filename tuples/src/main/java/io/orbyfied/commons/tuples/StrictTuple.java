package io.orbyfied.commons.tuples;

@SuppressWarnings("unchecked")
public class StrictTuple<V> extends Tuple {

    public StrictTuple(int s) { super(s); }
    public StrictTuple(V[] objs) { super(objs); }
    public StrictTuple(int s, V[] objs) {
        super(s, objs);
    }

    @Override
    public V get(int i) {
        return (V) super.get(i);
    }

    @Override
    public V[] toArray() {
        return (V[]) super.toArray();
    }

}
