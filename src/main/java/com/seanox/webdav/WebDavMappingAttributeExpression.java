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
 * WebDavMappingAttributeExpression 1.0.0 20210703<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
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