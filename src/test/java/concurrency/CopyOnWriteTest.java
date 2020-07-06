package concurrency;

import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteTest {

    @Test
    public void testCopyOnWriteArrayList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{1,2,3});

        System.out.println(list);

        Iterator<Integer> itr1 = list.iterator();

        list.add(4);

        System.out.println(list);

        Iterator<Integer> itr2 = list.iterator();

        System.out.println("Verify iterator 1 content=====");

        itr1.forEachRemaining(System.out:: println);

        System.out.println("Verify iterator 2 content=====");

        itr2.forEachRemaining(System.out:: println);
    }
}
