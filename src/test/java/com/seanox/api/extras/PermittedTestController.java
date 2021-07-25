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

import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMappingAttributeExpression;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.MetaData;
import org.springframework.stereotype.Component;

/**
 * Test the function of the permitted attribute.
 * {@link com.seanox.apidav.ApiDavMapping} and
 * {@link com.seanox.apidav.ApiDavInputMapping}.<br>
 * <br>
 * PermittedTestController 1.0.0 20210724<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210724
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
    void test_CX() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.Permitted)
    void test_C1() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.Permitted)
    String test_C2() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.Permitted)
    String test_C3() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.Permitted)
    String test_C4() {
        return " ";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.Permitted)
    String test_C5() {
        return "True";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.Permitted)
    String test_C6() {
        return "true";
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.Permitted)
    String test_C7() {
        return "True";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.Permitted)
    String test_C8() {
        return " true";
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.Permitted)
    String test_C9() {
        return "false";
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.Permitted)
    boolean test_CA() {
        return true;
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.Permitted)
    boolean test_CB() {
        return false;
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_CC() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_CD() {
        return Boolean.TRUE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_CE() {
        return Boolean.FALSE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.Permitted)
    Object test_CF() {
        return Boolean.TRUE;
    }
    @ApiDavAttributeMapping(path=MAPPING_CG, attribute=ApiDavMappingAttribute.Permitted)
    Exception test_CG() {
        return new RuntimeException();
    }
    @ApiDavAttributeMapping(path=MAPPING_CH, attribute=ApiDavMappingAttribute.Permitted)
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

    @ApiDavMapping(path=MAPPING_D1, isPermitted=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D1X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setPermitted(false);
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_D1() {
        return Boolean.FALSE;
    }

    @ApiDavMapping(path=MAPPING_D2, isPermitted=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D2X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setPermitted(false);
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_D2() {
        return true;
    }

    @ApiDavMapping(path=MAPPING_D3, isPermitted=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="false")
    })
    void test_D3X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setPermitted(true);
    }

    @ApiDavMapping(path=MAPPING_D4, isPermitted=false, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="true")
    })
    void test_D4X() {
    }

    @ApiDavMapping(path=MAPPING_D5, isPermitted=true)
    void test_D5X() {
    }

    @ApiDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }

    public static final String MAPPING_DA = "/extras/permitted/dA.txt";
    public static final String MAPPING_DB = "/extras/permitted/dB.txt";
    public static final String MAPPING_DC = "/extras/permitted/dC.txt";
    public static final String MAPPING_DD = "/extras/permitted/dD.txt";
    public static final String MAPPING_DE = "/extras/permitted/dE.txt";
    public static final String MAPPING_DF = "/extras/permitted/dF.txt";

    @ApiDavMapping(path=MAPPING_DA, isPermitted=true, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DAX() {
    }
    @ApiDavMetaMapping(path=MAPPING_DA)
    void test_DA(final MetaData metaData) {
        metaData.setPermitted(true);
    }
    @ApiDavAttributeMapping(path=MAPPING_DA, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_DA() {
        return Boolean.TRUE;
    }

    @ApiDavMapping(path=MAPPING_DB, isPermitted=true, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DBX() {
    }
    @ApiDavMetaMapping(path=MAPPING_DB)
    void test_DB(final MetaData metaData) {
        metaData.setPermitted(true);
    }
    @ApiDavAttributeMapping(path=MAPPING_DB, attribute=ApiDavMappingAttribute.Permitted)
    Boolean test_DB() {
        return false;
    }

    @ApiDavMapping(path=MAPPING_DC, isPermitted=true, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="true")
    })
    void test_DCX() {
    }
    @ApiDavMetaMapping(path=MAPPING_DC)
    void test_DC(final MetaData metaData) {
        metaData.setPermitted(false);
    }

    @ApiDavMapping(path=MAPPING_DD, isPermitted=true, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="false")
    })
    void test_DDX() {
    }

    @ApiDavMapping(path=MAPPING_DE, isPermitted=false)
    void test_DEX() {
    }

    @ApiDavMapping(path=MAPPING_DF)
    void test_DFX() {
    }
}