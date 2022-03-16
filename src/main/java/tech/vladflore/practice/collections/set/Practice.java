package tech.vladflore.practice.collections.set;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Practice {
    public static void main(String[] args) {

        Set<String> treeSet = new TreeSet<>();
        Set<String> synchronizedTreeSet = Collections.synchronizedSet(treeSet);
        Set<String> treeSet1 = new TreeSet<>(Comparator.comparing(String::length).reversed());

        treeSet1.add("hi");
        treeSet1.add("hello");
        treeSet1.add("howdyyy");

        System.out.println(treeSet1.contains("hello"));

        Iterator<String> iterator = treeSet1.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println();

        TreeSet<String> treeSet2 = new TreeSet<>(treeSet1);
        Iterator<String> descendingIterator = treeSet2.descendingIterator();
        while (descendingIterator.hasNext()) {
            System.out.println(descendingIterator.next());
        }

        System.out.println(treeSet2.first()); // hello
        System.out.println(treeSet2.last()); // howdy
    }
}
