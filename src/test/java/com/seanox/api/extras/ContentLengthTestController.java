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
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.MetaData;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.stereotype.Component;

/**
 * Test of the annotation {@link ApiDavAttributeMapping}
 *     + {@link ApiDavMappingAttribute#ContentLength} functions.
 *
 * ContentLengthTestController 1.0.0 20210720
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210720
 */
@Component
@SuppressWarnings("boxing")
public class ContentLengthTestController {

    // Test A for attributes + Variants of values (valid + invalid)
    // Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link ApiDavMapping} + contentLength

    public static final String MAPPING_A1 = "/extras/contentLength/a1.txt";
    public static final String MAPPING_A2 = "/extras/contentLength/a2.txt";
    public static final String MAPPING_A3 = "/extras/contentLength/a3.txt";
    public static final String MAPPING_A4 = "/extras/contentLength/a4.txt";
    public static final String MAPPING_A5 = "/extras/contentLength/a5.txt";

    @ApiDavMapping(path=MAPPING_A1, contentLength=-10)
    @ApiDavMapping(path=MAPPING_A2, contentLength=-1)
    @ApiDavMapping(path=MAPPING_A3, contentLength=0)
    @ApiDavMapping(path=MAPPING_A4, contentLength=1)
    @ApiDavMapping(path=MAPPING_A5, contentLength=10)
    void test_AX() {
    }

    // Test of {@link ApiDavMapping}
    //     + {@link ApiDavMappingAttributeExpression}
    //     + {@link ApiDavMappingAttribute.ContentLength}

    public static final String MAPPING_B1 = "/extras/contentLength/b1.txt";
    public static final String MAPPING_B2 = "/extras/contentLength/b2.txt";
    public static final String MAPPING_B3 = "/extras/contentLength/b3.txt";
    public static final String MAPPING_B4 = "/extras/contentLength/b4.txt";
    public static final String MAPPING_B5 = "/extras/contentLength/b5.txt";
    public static final String MAPPING_B6 = "/extras/contentLength/b6.txt";
    public static final String MAPPING_B7 = "/extras/contentLength/b7.txt";
    public static final String MAPPING_B8 = "/extras/contentLength/b8.txt";
    public static final String MAPPING_B9 = "/extras/contentLength/b9.txt";
    public static final String MAPPING_BA = "/extras/contentLength/bA.txt";
    public static final String MAPPING_BB = "/extras/contentLength/bB.txt";
    public static final String MAPPING_BC = "/extras/contentLength/bC.txt";
    public static final String MAPPING_BD = "/extras/contentLength/bD.txt";
    public static final String MAPPING_BE = "/extras/contentLength/bE.txt";
    public static final String MAPPING_BF = "/extras/contentLength/bF.txt";
    public static final String MAPPING_BG = "/extras/contentLength/bG.txt";
    public static final String MAPPING_BH = "/extras/contentLength/bH.txt";
    public static final String MAPPING_BI = "/extras/contentLength/bI.txt";

