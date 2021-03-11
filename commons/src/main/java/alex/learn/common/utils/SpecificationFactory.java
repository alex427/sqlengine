package alex.learn.common.utils;

import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Date;

/**
 * author  : zhiguang
 * date    : 2018/7/4
 * 条件工厂
 * 支持JPA条件查询
 */
public final class SpecificationFactory {

    //模糊查询
    public static Specification cotainsLike(String attribute, String value) {
        return ((root, query, cb) -> cb.like(root.get(attribute), "%" + value + "%"));
    }


    //精确查询
    public static Specification equal(String attribute, Object value) {
        return ((root, query, cb) -> cb.equal(root.get(attribute), value));
    }


    //区间查询
    public static Specification equal(String attribute, int min, int max) {
        return ((root, query, cb) -> cb.between(root.get(attribute), min, max));
    }

    public static Specification equal(String attribute, Double min, Double max) {
        return ((root, query, cb) -> cb.between(root.get(attribute), min, max));
    }

    public static Specification equal(String attribute, Date min, Date max) {
        return ((root, query, cb) -> cb.between(root.get(attribute), min, max));
    }


    //in 查询
    public static Specification in(String attribute, Collection collection) {
        return ((root, query, cb) -> root.get(attribute).in(collection));
    }


    //大于
    public static Specification greaterThan(String attribute, int value) {
        return ((root, query, cb) -> cb.greaterThan(root.get(attribute), value));
    }

    public static Specification greaterThan(String attribute, long value) {
        return ((root, query, cb) -> cb.greaterThan(root.get(attribute), value));
    }

    public static Specification greaterThan(String attribute, Date value) {
        return ((root, query, cb) -> cb.greaterThan(root.get(attribute), value));
    }


    //小于
    public static Specification lessThan(String attribute, int value) {
        return ((root, query, cb) -> cb.lessThan(root.get(attribute), value));
    }

    public static Specification lessThan(String attribute, long value) {
        return ((root, query, cb) -> cb.greaterThan(root.get(attribute), value));
    }

    public static Specification lessThan(String attribute, Date value) {
        return ((root, query, cb) -> cb.greaterThan(root.get(attribute), value));
    }
}