package tech.vladflore.practice.collections.map;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.util.LinkedCaseInsensitiveMap;

import lombok.AllArgsConstructor;
import lombok.ToString;

public class Practice {
    public static void main(String[] args) {

        Map<String, Product> products = new HashMap<>();
        Product p1 = new Product("p1", "p1 description");
        Product p2 = new Product("p2", "p2 description");
        products.put(p1.getName(), p1);
        products.put(p2.getName(), p2);

        for (String pName : products.keySet()) {
            System.out.println(pName);
        }

        p1.addTag("tag1_p1").addTag("tag2_p1");
        p2.setTags(List.of("tag1_p2", "tag2_p2"));

        for (Map.Entry<String, Product> entry : products.entrySet()) {
            System.out.printf("Key=%s, Value=%s\n", entry.getKey(), entry.getValue());
        }

        products.values().forEach(System.out::println);

        Map<Product, Integer> quantities = new HashMap<>();
        quantities.put(p1, 7);
        quantities.put(p2, 3);

        printMap(quantities);

        System.out.println(quantities.get(p1)); // prints 7
        System.out.println(quantities.get(p2)); // prints 3

        // find the product in the map by the id
        UUID productId = p1.getId();
        System.out.println(quantities.get(new Product(productId))); // prints 7

        quantities.forEach((k, v) -> System.out.printf("%s %d\n", k.getName(), v));

        System.out.println(quantities.getOrDefault("p3", -1));

        Map<String, String> myMap = new HashMap<>();
        myMap.put(null, null);

        printMap(myMap);

        MyKey myKey = new MyKey(7, "7");
        Map<MyKey, String> myMap1 = new HashMap<>();
        myMap1.put(myKey, "Custom key");

        Set<MyKey> myKeysSet = myMap1.keySet();
        myKeysSet.remove(myKey);
        System.out.println(myMap1.size());

        System.out.println("===============================================");

        Map<MyKey, String> collision = new HashMap<>();
        collision.put(new MyKey(1, "1"), "one");
        collision.put(new MyKey(2, "2"), "two");
        collision.put(new MyKey(2, "3"), "three");

        // until here we have:

        // Inside hashCode
        // Inside hashCode
        // Inside hashCode
        // Inside equals

        System.out.println(collision.get(new MyKey(1, "1")));
        System.out.println(collision.get(new MyKey(2, "2")));
        System.out.println(collision.get(new MyKey(2, "3")));

        // until here we have:

        // Inside hashCode
        // Inside equals
        // one
        // Inside hashCode
        // Inside equals
        // three
        // Inside hashCode
        // Inside equals
        // three

        System.out.println();

        collision.keySet().forEach(System.out::println);
        collision.values().forEach(System.out::println);

        // SoftReference
        // WeakReference
        // WeakHashMap -> WeakReference as key

        System.out.println("===============================================");

        TreeMap<Integer, String> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.put(1, "1");
        treeMap.put(2, "2");
        treeMap.put(3, "3");
        treeMap.keySet().forEach(System.out::println);

        System.out.println("===============================================");
        Map<Integer, String> linkedMap = new LinkedHashMap<>();
        linkedMap.put(1, "1");
        linkedMap.put(2, "2");
        linkedMap.put(3, "3");
        linkedMap.entrySet().forEach(System.out::println);

        // LRU cache, i.e. the elements are orderd from least-recently used to
        // most-recently used
        linkedMap = new LinkedHashMap<>(16, .75f, true);
        linkedMap.put(1, "1");
        linkedMap.put(2, "2");
        linkedMap.put(3, "3");
        linkedMap.keySet().forEach(System.out::print); // 1 2 3

        System.out.println();

        linkedMap.get(1);
        linkedMap.keySet().forEach(System.out::print); // 2 3 1

        System.out.println();

        Map<String, Integer> m = new MyLinkedHashMap<>(16, .75f, true);
        m.put("1", 1);
        m.put("2", 2);
        m.put("3", 3);
        m.put("4", 4);
        m.put("5", 5);
        m.get("4");
        System.out.println(m); // 3 5 4

        System.out.println("===============================================");

        EnumMap<Day, String> days = new EnumMap<>(Day.class);
        days.put(Day.MONDAY, "Monday");
        days.put(Day.TUESDAY, "Tuesday");
        days.put(Day.WEDNESDAY, "Wednesday");
        days.keySet().forEach(System.out::println); // iterates in Enum order (as the enums are defined in the Day enum)

        System.out.println("===============================================");
        Map<String, String> mInsensitive = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        mInsensitive.put("vlad", "man");
        mInsensitive.put("Vlad", "man");
        System.out.println(mInsensitive);

        LinkedCaseInsensitiveMap<Object> linkedCaseInsensitiveMap = new LinkedCaseInsensitiveMap<>();
        linkedCaseInsensitiveMap.put("flore", "man");
        linkedCaseInsensitiveMap.put("Flore", "man");
        System.out.println(linkedCaseInsensitiveMap);

        Map<String, String> mutableMap = new HashMap<>();
        mutableMap.put("key", "value");
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(mutableMap);
        try {
            unmodifiableMap.put("key1", "value1");
        } catch (Exception e) {
            System.out.println(e);
        }
        mutableMap.put("key1", "value1");
        System.out.println(unmodifiableMap.get("key1"));

    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.printf("Key=%s, Value=%s\n", entry.getKey(), entry.getValue());
        }
    }

    @AllArgsConstructor
    @ToString
    static class MyKey {
        private int key;
        private String name;

        @Override
        public int hashCode() {
            System.out.println("Calling hashCode() for key: " + this);
            return key;
        }

        @Override
        public boolean equals(Object obj) {
            System.out.print("Calling equals() for key: " + this + " against other: " + obj);
            boolean result = true;
            if (this == obj)
                result = true;
            if (obj == null)
                result = false;
            if (getClass() != obj.getClass())
                result = false;
            MyKey other = (MyKey) obj;
            if (key != other.key)
                result = false;
            if (name == null) {
                if (other.name != null)
                    result = false;
            } else if (!name.equals(other.name))
                result = false;

            System.out.println(" => " + result);
            return result;
        }

    }
}
