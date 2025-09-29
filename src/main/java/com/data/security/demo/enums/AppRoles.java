package com.data.security.demo.enums;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.enums
 *  👤 Author: hardik
 *  📅 Date: Wednesday, 24 September 2025
 *  ⏰ Time: 12:25
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */

public enum AppRoles {

    SUPER_ADMIN(1L),

    EVENT_MANAGER(2L),

    STAFF(3L),

    ATTENDEE(4L),

    VENDOR(5L);

    private final Long roleId;

    AppRoles(Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }
}
