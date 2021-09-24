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
package com.seanox.api.marketing;

import com.seanox.api.marketing.data.MarketingFlyer;
import com.seanox.api.marketing.data.MarketingNewsletter;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;

/**
 * Simulation of a simple data access layer.<br>
 * <br>
 * MarketingService 1.0.0 20210629<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
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