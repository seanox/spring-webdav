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
package com.seanox.test;

import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.SitemapAdapter;
import com.seanox.apidav.SitemapExceptionAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.nio.file.InvalidPathException;

/**
 * Test of the Sitemap functions.
 *
 * SitemapTest 1.0.0 20210707
 * Copyright (C) 2021 Seanox Software Solutions
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

    @ApiDavMapping(path="a")
    @ApiDavMapping(path="/a")
    @Test
    void testMap_1() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c")
    @ApiDavMapping(path="/a/b/c/d/e")
    @Test
    void testMap_2() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c/d", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    @ApiDavMapping(path="/a/b/c")
    @Test
    void testMap_3() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    @ApiDavMapping(path="/a/b/C")
    @Test
    void testMap_4() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/B/c/d/e")
    @ApiDavMapping(path="/A/b/c")
    @Test
    void testMap_5() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/B/c", throwable.getMessage());
    }

    @ApiDavMapping(path="/customer/list.xlsx")
    @ApiDavMapping(path="/customer/reports/statistic.xlsx")
    @ApiDavMapping(path="/customer/reports/turnover.xlsx")
    @ApiDavMapping(path="/marketing/newsletter.pptx")
    @ApiDavMapping(path="/marketing/sales.pptx")
    @Test
    void testMap_6()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
            SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        final String output = sitemap.toString().replaceAll("\\R", "\n");
        final String pattern = "+ customer\n" +
                "  - list.xlsx\n" +
                "  + reports\n" +
                "    - statistic.xlsx\n" +
                "    - turnover.xlsx\n" +
                "+ marketing\n" +
                "  - newsletter.pptx\n" +
                "  - sales.pptx";
        Assertions.assertEquals(pattern, output);
    }
}