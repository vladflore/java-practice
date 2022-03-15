package tech.vladflore.practice.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Practice {
    public static void main(String[] args) {

        LinkedList<Integer> integers = new LinkedList<>();

        integers.add(1);
        integers.addLast(2);
        integers.addFirst(3);
        integers.offer(4);
        integers.offerFirst(5);
        integers.offerLast(6);
        integers.push(7);

        // 7531246
        integers.forEach(System.out::print);

        System.out.println();

        ArrayList<Integer> integers1 = new ArrayList<>(IntStream.range(0, 10).boxed().collect(Collectors.toSet()));
        ArrayList<Integer> integers2 = IntStream.range(0, 10).boxed().collect(Collectors.toCollection(ArrayList::new));

        List<Long> list = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        LongStream.range(4, 10)
                .boxed()
                .collect(Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), l -> list.addAll(0, l)));
        System.out.println(list);

        List<Integer> integers3 = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        ListIterator<Integer> iterator = integers3.listIterator(integers3.size());
        List<Integer> result = new ArrayList<>(integers3.size());
        while (iterator.hasPrevious()) {
            result.add(iterator.previous());
        }
        Collections.reverse(integers3);
        integers3.forEach(System.out::print);
        System.out.println();
        result.forEach(System.out::print);
        System.out.println();

        ArrayList<String> strings = LongStream.range(0, 16).boxed().map(Long::toHexString)
                .collect(Collectors.toCollection(ArrayList::new));
        List<String> strings1 = new ArrayList<>(strings);
        strings1.addAll(strings);
        HashSet<String> matching = new HashSet<>(Arrays.asList("a", "c", "9"));
        List<String> result1 = strings.stream().filter(matching::contains).collect(Collectors.toList());
        System.out.println(result1);

        List<Integer> integers4 = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Collections.shuffle(integers4);
        Collections.sort(integers4);
        System.out.println(Collections.binarySearch(integers4, 11));

        integers4.remove(0);
        integers4.remove(Integer.valueOf(5));
        System.out.println(integers4);

        List<Integer> integers5 = List.of(Arrays.asList(1, 2, 3, 4, 5, 6, 7).toArray(new Integer[7]));
        System.out.println(integers5);
        // integers5.add(6);

        CopyOnWriteArrayList<Integer> integers6 = new CopyOnWriteArrayList<>(new Integer[] { 1, 2, 3 });
        Iterator<Integer> iterator1 = integers6.iterator();
        integers6.add(4);
        iterator1.forEachRemaining(System.out::print);
        System.out.println();
        iterator1 = integers6.iterator();
        iterator1.forEachRemaining(System.out::print);
        System.out.println();

        Iterator<Integer> iterator2 = Arrays.asList(1, 2, 3).iterator();
        Iterable<Integer> iterable = () -> iterator2;
        List<Integer> integers7 = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
        System.out.println(integers7.get(new Random().nextInt(integers7.size())));

        List<Person> persons = new ArrayList<>();
        persons.add(new Person("Flore"));

        List<Person> copyOfPersons = Collections.unmodifiableList(persons);

        // changing the initial list content, changes the content of the copy too
        persons.get(0).setName("Flore Vlad");

        System.out.println(copyOfPersons);

        // the content object can still be changed, because it is not immutable
        copyOfPersons.get(0).setName("Still happening");

        System.out.println(copyOfPersons);

        List<String> source = List.of("a");
        List<String> destination = Arrays.asList("b", "c");
        Collections.copy(destination, source);
        System.out.println(destination);

        List<Person> newList = Optional.ofNullable(persons).map(List::stream).orElseGet(Stream::empty)
                .collect(Collectors.toList());
        System.out.println(newList);

        List<Integer> deleteFrom = new ArrayList<>(Arrays.asList(1, 1, 1, 4, 1, 6, 1, 8, 1));
        // deleteAll(deleteFrom, 1);
        // deleteAllImproved(deleteFrom, 1);

        // while(deleteFrom.remove(Integer.valueOf(1)));

        // removeAll1(deleteFrom, 1);
        // removeAll2(deleteFrom, 1);

        // deleteFrom = deleteFrom.stream().filter(el -> el !=
        // 1).collect(Collectors.toList());

        // deleteFrom.removeIf(el -> Objects.equals(el, 1));
        deleteFrom.removeIf(el -> el == 1);

        System.out.println(deleteFrom);

        Collections.addAll(deleteFrom, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println(deleteFrom);

        List<Integer> src = new ArrayList<>(Arrays.asList(-1, -2, -3));
        List<Integer> dst = new ArrayList<>();

        // src.stream().forEachOrdered(dst::add);

        Optional.ofNullable(src).ifPresent(dst::addAll);

        System.out.println(dst);

        dst.forEach(System.out::println);
        dst.stream().forEach(System.out::println);

        List<String> list2 = Arrays.asList("red", "blue", "blue", "green", "red");
        List<String> otherList = Arrays.asList("red", "green", "green", "yellow");

        list2.stream().distinct().filter(otherList::contains).forEach(System.out::println);

        Map<String, Integer> frequencies = new HashMap<>();
        // count how many time each element in list2 appears
        // list2.forEach(el -> frequencies.put(el, frequencies.getOrDefault(el, 0)+1));

        // BiFunction<String, Integer, Integer> mappingFunction = (k, v) -> v == null ?
        // 1 : v + 1;
        // list2.forEach(el -> frequencies.compute(el, mappingFunction));

        list2.forEach(el -> frequencies.merge(el, 1, (k, v) -> v + 1));
        System.out.println(frequencies);

        Map<String, Integer> frequencies2 = list2.stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum));
        System.out.println(frequencies2);

        Map<String, Long> frequencies3 = list2.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(frequencies3);

        List<String> listOne = Arrays.asList("Jack", "Tom", "Sam", "John", "James", "Jack");
        List<String> listTwo = Arrays.asList("Jack", "Daniel", "Sam", "Alan", "James", "George");

        List<String> diff = new ArrayList<>(listOne);
        diff.removeAll(listTwo); // listOne - listTwo
        System.out.println(diff);

        // listOne - listTwo
        listOne.stream().filter(el -> !listTwo.contains(el)).collect(Collectors.toList()).forEach(System.out::println);

        // listTwo - listOne
        listTwo.stream().filter(el -> !listOne.contains(el)).collect(Collectors.toList()).forEach(System.out::println);
    }

    static void removeAll1(List<Integer> list, int element) {
        for (int i = 0; i < list.size();) {
            if (Objects.equals(element, list.get(i))) {
                list.remove(i);
            } else {
                i++;
            }
        }
    }

    static void removeAll2(List<Integer> list, int element) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == element) {
                list.remove(i);
                i--;
            }
        }
    }

    static void deleteAll(List<Integer> list, Integer element) {
        while (list.contains(element)) {
            list.remove(element);
        }
    }

    static void deleteAllImproved(List<Integer> list, Integer element) {
        int index;
        while ((index = list.indexOf(element)) >= 0) {
            list.remove(index);
        }
    }

}

class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
