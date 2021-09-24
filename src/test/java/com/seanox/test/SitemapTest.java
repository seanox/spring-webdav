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
package com.seanox.test;

import com.seanox.webdav.SitemapAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

/**
 * Test of the Sitemap functions.<br>
 * <br>
 * SitemapTest 1.0.0 20210815<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class SitemapTest extends AbstractTest {

    @Test
    void testNormalizePath_1() {
        Assertions.assertEquals("/", SitemapAdapter.normalizePath(" "));
        Assertions.assertEquals("/a", SitemapAdapter.normalizePath(" a "));
        Assertions.assertEquals("/a/b/c", SitemapAdapter.normalizePath(" \\a/b\\c/ "));
        Assertions.assertEquals("/a/c", SitemapAdapter.normalizePath(" \\a/.\\c/ "));
        Assertions.assertEquals("/c", SitemapAdapter.normalizePath(" \\a/..\\c/ "));
        Assertions.assertEquals("/c", SitemapAdapter.normalizePath(" \\a/..\\.\\c/ "));
        Assertions.assertEquals("/c", SitemapAdapter.normalizePath(" \\a/..\\..\\c/ "));
        Assertions.assertEquals("/c", SitemapAdapter.normalizePath(" \\a/..\\..\\..\\c/ "));
        Assertions.assertEquals("/c", SitemapAdapter.normalizePath(" \\a/..\\..\\..\\..\\c/ "));
        Assertions.assertEquals("/a/b/C/d/e", SitemapAdapter.normalizePath("a/b/C/d/e///"));
    }

    @Test
    void testNormalizePath_2() {

        // Paths based only on spaces and dots are often a problem
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/C/ /e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/C/ // /e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("../../../a/b/c/ // /e"));

        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/c/d/e/..."));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/.../d/e/"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/. . ./d/e/"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/c/d/e/. . ."));

        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/\t/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/\n/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/\00/d/e"));

        // Special characters only relevant for Windows (:*?"'<>|)
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/?/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/*/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/:/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/\"/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/|/d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/</d/e"));
        Assertions.assertThrows(InvalidPathException.class, () -> SitemapAdapter.normalizePath("a/b/>/d/e"));
    }

    @Test
    void testNormalizePath_3() {
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c/d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c//d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c///d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c////d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c/d/e/."));
        Assertions.assertEquals("/a/b/c/d", SitemapAdapter.normalizePath("a/b/c/d/e/.."));

        Assertions.assertEquals("/a/b/d/e", SitemapAdapter.normalizePath("a/b/./d/e/"));
        Assertions.assertEquals("/a/b/d/e", SitemapAdapter.normalizePath("a/b//.//d/e/"));
        Assertions.assertEquals("/a/b/d/e", SitemapAdapter.normalizePath("a/b///.///d/e/"));
        Assertions.assertEquals("/a/b/d/e", SitemapAdapter.normalizePath("a/b////.////d/e/"));
        Assertions.assertEquals("/a/d/e", SitemapAdapter.normalizePath("a/b/../d/e/"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c/\\/d/e"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c/\\\\/d/e"));
        Assertions.assertEquals("/a/b/c/d/e", SitemapAdapter.normalizePath("a/b/c/\\\\\\/d/e"));
    }
}