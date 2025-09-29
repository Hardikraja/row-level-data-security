package com.data.security.demo.common.querybuilder.abs;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.common.querybuilder.abs
 *  👤 Author: hardik
 *  📅 Date: Friday, 26 September 2025
 *  ⏰ Time: 14:05
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.PagedList;
import com.data.security.demo.common.querybuilder.BaseQueryBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;

import java.util.List;

public abstract class AbstractFilterableQueryBuilder<T, F> implements BaseQueryBuilder<T, F> {

    protected final EntityManager entityManager;
    protected final CriteriaBuilderFactory cbf;

    public AbstractFilterableQueryBuilder(EntityManager entityManager, CriteriaBuilderFactory cbf) {
        this.entityManager = entityManager;
        this.cbf = cbf;
    }

    @Override
    public List<T> searchRecords(F filter, Sort sort) {
        CriteriaBuilder<T> cb = buildSafeCriteria(filter);
        applySorting(cb, sort);
        return cb.getResultList();
    }

    @Override
    public Page<T> searchPaginatedRecords(F filter, Pageable pageable) {
        CriteriaBuilder<T> cb = buildSafeCriteria(filter);

        boolean orderApplied = false;
        if (pageable != null && pageable.getSort() != null && !pageable.getSort().isEmpty()) {
            applySorting(cb, pageable.getSort());
            orderApplied = true;
        }

        if (!orderApplied) cb.orderByAsc(getDefaultOrderProperty());

        int pageNumber = pageable != null ? pageable.getPageNumber() : 0;
        int pageSize = pageable != null ? pageable.getPageSize() : 20;

        PagedList<T> resultList = cb.page(pageNumber, pageSize)
                .withInlineCountQuery(false)
                .getResultList();

        return new PageImpl<>(resultList, pageable != null ? pageable : PageRequest.of(pageNumber, pageSize),
                resultList.getTotalSize());
    }

    /**
     * Builds criteria and ensures **all subqueries/predicates are properly ended**
     */
    private CriteriaBuilder<T> buildSafeCriteria(F filter) {
        CriteriaBuilder<T> cb = cbf.create(entityManager, getEntityClass())
                .from(getEntityClass(), getEntityAlias());

        // Apply optional hooks
        applyAuthorization(cb); // MUST fully end any subquery inside
        applyFilters(cb, filter); // MUST fully end any subquery inside

        return cb;
    }

    /**
     * Default order property
     */
    protected String getDefaultOrderProperty() {
        return getEntityAlias() + ".id";
    }

    /**
     * Sorting — safe to call because criteria builder is fully built
     */
    protected void applySorting(CriteriaBuilder<T> cb, Sort sort) {
        // builder must be fully ended before calling this
        if (sort == null || sort.isEmpty()) {
            cb.orderByAsc(getDefaultOrderProperty());
            return;
        }

        sort.forEach(order -> {
            String property = getEntityAlias() + "." + order.getProperty();
            if (order.isAscending()) cb.orderByAsc(property);
            else cb.orderByDesc(property);
        });
    }


    /**
     * Subclasses provide entity class & alias
     */
    protected abstract Class<T> getEntityClass();
    protected abstract String getEntityAlias();

    /**
     * Subclasses implement any filters/authorization.
     * ⚠ Ensure that all subqueries or predicate chains are ended before returning.
     */
    protected void applyFilters(CriteriaBuilder<T> cb, F filter) { }
    protected void applyAuthorization(CriteriaBuilder<T> cb) { }
}

