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
package com.seanox.api.marketing;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Simulation of a simple data access layer.
 *
 * MarketingService 1.0.0 20210626
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210626
 */
@Service
class MarketingService {

    private byte[] marketingFlyer;
    @PostConstruct
    private void initMarketingFlyer() throws IOException {
        this.marketingFlyer = MarketingService.class.getResourceAsStream("/templates/flyer.pptx").readAllBytes();
    }
    byte[] readMarketingFlyer() {
        return this.marketingFlyer;
    }
    void saveMarketingFlyer(byte[] marketingFlyer) {
        this.marketingFlyer = marketingFlyer;
    }

    private byte[] marketingNewsletter;
    @PostConstruct
    private void initMarketingNewsletter() throws IOException {
        this.marketingNewsletter = MarketingService.class.getResourceAsStream("/templates/newsletter.docx").readAllBytes();
    }
    byte[] readMarketingNewsletter() {
        return this.marketingNewsletter;
    }
    void saveMarketingNewsletter(byte[] marketingNewsletter) {
        this.marketingNewsletter = marketingNewsletter;
    }
}