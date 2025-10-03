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
     * As per Attendee role: Return only those events for which attendee has ticket
     */
    @Test
    void testEventServiceRecordsForAttendee(){
        currentLoggedInUsersContext.setContext(6L ,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(1,events.size());
        Event event = events.get(0);
        Assertions.assertEquals("Tech Conference 2025",event.getName());
    }

    /**
     * Logged In As :- Attendee
     * Tenant_id :- 1
     * For the Attendee role: Only events for which the attendee has a ticket should be returned.
     * This test checks filtering for an event where the attendee does not have a ticket.
     */
    @Test
    void testEventServiceRecordsForAttendeeFromOtherTenant(){
        currentLoggedInUsersContext.setContext(8L,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        eventFilter.setName("Startup Meetup");
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(0,events.size());
    }

    /**
     * Context: Logged in as an Attendee
     * Tenant ID: 1
     * For the Attendee role: Only events for which the attendee has a ticket should be returned.
     * This test checks filtering for an event where the attendee have a ticket.
     */
    @Test
    void testEventServiceRecordsForAttendeeFromSameTenant(){
        currentLoggedInUsersContext.setContext(8L,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        eventFilter.setName("Healthcare Summit");
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(1,events.size());
        Event event = events.get(0);
        Assertions.assertEquals("Healthcare Summit",event.getName());
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
        Assertions.assertEquals(4, events.getTotalElements());
    }


    /**
     * Logged In As :- Attendee
     * As per Attendee role: Return all events data for which attendee has ticket
     */
    @Test
    void testEventServiceRecordsForAttendeePaginated(){
        currentLoggedInUsersContext.setContext(6L,AppRoles.ATTENDEE.getRoleId());
        EventFilter eventFilter = new EventFilter();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Event> events = this.eventService.searchPaginatedEvents(eventFilter, pageable);
        Assertions.assertEquals(1, events.getTotalElements());
    }

    /**
     * Logged In As :- Vendor
     * For the Vendor role: return all events associated with the vendor’s assigned booth.
     */
    @Test
    void testEventServiceRecordsForVendorRole(){
        currentLoggedInUsersContext.setContext(7L,AppRoles.VENDOR.getRoleId());
        EventFilter eventFilter = new EventFilter();
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(1,events.size());
    }

    /**
     * Logged In As :- Event Manager
     * For the Vendor role: return all events associated with the event manager's tenant
     */
    @Test
    void testEventServiceRecordsForEventManagerRole(){
        currentLoggedInUsersContext.setContext(2L, AppRoles.EVENT_MANAGER.getRoleId());
        EventFilter eventFilter = new EventFilter();
        List<Event> events = this.eventService.searchEvents(eventFilter);
        Assertions.assertEquals(2,events.size());
    }

    /**
     * Paginated Search
     * Logged In As :- Event Manager
     * For the Vendor role: return all events associated with the event manager's tenant
     */
    @Test
    void testEventServiceRecordsForEventManagerRolePaginated(){
        currentLoggedInUsersContext.setContext(2L, AppRoles.EVENT_MANAGER.getRoleId());
        EventFilter eventFilter = new EventFilter();
        Pageable pageable = PageRequest.of(0, 1);
        Page<Event> events = this.eventService.searchPaginatedEvents(eventFilter, pageable);
        Assertions.assertEquals(2, events.getTotalElements());
    }


}
