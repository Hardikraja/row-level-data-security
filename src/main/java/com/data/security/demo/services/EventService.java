package com.data.security.demo.services;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.services
 *  👤 Author: hardik
 *  📅 Date: Tuesday, 23 September 2025
 *  ⏰ Time: 13:18
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import com.data.security.demo.entities.Event;
import com.data.security.demo.filter.EventFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService {
    List<Event> searchEvents(EventFilter eventFilter);

    Page<Event> searchPaginatedEvents(EventFilter eventFilter, Pageable pageable);

}
