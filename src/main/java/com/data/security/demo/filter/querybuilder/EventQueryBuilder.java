package com.data.security.demo.filter.querybuilder;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.filter.querybuilder
 *  👤 Author: hardik
 *  📅 Date: Tuesday, 23 September 2025
 *  ⏰ Time: 15:47
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.data.security.demo.common.querybuilder.abs.AbstractFilterableQueryBuilder;
import com.data.security.demo.entities.AppUser;
import com.data.security.demo.entities.Event;
import com.data.security.demo.entities.Ticket;
import com.data.security.demo.entities.VendorBooth;
import com.data.security.demo.enums.AppRoles;
import com.data.security.demo.filter.EventFilter;
import com.data.security.demo.utils.CurrentLoggedInUsersContext;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class EventQueryBuilder extends AbstractFilterableQueryBuilder<Event, EventFilter> {

    private final CurrentLoggedInUsersContext currentLoggedInUsersContext;


    public EventQueryBuilder(EntityManager entityManager, CriteriaBuilderFactory cf, CurrentLoggedInUsersContext currentLoggedInUsersContext){
        super(entityManager, cf);
        this.currentLoggedInUsersContext = currentLoggedInUsersContext;
    }


    /**
     * Must be implemented: entity class & alias
     */
    @Override
    protected Class<Event> getEntityClass() {
        return Event.class;
    }

    @Override
    protected String getEntityAlias() {
        return "e";
    }

    @Override
    protected void applyAuthorization(CriteriaBuilder<Event> cb) {
        Long roleId = currentLoggedInUsersContext.getLoggedInRoleId();
        if (AppRoles.SUPER_ADMIN.getRoleId().equals(roleId)) {
            return; // no restrictions
        }

        if (AppRoles.EVENT_MANAGER.getRoleId().equals(roleId)) {
            cb.where("e.tenant.id").in()
                    .from(AppUser.class, "u")
                    .select("u.tenant.id")
                    .where("u.id").eq(currentLoggedInUsersContext.getLoggedInUsersId())
                    .end();
        }

        if (AppRoles.ATTENDEE.getRoleId().equals(roleId)){
            cb.where("e.id").in()
                    .from(Ticket.class, "t")
                    .select("t.event.id")
                    .where("t.attendee.id").eq(currentLoggedInUsersContext.getLoggedInUsersId())
                    .end();
        }

        if (AppRoles.VENDOR.getRoleId().equals(roleId)){
            cb.where("e.id").in()
                    .from(VendorBooth.class,"v")
                    .select("v.event.id")
                    .where("v.vendor.id").eq(currentLoggedInUsersContext.getLoggedInUsersId())
                    .end();
        }
    }


    @Override
    protected void applyFilters(CriteriaBuilder<Event> cb, EventFilter filter) {
        if (filter.getEventId() != null) cb.where("e.id").eq(filter.getEventId());
        if (filter.getName() != null && !filter.getName().isBlank())
            cb.where("e.name").like().value("%" + filter.getName() + "%").noEscape();
        if (filter.getLocation() != null && !filter.getLocation().isBlank())
            cb.where("e.location").like().value("%" + filter.getLocation() + "%").noEscape();
    }
}
