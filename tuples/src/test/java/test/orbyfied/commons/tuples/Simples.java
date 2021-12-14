package test.orbyfied.commons.tuples;

import io.orbyfied.commons.tuples.Mutuple;
import io.orbyfied.commons.tuples.StrictTuple;
import io.orbyfied.commons.tuples.Tuple;
import io.orbyfied.commons.tuples.internal.ArrayUtils;

/**
 * Simple, manual tests.
 */
public class Simples {

    public static void main(String[] args) {

                               //   0    1    2    3   4  5  6
        int idx = ArrayUtils.endOf("a", "b", 'c', 'd', 3, 5, 6);
        System.out.println(idx);

        /////////////////////////////////////

        Tuple tuple = Tuple.of("Hello!");
        tuple.toMutable()
                .mergeBack(6, 9, 420);
        System.out.println(tuple);

    }

}
