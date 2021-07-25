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
package com.seanox.test;

import com.seanox.webdav.SitemapAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.InvalidPathException;

/**
 * Test of the Sitemap functions.<br>
 * <br>
 * SitemapTest 1.0.0 20210707<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210707
 */
public class SitemapTest extends AbstractTest {

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