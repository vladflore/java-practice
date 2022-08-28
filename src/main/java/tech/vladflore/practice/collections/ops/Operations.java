package tech.vladflore.practice.collections.ops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Operations {
        public static void main(String[] args) {
                List<Integer> digits = IntStream.rangeClosed(0, 9).boxed().toList();
                digits.forEach(System.out::print);
                System.out.println();
                digits.stream().forEach(System.out::print);
                System.out.println();
                digits.parallelStream().forEach(System.out::print);
                System.out.println();

                // sorting an array
                Integer[] myarray = new Integer[] { 3, 1, 2, 6, 7, 9, 0, 8, 4, 5 };
                Arrays.sort(myarray);
                System.out.println(Arrays.toString(myarray));

                // shuffling an array via a list
                List<Integer> list = Arrays.stream(myarray).collect(Collectors.toList());
                Collections.shuffle(list);
                list.toArray(myarray);
                System.out.println(Arrays.toString(myarray));

                // sorting a set
                LinkedHashSet<Integer> lhs = new LinkedHashSet<>(Arrays.asList(3, 1, 2, 6, 7, 9, 0, 8, 4, 5));
                var tmp = new ArrayList<>(lhs);
                Collections.sort(tmp, Collections.reverseOrder());
                lhs = new LinkedHashSet<>(tmp);
                System.out.println(lhs);

                // sorting a map by keys/values
                Map<Integer, String> map = Map.of(1, "one", 3, "three", 2, "two", 0, "zero");
                ArrayList<Entry<Integer, String>> entries = new ArrayList<>(map.entrySet());
                Collections.sort(entries, Map.Entry.comparingByKey(Collections.reverseOrder()));
                map = entries.stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue(),
                                (existing, replacement) -> existing, LinkedHashMap::new));
                System.out.println(map);

                // sort custom objects
                var random = new Random();
                List<Employee> employees = random.ints(30, 33).limit(5).boxed()
                                .map(age -> new Employee(Operations.shuffle("abc"), age))
                                .collect(Collectors.toList());
                Collections.sort(employees, Comparator.comparing(Employee::age).thenComparing(Employee::name));
                System.out.println(employees);

                List<List<Integer>> nested = List.of(
                                List.of(1, 2),
                                List.of(3, 4));

                List<Integer> flattened = nested.stream().flatMap(Collection::stream).toList();
                System.out.println(flattened);

                List<String> names = List.of("vlad", "ioana");
                List<Integer> ages = List.of(36, 33);
                List<Employee> zipped = IntStream.range(0, Math.min(names.size(), ages.size()))
                                .mapToObj(idx -> new Employee(names.get(idx), ages.get(idx)))
                                .toList();
                System.out.println(zipped);

        }

        static String shuffle(String original) {
                List<String> letters = Arrays.asList(original.split(""));
                Collections.shuffle(letters);
                return letters.stream().collect(Collectors.joining());
        }
}

record Employee(String name, Integer age) {
}