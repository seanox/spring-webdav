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
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

// A managed bean is required.
// There are various annotations for this: e.g. @Component, @Service, @RestController, ...
// The methods and annotations for apiDAV combine well with @RestController.
@Component
public class CustomerController {

    private static final String CUSTOMER_LIST_XLSX = "/customer/list.xlsx";
    @ApiDavMapping(path=CUSTOMER_LIST_XLSX)
    public void list(OutputStream output) {
    }
    @ApiDavInput(path="/customer/list.xlsx")
    public void list(InputStream output) {
    }

    private static final String CUSTOMER_REPORTS_STATISTIC_XLSX = "/customer/reports/statistic.xlsx";
    @ApiDavMapping(path=CUSTOMER_REPORTS_STATISTIC_XLSX)
    public void statistic(OutputStream output) {
    }

    private static final String CUSTOMER_REPORTS_TURNOVER_XLSX = "/customer/reports/turnover.xlsx";
    @ApiDavMapping(path=CUSTOMER_REPORTS_TURNOVER_XLSX)
    public void turnover(OutputStream output) {
    }

    private static final String MARKETING_NEWSLETTER_PPTX = "/marketing/newsletter.pptx";
    @ApiDavMapping(path=MARKETING_NEWSLETTER_PPTX)
    public void newsletter(OutputStream output) {
    }
    @ApiDavInput(path=MARKETING_NEWSLETTER_PPTX)
    public void newsletter(InputStream output) {
    }

    private static final String MARKETING_SALES_PPTX = "/marketing/sales.pptx";
    @ApiDavMapping(path=MARKETING_SALES_PPTX)
    public void sales(OutputStream output) {
    }
    @ApiDavInput(path=MARKETING_SALES_PPTX)
    public void sales(InputStream output) {
    }
}