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

import com.seanox.apidav.ApiDavInputMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Test of the annotation {@link ApiDavInputMapping} + accept functions.
 *
 * AcceptTestController 1.0.0 20210718
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210718
 */
@Component
public class AcceptTestController {

    // Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // TODO: Test D of priorities, what is used when -- Expression, Static, (Default)

    public static final String MAPPING_A1 = "/extras/accept/a1.txt";
    private String a1 = "";
    @ApiDavMapping(path=MAPPING_A1, isReadOnly=false)
    void test_A1(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a1.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A1)
    void test_A1(final MetaInputStream inputStream) throws IOException {
        this.a1 += new String(inputStream.readAllBytes());
    }

    public static final String MAPPING_A2 = "/extras/accept/a2.txt";
    private String a2 = "";
    @ApiDavMapping(path=MAPPING_A2, isReadOnly=false)
    void test_A2(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a2.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A2, accept="")
    void test_A2(final MetaInputStream inputStream) throws IOException {
        this.a2 += new String(inputStream.readAllBytes());
    }

    public static final String MAPPING_A3 = "/extras/accept/a3.txt";
    private String a3 = "";
    @ApiDavMapping(path=MAPPING_A3, isReadOnly=false)
    void test_A3(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a3.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A3, accept="text/*")
    void test_A3(MetaInputStream inputStream) throws IOException {
        this.a3 += new String(inputStream.readAllBytes());
    }

    public static final String MAPPING_A4 = "/extras/accept/a4.txt";
    private String a4 = "";
    @ApiDavMapping(path=MAPPING_A4, isReadOnly=false)
    void test_A4(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a4.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A4, accept="text/* , xxx/* ")
    void test_A4(MetaInputStream inputStream) throws IOException {
        this.a4 += new String(inputStream.readAllBytes());
    }

    public static final String MAPPING_A5 = "/extras/accept/a5.txt";
    private String a5 = "";
    @ApiDavMapping(path=MAPPING_A5, isReadOnly=false)
    void test_A5(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a5.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A5, accept="text/a, text/b, xxx/* ")
    void test_A5(final MetaInputStream inputStream) throws IOException {
        this.a5 += new String(inputStream.readAllBytes());
    }

    public static final String MAPPING_A6 = "/extras/accept/a6.txt";
    private String a6 = "";
    @ApiDavMapping(path=MAPPING_A6, isReadOnly=false)
    void test_A6(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(this.a6.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A6, accept="*/a,*/b")
    void test_A6(final MetaInputStream inputStream) throws IOException {
        this.a6 += new String(inputStream.readAllBytes());
    }
}