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
import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavInputMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * Example for the integration of apiDAV into a RestController.<br>
 * <br>
 * In general, a managed bean is required.<br>
 * There are various annotations for this:<br>
 *     e.g. @Component, @Service, @RestController, ...<br>
 * The methods and annotations for apiDAV combine well with @RestController.<br>
 * <br>
 * FinancialController 1.0.0 20210719<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210719
 */
@RequiredArgsConstructor
@RestController
class FinancialController {

    private final FinancialService financialService;

    // The method signatures are not fixed.
    // The arguments and their data type are to be understood as placeholders
    // that are set by the apiDAV filter when called. For unknown arguments or
    // data types null is used.

    // The method signatures can be composed as follows, the order of the
    // arguments is unimportant, all arguments are optional and further
    // arguments can be used and are filled with null:
    //     GET-Method Callback:
    // Return: void, Arguments: Properties, MetaOutputStream
    //     PUT-Method Callback:
    // Return: void, Arguments: Properties, MetaInputStream
    //     Meta-Method Callback:
    // Return: Meta, Arguments: Properties
    //     Attribute Callback:
    // Return: Object, Arguments: Properties

    // The declaration of the ContentType is optional.
    // In many cases this can be derived from the file name from the mapping.

    private static final String FINANCIAL_COSTS_XLSX ="/financial/costs.xlsx";
    @ApiDavMapping(path=FINANCIAL_COSTS_XLSX, isReadOnly=false)
    void getFinancialCosts(final MetaOutputStream output) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        output.write(financialCosts.getData());
    }
    @ApiDavInputMapping(path=FINANCIAL_COSTS_XLSX)
    void putFinancialCosts(final MetaInputStream input) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        financialCosts.setData(input.readAllBytes());
        this.financialService.saveFinancialCosts(financialCosts);
    }
    @ApiDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=ApiDavMappingAttribute.ContentLength)
    Integer getFinancialCostsContentLength() {
        return Integer.valueOf(this.financialService.readFinancialCosts().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinancialCostsLastModified() {
        return this.financialService.readFinancialCosts().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_STATISTIC_PPTX="/financial/reports/statistic.pptx";
    @ApiDavMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, isReadOnly=false)
    void getFinancialReportStatistic(final MetaOutputStream output) throws IOException  {
        final FinancialReportStatistic financialReportStatistic = this.financialService.readFinancialReportStatistic();
        output.write(financialReportStatistic.getData());
    }
    @ApiDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=ApiDavMappingAttribute.ContentLength)
    Integer getFinancialReportStatisticLength() {
        return Integer.valueOf(this.financialService.readFinancialReportStatistic().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinancialReportStatisticLastModified() {
        return this.financialService.readFinancialReportStatistic().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_SALES_PPTX="/financial/reports/sales.pptx";
    @ApiDavMapping(path=FINANCIAL_REPORTS_SALES_PPTX)
    void getFinancialReportSales(final MetaOutputStream output) throws IOException  {
        final FinancialReportSales financialReportSales = this.financialService.readFinancialReportSales();
        output.write(financialReportSales.getData());
    }
    @ApiDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=ApiDavMappingAttribute.ContentLength)
    Integer getFinancialReportSalesLength() {
        return Integer.valueOf(this.financialService.readFinancialReportSales().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinancialReportSalesLastModified() {
        return this.financialService.readFinancialReportSales().getLastModified();
    }
}