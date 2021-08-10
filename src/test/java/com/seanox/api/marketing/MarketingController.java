/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * WebDAV mapping for Spring Boot
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
import com.seanox.webdav.MetaData;
import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.MetaInputStream;
import com.seanox.webdav.MetaOutputStream;
import com.seanox.webdav.WebDavMetaMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

/**
 * Example for the integration of webDAV into a RestController.<br>
 * <br>
 * In general, a managed bean is required.<br>
 * There are various annotations for this:<br>
 *     e.g. @Component, @Service, @RestController, ...<br>
 * The methods and annotations for webDAV combine well with @RestController.<br>
 * <br>
 * MarketingController 1.1.0 20210810<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210810
 */
@Profile({"test", "demo"})
@RequiredArgsConstructor
@RestController
class MarketingController {

    private final MarketingService marketingService;

    private static final String MARKETING_NEWSLETTER_DOTX="/marketing/newsletter.docx";
    @WebDavMapping(path=MARKETING_NEWSLETTER_DOTX, readOnly=false)
    void marketingNewsletterGet(final MetaOutputStream output) throws IOException {
        final MarketingNewsletter marketingNewsletter = this.marketingService.readMarketingNewsletter();
        output.write(marketingNewsletter.getData());
    }
    @WebDavInputMapping(path=MARKETING_NEWSLETTER_DOTX)
    void marketingNewsletterPut(final MetaInputStream output) throws IOException {
        final MarketingNewsletter marketingNewsletter = this.marketingService.readMarketingNewsletter();
        marketingNewsletter.setData(output.readAllBytes());
        this.marketingService.saveMarketingNewsletter(marketingNewsletter);
    }
    @WebDavMetaMapping(path=MARKETING_NEWSLETTER_DOTX)
    void marketingNewsletterMeta(final MetaData metaData) {
        metaData.setContentLength(this.marketingService.readMarketingNewsletter().getData().length);
        metaData.setLastModified(this.marketingService.readMarketingNewsletter().getLastModified());
    }

    private static final String MARKETING_FLYER_PPTX="/marketing/flyer.pptx";
    @WebDavMapping(path=MARKETING_FLYER_PPTX, readOnly=false)
    void marketingFlyerGet(final MetaOutputStream output) throws IOException {
        final MarketingFlyer marketingFlyer = this.marketingService.readMarketingFlyer();
        output.write(marketingFlyer.getData());
    }
    @WebDavInputMapping(path=MARKETING_FLYER_PPTX)
    void marketingFlyerPut(final MetaInputStream output) throws IOException {
        final MarketingFlyer marketingFlyer = this.marketingService.readMarketingFlyer();
        marketingFlyer.setData(output.readAllBytes());
        this.marketingService.saveMarketingFlyer(marketingFlyer);
    }
    @WebDavAttributeMapping(path=MARKETING_FLYER_PPTX, attribute=WebDavMappingAttribute.ContentLength)
    Integer marketingFlyerContentLength() {
        return Integer.valueOf(this.marketingService.readMarketingFlyer().getData().length);
    }
    @WebDavAttributeMapping(path=MARKETING_FLYER_PPTX, attribute=WebDavMappingAttribute.LastModified)
    Date marketingFlyerLastModified() {
        return this.marketingService.readMarketingFlyer().getLastModified();
    }
}