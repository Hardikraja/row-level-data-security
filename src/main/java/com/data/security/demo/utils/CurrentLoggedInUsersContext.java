package com.data.security.demo.utils;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.utils
 *  👤 Author: hardik
 *  📅 Date: Monday, 22 September 2025
 *  ⏰ Time: 18:24
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrentLoggedInUsersContext {

    private Long loggedInUsersId;

    private Long loggedInRoleId;

    public void setContext(Long loggedInUsersId, Long loggedInRoleId){
        this.setLoggedInUsersId(loggedInUsersId);
        this.setLoggedInRoleId(loggedInRoleId);
    }


}
