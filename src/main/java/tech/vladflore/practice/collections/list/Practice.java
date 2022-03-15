package tech.vladflore.practice.collections.list;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
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

        ArrayList<String> strings = LongStream.range(0, 16).boxed().map(Long::toHexString).collect(Collectors.toCollection(ArrayList::new));
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
//        integers5.add(6);

        CopyOnWriteArrayList<Integer> integers6 = new CopyOnWriteArrayList<>(new Integer[]{1, 2, 3});
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

    }
}
