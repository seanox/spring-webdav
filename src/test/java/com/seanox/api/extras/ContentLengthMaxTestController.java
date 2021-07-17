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
 * Test of the annotation {@link ApiDavInputMapping}
 *     + contentLengthMax functions.
 *
 * ContentLengthMaxTestController 1.0.0 20210715
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
@Component
public class ContentLengthMaxTestController {

    public static final String MAPPING_A1 = "/extras/contentLengthMax/a1.txt";
    private byte[] a1 = new byte[0];
    @ApiDavMapping(path=MAPPING_A1, isReadOnly=false)
    void test_A1(MetaOutputStream outputStream)
            throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a1.length).getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A1, contentLengthMax=-1)
    void test_A1(MetaInputStream inputStream)
            throws IOException {
        this.a1 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A2 = "/extras/contentLengthMax/a2.txt";
    private byte[] a2 = new byte[0];
    @ApiDavMapping(path=MAPPING_A2, isReadOnly=false)
    void test_A2(MetaOutputStream outputStream)
            throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a2.length).getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A2, contentLengthMax=0)
    void test_A2(MetaInputStream inputStream)
            throws IOException {
        this.a2 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A3 = "/extras/contentLengthMax/a3.txt";
    private byte[] a3 = new byte[0];
    @ApiDavMapping(path=MAPPING_A3, isReadOnly=false)
    void test_A3(MetaOutputStream outputStream)
            throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a3.length).getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A3, contentLengthMax=10)
    void test_A3(MetaInputStream inputStream)
            throws IOException {
        this.a3 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A4 = "/extras/contentLengthMax/a4.txt";
    private byte[] a4 = new byte[0];
    @ApiDavMapping(path=MAPPING_A4, isReadOnly=false)
    void test_A4(MetaOutputStream outputStream)
            throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a4.length).getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A4, contentLengthMax=50)
    void test_A4(MetaInputStream inputStream)
            throws IOException {
        this.a4 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A5 = "/extras/contentLengthMax/a5.txt";
    private byte[] a5 = new byte[0];
    @ApiDavMapping(path=MAPPING_A5, isReadOnly=false)
    void test_A5(MetaOutputStream outputStream)
            throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a5.length).getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_A5)
    void test_A5(MetaInputStream inputStream)
            throws IOException {
        this.a5 = inputStream.readAllBytes();
    }
}