package tech.vladflore.practice.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Practice {

        public static void main(String[] args) {

                Stream<String> streamEmpty = Stream.empty();
                Stream<String> streamOfArray = Stream.of("a", "b", "c");
                Stream<String> streamBuilder = Stream.<String>builder().add("a").add("b").add("c").build();
                Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
                Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);

                DoubleStream doubles = new Random().doubles(5);
                doubles.forEach(System.out::println);

                Stream<Character> charsStream = "vlad".chars()
                                .mapToObj(c -> (char) c);
                charsStream.forEach(System.out::println);

                Pattern.compile(", ").splitAsStream("a, b, c").forEach(System.out::println);

                List<String> list = Arrays.asList("abc1", "abc2", "abc3", "abc2", "abc3", "abc1");
                list.stream()
                                .skip(1)
                                .map(element -> element.substring(element.length() - 1))
                                .map(Integer::parseInt)
                                .distinct()
                                .sorted(Collections.reverseOrder())
                                .forEach(System.out::println);

                final var reduce = IntStream.rangeClosed(1, 5)
                                .reduce(0, Integer::sum);
                System.out.println(reduce);

                List<Product> productList = Arrays.asList(new Product(23, "potatoes"),
                                new Product(14, "orange"), new Product(13, "lemon"),
                                new Product(23, "bread"), new Product(13, "sugar"));

                System.out.println("****************************************************************************");
                // get the most expensive products, i.e. 23 -> potatoes, bread
                String mostExpensiveProducts = productList.stream().collect(Collectors.groupingBy(Product::price))
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                                .findFirst()
                                .map(entry -> entry.getValue())
                                .map(products -> products.stream().map(Product::name).collect(Collectors.joining(", ")))
                                .orElse("n/a");
                System.out.println(mostExpensiveProducts);
                System.out.println("****************************************************************************");

                Map<Integer, List<Product>> groupedByPriceList = productList.stream()
                                .collect(Collectors.groupingBy(Product::price));

                Map<Integer, Set<Product>> groupedByPriceSet = productList.stream()
                                .collect(Collectors.groupingBy(Product::price, Collectors.toSet()));

                System.out.println("---------------------------------------------------------------------------");
                Map<Integer, List<Integer>> collect = productList.stream().collect(Collectors.groupingBy(Product::price,
                                Collectors.mapping(p -> p.name().length(), Collectors.toList())));
                System.out.println(collect);
                System.out.println("---------------------------------------------------------------------------");

                System.out.println(productList.stream().map(Product::name)
                                .collect(Collectors.joining(", ", "[", "]")));

                System.out.println(productList.stream().collect(Collectors.averagingInt(Product::price)));
                // System.out.println(productList.stream().collect(Collectors.summingInt(Product::price)));
                System.out.println(productList.stream().mapToInt(Product::price).sum());

                Stream<Product> streamOfCollection = productList.parallelStream();
                boolean isParallel = streamOfCollection.isParallel();
                boolean bigPrice = streamOfCollection
                                .map(product -> product.price() * 12)
                                .anyMatch(price -> price > 200);
                System.out.println(isParallel);
                System.out.println(bigPrice);

                IntStream intStreamParallel = IntStream.range(1, 150).parallel();
                isParallel = intStreamParallel.isParallel();
                System.out.println(isParallel);

                IntStream intStreamSequential = intStreamParallel.sequential();
                isParallel = intStreamSequential.isParallel();
                System.out.println(isParallel);

                var result = -1;
                do {
                        result = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
                                        .stream().parallel()
                                        .filter(num -> num < 9).findAny().get();
                } while (result == 7);
                System.out.println(result);

                List<Integer> numbers = Arrays.asList(42, 4, 2, 24, 1, 123, 777);
                final var minMax = numbers.stream().collect(
                                Collectors.teeing(
                                                Collectors.minBy(Integer::compareTo),
                                                Collectors.maxBy(Integer::compareTo),
                                                (val1, val2) -> "min=%d, max=%d".formatted(val1.get(), val2.get())));
                System.out.println(minMax);

                // two new collectors added in Java 9: Collectors.filtering and
                // Collectors.flatMapping
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
                System.out.println(numbers.stream()
                                .collect(Collectors.groupingBy(
                                                Function.identity(),
                                                Collectors.filtering(val -> val > 10, Collectors.counting()))));
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

                List<Book> bookList = new ArrayList<>();
                bookList.add(new Book("The Fellowship of the Ring", 1954, "0395489318"));
                bookList.add(new Book("The Two Towers", 1954, "0345339711"));
                bookList.add(new Book("The Return of the King", 1955, "0618129111"));

                System.out.println(bookList.stream().collect(
                                Collectors.toMap(
                                                Book::releaseYear, Function.identity(),
                                                (existing, replacement) -> existing)));

                // group books by year, for each book take only the name
                final var res = bookList.stream().collect(
                                Collectors.groupingBy(
                                                Book::releaseYear,
                                                Collectors.mapping(Book::name, Collectors.toList())));
                System.out.println(res);

                final var booksAsTreeMap = bookList.stream().collect(
                                Collectors.toMap(Book::name, Function.identity(), (book, book2) -> book, TreeMap::new));
                System.out.println(booksAsTreeMap);

                Stream.of("cat", "dog", "elephant", "fox", "rabbit", "duck")
                                .takeWhile(s -> s.length() % 2 != 0)
                                .forEach(System.out::println);

                List<Optional<String>> listOfOptionals = Arrays.asList(
                                Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));

                List<String> collect2 = listOfOptionals.stream()
                                .flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
                                .collect(Collectors.toList());
                System.out.println(collect2);

                System.out.println(listOfOptionals.stream().flatMap(
                                o -> o.map(Stream::of).orElseGet(Stream::empty)).collect(Collectors.toList()));

                System.out.println(listOfOptionals.stream().flatMap(
                                Optional::stream).toList());

                Stream<Integer> stream1 = Stream.of(1, 3, 5);
                Stream<Integer> stream2 = Stream.of(2, 4, 6);
                Stream<Integer> stream3 = Stream.of(18, 15, 36);
                Stream<Integer> stream4 = Stream.of(99);
                Stream.of(stream1, stream2, stream3, stream4).flatMap(Function.identity()).toList()
                                .forEach(System.out::println);

                List<String> valueList = new ArrayList<>();
                valueList.add("Joe");
                valueList.add("John");
                valueList.add("Sean");
                System.out.println(valueList.stream().reduce((s1, s2) -> s2).orElse(null));

                final var animalStream = Stream.of(new Animal("cat"), new Animal("dog"), new Animal("cat"),
                                new Animal("dog"), new Animal("rabbit"),
                                new Animal("cat"));
                System.out.println(animalStream.map(Animal::type).collect(Collectors.toSet()));

                List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
                int computedAges = users.stream()
                                .reduce(0, (partialAgeResult, user) -> partialAgeResult + user.age(), Integer::sum);

        }
}

record Product(int price, String name) {

}

record Book(String name, int releaseYear, String isbn) {

}

record Animal(String type) {

}

record User(String name, int age) {

}