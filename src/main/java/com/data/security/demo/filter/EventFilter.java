package com.data.security.demo.filter;/*
 *----------------------------------------------------------------------------
 *                 🌟 demo 🌟
 *----------------------------------------------------------------------------
 *  📁 Package: com.data.security.demo.filter
 *  👤 Author: hardik
 *  📅 Date: Tuesday, 23 September 2025
 *  ⏰ Time: 15:10
 *----------------------------------------------------------------------------
 *  Description:
 *  - Brief description of the file's purpose and functionality.
 *  - Additional details if necessary.
 *----------------------------------------------------------------------------
 */


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventFilter {

    private Long eventId;

    private String name;

    private String location;

}
