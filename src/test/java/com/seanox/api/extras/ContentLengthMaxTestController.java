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

import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.MetaInputStream;
import com.seanox.webdav.MetaOutputStream;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * Test the function of the ContentLengthMaxTest attribute for
 * {@link WebDavInputMapping}.<br>
 * <br>
 * ContentLengthMaxTestController 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
@Component
public class ContentLengthMaxTestController {

    // Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // TODO: Test D of priorities, what is used when -- Expression, Static, (Default)

    public static final String MAPPING_A1 = "/extras/contentLengthMax/a1.txt";
    private byte[] a1 = new byte[0];
    @WebDavMapping(path=MAPPING_A1, readOnly=false)
    void test_A1(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a1.length).getBytes());
    }
    @WebDavInputMapping(path=MAPPING_A1, contentLengthMax=-1)
    void test_A1(final MetaInputStream inputStream) throws IOException {
        this.a1 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A2 = "/extras/contentLengthMax/a2.txt";
    private byte[] a2 = new byte[0];
    @WebDavMapping(path=MAPPING_A2, readOnly=false)
    void test_A2(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a2.length).getBytes());
    }
    @WebDavInputMapping(path=MAPPING_A2, contentLengthMax=0)
    void test_A2(final MetaInputStream inputStream) throws IOException {
        this.a2 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A3 = "/extras/contentLengthMax/a3.txt";
    private byte[] a3 = new byte[0];
    @WebDavMapping(path=MAPPING_A3, readOnly=false)
    void test_A3(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a3.length).getBytes());
    }
    @WebDavInputMapping(path=MAPPING_A3, contentLengthMax=10)
    void test_A3(final MetaInputStream inputStream) throws IOException {
        this.a3 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A4 = "/extras/contentLengthMax/a4.txt";
    private byte[] a4 = new byte[0];
    @WebDavMapping(path=MAPPING_A4, readOnly=false)
    void test_A4(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a4.length).getBytes());
    }
    @WebDavInputMapping(path=MAPPING_A4, contentLengthMax=50)
    void test_A4(MetaInputStream inputStream) throws IOException {
        this.a4 = inputStream.readAllBytes();
    }

    public static final String MAPPING_A5 = "/extras/contentLengthMax/a5.txt";
    private byte[] a5 = new byte[0];
    @WebDavMapping(path=MAPPING_A5, readOnly=false)
    void test_A5(final MetaOutputStream outputStream) throws IOException {
        outputStream.setLastModified(new Date());
        outputStream.write(String.valueOf(this.a5.length).getBytes());
    }
    @WebDavInputMapping(path=MAPPING_A5)
    void test_A5(final MetaInputStream inputStream) throws IOException {
        this.a5 = inputStream.readAllBytes();
    }
}