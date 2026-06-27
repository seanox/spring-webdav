/**
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
package com.seanox.webdav;

/** Constants for attributes of {@link WebDavInputMappingAttributeExpression}. */
public enum WebDavInputMappingAttribute {

    /** Constant for attribute Accept */
    Accept(Annotation.Attribute.AttributeType.Accept),

    /** Constant for attribute ContentLengthMax */
    ContentLengthMax(Annotation.Attribute.AttributeType.ContentLengthMax);

    final Annotation.Attribute.AttributeType attributeType;

    WebDavInputMappingAttribute(final Annotation.Attribute.AttributeType type) {
        this.attributeType = type;
    }
}