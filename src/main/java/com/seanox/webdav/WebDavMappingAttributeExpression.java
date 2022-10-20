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
package com.seanox.webdav;

/**
 * <p>
 *   Optional extension for {@code WebDavMapping} to define attributes with
 *   dynamic values as Spring Expression Language.
 * </p>
 * <p>
 *   The expressions are interpreted in their own context. In this, all beans
 *   whose name does not contain a dot, as well as
 *   <code>applicationContext</code>, <code>servletContext</code>,
 *   <code>request</code> and <code>session</code> are available as variables.
 * </p>
 * <pre>
 *   &#64;WebDavMapping(path="/example/file.txt", attributeExpressions={
 *       &#64;WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="..."),
 *       &#64;WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="..."),
 *       ...
 *   })
 *   void getEntity(final MetaOutputStream output) throws IOException {
 *       ...
 *   }
 * </pre>
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210703
 */
public @interface WebDavMappingAttributeExpression {

    /** Attribute */
    WebDavMappingAttribute attribute();

    /** Expression as string/phrase */
    String phrase();
}