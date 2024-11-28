package com.goldencargo.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GenericService {

    private final EntityManager entityManager;

    public GenericService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> List<T> getFilteredAndSortedEntities(
            Class<T> entityType,
            String entityAlias,
            String filterType,
            String filterValue,
            String comparisonType,
            String sortBy,
            String sortLogic) {

        String queryString = buildQuery(entityType, entityAlias, filterType, filterValue, comparisonType, sortBy, sortLogic);
        Map<String, Object> params = buildQueryParameters(filterValue);

        Query query = entityManager.createQuery(queryString, entityType);
        params.forEach(query::setParameter);

        return query.getResultList();
    }

    private <T> String buildQuery(
            Class<T> entityType,
            String entityAlias,
            String filterType,
            String filterValue,
            String comparisonType,
            String sortBy,
            String sortLogic) {

        StringBuilder queryBuilder = new StringBuilder("SELECT ")
                .append(entityAlias)
                .append(" FROM ")
                .append(entityType.getSimpleName())
                .append(" ")
                .append(entityAlias)
                .append(" WHERE 1=1")
                .append(" AND ")
                .append(entityAlias)
                .append(".isDeleted = false");

        if (filterType != null && filterValue != null && !filterValue.isEmpty()) {
            queryBuilder.append(buildFilterCondition(entityAlias, filterType, comparisonType));
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            queryBuilder.append(buildSortCondition(entityAlias, sortBy, sortLogic));
        }

        return queryBuilder.toString();
    }

    private String buildFilterCondition(String entityAlias, String filterType, String comparisonType) {
        StringBuilder filterCondition = new StringBuilder(" AND ")
                .append(entityAlias)
                .append(".")
                .append(filterType);

        switch (comparisonType) {
            case "=":
                filterCondition.append(" = :filterValue");
                break;
            case "!=":
                filterCondition.append(" != :filterValue");
                break;
            case ">":
                filterCondition.append(" > :filterValue");
                break;
            case ">=":
                filterCondition.append(" >= :filterValue");
                break;
            case "<":
                filterCondition.append(" < :filterValue");
                break;
            case "<=":
                filterCondition.append(" <= :filterValue");
                break;
            case "like":
                filterCondition.append(" LIKE :filterValue");
                break;
            default:
                throw new IllegalArgumentException("Invalid comparison type: " + comparisonType);
        }
        return filterCondition.toString();
    }

    private String buildSortCondition(String entityAlias, String sortBy, String sortLogic) {
        return " ORDER BY " + entityAlias + "." + sortBy + " " + (sortLogic != null ? sortLogic : "asc");
    }

    private Map<String, Object> buildQueryParameters(String filterValue) {
        Map<String, Object> params = new HashMap<>();
        if (filterValue != null && !filterValue.isEmpty()) {
            params.put("filterValue", filterValue.contains("%") ? filterValue : "%" + filterValue + "%");
        }
        return params;
    }
}
