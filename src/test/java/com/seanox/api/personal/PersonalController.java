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
package com.seanox.api.personal;

import com.seanox.apidav.ApiDavAttribute;
import com.seanox.apidav.ApiDavInput;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Example for the integration of apiDAV into a RestController.
 *
 * In general, a managed bean is required.
 * There are various annotations for this: e.g. @Component, @Service, @RestController, ...
 * The methods and annotations for apiDAV combine well with @RestController.
 *
 * PersonalController 1.0.0 20210626
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210626
 */
@RequiredArgsConstructor
@RestController
class PersonalController {

    private final PersonalService personalService;

    private static final String PERSONAL_BUDGET_XLSX="/personal/budget.xlsx";
    @ApiDavMapping(path=PERSONAL_BUDGET_XLSX, isReadOnly=false,
            contentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    void getPersonalBudget(MetaOutputStream output) throws IOException {
        output.write(this.personalService.readPersonalBudget());
    }
    @ApiDavInput(path=PERSONAL_BUDGET_XLSX)
    void putPersonalBudget(MetaInputStream output) throws IOException {
        this.personalService.savePersonalBudget(output.readAllBytes());
    }
    @ApiDavAttribute(path=PERSONAL_BUDGET_XLSX, attribute=ApiDavAttribute.Attribute.ContentLength)
    Long getPersonalBudgetContentLength() throws IOException {
        return Long.valueOf(this.personalService.readPersonalBudget().length);
    }

    private static final String PERSONAL_REPORTS_STATISTIC_PPTX="/personal/reports/statistic.pptx";
    @ApiDavMapping(path=PERSONAL_REPORTS_STATISTIC_PPTX,
            contentType="application/vnd.openxmlformats-officedocument.presentationml.presentation")
    void getPersonalReportStatistic(MetaOutputStream output) throws IOException  {
        output.write(this.personalService.readPersonalReportStatistic());
    }
    @ApiDavAttribute(path=PERSONAL_REPORTS_STATISTIC_PPTX, attribute=ApiDavAttribute.Attribute.ContentLength)
    Long getPersonalReportStatisticLength() throws IOException {
        return Long.valueOf(this.personalService.readPersonalBudget().length);
    }

    // TODO: final idea for the annotations (outsourcing) e.g. ApiDavMappingAttributeExpression + ApiDavMappingAttribute
    // private static final String PERSONAL_REPORTS_STATISTIC_PPTX="/personal/reports/statistic.pptx";
    // @ApiDavMapping(path=PERSONAL_REPORTS_STATISTIC_PPTX, attributeExpressions={
    //         @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.Permitted, phrase="#{'TODO:'}")
    // })
    // public void getPersonalReportStatistic(MetaOutputStream output) {
    // }

    private static final String PERSONAL_REPORTS_SALES_PPTX="/personal/reports/sales.pptx";
    @ApiDavMapping(path=PERSONAL_REPORTS_SALES_PPTX,
            contentType="application/vnd.openxmlformats-officedocument.presentationml.presentation")
    void getPersonalReportSales(MetaOutputStream output) throws IOException  {
        output.write(this.personalService.readPersonalReportSales());
    }
    @ApiDavAttribute(path=PERSONAL_REPORTS_SALES_PPTX, attribute=ApiDavAttribute.Attribute.ContentLength)
    Long getPersonalReportSalesLength() throws IOException {
        return Long.valueOf(this.personalService.readPersonalReportSales().length);
    }
}