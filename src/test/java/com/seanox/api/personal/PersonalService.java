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

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Simulation of a simple data access layer.
 *
 * PersonalService 1.0.0 20210626
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210626
 */
@Service
class PersonalService {

    private byte[] personalBudget;
    @PostConstruct
    private void initPersonalBudget() throws IOException {
        this.personalBudget = PersonalService.class.getResourceAsStream("/templates/budget.xlsx").readAllBytes();
    }
    byte[] readPersonalBudget() {
        return this.personalBudget;
    }
    void savePersonalBudget(byte[] personalBudget) {
        this.personalBudget = personalBudget;
    }

    private byte[] personalReportStatistic;
    @PostConstruct
    private void initPersonalReportStatistic() throws IOException {
        this.personalReportStatistic = PersonalService.class.getResourceAsStream("/templates/statistic.pptx").readAllBytes();
    }
    byte[] readPersonalReportStatistic() {
        return this.personalReportStatistic;
    }

    private byte[] personalReportSales;
    @PostConstruct
    private void initPersonalReportSales() throws IOException {
        this.personalReportSales = PersonalService.class.getResourceAsStream("/templates/sales.pptx").readAllBytes();
    }
    byte[] readPersonalReportSales() {
        return this.personalReportSales;
    }
}