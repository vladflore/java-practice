package tech.vladflore.practice.collections.map;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode.CacheStrategy;

@Getter
@EqualsAndHashCode(cacheStrategy = CacheStrategy.LAZY)
public class Product {

    private static final String NOT_AVAILABLE = "n/a";

    private UUID id;

    @EqualsAndHashCode.Exclude
    @Setter
    private String name;

    @EqualsAndHashCode.Exclude
    @Setter
    private String description;

    @Setter
    @EqualsAndHashCode.Exclude
    private List<String> tags;

    public Product(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        tags = new ArrayList<>();
    }

    public Product(UUID id) {
        this.id = id;
        name = NOT_AVAILABLE;
        description = NOT_AVAILABLE;
        tags = new ArrayList<>();
    }

    public Product addTagsOfOtherProduct(Product product) {
        tags.addAll(product.getTags());
        return this;
    }

    public Product addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public String toString() {
        return String.format("%s - %s - %s", name, description, tags);
    }

}
