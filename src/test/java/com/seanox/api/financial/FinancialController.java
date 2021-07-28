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
package com.seanox.api.financial;

import com.seanox.api.financial.data.FinancialCosts;
import com.seanox.api.financial.data.FinancialReportSales;
import com.seanox.api.financial.data.FinancialReportStatistic;
import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.MetaInputStream;
import com.seanox.webdav.MetaOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * Example for the integration of webDAV into a RestController.<br>
 * <br>
 * In general, a managed bean is required.<br>
 * There are various annotations for this:<br>
 *     e.g. @Component, @Service, @RestController, ...<br>
 * The methods and annotations for webDAV combine well with @RestController.<br>
 * <br>
 * FinancialController 1.0.0 20210726<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile({"test", "demo"})
@RequiredArgsConstructor
@RestController
class FinancialController {

    private final FinancialService financialService;

    // The method signatures are not fixed.
    // The arguments and their data type are to be understood as placeholders
    // that are set by the webDAV filter when called. For unknown arguments or
    // data types null is used.

    // The method signatures can be composed as follows, the order of the
    // arguments is unimportant, all arguments are optional and further
    // arguments can be used and are filled with null:
    //
    //     GET-Method Callback:
    // Return: void
    // Arguments: URI, Properties, MetaProperties, MetaOutputStream
    //
    //     PUT-Method Callback:
    // Return: void
    // Arguments: URI, Properties, MetaProperties, MetaInputStream
    //
    //     Meta-Method Callback:
    // Return: void
    // Arguments: URI, Properties, MetaData
    //
    //     Attribute Callback:
    // Return: Object
    // Arguments: URI, Properties

    // The declaration of the ContentType is optional.
    // In many cases this can be derived from the file name from the mapping.

    private static final String FINANCIAL_COSTS_XLSX ="/financial/costs.xlsx";
    @WebDavMapping(path=FINANCIAL_COSTS_XLSX, readOnly=false)
    void getFinancialCosts(final MetaOutputStream output) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        output.write(financialCosts.getData());
    }
    @WebDavInputMapping(path=FINANCIAL_COSTS_XLSX)
    void putFinancialCosts(final MetaInputStream input) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        financialCosts.setData(input.readAllBytes());
        this.financialService.saveFinancialCosts(financialCosts);
    }
    @WebDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=WebDavMappingAttribute.ContentLength)
    Integer getFinancialCostsContentLength() {
        return Integer.valueOf(this.financialService.readFinancialCosts().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=WebDavMappingAttribute.LastModified)
    Date getFinancialCostsLastModified() {
        return this.financialService.readFinancialCosts().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_STATISTIC_PPTX="/financial/reports/statistic.pptx";
    @WebDavMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, readOnly=false)
    void getFinancialReportStatistic(final MetaOutputStream output) throws IOException  {
        final FinancialReportStatistic financialReportStatistic = this.financialService.readFinancialReportStatistic();
        output.write(financialReportStatistic.getData());
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=WebDavMappingAttribute.ContentLength)
    Integer getFinancialReportStatisticLength() {
        return Integer.valueOf(this.financialService.readFinancialReportStatistic().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=WebDavMappingAttribute.LastModified)
    Date getFinancialReportStatisticLastModified() {
        return this.financialService.readFinancialReportStatistic().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_SALES_PPTX="/financial/reports/sales.pptx";
    @WebDavMapping(path=FINANCIAL_REPORTS_SALES_PPTX)
    void getFinancialReportSales(final MetaOutputStream output) throws IOException  {
        final FinancialReportSales financialReportSales = this.financialService.readFinancialReportSales();
        output.write(financialReportSales.getData());
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=WebDavMappingAttribute.ContentLength)
    Integer getFinancialReportSalesLength() {
        return Integer.valueOf(this.financialService.readFinancialReportSales().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=WebDavMappingAttribute.LastModified)
    Date getFinancialReportSalesLastModified() {
        return this.financialService.readFinancialReportSales().getLastModified();
    }
}