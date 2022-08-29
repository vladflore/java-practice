package tech.vladflore.practice.collections.ops;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
        Integer[] myarray = new Integer[]{3, 1, 2, 6, 7, 9, 0, 8, 4, 5};
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

        // do not use, it is considered an anti-pattern
        Set<String> unmodifiableSet = Collections.unmodifiableSet(new HashSet<String>() {
            {
                add("one");
                add("two");
                add("one");
            }
        });

        Set<String> unmodifiableSet1 = Stream.of("one", "two", "three").collect(
            Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));

        int[] arr = {1, 2, 3, 4, 5};
        List<int[]> lst = List.of(arr);
        List<Integer> lst1 = List.of(1, 2, 3, 4, 5);

        Map<String, String> m1 = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("foo", "a"),
            new AbstractMap.SimpleEntry<>("bar", "b"));

        // The instances created by factory methods are value-based. This means that
        // factories are free to create a new instance or return an existing instance.
        System.out.println(List.of(1, 2, 3) == List.of(1, 2, 3));

        var seven = List.of(1, 2, 3, 4, 5, 6, 7);
        ListIterator<Integer> it = seven.listIterator();
        it.forEachRemaining(System.out::print);
        System.out.println();
        it = seven.listIterator(seven.size());
        while (it.hasPrevious()) {
            System.out.println("index=%d, value=%d".formatted(it.previousIndex(), it.previous()));
        }

        StreamSupport.stream(seven.spliterator(), false).forEach(System.out::println);

        var words = List.of("cat", "dog", "car", "driver");
        final var partitioned = words.stream().collect(Collectors.partitioningBy(word -> word.startsWith("d")));
        System.out.println(partitioned.get(true));
        System.out.println(partitioned.get(false));

    }

    static String shuffle(String original) {
        List<String> letters = Arrays.asList(original.split(""));
        Collections.shuffle(letters);
        return letters.stream().collect(Collectors.joining());
    }
}

record Employee(String name, Integer age) {

}