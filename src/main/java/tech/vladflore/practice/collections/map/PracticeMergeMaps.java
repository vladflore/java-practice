package tech.vladflore.practice.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PracticeMergeMaps {
    public static void main(String[] args) {

        Map<String, Employee> map1 = Map.of(
                "Name1", new Employee(1L, "Name1"),
                "Name2", new Employee(2L, "Name2"),
                "Name3", new Employee(3L, "Name3"));
        Map<String, Employee> map2 = Map.of(
                "Name4", new Employee(4L, "Name4"),
                "Name5", new Employee(5L, "Name5"),
                "Name3", new Employee(6L, "Name3 Correct"));
        Map<String, Employee> result = new HashMap<>(map1);

        // merge map2 into result
        map2.forEach(
                (k, v) -> result.merge(k, v, (v1, v2) -> new Employee(v1.getId(), v2.getName())));
        System.out.println(result);

        Map<String, Employee> result1 = Stream.concat(map1.entrySet().stream(), map2.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (v1, v2) -> new Employee(v1.getId(), v2.getName())));
        // without a merge function it will throw IllegalStateException when duplicated
        // keys are found
        System.out.println(result1);

        Map<String, Employee> result2 = Stream.of(map1, map2).flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (v1, v2) -> new Employee(v1.getId(), v2.getName())));
        System.out.println(result2);
    }
}
