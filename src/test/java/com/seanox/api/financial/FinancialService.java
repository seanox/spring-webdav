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
package com.seanox.api.financial;

import com.seanox.api.financial.data.FinancialCosts;
import com.seanox.api.financial.data.FinancialReportSales;
import com.seanox.api.financial.data.FinancialReportStatistic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.
 *
 * FinancialService 1.0.0 20210629
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210629
 */
@Service
class FinancialService {

    private FinancialCosts financialCosts;
    @PostConstruct
    private void initFinancialCosts() throws IOException {
        this.financialCosts = new FinancialCosts();
        this.financialCosts.setData(FinancialService.class.getResourceAsStream("/templates/costs.xlsx").readAllBytes());
        this.financialCosts.setLastModified(new Date());
    }
    FinancialCosts readFinancialCosts() {
        return this.financialCosts;
    }
    void saveFinancialCosts(FinancialCosts financialCosts) {
        this.financialCosts.setData(financialCosts.getData());
        this.financialCosts.setLastModified(new Date());
    }

    private FinancialReportStatistic financialReportStatistic;
    @PostConstruct
    private void initFinancialReportStatistic() throws IOException {
        this.financialReportStatistic = new FinancialReportStatistic();
        this.financialReportStatistic.setData(FinancialService.class.getResourceAsStream("/templates/statistic.pptx").readAllBytes());
        this.financialReportStatistic.setLastModified(new Date());
    }
    FinancialReportStatistic readFinancialReportStatistic() {
        return this.financialReportStatistic;
    }

    private FinancialReportSales financialReportSales;
    @PostConstruct
    private void initFinancialReportSales() throws IOException {
        this.financialReportSales = new FinancialReportSales();
        this.financialReportSales.setData(FinancialService.class.getResourceAsStream("/templates/sales.pptx").readAllBytes());
        this.financialReportSales.setLastModified(new Date());
    }
    FinancialReportSales readFinancialReportSales() {
        return this.financialReportSales;
    }
}