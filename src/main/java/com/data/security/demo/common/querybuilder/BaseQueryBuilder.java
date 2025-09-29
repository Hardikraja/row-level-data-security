package com.data.security.demo.common.querybuilder;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.common.querybuilder
 *  👤 Author: hardik
 *  📅 Date: Friday, 26 September 2025
 *  ⏰ Time: 14:03
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BaseQueryBuilder<T, F> {
    List<T> searchRecords(F filter, Sort sort);
    Page<T> searchPaginatedRecords(F filter, Pageable pageable);
}
