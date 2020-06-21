package com.example.bookingapi.model.hospede;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class HospedeSpecs {
    public static Specification<HospedeEntity> getEmployeesByNameSpec(String name) {
        return new Specification<HospedeEntity>() {
            @Override
            public Predicate toPredicate(Root<HospedeEntity> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get("name"), name);
                return equalPredicate;
            }
        };
    }

    public static Specification<HospedeEntity> getEmployeesByDocumentSpec(String document) {
        return new Specification<HospedeEntity>() {
            @Override
            public Predicate toPredicate(Root<HospedeEntity> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get("document"), document);
                return equalPredicate;
            }
        };
    }

    public static Specification<HospedeEntity> getEmployeesByPhoneSpec(String phone) {
        return new Specification<HospedeEntity>() {
            @Override
            public Predicate toPredicate(Root<HospedeEntity> root,
                                         CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate equalPredicate = criteriaBuilder.equal(root.get("phone"), phone);
                return equalPredicate;
            }
        };
    }

}