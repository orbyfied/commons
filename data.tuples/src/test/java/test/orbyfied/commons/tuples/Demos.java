package test.orbyfied.commons.tuples;

import io.orbyfied.commons.tuples.Mutuple;
import io.orbyfied.commons.tuples.Tuple;

import java.util.StringJoiner;

/**
 * Simple demo(s).
 */
public class Demos {

    public static void main(String[] args) {

        /* 1 - Basic Tuple Creation */
        Tuple a = Tuple.of("hello", Integer.MAX_VALUE);
        System.out.println(a); // should yield "(hello, 2147483647)"

        /* 2 - Basic Tuple Modification */
        Tuple b = Tuple.of("Hello World!");
        System.out.println(b); // should yield "(Hello World!)"

        // Mutuples are mutable wrappers for tuples.
        // This is how you get one from an existent tuple;
        Mutuple mb = b.toMutable();
        mb.resize(3); // resize the tuple to 3 elements instead of one
        mb.set(1, 6).set(2, 9); // set elements 2 and 3 to 6 and 9 respectively
        // as you can see, you can chain Mutuple calls.

        System.out.println(b); // should yield "(Hello World!, 6, 9)

        /* 3 - Layouts */

        // Layouts are classes which can be filled with tuple(s).
        // All fields in a layout that are annotated with @Tuple.Packed get filled. (in order)

        // /!\ Layout class (DateLayout) can be found at the bottom of this file. (ln 58)

        // To unpack a tuple into a layout, just do;
        Tuple c = Tuple.of(13, 12, 2021);
        DateLayout date = c.unpack(DateLayout.class);
        System.out.println(date); // should yield "13/12/2021"

        // You can even unpack multiple tuples into one layout
        // Imagine a method that returns the day and month, but not the year.
        // Combining them is quite simple;
        Tuple dayAndMonth = Tuple.of(13, 12);
        Tuple onlyYear    = Tuple.of(2021);
        DateLayout layout = Tuple.unpackAll(DateLayout.class, dayAndMonth, onlyYear);
        System.out.println(layout); // should (also) yield "13/12/2021"

    }

    /**
     * From §3 - Layouts.
     * A really unspecific date.
     */
    public static class DateLayout {
        @Tuple.Packed public Integer day;
        @Tuple.Packed public Integer month;
        @Tuple.Packed public Integer year;

        @Override
        public String toString() { return day + "/" + month + "/" + year; }
    }

}
