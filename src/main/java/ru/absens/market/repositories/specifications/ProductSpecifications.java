package ru.absens.market.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.absens.market.models.Category;
import ru.absens.market.models.Product;

import javax.persistence.criteria.Path;
import java.util.ArrayList;
import java.util.List;


public class ProductSpecifications {
    private static Specification<Product> priceGreaterOrEqualsThan(int minPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> priceLesserOrEqualsThan(int maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Product> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    private static Specification<Product> categoryIdEqual(List<String> categoriesId) {
        List<Integer> intCategoriesId = new ArrayList<>();
        for(String s : categoriesId) intCategoriesId.add(Integer.valueOf(s));
        return (root, query, builder) -> {
            final Path<Category> category = root.get("category");
            return category.in(intCategoriesId);
        };
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if (params.containsKey("min_price") && !params.getFirst("min_price").isBlank()) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(Integer.parseInt(params.getFirst("min_price"))));
        }
        if (params.containsKey("max_price") && !params.getFirst("max_price").isBlank()) {
            spec = spec.and(ProductSpecifications.priceLesserOrEqualsThan(Integer.parseInt(params.getFirst("max_price"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(ProductSpecifications.titleLike(params.getFirst("title")));
        }
        if (params.containsKey("categoriesId") && !params.get("categoriesId").isEmpty()) {
            spec = spec.and(ProductSpecifications.categoryIdEqual(params.get("categoriesId")));
        }
        return spec;
    }
}
