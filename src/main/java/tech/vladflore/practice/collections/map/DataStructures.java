package tech.vladflore.practice.collections.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class DataStructures {
    public static void main(String[] args) {
        Map<Person, Integer> map = new HashMap<>();

        new Hashtable<>();

        map.put(new Person("Vlad"), 1);
        map.putIfAbsent(new Person("Ioana"), 2);

        map.put(null, null);
//        map.put(null, -7);
        map.putIfAbsent(null, 7);

        System.out.println(map);
    }
}

record Person(String name) {
}