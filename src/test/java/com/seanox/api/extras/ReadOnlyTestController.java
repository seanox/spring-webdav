/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * WebDAV mapping for Spring Boot
 * Copyright (C) 2021 Seanox Software Solutions
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 2 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.seanox.api.extras;

import org.springframework.stereotype.Component;

/**
 * Test the function of the readOnly attribute for
 * {@link com.seanox.apidav.ApiDavMapping} and
 * {@link com.seanox.apidav.ApiDavInputMapping}.<br>
 * <br>
 * ReadOnlyTestController 1.0.0 20210724<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210724
 */
@Component
public class ReadOnlyTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // TODO: Test C for callbacks + Variants of values (valid + invalid)
    // TODO: Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)
}