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
package com.seanox.api.extras;

import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMappingAttributeExpression;
import com.seanox.apidav.MetaOutputStream;
import com.seanox.apidav.Properties;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Test of the annotation {@link AcceptedTestController} + accepted functions.
 *
 * AcceptedTestController 1.0.0 20210716
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210716
 */
@Component
public class AcceptedTestController {

    // TODO:

    public static final String MAPPING_C1 = "/extras/accepted/c1.txt";
    public static final String MAPPING_C2 = "/extras/accepted/c2.txt";
    public static final String MAPPING_C3 = "/extras/accepted/c3.txt";
    public static final String MAPPING_C4 = "/extras/accepted/c4.txt";
    public static final String MAPPING_C5 = "/extras/accepted/c5.txt";
    public static final String MAPPING_C6 = "/extras/accepted/c6.txt";
    public static final String MAPPING_C7 = "/extras/accepted/c7.txt";
    public static final String MAPPING_C8 = "/extras/accepted/c8.txt";
    public static final String MAPPING_C9 = "/extras/accepted/c9.txt";
    public static final String MAPPING_CA = "/extras/accepted/cA.txt";
    public static final String MAPPING_CB = "/extras/accepted/cB.txt";
    public static final String MAPPING_CC = "/extras/accepted/cC.txt";
    public static final String MAPPING_CD = "/extras/accepted/cD.txt";
    public static final String MAPPING_CE = "/extras/accepted/cE.txt";
    public static final String MAPPING_CF = "/extras/accepted/cF.txt";
    public static final String MAPPING_CG = "/extras/accepted/cG.txt";
    public static final String MAPPING_CH = "/extras/accepted/cH.txt";

    @ApiDavMapping(path=MAPPING_C1, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C2, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C3, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C4, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C5, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C6, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C7, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C8, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_C9, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CA, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CB, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CC, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CD, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CE, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CF, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CG, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @ApiDavMapping(path=MAPPING_CH, isReadOnly=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    void test_CX(MetaOutputStream outputStream) {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.Accepted)
    void test_C1(URI uri, Properties properties) {
        return;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.Accepted)
    String test_C2(Properties properties) {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.Accepted)
    String test_C3() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.Accepted)
    String test_C4() {
        return " ";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.Accepted)
    String test_C5() {
        return "True";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.Accepted)
    String test_C6() {
        return "true";
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.Accepted)
    String test_C7() {
        return "True";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.Accepted)
    String test_C8() {
        return " true";
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.Accepted)
    String test_C9() {
        return "false";
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.Accepted)
    boolean test_CA() {
        return true;
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.Accepted)
    boolean test_CB() {
        return false;
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.Accepted)
    Boolean test_CC() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.Accepted)
    Boolean test_CD() {
        return Boolean.TRUE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.Accepted)
    Boolean test_CE() {
        return Boolean.FALSE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.Accepted)
    Object test_CF() {
        return Boolean.TRUE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CG, attribute=ApiDavMappingAttribute.Accepted)
    Exception test_CG() {
        return new RuntimeException();
    }
    @ApiDavAttributeMapping(path=MAPPING_CH, attribute=ApiDavMappingAttribute.Accepted)
    Boolean test_CH() {
        throw new RuntimeException("Test CH");
    }
}