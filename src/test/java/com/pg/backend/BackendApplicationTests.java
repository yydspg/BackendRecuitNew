package com.pg.backend;

import com.pg.backend.algorithm.linkedList.DoublyLinkedListSentinel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Test
    public void test() throws NoSuchMethodException {
        DoublyLinkedListSentinel<Integer> integers = new DoublyLinkedListSentinel<>();
        integers.addFirst(1);

        while (integers.iterator().hasNext()) {
            Integer next =  integers.iterator().next();
            System.out.println(next+" ");
        }
    }

}

