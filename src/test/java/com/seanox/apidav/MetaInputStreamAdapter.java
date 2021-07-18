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
package com.seanox.apidav;

import java.io.InputStream;
import java.util.Objects;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.
 *
 * Why are the tests not in com.seanox.apidav?
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.apidav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.apidav.
 */
@SuppressWarnings("boxing")
public class MetaInputStreamAdapter extends MetaInputStream {

    public MetaInputStreamAdapter(final InputStream input) {
        super(null, null, null, null, input, 0, null);
    }

    public MetaInputStreamAdapter(final InputStream input, final Long limit) {
        super(null, null, null, limit, input, Objects.nonNull(limit) ? limit : 0, null);
    }

    public static Class<MetaInputStreamLimitException> geMetaInputStreamLimitExceptionClass() {
        return MetaInputStreamLimitException.class;
    }
}