    @ApiDavMapping(path=MAPPING_B1, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(110)")
    })
    @ApiDavMapping(path=MAPPING_B2, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(120).intValue()")
    })
    @ApiDavMapping(path=MAPPING_B3, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="'130'")
    })
    @ApiDavMapping(path=MAPPING_B4, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="'-140'")
    })
    @ApiDavMapping(path=MAPPING_B5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="'wrong text value'")
    })
    @ApiDavMapping(path=MAPPING_B6, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="null")
    })
    @ApiDavMapping(path=MAPPING_B7, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(0)")
    })
    @ApiDavMapping(path=MAPPING_B8, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(-1)")
    })
    @ApiDavMapping(path=MAPPING_B9, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(-100)")
    })
    @ApiDavMapping(path=MAPPING_BA, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="")
    })
    @ApiDavMapping(path=MAPPING_BB, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="''")
    })
    @ApiDavMapping(path=MAPPING_BC, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="' '")
    })
    @ApiDavMapping(path=MAPPING_BD, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="expression exception")
    })
    @ApiDavMapping(path=MAPPING_BE, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="-10")
    })
    @ApiDavMapping(path=MAPPING_BF, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="-1")
    })
    @ApiDavMapping(path=MAPPING_BG, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="0")
    })
    @ApiDavMapping(path=MAPPING_BH, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="1")
    })
    @ApiDavMapping(path=MAPPING_BI, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="10")
    })
    void test_BX() {
    }

    // Test of {@link ApiDavAttribute}

    public static final String MAPPING_C1 = "/extras/contentLength/c1.txt";
    public static final String MAPPING_C2 = "/extras/contentLength/c2.txt";
    public static final String MAPPING_C3 = "/extras/contentLength/c3.txt";
    public static final String MAPPING_C4 = "/extras/contentLength/c4.txt";
    public static final String MAPPING_C5 = "/extras/contentLength/c5.txt";
    public static final String MAPPING_C6 = "/extras/contentLength/c6.txt";
    public static final String MAPPING_C7 = "/extras/contentLength/c7.txt";
    public static final String MAPPING_C8 = "/extras/contentLength/c8.txt";
    public static final String MAPPING_C9 = "/extras/contentLength/c9.txt";
    public static final String MAPPING_CA = "/extras/contentLength/cA.txt";
    public static final String MAPPING_CB = "/extras/contentLength/cB.txt";
    public static final String MAPPING_CC = "/extras/contentLength/cC.txt";
    public static final String MAPPING_CD = "/extras/contentLength/cD.txt";
    public static final String MAPPING_CE = "/extras/contentLength/cE.txt";
    public static final String MAPPING_CF = "/extras/contentLength/cF.txt";

    @ApiDavMapping(path=MAPPING_C1)
    @ApiDavMapping(path=MAPPING_C2)
    @ApiDavMapping(path=MAPPING_C3)
    @ApiDavMapping(path=MAPPING_C4)
    @ApiDavMapping(path=MAPPING_C5)
    @ApiDavMapping(path=MAPPING_C6)
    @ApiDavMapping(path=MAPPING_C7)
    @ApiDavMapping(path=MAPPING_C8)
    @ApiDavMapping(path=MAPPING_C9)
    @ApiDavMapping(path=MAPPING_CA)
    @ApiDavMapping(path=MAPPING_CB)
    @ApiDavMapping(path=MAPPING_CC)
    @ApiDavMapping(path=MAPPING_CD)
    @ApiDavMapping(path=MAPPING_CE)
    @ApiDavMapping(path=MAPPING_CF)
    void test_CX() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_C1() {
        return 110;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.ContentLength)
    int test_C2() {
        return 120;
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C3() {
        return "130";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C4() {
        return "-140";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C5() {
        return "wrong text value";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.ContentLength)
    Object test_C6() {
        return 160;
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.ContentLength)
    Object test_C7() {
        return "170";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_C8() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_C9() {
        return 0;
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_CA() {
        return -1;
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_CB() {
        return -100;
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.ContentLength)
    Exception test_CC() {
        return new Exception("Test CC");
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_CD() {
        throw new RuntimeException("Test CD");
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.ContentLength)
    String test_CE() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.ContentLength)
    String test_CF() {
        return " ";
    }

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/contentLength/d1.txt";
    public static final String MAPPING_D2 = "/extras/contentLength/d2.txt";
    public static final String MAPPING_D3 = "/extras/contentLength/d3.txt";
    public static final String MAPPING_D4 = "/extras/contentLength/d4.txt";
    public static final String MAPPING_D5 = "/extras/contentLength/d5.txt";
    public static final String MAPPING_D6 = "/extras/contentLength/d6.txt";

    @ApiDavMapping(path=MAPPING_D1, contentLength=5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D1X(final MetaOutputStream outputStream) {
        outputStream.setContentLength(9);
    }
    @ApiDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setContentLength(7);
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_D1() {
        return 8;
    }

    @ApiDavMapping(path=MAPPING_D2, contentLength=5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D2X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setContentLength(7);
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.ContentLength)
    Integer test_D2() {
        return 8;
    }

    @ApiDavMapping(path=MAPPING_D3, contentLength=5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D3X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setContentLength(7);
    }

    @ApiDavMapping(path=MAPPING_D4, contentLength=5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D4X() {
    }

    @ApiDavMapping(path=MAPPING_D5, contentLength=5)
    void test_D5X() {
    }

    @ApiDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}