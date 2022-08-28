package tech.vladflore.practice.collections.convert;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Convert {
    public static void main(String[] args) {
        List<Integer> integers5 = List.of(Arrays.asList(1, 2, 3, 4, 5, 6, 7).toArray(new Integer[7]));
        System.out.println(integers5);

        HashSet<Integer> hashSet = new HashSet<>(Arrays.asList(1, 2, 3));
        Collections.addAll(hashSet, 1, 2, 3, 4);
        System.out.println(hashSet);

        Set<Integer> set = Set.copyOf(List.of(9, 8, 7));
        System.out.println(set);

        List<Animal> animals = IntStream.rangeClosed(1, 5).boxed().map(id -> new Animal(id, "name" + id)).toList();
        Map<Integer, Animal> animalsMap = animals.stream().collect(Collectors.toMap(Animal::id, Function.identity()));
        System.out.println(animalsMap);

        // convert map to string
        String str = animalsMap.keySet()
                        .stream()
                        .map(key -> key + "=" + animalsMap.get(key))
                        .collect(Collectors.joining(", ", "{", "}"));
        System.out.println(str);

        // List<String> countries = Arrays.stream(Locale.getISOCountries()).toList();
        List<String> countries = Arrays.stream(Locale.getISOCountries()).collect(Collectors.toUnmodifiableList()); //.toList()
        System.out.println(countries);

    }
}

record Animal(int id, String name) {
}