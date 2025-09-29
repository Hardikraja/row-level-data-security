package com.data.security.demo.services.impl;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.services.impl
 *  👤 Author: hardik
 *  📅 Date: Tuesday, 23 September 2025
 *  ⏰ Time: 15:17
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import com.blazebit.persistence.PagedList;
import com.data.security.demo.entities.Event;
import com.data.security.demo.filter.EventFilter;
import com.data.security.demo.filter.querybuilder.EventQueryBuilder;
import com.data.security.demo.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventQueryBuilder eventQueryBuilder;

    @Override
    public List<Event> searchEvents(EventFilter eventFilter) {
        return eventQueryBuilder.searchRecords(eventFilter,null);
    }

    @Override
    public Page<Event> searchPaginatedEvents(EventFilter eventFilter, Pageable pageable) {
        return eventQueryBuilder.searchPaginatedRecords(eventFilter,pageable);
    }


}
