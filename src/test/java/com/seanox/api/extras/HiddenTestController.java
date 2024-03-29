/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der Apache License.
 *
 * WebDAV mapping for Spring Boot
 * Copyright (C) 2021 Seanox Software Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.seanox.api.extras;

import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.MetaOutputStream;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Test the function of the hidden attribute for
 * {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class HiddenTestController {

    // Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // TODO: Test C for callbacks + Variants of values (valid + invalid)
    // TODO: Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // The directory structure shows only substructures with visible content.
    // If a directory has no visible content, this structure is not displayed.
    // However, the paths to the data exist.
    // Expected behavior:
    // - Explorer displays only /extras/hidden/c/c-1/c-2/c-3/c2.txt
    // - /extras/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
    // - /extras/hidden/a/a-1/a-2/a-3 can be used, but is empty
    // - /extras/hidden/a/a-1/a-2 can be used, but is empty
    // - /extras/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
    // - /extras/hidden/b/b-1/b-2/b-3 can be used, but is empty
    // - /extras/hidden/b/b-1/b-2/b-3/b1.txt can be used, shows the file name
    // - /extras/hidden/c/c-1/c-2/c-3 can be used and is not empty

    public static final String MAPPING_A_FILE_A1 = "/extras/hidden/a/a-1/a-2/a-3/a1.txt";
    public static final String MAPPING_A_FILE_A2 = "/extras/hidden/a/a-1/a-2/a-3/a2.txt";
    public static final String MAPPING_A_FILE_B1 = "/extras/hidden/b/b-1/b-2/b-3/b1.txt";
    public static final String MAPPING_A_FILE_C1 = "/extras/hidden/c/c-1/c-2/c-3/c1.txt";
    public static final String MAPPING_A_FILE_C2 = "/extras/hidden/c/c-1/c-2/c-3/c2.txt";
    public static final String MAPPING_A_FILE_D1 = "/extras/hidden/d/d-1/d-2/d-3/d1.txt";

    public static final String MAPPING_A_FOLDER_C3_REDIRECT = "/extras/hidden/c/c-1/c-2/c-3";
    public static final String MAPPING_A_FOLDER_C3 = "/extras/hidden/c/c-1/c-2/c-3/";

    public static final String MAPPING_A_FOLDER_A3_REDIRECT = "/extras/hidden/a/a-1/a-2/a-3";
    public static final String MAPPING_A_FOLDER_A3 = "/extras/hidden/a/a-1/a-2/a-3/";
    public static final String MAPPING_A_FOLDER_A2_REDIRECT = "/extras/hidden/a/a-1/a-2";
    public static final String MAPPING_A_FOLDER_A2 = "/extras/hidden/a/a-1/a-2/";
    public static final String MAPPING_A_FOLDER_B3_REDIRECT = "/extras/hidden/b/b-1/b-2/b-3";
    public static final String MAPPING_A_FOLDER_B3 = "/extras/hidden/b/b-1/b-2/b-3/";

    @WebDavMapping(path=MAPPING_A_FILE_A1, hidden=true)
    void testA1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("a1.txt").getBytes());
    }
    @WebDavMapping(path=MAPPING_A_FILE_A2, hidden=true)
    void testA2(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("a2.txt").getBytes());
    }
    @WebDavMapping(path=MAPPING_A_FILE_B1, hidden=true)
    void testB1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("b1.txt").getBytes());
    }
    @WebDavMapping(path=MAPPING_A_FILE_C1, hidden=true)
    void testC1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("c1.txt").getBytes());
    }
    @WebDavMapping(path=MAPPING_A_FILE_C2, hidden=false)
    void testC2(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("c2.txt").getBytes());
    }
    @WebDavMapping(path=MAPPING_A_FILE_D1, hidden=true)
    void testD1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(("d1.txt").getBytes());
    }
}