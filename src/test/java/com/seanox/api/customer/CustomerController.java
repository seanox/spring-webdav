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

import com.seanox.apidav.ApiDavMapping;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

import static com.seanox.apidav.ApiDavMapping.Type.GET;
import static com.seanox.apidav.ApiDavMapping.Type.PUT;

// A managed bean is required.
// There are various annotations for this: e.g. @Component, @Service, @RestController, ...
@Component
public class CustomerController {

    @ApiDavMapping(type=GET, path="/customer/list.xlsx")
    public void getList(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/customer/list.xlsx")
    public void putList(InputStream output) {
    }

    @ApiDavMapping(type=GET, path="/customer/reports/statistic.xlsx")
    public void statistic(OutputStream output) {
    }

    @ApiDavMapping(type=GET, path="/customer/reports/turnover.xlsx")
    public void turnover(OutputStream output) {
    }

    @ApiDavMapping(type=GET, path="/marketing/newsletter.pptx")
    public void getNewsletter(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/marketing/newsletter.pptx")
    public void putNewsletter(InputStream output) {
    }

    @ApiDavMapping(type=GET, path="/marketing/sales.pptx")
    public void getSales(OutputStream output) {
    }
    @ApiDavMapping(type=PUT, path="/marketing/sales.pptx")
    public void putSales(InputStream output) {
    }
}