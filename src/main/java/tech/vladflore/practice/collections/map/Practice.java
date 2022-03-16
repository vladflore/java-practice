package tech.vladflore.practice.collections.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.printf("Key=%s, Value=%s\n", entry.getKey(), entry.getValue());
        }
    }

}
