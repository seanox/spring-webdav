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

import com.seanox.api.marketing.data.MarketingFlyer;
import com.seanox.api.marketing.data.MarketingNewsletter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.
 *
 * MarketingService 1.0.0 20210629
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210629
 */
@Service
class MarketingService {

    private MarketingFlyer marketingFlyer;
    @PostConstruct
    private void initMarketingFlyer() throws IOException {
        this.marketingFlyer = new MarketingFlyer();
        this.marketingFlyer.setData(MarketingService.class.getResourceAsStream("/templates/flyer.pptx").readAllBytes());
        this.marketingFlyer.setLastModified(new Date());
    }
    MarketingFlyer readMarketingFlyer() {
        return this.marketingFlyer;
    }
    void saveMarketingFlyer(MarketingFlyer marketingFlyer) {
        this.marketingFlyer.setData(marketingFlyer.getData());
        this.marketingFlyer.setLastModified(new Date());
    }

    private MarketingNewsletter marketingNewsletter;
    @PostConstruct
    private void initMarketingNewsletter() throws IOException {
        this.marketingNewsletter = new MarketingNewsletter();
        this.marketingNewsletter.setData(MarketingService.class.getResourceAsStream("/templates/newsletter.docx").readAllBytes());
        this.marketingNewsletter.setLastModified(new Date());
    }
    MarketingNewsletter readMarketingNewsletter() {
        return this.marketingNewsletter;
    }
    void saveMarketingNewsletter(MarketingNewsletter marketingNewsletter) {
        this.marketingNewsletter.setData(marketingNewsletter.getData());
        this.marketingNewsletter.setLastModified(new Date());
    }
}