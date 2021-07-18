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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.
 *
 * FinanceService 1.0.0 20210629
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210629
 */
@Service
class FinanceService {

    private FinanceBudget financeBudget;
    @PostConstruct
    private void initFinanceBudget() throws IOException {
        this.financeBudget = new FinanceBudget();
        this.financeBudget.setData(FinanceService.class.getResourceAsStream("/templates/costs.xlsx").readAllBytes());
        this.financeBudget.setLastModified(new Date());
    }
    FinanceBudget readFinanceBudget() {
        return this.financeBudget;
    }
    void saveFinanceBudget(FinanceBudget financeBudget) {
        this.financeBudget.setData(financeBudget.getData());
        this.financeBudget.setLastModified(new Date());
    }

    private FinanceReportStatistic financeReportStatistic;
    @PostConstruct
    private void initFinanceReportStatistic() throws IOException {
        this.financeReportStatistic = new FinanceReportStatistic();
        this.financeReportStatistic.setData(FinanceService.class.getResourceAsStream("/templates/statistic.pptx").readAllBytes());
        this.financeReportStatistic.setLastModified(new Date());
    }
    FinanceReportStatistic readFinanceReportStatistic() {
        return this.financeReportStatistic;
    }

    private FinanceReportSales financeReportSales;
    @PostConstruct
    private void initFinanceReportSales() throws IOException {
        this.financeReportSales = new FinanceReportSales();
        this.financeReportSales.setData(FinanceService.class.getResourceAsStream("/templates/sales.pptx").readAllBytes());
        this.financeReportSales.setLastModified(new Date());
    }
    FinanceReportSales readFinanceReportSales() {
        return this.financeReportSales;
    }
}