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
 * MarketingController 1.0.0 20210626
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210626
 */
@RequiredArgsConstructor
@RestController
class MarketingController {

    private final MarketingService marketingService;

    private static final String MARKETING_NEWSLETTER_DOTX="/marketing/newsletter.docx";
    @ApiDavMapping(path=MARKETING_NEWSLETTER_DOTX, isReadOnly=false,
            contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    void getMarketingNewsletter(MetaOutputStream output) throws IOException {
        output.write(this.marketingService.readMarketingNewsletter());
    }
    @ApiDavInput(path=MARKETING_NEWSLETTER_DOTX)
    void putMarketingNewsletter(MetaInputStream output) throws IOException {
        this.marketingService.saveMarketingNewsletter(output.readAllBytes());
    }
    @ApiDavAttribute(path=MARKETING_NEWSLETTER_DOTX, attribute=ApiDavAttribute.Attribute.ContentLength)
    Long getMarketingNewsletterContentLength() throws IOException {
        return Long.valueOf(this.marketingService.readMarketingNewsletter().length);
    }

    private static final String MARKETING_FLYER_PPTX="/marketing/flyer.pptx";
    @ApiDavMapping(path=MARKETING_FLYER_PPTX, isReadOnly=false,
            contentType="application/vnd.openxmlformats-officedocument.presentationml.presentation")
    void getMarketingFlyer(MetaOutputStream output) throws IOException {
        output.write(this.marketingService.readMarketingFlyer());
    }
    @ApiDavInput(path=MARKETING_FLYER_PPTX)
    void putMarketingFlyer(MetaInputStream output) throws IOException {
        this.marketingService.saveMarketingFlyer(output.readAllBytes());
    }
    @ApiDavAttribute(path=MARKETING_FLYER_PPTX, attribute=ApiDavAttribute.Attribute.ContentLength)
    Long getMarketingFlyerContentLength() throws IOException {
        return Long.valueOf(this.marketingService.readMarketingFlyer().length);
    }
}