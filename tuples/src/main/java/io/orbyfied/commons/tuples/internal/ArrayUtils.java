package io.orbyfied.commons.tuples.internal;

public class ArrayUtils {

    public static int endOf(Object... arr) {
        int l = arr.length;
        for (int i = 0; i < l; i++) {
            boolean fe = false;
            for (int f = i; f < l; f++) {
                if (arr[f] != null) {
                    i = f;
                    fe = true;
                    break;
                }
            }
            if (!fe)
                return i - 1;
        }

        return arr.length - 1;
    }

}
