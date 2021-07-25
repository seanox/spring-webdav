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
package com.seanox.api.extras;

import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.WebDavMetaMapping;
import com.seanox.webdav.MetaData;
import com.seanox.webdav.WebDavInputMapping;
import org.springframework.stereotype.Component;

/**
 * Test the function of the permitted attribute.
 * {@link WebDavMapping} and
 * {@link WebDavInputMapping}.<br>
 * <br>
 * PermittedTestController 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
@Component
public class PermittedTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_C1 = "/extras/permitted/c1.txt";
    public static final String MAPPING_C2 = "/extras/permitted/c2.txt";
    public static final String MAPPING_C3 = "/extras/permitted/c3.txt";
    public static final String MAPPING_C4 = "/extras/permitted/c4.txt";
    public static final String MAPPING_C5 = "/extras/permitted/c5.txt";
    public static final String MAPPING_C6 = "/extras/permitted/c6.txt";
    public static final String MAPPING_C7 = "/extras/permitted/c7.txt";
    public static final String MAPPING_C8 = "/extras/permitted/c8.txt";
    public static final String MAPPING_C9 = "/extras/permitted/c9.txt";
    public static final String MAPPING_CA = "/extras/permitted/cA.txt";
    public static final String MAPPING_CB = "/extras/permitted/cB.txt";
    public static final String MAPPING_CC = "/extras/permitted/cC.txt";
    public static final String MAPPING_CD = "/extras/permitted/cD.txt";
    public static final String MAPPING_CE = "/extras/permitted/cE.txt";
    public static final String MAPPING_CF = "/extras/permitted/cF.txt";
    public static final String MAPPING_CG = "/extras/permitted/cG.txt";
    public static final String MAPPING_CH = "/extras/permitted/cH.txt";

    @WebDavMapping(path=MAPPING_C1, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C2, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C3, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C4, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C5, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C6, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C7, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C8, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C9, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CA, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CB, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CC, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CD, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CE, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CF, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CG, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CH, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    void test_CX() {
    }
    @WebDavAttributeMapping(path=MAPPING_C1, attribute= WebDavMappingAttribute.Permitted)
    void test_C1() {
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute= WebDavMappingAttribute.Permitted)
    String test_C2() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute= WebDavMappingAttribute.Permitted)
    String test_C3() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute= WebDavMappingAttribute.Permitted)
    String test_C4() {
        return " ";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute= WebDavMappingAttribute.Permitted)
    String test_C5() {
        return "True";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute= WebDavMappingAttribute.Permitted)
    String test_C6() {
        return "true";
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute= WebDavMappingAttribute.Permitted)
    String test_C7() {
        return "True";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute= WebDavMappingAttribute.Permitted)
    String test_C8() {
        return " true";
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute= WebDavMappingAttribute.Permitted)
    String test_C9() {
        return "false";
    }
    @WebDavAttributeMapping(path=MAPPING_CA, attribute= WebDavMappingAttribute.Permitted)
    boolean test_CA() {
        return true;
    }
    @WebDavAttributeMapping(path=MAPPING_CB, attribute= WebDavMappingAttribute.Permitted)
    boolean test_CB() {
        return false;
    }
    @WebDavAttributeMapping(path=MAPPING_CC, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_CC() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_CD, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_CD() {
        return Boolean.TRUE;
    }
    @WebDavAttributeMapping(path=MAPPING_CE, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_CE() {
        return Boolean.FALSE;
    }
    @WebDavAttributeMapping(path=MAPPING_CF, attribute= WebDavMappingAttribute.Permitted)
    Object test_CF() {
        return Boolean.TRUE;
    }
    @WebDavAttributeMapping(path=MAPPING_CG, attribute= WebDavMappingAttribute.Permitted)
    Exception test_CG() {
        return new RuntimeException();
    }
    @WebDavAttributeMapping(path=MAPPING_CH, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_CH() {
        throw new RuntimeException("Test CH");
    }

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/permitted/d1.txt";
    public static final String MAPPING_D2 = "/extras/permitted/d2.txt";
    public static final String MAPPING_D3 = "/extras/permitted/d3.txt";
    public static final String MAPPING_D4 = "/extras/permitted/d4.txt";
    public static final String MAPPING_D5 = "/extras/permitted/d5.txt";
    public static final String MAPPING_D6 = "/extras/permitted/d6.txt";

    @WebDavMapping(path=MAPPING_D1, permitted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D1X() {
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setPermitted(false);
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_D1() {
        return Boolean.FALSE;
    }

    @WebDavMapping(path=MAPPING_D2, permitted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setPermitted(false);
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_D2() {
        return true;
    }

    @WebDavMapping(path=MAPPING_D3, permitted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setPermitted(true);
    }

    @WebDavMapping(path=MAPPING_D4, permitted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="true")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, permitted=true)
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }

    public static final String MAPPING_DA = "/extras/permitted/dA.txt";
    public static final String MAPPING_DB = "/extras/permitted/dB.txt";
    public static final String MAPPING_DC = "/extras/permitted/dC.txt";
    public static final String MAPPING_DD = "/extras/permitted/dD.txt";
    public static final String MAPPING_DE = "/extras/permitted/dE.txt";
    public static final String MAPPING_DF = "/extras/permitted/dF.txt";

    @WebDavMapping(path=MAPPING_DA, permitted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DAX() {
    }
    @WebDavMetaMapping(path=MAPPING_DA)
    void test_DA(final MetaData metaData) {
        metaData.setPermitted(true);
    }
    @WebDavAttributeMapping(path=MAPPING_DA, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_DA() {
        return Boolean.TRUE;
    }

    @WebDavMapping(path=MAPPING_DB, permitted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DBX() {
    }
    @WebDavMetaMapping(path=MAPPING_DB)
    void test_DB(final MetaData metaData) {
        metaData.setPermitted(true);
    }
    @WebDavAttributeMapping(path=MAPPING_DB, attribute= WebDavMappingAttribute.Permitted)
    Boolean test_DB() {
        return false;
    }

    @WebDavMapping(path=MAPPING_DC, permitted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DCX() {
    }
    @WebDavMetaMapping(path=MAPPING_DC)
    void test_DC(final MetaData metaData) {
        metaData.setPermitted(false);
    }

    @WebDavMapping(path=MAPPING_DD, permitted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute= WebDavMappingAttribute.Permitted, phrase="false")
    })
    void test_DDX() {
    }

    @WebDavMapping(path=MAPPING_DE, permitted=false)
    void test_DEX() {
    }

    @WebDavMapping(path=MAPPING_DF)
    void test_DFX() {
    }
}