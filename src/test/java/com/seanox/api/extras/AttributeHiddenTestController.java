/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * apiDAV, API-WebDAV mapping for Spring Boot
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

import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * AttributeHiddenTestController
 *
 * AttributeHiddenTestController 1.0.0 20210707
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210707
 */
@Component
public class AttributeHiddenTestController {

    // The directory structure shows only substructures with visible content.
    // If a directory has no visible content, this structure is not displayed.
    // However, the paths to the data exist.
    // Expected behavior:
    // - Explorer displays only /extras/attributes/hidden/c/c-1/c-2/c-3/c2.txt
    // - /extras/attributes/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
    // - /extras/attributes/hidden/a/a-1/a-2/a-3 can be used, but is empty
    // - /extras/attributes/hidden/a/a-1/a-2 can be used, but is empty
    // - /extras/attributes/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
    // - /extras/attributes/hidden/b/b-1/b-2/b-3 can be used, but is empty
    // - /extras/attributes/hidden/b/b-1/b-2/b-3/b1.txt can be used, shows the file name
    // - /extras/attributes/hidden/c/c-1/c-2/c-3 can be used and is not empty

    @ApiDavMapping(path="/extras/attributes/hidden/a/a-1/a-2/a-3/a1.txt", isHidden=true)
    void testA1(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("a1.txt").getBytes());
    }
    @ApiDavMapping(path="/extras/attributes/hidden/a/a-1/a-2/a-3/a2.txt", isHidden=true)
    void testA2(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("a2.txt").getBytes());
    }
    @ApiDavMapping(path="/extras/attributes/hidden/b/b-1/b-2/b-3/b1.txt", isHidden=true)
    void testB1(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("b1.txt").getBytes());
    }
    @ApiDavMapping(path="/extras/attributes/hidden/c/c-1/c-2/c-3/c1.txt", isHidden=true)
    void testC1(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("c1.txt").getBytes());
    }
    @ApiDavMapping(path="/extras/attributes/hidden/c/c-1/c-2/c-3/c2.txt", isHidden=false)
    void testC2(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("c2.txt").getBytes());
    }
    @ApiDavMapping(path="/extras/attributes/hidden/d/d-1/d-2/d-3/d1.txt", isHidden=true)
    void testD1(MetaOutputStream outputStream) throws IOException {
        outputStream.write(("d1.txt").getBytes());
    }
}