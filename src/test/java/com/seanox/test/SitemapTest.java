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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SitemapTest {

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

    private static Collection<Annotation> collectApiAnnotations(String methodRegExFilter) {
        final Collection<Annotation> annotations = new ArrayList<>();
        Arrays.stream(SitemapTest.class.getDeclaredMethods())
                .filter(method ->
                        method.getName().matches(methodRegExFilter))
                .sorted((method, compare) -> method.getName().compareToIgnoreCase(compare.getName()))
                .forEach(method -> {
                    Arrays.stream(method.getDeclaredAnnotations())
                            .forEach(annotation -> annotations.add(annotation));
                });
        return annotations;
    }

    @ApiDavMapping(path="a")
    private void map_1_1() {
    }
    @ApiDavMapping(path="/a")
    private void map_1_2() {
    }
    @Test
    void testMap_1()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : SitemapTest.collectApiAnnotations("^map_1_.*"))
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c")
    private void map_2_1() {
    }
    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_2_2() {
    }
    @Test
    void testMap_2()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : SitemapTest.collectApiAnnotations("^map_2_.*"))
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c/d", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_3_1() {
    }
    @ApiDavMapping(path="/a/b/c")
    private void map_3_2() {
    }
    @Test
    void testMap_3()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : SitemapTest.collectApiAnnotations("^map_3_.*"))
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/b/c/d/e")
    private void map_4_1() {
    }
    @ApiDavMapping(path="/a/b/C")
    private void map_4_2() {
    }
    @Test
    void testMap_4()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : SitemapTest.collectApiAnnotations("^map_4_.*"))
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/C", throwable.getMessage());
    }

    @ApiDavMapping(path="/a/B/c/d/e")
    private void map_5_1() {
    }
    @ApiDavMapping(path="/A/b/c")
    private void map_5_2() {
    }
    @Test
    void testMap_5()
            throws Exception {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : SitemapTest.collectApiAnnotations("^map_5_.*"))
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /A/b/c", throwable.getMessage());
    }
}