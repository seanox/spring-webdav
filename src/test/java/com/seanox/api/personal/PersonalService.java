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

import com.seanox.api.personal.data.PersonalBudget;
import com.seanox.api.personal.data.PersonalReportSales;
import com.seanox.api.personal.data.PersonalReportStatistic;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.
 *
 * PersonalService 1.0.0 20210629
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210629
 */
@Service
class PersonalService {

    private PersonalBudget personalBudget;
    @PostConstruct
    private void initPersonalBudget() throws IOException {
        this.personalBudget = new PersonalBudget();
        this.personalBudget.setData(PersonalService.class.getResourceAsStream("/templates/budget.xlsx").readAllBytes());
        this.personalBudget.setLastModified(new Date());
    }
    PersonalBudget readPersonalBudget() {
        return this.personalBudget;
    }
    void savePersonalBudget(PersonalBudget personalBudget) {
        this.personalBudget.setData(personalBudget.getData());
        this.personalBudget.setLastModified(new Date());
    }

    private PersonalReportStatistic personalReportStatistic;
    @PostConstruct
    private void initPersonalReportStatistic() throws IOException {
        this.personalReportStatistic = new PersonalReportStatistic();
        this.personalReportStatistic.setData(PersonalService.class.getResourceAsStream("/templates/statistic.pptx").readAllBytes());
        this.personalReportStatistic.setLastModified(new Date());
    }
    PersonalReportStatistic readPersonalReportStatistic() {
        return this.personalReportStatistic;
    }

    private PersonalReportSales personalReportSales;
    @PostConstruct
    private void initPersonalReportSales() throws IOException {
        this.personalReportSales = new PersonalReportSales();
        this.personalReportSales.setData(PersonalService.class.getResourceAsStream("/templates/sales.pptx").readAllBytes());
        this.personalReportSales.setLastModified(new Date());
    }
    PersonalReportSales readPersonalReportSales() {
        return this.personalReportSales;
    }
}