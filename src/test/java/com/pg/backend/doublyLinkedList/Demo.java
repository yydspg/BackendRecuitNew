package com.pg.backend.doublyLinkedList;

import com.pg.backend.algorithm.linkedList.DoublyLinkedListSentinel;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.Scanner;

@SpringBootTest
public class Demo {

    @Test
    public void testList1() throws NoSuchMethodException {
        DoublyLinkedListSentinel<Integer> integers = new DoublyLinkedListSentinel<>();
        System.out.println("============");
        integers.addLast(1);
        integers.addLast(2);
        integers.addLast(4);
        integers.addLast(5);
        integers.addLast(6);
        integers.addByIndex(2,3);
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer next =  iterator.next();
            System.out.println(next+" ");
        }
    }
    @Test
    public void testList2() throws NoSuchMethodException {
        DoublyLinkedListSentinel<Integer> integers = new DoublyLinkedListSentinel<>();
        System.out.println("============");
        integers.addLast(1);
        integers.addLast(2);
        integers.addLast(4);
        integers.addLast(5);
        integers.addLast(6);
        integers.addByIndex(2,3);
        integers.removeFirst();
        integers.removeLast();
        integers.removeByIndex(2);
        Iterator<Integer> iterator = integers.iterator();
        while (iterator.hasNext()) {
            Integer next =  iterator.next();
            System.out.println(next+" ");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        String[] s = in.nextLine().split(" ");
        System.out.println(s.length);
        int t1 = Integer.parseInt(s[0]);
        int t2 = Integer.parseInt(s[1]);
        int t3 = Integer.parseInt(s[2]);
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
    }
}
