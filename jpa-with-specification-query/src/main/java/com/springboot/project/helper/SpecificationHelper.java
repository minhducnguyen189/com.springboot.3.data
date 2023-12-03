package com.springboot.project.helper;

import com.springboot.project.entity.CustomerEntity;
import com.springboot.project.entity.LoyaltyCardEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@UtilityClass
public class SpecificationHelper {

    public <T> Specification<T> queryDateBetweenSpecification(String checkFieldName, Date dateFrom, Date dateTo) {
        return (root,query,builder) -> {
            if (StringUtils.isEmpty(checkFieldName)) {
                return builder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(dateFrom)) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(checkFieldName), dateFrom));
            }
            if (Objects.nonNull(dateTo)) {
                predicates.add(builder.lessThanOrEqualTo(root.get(checkFieldName), dateTo));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public <T> Specification<T> queryJoinTableNumberEqualSpecification(String joinTable, String field, Integer value) {
        return (root,query,builder) -> {
            if (Objects.isNull(joinTable) || Objects.isNull(field) || Objects.isNull(value)) {
                return builder.conjunction();
            }
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(builder.equal(root.get(joinTable).get(field), value));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static <T> Specification<T> initSpecificationWithExample(Example<T> example) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // If example is null or has all null attributes, return all items
            if (Objects.isNull(example) || allAttributesNull(example.getProbe())) {
                return builder.conjunction();
            }
            // Add predicates based on the example
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

//    public static <T, S> Specification<T> joinTableSpecification(String joinTableName, String joinTablePkField) {
//        return (root, query, builder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (Objects.isNull(joinTableName) || Objects.isNull(joinTablePkField)) {
//                return builder.conjunction();
//            }
//            root.join(joinTableName, JoinType.INNER);
//            return builder.conjunction();
//        };
//    }

    private static <T> boolean allAttributesNull(T probe) {
        // Check if all attributes in the probe object are null using reflection
        for (Field field : probe.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(probe) != null) {
                    return false;
                }
            } catch (IllegalAccessException e) {
                return true;
            }
        }
        return true;
    }

}
