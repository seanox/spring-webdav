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
 * FinancialController 1.1.0 20210810<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210810
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
    void financialCostsGet(final MetaOutputStream output) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        output.write(financialCosts.getData());
    }
    @WebDavInputMapping(path=FINANCIAL_COSTS_XLSX)
    void financialCostsPut(final MetaInputStream input) throws IOException {
        final FinancialCosts financialCosts = this.financialService.readFinancialCosts();
        financialCosts.setData(input.readAllBytes());
        this.financialService.saveFinancialCosts(financialCosts);
    }
    @WebDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=WebDavMappingAttribute.ContentLength)
    Integer financialCostsContentLength() {
        return Integer.valueOf(this.financialService.readFinancialCosts().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_COSTS_XLSX, attribute=WebDavMappingAttribute.LastModified)
    Date financialCostsLastModified() {
        return this.financialService.readFinancialCosts().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_STATISTIC_PPTX="/financial/reports/statistic.pptx";
    @WebDavMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, readOnly=false)
    void financialReportStatisticGet(final MetaOutputStream output) throws IOException  {
        final FinancialReportStatistic financialReportStatistic = this.financialService.readFinancialReportStatistic();
        output.write(financialReportStatistic.getData());
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=WebDavMappingAttribute.ContentLength)
    Integer financialReportStatisticLength() {
        return Integer.valueOf(this.financialService.readFinancialReportStatistic().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_STATISTIC_PPTX, attribute=WebDavMappingAttribute.LastModified)
    Date financialReportStatisticLastModified() {
        return this.financialService.readFinancialReportStatistic().getLastModified();
    }





    private static final String FINANCIAL_REPORTS_SALES_PPTX="/financial/reports/sales.pptx";
    @WebDavMapping(path=FINANCIAL_REPORTS_SALES_PPTX)
    void financialReportSalesGet(final MetaOutputStream output) throws IOException  {
        final FinancialReportSales financialReportSales = this.financialService.readFinancialReportSales();
        output.write(financialReportSales.getData());
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=WebDavMappingAttribute.ContentLength)
    Integer financialReportSalesContentLength() {
        return Integer.valueOf(this.financialService.readFinancialReportSales().getData().length);
    }
    @WebDavAttributeMapping(path=FINANCIAL_REPORTS_SALES_PPTX, attribute=WebDavMappingAttribute.LastModified)
    Date financialReportSalesLastModified() {
        return this.financialService.readFinancialReportSales().getLastModified();
    }
}