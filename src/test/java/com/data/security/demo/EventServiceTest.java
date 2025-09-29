package com.data.security.demo;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo
 *  👤 Author: hardik
 *  📅 Date: Wednesday, 24 September 2025
 *  ⏰ Time: 12:00
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import com.data.security.demo.entities.Event;
import com.data.security.demo.enums.AppRoles;
import com.data.security.demo.filter.EventFilter;
import com.data.security.demo.services.EventService;
import com.data.security.demo.utils.CurrentLoggedInUsersContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
class EventServiceTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private CurrentLoggedInUsersContext currentLoggedInUsersContext;


    /**
     * Logged In As :- Super Admin
     * As per Super Admin Role :- Get all the data of events
     */
    @Test
    void testEventServiceRecordsForSuperAdmin(){
        currentLoggedInUsersContext.setContext(1L,AppRoles.SUPER_ADMIN.getRoleId());
        EventFilter eventFilter = new EventFilter();
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(4 ,events.size());
    }

    /**
     * Logged In As :- Attendee
     * As per Attendee role: Return all data belonging to the associated tenant.
     */
    @Test
    void testEventServiceRecordsForAttendee(){
        currentLoggedInUsersContext.setContext(6L,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(2,events.size());
    }

    /**
     * Returns :- Paginated Data
     * Logged In As :- Super Admin
     * As per Super Admin Role :- Get all the data of events
     */
    @Test
    void testEventServiceRecordsForSuperAdminPaginated(){
        currentLoggedInUsersContext.setContext(1L,AppRoles.SUPER_ADMIN.getRoleId());
        EventFilter eventFilter = new EventFilter();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Event> events = this.eventService.searchPaginatedEvents(eventFilter, pageable);
        System.out.println(events);
    }


    /**
     * Logged In As :- Attendee
     * As per Attendee role: Return all data belonging to the associated tenant.
     */
    @Test
    void testEventServiceRecordsForAttendeePaginated(){
        currentLoggedInUsersContext.setContext(6L,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Event> events = this.eventService.searchPaginatedEvents(eventFilter, pageable);
        System.out.println(events);
    }

}
