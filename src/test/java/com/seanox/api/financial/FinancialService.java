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
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.<br>
 * <br>
 * FinancialService 1.0.0 20210629<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
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