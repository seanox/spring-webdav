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
 * MarketingController 1.0.0 20210703
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210703
 */
@RequiredArgsConstructor
@RestController
class MarketingController {

    private final MarketingService marketingService;

    private static final String MARKETING_NEWSLETTER_DOTX="/marketing/newsletter.docx";
    @ApiDavMapping(path=MARKETING_NEWSLETTER_DOTX, isReadOnly=false)
    void getMarketingNewsletter(MetaOutputStream output) throws IOException {
        final MarketingNewsletter marketingNewsletter = this.marketingService.readMarketingNewsletter();
        output.setContentLength((long)marketingNewsletter.getData().length);
        output.setLastModified(marketingNewsletter.getLastModified());
        output.write(marketingNewsletter.getData());
    }
    @ApiDavInputMapping(path=MARKETING_NEWSLETTER_DOTX)
    void putMarketingNewsletter(MetaInputStream output) throws IOException {
        final MarketingNewsletter marketingNewsletter = this.marketingService.readMarketingNewsletter();
        marketingNewsletter.setData(output.readAllBytes());
        this.marketingService.saveMarketingNewsletter(marketingNewsletter);
    }
    @ApiDavAttributeMapping(path=MARKETING_NEWSLETTER_DOTX, attribute=ApiDavMappingAttribute.ContentLength)
    Long getMarketingNewsletterLength() {
        return Long.valueOf(this.marketingService.readMarketingNewsletter().getData().length);
    }
    @ApiDavAttributeMapping(path=MARKETING_NEWSLETTER_DOTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getMarketingNewsletterLastModified() {
        return this.marketingService.readMarketingNewsletter().getLastModified();
    }

    private static final String MARKETING_FLYER_PPTX="/marketing/flyer.pptx";
    @ApiDavMapping(path=MARKETING_FLYER_PPTX, isReadOnly=false)
    void getMarketingFlyer(MetaOutputStream output) throws IOException {
        final MarketingFlyer marketingFlyer = this.marketingService.readMarketingFlyer();
        output.setContentLength((long)marketingFlyer.getData().length);
        output.setLastModified(marketingFlyer.getLastModified());
        output.write(marketingFlyer.getData());
    }
    @ApiDavInputMapping(path=MARKETING_FLYER_PPTX)
    void putMarketingFlyer(MetaInputStream output) throws IOException {
        final MarketingFlyer marketingFlyer = this.marketingService.readMarketingFlyer();
        marketingFlyer.setData(output.readAllBytes());
        this.marketingService.saveMarketingFlyer(marketingFlyer);
    }
    @ApiDavAttributeMapping(path=MARKETING_FLYER_PPTX, attribute=ApiDavMappingAttribute.ContentLength)
    Long getMarketingFlyerLength() {
        return Long.valueOf(this.marketingService.readMarketingNewsletter().getData().length);
    }
    @ApiDavAttributeMapping(path=MARKETING_FLYER_PPTX, attribute=ApiDavMappingAttribute.LastModified)
    Date getMarketingFlyerLastModified() {
        return this.marketingService.readMarketingNewsletter().getLastModified();
    }
}