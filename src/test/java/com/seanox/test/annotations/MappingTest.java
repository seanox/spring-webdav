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
package com.seanox.test.annotations;

import com.seanox.test.AbstractTest;
import com.seanox.webdav.SitemapAdapter;
import com.seanox.webdav.SitemapExceptionAdapter;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

/**
 * Test of the annotation {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class MappingTest extends AbstractTest {

    @WebDavMapping(path="a")
    @WebDavMapping(path="/a")
    @Test
    void test_1() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a", throwable.getMessage());
    }

    @WebDavMapping(path="/a/b/c")
    @WebDavMapping(path="/a/b/c/d/e")
    @Test
    void test_2() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c/d", throwable.getMessage());
    }

    @WebDavMapping(path="/a/b/c/d/e")
    @WebDavMapping(path="/a/b/c")
    @Test
    void test_3() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @WebDavMapping(path="/a/b/c/d/e")
    @WebDavMapping(path="/a/b/C")
    @Test
    void test_4() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/b/c", throwable.getMessage());
    }

    @WebDavMapping(path="/a/B/c/d/e")
    @WebDavMapping(path="/A/b/c")
    @Test
    void test_5() {
        final Object sitemap = SitemapAdapter.createInstance();
        Throwable throwable = Assertions.assertThrows(SitemapExceptionAdapter.getSitemapExceptionClass(), () -> {
            for (Annotation annotation : this.collectApiAnnotationsFromCurrentMethod())
                SitemapAdapter.map(sitemap, new Annotation[] {annotation});
        });
        Assertions.assertEquals("Ambiguous Mapping: /a/B/c", throwable.getMessage());
    }

    @WebDavMapping(path="/customer/list.xlsx")
    @WebDavMapping(path="/customer/reports/statistic.xlsx")
    @WebDavMapping(path="/customer/reports/turnover.xlsx")
    @WebDavMapping(path="/marketing/newsletter.pptx")
    @WebDavMapping(path="/marketing/sales.pptx")
    @Test
    void test_6() throws Exception {
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

    // The following test is difficult to implement, because the expected
    // exception only occurs when initializing the filter and the filter
    // requires the bean scan of Spring.
    // expected: com.seanox.webdav.AnnotationException: Ambiguous Attribute Mapping: /example/file.txt
    // @WebDavMapping(path="/test/1.txt")
    // @WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.ContentType)
    // @WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.ContentType)
    // @Test
    // void test_8() throws Exception {
    // }
}