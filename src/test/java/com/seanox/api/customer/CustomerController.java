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
package com.seanox.api.customer;

import com.seanox.apidav.ApiDavInput;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

// A managed bean is required.
// There are various annotations for this: e.g. @Component, @Service, @RestController, ...
// The methods and annotations for apiDAV combine well with @RestController.
@RestController
public class CustomerController {

    private byte[] customerList;
    private static final String CUSTOMER_LIST_XLSX = "/customer/list.xlsx";
    private static final String CUSTOMER_LIST_XLSX_TEMP = "/customer/~$list.xlsx";
    @ApiDavMapping(path=CUSTOMER_LIST_XLSX, isReadOnly=false)
    @ApiDavMapping(path=CUSTOMER_LIST_XLSX_TEMP, isReadOnly=false, isHidden=true)
    public void list(MetaOutputStream output) throws IOException {
        if (Objects.isNull(this.customerList))
            this.customerList = CustomerController.class.getResourceAsStream("/templates/empty.xlsx").readAllBytes();
        output.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        output.setContentLength((long)this.customerList.length);
        output.write(this.customerList);
    }
    @ApiDavInput(path=CUSTOMER_LIST_XLSX)
    @ApiDavInput(path=CUSTOMER_LIST_XLSX_TEMP)
    public void list(MetaInputStream output) throws IOException {
        final byte[] bytes = output.readAllBytes();
        this.customerList = bytes;
    }

    private static final String CUSTOMER_REPORTS_STATISTIC_PPTX = "/customer/reports/statistic.pptx";
    @ApiDavMapping(path=CUSTOMER_REPORTS_STATISTIC_PPTX)
    public void statistic(MetaOutputStream output) throws IOException  {
        final byte[] data = this.customerList = CustomerController.class.getResourceAsStream("/templates/empty.pptx").readAllBytes();
        output.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        output.setContentLength((long)data.length);
        output.write(data);
    }

    // TODO: final idea for the annotations (outsourcing) e.g. ApiDavMappingAttributeExpression + ApiDavMappingAttribute
    // private static final String CUSTOMER_REPORTS_STATISTIC_PPTX = "/customer/reports/statistic.pptx";
    // @ApiDavMapping(path=CUSTOMER_REPORTS_STATISTIC_PPTX, attributeExpressions={
    //         @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="#{'TODO:'}")
    // })
    // public void statistic(MetaOutputStream output) {
    // }

    private static final String CUSTOMER_REPORTS_TURNOVER_PPTX = "/customer/reports/turnover.pptx";
    @ApiDavMapping(path=CUSTOMER_REPORTS_TURNOVER_PPTX)
    public void turnover(MetaOutputStream output) throws IOException {
        final byte[] data = this.customerList = CustomerController.class.getResourceAsStream("/templates/empty.pptx").readAllBytes();
        output.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
        output.setContentLength((long)data.length);
        output.write(data);
    }

    private byte[] newsletter;
    private static final String MARKETING_NEWSLETTER_PUB = "/marketing/newsletter.pub";
    private static final String MARKETING_NEWSLETTER_PUB_TEMP = "/marketing/~$newsletter.pub";
    @ApiDavMapping(path=MARKETING_NEWSLETTER_PUB, isReadOnly=false)
    @ApiDavMapping(path=MARKETING_NEWSLETTER_PUB_TEMP, isReadOnly=false, isHidden=true)
    public void newsletter(MetaOutputStream output) throws IOException {
        if (Objects.isNull(this.customerList))
            this.newsletter = CustomerController.class.getResourceAsStream("/templates/empty.pub").readAllBytes();
        output.setContentType("application/x-mspublisher");
        output.setContentLength((long)this.customerList.length);
        output.write(this.newsletter);
    }
    @ApiDavInput(path=MARKETING_NEWSLETTER_PUB)
    @ApiDavInput(path=MARKETING_NEWSLETTER_PUB_TEMP)
    public void newsletter(MetaInputStream output)throws IOException {
        final byte[] bytes = output.readAllBytes();
        this.customerList = bytes;
    }

    private byte[] sales;
    private static final String MARKETING_SALES_PUB = "/marketing/sales.pub";
    private static final String MARKETING_SALES_PUB_TEMP = "/marketing/~$sales.pub";
    @ApiDavMapping(path=MARKETING_SALES_PUB, isReadOnly=false)
    @ApiDavMapping(path=MARKETING_SALES_PUB_TEMP, isReadOnly=false, isHidden=true)
    public void sales(MetaOutputStream output) throws IOException {
        if (Objects.isNull(this.customerList))
            this.newsletter = CustomerController.class.getResourceAsStream("/templates/empty.pub").readAllBytes();
        output.setContentType("application/x-mspublisher");
        output.setContentLength((long)this.customerList.length);
        output.write(this.newsletter);
    }
    @ApiDavInput(path=MARKETING_SALES_PUB)
    @ApiDavInput(path=MARKETING_SALES_PUB_TEMP)
    public void sales(MetaInputStream output)throws IOException {
        final byte[] bytes = output.readAllBytes();
        this.customerList = bytes;
    }
}