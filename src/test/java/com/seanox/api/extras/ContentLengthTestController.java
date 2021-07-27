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
import com.seanox.webdav.MetaOutputStream;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Test the function of the ContentLength attribute for
 * {@link WebDavMapping}.<br>
 * <br>
 * ContentLengthTestController 1.0.0 20210726<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
@SuppressWarnings("boxing")
public class ContentLengthTestController {

    // Test A for attributes + Variants of values (valid + invalid)
    // Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link WebDavMapping} + contentLength

    public static final String MAPPING_A1 = "/extras/contentLength/a1.txt";
    public static final String MAPPING_A2 = "/extras/contentLength/a2.txt";
    public static final String MAPPING_A3 = "/extras/contentLength/a3.txt";
    public static final String MAPPING_A4 = "/extras/contentLength/a4.txt";
    public static final String MAPPING_A5 = "/extras/contentLength/a5.txt";

    @WebDavMapping(path=MAPPING_A1, contentLength=-10)
    @WebDavMapping(path=MAPPING_A2, contentLength=-1)
    @WebDavMapping(path=MAPPING_A3, contentLength=0)
    @WebDavMapping(path=MAPPING_A4, contentLength=1)
    @WebDavMapping(path=MAPPING_A5, contentLength=10)
    void test_AX() {
    }

    // Test of {@link WebDavMapping}
    //     + {@link WebDavMappingAttributeExpression}
    //     + {@link WebDavMappingAttribute.ContentLength}

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

    @WebDavMapping(path=MAPPING_B1, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(110)")
    })
    @WebDavMapping(path=MAPPING_B2, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(120).intValue()")
    })
    @WebDavMapping(path=MAPPING_B3, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="'130'")
    })
    @WebDavMapping(path=MAPPING_B4, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="'-140'")
    })
    @WebDavMapping(path=MAPPING_B5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="'wrong text value'")
    })
    @WebDavMapping(path=MAPPING_B6, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="null")
    })
    @WebDavMapping(path=MAPPING_B7, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(0)")
    })
    @WebDavMapping(path=MAPPING_B8, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(-1)")
    })
    @WebDavMapping(path=MAPPING_B9, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="T(java.lang.Integer).valueOf(-100)")
    })
    @WebDavMapping(path=MAPPING_BA, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="")
    })
    @WebDavMapping(path=MAPPING_BB, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="''")
    })
    @WebDavMapping(path=MAPPING_BC, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="' '")
    })
    @WebDavMapping(path=MAPPING_BD, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="expression exception")
    })
    @WebDavMapping(path=MAPPING_BE, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="-10")
    })
    @WebDavMapping(path=MAPPING_BF, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="-1")
    })
    @WebDavMapping(path=MAPPING_BG, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="0")
    })
    @WebDavMapping(path=MAPPING_BH, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="1")
    })
    @WebDavMapping(path=MAPPING_BI, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="10")
    })
    void test_BX() {
    }

    // Test of {@link WebDavAttribute}

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

    @WebDavMapping(path=MAPPING_C1)
    @WebDavMapping(path=MAPPING_C2)
    @WebDavMapping(path=MAPPING_C3)
    @WebDavMapping(path=MAPPING_C4)
    @WebDavMapping(path=MAPPING_C5)
    @WebDavMapping(path=MAPPING_C6)
    @WebDavMapping(path=MAPPING_C7)
    @WebDavMapping(path=MAPPING_C8)
    @WebDavMapping(path=MAPPING_C9)
    @WebDavMapping(path=MAPPING_CA)
    @WebDavMapping(path=MAPPING_CB)
    @WebDavMapping(path=MAPPING_CC)
    @WebDavMapping(path=MAPPING_CD)
    @WebDavMapping(path=MAPPING_CE)
    @WebDavMapping(path=MAPPING_CF)
    void test_CX() {
    }
    @WebDavAttributeMapping(path=MAPPING_C1, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_C1() {
        return 110;
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute=WebDavMappingAttribute.ContentLength)
    int test_C2() {
        return 120;
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute=WebDavMappingAttribute.ContentLength)
    String test_C3() {
        return "130";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute=WebDavMappingAttribute.ContentLength)
    String test_C4() {
        return "-140";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute=WebDavMappingAttribute.ContentLength)
    String test_C5() {
        return "wrong text value";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute=WebDavMappingAttribute.ContentLength)
    Object test_C6() {
        return 160;
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute=WebDavMappingAttribute.ContentLength)
    Object test_C7() {
        return "170";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_C8() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_C9() {
        return 0;
    }
    @WebDavAttributeMapping(path=MAPPING_CA, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_CA() {
        return -1;
    }
    @WebDavAttributeMapping(path=MAPPING_CB, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_CB() {
        return -100;
    }
    @WebDavAttributeMapping(path=MAPPING_CC, attribute=WebDavMappingAttribute.ContentLength)
    Exception test_CC() {
        return new Exception("Test CC");
    }
    @WebDavAttributeMapping(path=MAPPING_CD, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_CD() {
        throw new RuntimeException("Test CD");
    }
    @WebDavAttributeMapping(path=MAPPING_CE, attribute=WebDavMappingAttribute.ContentLength)
    String test_CE() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_CF, attribute=WebDavMappingAttribute.ContentLength)
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

    @WebDavMapping(path=MAPPING_D1, contentLength=5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D1X(final MetaOutputStream outputStream) {
        outputStream.setContentLength(9);
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setContentLength(7);
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_D1() {
        return 8;
    }

    @WebDavMapping(path=MAPPING_D2, contentLength=5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setContentLength(7);
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.ContentLength)
    Integer test_D2() {
        return 8;
    }

    @WebDavMapping(path=MAPPING_D3, contentLength=5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setContentLength(7);
    }

    @WebDavMapping(path=MAPPING_D4, contentLength=5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentLength, phrase="6")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, contentLength=5)
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}