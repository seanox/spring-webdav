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
package com.seanox.test.annotations;

import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.SitemapAdapter;
import com.seanox.apidav.SitemapExceptionAdapter;
import com.seanox.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

/**
 * Test of the annotation {@link ApiDavMapping} functions.
 *
 * MappingTest 1.0.0 20210712
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210712
 */
public class MappingTest extends AbstractTest {

    @ApiDavMapping(path="a")
    @ApiDavMapping(path="/a")
    @Test
    void test_1() {
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
    void test_2() {
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
    void test_3() {
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
    void test_4() {
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
    void test_5() {
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
    void test_6()
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