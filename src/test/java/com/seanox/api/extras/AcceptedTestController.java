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
package com.seanox.api.extras;

import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.WebDavMetaMapping;
import com.seanox.webdav.MetaData;
import com.seanox.webdav.WebDavInputMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Test the function of the accepted attribute for
 * {@link WebDavMapping} and {@link WebDavInputMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class AcceptedTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

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

    @WebDavMapping(path=MAPPING_C1, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C2, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C3, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C4, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C5, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C6, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C7, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C8, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_C9, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CA, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CB, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CC, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CD, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CE, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CF, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CG, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    @WebDavMapping(path=MAPPING_CH, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")})
    void test_CX() {
    }
    @WebDavAttributeMapping(path=MAPPING_C1, attribute=WebDavMappingAttribute.Accepted)
    void test_C1() {
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute=WebDavMappingAttribute.Accepted)
    String test_C2() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute=WebDavMappingAttribute.Accepted)
    String test_C3() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute=WebDavMappingAttribute.Accepted)
    String test_C4() {
        return " ";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute=WebDavMappingAttribute.Accepted)
    String test_C5() {
        return "True";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute=WebDavMappingAttribute.Accepted)
    String test_C6() {
        return "true";
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute=WebDavMappingAttribute.Accepted)
    String test_C7() {
        return "True";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute=WebDavMappingAttribute.Accepted)
    String test_C8() {
        return " true";
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute=WebDavMappingAttribute.Accepted)
    String test_C9() {
        return "false";
    }
    @WebDavAttributeMapping(path=MAPPING_CA, attribute=WebDavMappingAttribute.Accepted)
    boolean test_CA() {
        return true;
    }
    @WebDavAttributeMapping(path=MAPPING_CB, attribute=WebDavMappingAttribute.Accepted)
    boolean test_CB() {
        return false;
    }
    @WebDavAttributeMapping(path=MAPPING_CC, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_CC() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_CD, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_CD() {
        return Boolean.TRUE;
    }
    @WebDavAttributeMapping(path=MAPPING_CE, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_CE() {
        return Boolean.FALSE;
    }
    @WebDavAttributeMapping(path=MAPPING_CF, attribute=WebDavMappingAttribute.Accepted)
    Object test_CF() {
        return Boolean.TRUE;
    }
    @WebDavAttributeMapping(path=MAPPING_CG, attribute=WebDavMappingAttribute.Accepted)
    Exception test_CG() {
        return new RuntimeException();
    }
    @WebDavAttributeMapping(path=MAPPING_CH, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_CH() {
        throw new RuntimeException("Test CH");
    }

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/accepted/d1.txt";
    public static final String MAPPING_D2 = "/extras/accepted/d2.txt";
    public static final String MAPPING_D3 = "/extras/accepted/d3.txt";
    public static final String MAPPING_D4 = "/extras/accepted/d4.txt";
    public static final String MAPPING_D5 = "/extras/accepted/d5.txt";
    public static final String MAPPING_D6 = "/extras/accepted/d6.txt";

    @WebDavMapping(path=MAPPING_D1, accepted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="false")
    })
    void test_D1X() {
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setAccepted(false);
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_D1() {
        return Boolean.FALSE;
    }

    @WebDavMapping(path=MAPPING_D2, accepted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="false")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setAccepted(false);
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_D2() {
        return true;
    }

    @WebDavMapping(path=MAPPING_D3, accepted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="false")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setAccepted(true);
    }

    @WebDavMapping(path=MAPPING_D4, accepted=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="true")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, accepted=true)
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }

    public static final String MAPPING_DA = "/extras/accepted/dA.txt";
    public static final String MAPPING_DB = "/extras/accepted/dB.txt";
    public static final String MAPPING_DC = "/extras/accepted/dC.txt";
    public static final String MAPPING_DD = "/extras/accepted/dD.txt";
    public static final String MAPPING_DE = "/extras/accepted/dE.txt";
    public static final String MAPPING_DF = "/extras/accepted/dF.txt";

    @WebDavMapping(path=MAPPING_DA, accepted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="true")
    })
    void test_DAX() {
    }
    @WebDavMetaMapping(path=MAPPING_DA)
    void test_DA(final MetaData metaData) {
        metaData.setAccepted(true);
    }
    @WebDavAttributeMapping(path=MAPPING_DA, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_DA() {
        return Boolean.TRUE;
    }

    @WebDavMapping(path=MAPPING_DB, accepted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="true")
    })
    void test_DBX() {
    }
    @WebDavMetaMapping(path=MAPPING_DB)
    void test_DB(final MetaData metaData) {
        metaData.setAccepted(true);
    }
    @WebDavAttributeMapping(path=MAPPING_DB, attribute=WebDavMappingAttribute.Accepted)
    Boolean test_DB() {
        return false;
    }

    @WebDavMapping(path=MAPPING_DC, accepted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="true")
    })
    void test_DCX() {
    }
    @WebDavMetaMapping(path=MAPPING_DC)
    void test_DC(final MetaData metaData) {
        metaData.setAccepted(false);
    }

    @WebDavMapping(path=MAPPING_DD, accepted=true, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Accepted, phrase="false")
    })
    void test_DDX() {
    }

    @WebDavMapping(path=MAPPING_DE, accepted=false)
    void test_DEX() {
    }

    @WebDavMapping(path=MAPPING_DF)
    void test_DFX() {
    }
}