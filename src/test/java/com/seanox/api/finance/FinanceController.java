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
package com.seanox.api.finance;

import com.seanox.api.finance.data.FinanceBudget;
import com.seanox.api.finance.data.FinanceReportSales;
import com.seanox.api.finance.data.FinanceReportStatistic;
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
 * Example for the integration of apiDAV into a RestController.
 *
 * In general, a managed bean is required.
 * There are various annotations for this: e.g. @Component, @Service, @RestController, ...
 * The methods and annotations for apiDAV combine well with @RestController.
 *
 * FinanceController 1.0.0 20210703
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210703
 */
@RequiredArgsConstructor
@RestController
class FinanceController {

    private final FinanceService financeService;

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

    private static final String FINANCE_BUDGET_XLSX="/finance/budget.xlsx";
    @ApiDavMapping(path=FINANCE_BUDGET_XLSX, isReadOnly=false)
    void getFinanceBudget(MetaOutputStream output) throws IOException {
        final FinanceBudget financeBudget = this.financeService.readFinanceBudget();
        output.write(financeBudget.getData());
    }
    @ApiDavInputMapping(path=FINANCE_BUDGET_XLSX)
    void putFinanceBudget(MetaInputStream input) throws IOException {
        final FinanceBudget financeBudget = this.financeService.readFinanceBudget();
        financeBudget.setData(input.readAllBytes());
        this.financeService.saveFinanceBudget(financeBudget);
    }
    @ApiDavAttributeMapping(path=FINANCE_BUDGET_XLSX, attribute=ApiDavMappingAttribute.ContentLength)
    Long getFinanceBudgetContentLength() {
        return Long.valueOf(this.financeService.readFinanceBudget().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCE_BUDGET_XLSX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinanceBudgetLastModified() {
        return this.financeService.readFinanceBudget().getLastModified();
    }





    private static final String FINANCE_REPORTS_STATISTIC_PPTX="/finance/reports/statistic.pptx";
    @ApiDavMapping(path=FINANCE_REPORTS_STATISTIC_PPTX)
    void getFinanceReportStatistic(MetaOutputStream output) throws IOException  {
        final FinanceReportStatistic financeReportStatistic = this.financeService.readFinanceReportStatistic();
        output.write(financeReportStatistic.getData());
    }
    @ApiDavAttributeMapping(path=FINANCE_REPORTS_STATISTIC_PPTX, attribute=ApiDavMappingAttribute.ContentLength)
    Long getFinanceReportStatisticLength() {
        return Long.valueOf(this.financeService.readFinanceReportStatistic().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCE_REPORTS_STATISTIC_PPTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinanceReportStatisticLastModified() {
        return this.financeService.readFinanceReportStatistic().getLastModified();
    }





    private static final String FINANCE_REPORTS_SALES_PPTX="/finance/reports/sales.pptx";
    @ApiDavMapping(path=FINANCE_REPORTS_SALES_PPTX)
    void getFinanceReportSales(MetaOutputStream output) throws IOException  {
        final FinanceReportSales financeReportSales = this.financeService.readFinanceReportSales();
        output.write(financeReportSales.getData());
    }
    @ApiDavAttributeMapping(path=FINANCE_REPORTS_SALES_PPTX, attribute=ApiDavMappingAttribute.ContentLength)
    Long getFinanceReportSalesLength() {
        return Long.valueOf(this.financeService.readFinanceReportSales().getData().length);
    }
    @ApiDavAttributeMapping(path=FINANCE_REPORTS_SALES_PPTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getFinanceReportSalesLastModified() {
        return this.financeService.readFinanceReportSales().getLastModified();
    }
}