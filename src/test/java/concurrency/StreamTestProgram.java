package concurrency;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamTestProgram {

    @Test
    public void testStreamBasic() {
        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList.stream().
                filter(s -> s.startsWith("c")).
                map(String::toUpperCase).sorted().
                forEach(System.out::println);
    }

    @Test
    public void testPrintInt() {
        IntStream.range(1, 4)
                .forEach(System.out::println);
    }

    @Test
    public void testAverage() {
        IntStream.range(1, 10)
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);
    }

    @Test
    public void testMax() {
        Stream.of("a1", "a2", "a3")
                .map(s -> s.substring(1))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);
    }

    @Test
    public void testMapToObj() {
        Stream.of(1.0,2.0,3.0).mapToDouble(Double::doubleValue)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
    }

    @Test
    public void testImtermediateAndTerminalFunc() {
        Stream.of("d2", "a2", "b1", "b3", "c")
                .filter(s -> {
                    System.out.println("filter: " + s);
                    return true;
                })
                .forEach(s -> System.out.println("forEach: " + s));
    }

    @Test
    public void testMatchAny() {
        // once anyMatch return true, terminate the chain
        boolean anyMatch = Stream.of(1,2,3,4,5,6)
                .filter(s -> {
                    System.out.println("num: " + s);
                    return true;
                }).anyMatch(s-> {return s > 4;});
        System.out.println(anyMatch);

    }
}
