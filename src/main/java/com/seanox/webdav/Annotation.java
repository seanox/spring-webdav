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
package com.seanox.webdav;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

/**
 * Annotation is the base class for handling the annotations. There are two
 * worlds here, the public one where deliberately fine-tuned WebDav annotations
 * are used and there is the internal world where logic-optimized annotations
 * and meta-objects are used. The decoupling made sense because the WebDav
 * prefix would be too present and more information, functions and abstraction
 * can be provided for the internal world, which would rather confuse the
 * public world.<br>
 * <br>
 * Annotation 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
abstract class Annotation {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final String path;
    private final AnnotationType type;
    private final Object origin;
    private final Object object;
    private final Method method;

    private static Date convertDateTime(final String datetime)
            throws ParseException {
        if (datetime.isBlank())
            return null;
        return new SimpleDateFormat(DATE_FORMAT).parse(datetime.trim());
    }

    private static String convertText(final String text) {
        if (text.isBlank())
            return null;
        return text.trim();
    }

    enum AnnotationType {
        Mapping, Input, Meta, Attribute
    }

    enum Target {
        ReadOnly(Boolean.TYPE), Hidden(Boolean.TYPE), Accepted(Boolean.TYPE), Permitted(Boolean.TYPE),
        ContentType(String.class), ContentLength(Integer.class), CreationDate(Date.class), LastModified(Date.class),
        Accept(String.class), ContentLengthMax(Integer.class);

        final Class<?> type;

        Target(final Class<?> type) {
            this.type = type;
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Attribute extends Annotation {

        final AttributeType attributeType;

        enum AttributeType {

            ReadOnly(Target.ReadOnly),
            Hidden(Target.Hidden),
            Accepted(Target.Accepted),
            Permitted(Target.Permitted),

            ContentType(Target.ContentType),
            ContentLength(Target.ContentLength),
            CreationDate(Target.CreationDate),
            LastModified(Target.LastModified),

            Accept(Target.Accept),
            ContentLengthMax(Target.ContentLengthMax);

            final Target target;

            AttributeType(final Target target) {
                this.target = target;
            }
        }

        // Attribute as container can be used independently of WebDav
        // annotations. Outside of the WebDavFilter all components should work
        // without the WebDav annotations, because otherwise the prefix
        // WebDav*** is always present and it slays you.
        // WebDav*** is intended and helpful for the end developer only.

        @Builder(access=AccessLevel.PRIVATE)
        Attribute(final String path, final AnnotationType type, final Object origin, final Object object, final Method method, final AttributeType attribute) {
            super(path, type, origin, object, method);
            this.attributeType = attribute;
        }

        static Attribute create(final WebDavAttributeMapping webDavAttribute, final Object object, final Method method) {
            return Annotation.Attribute.builder()
                    .path(webDavAttribute.path())
                    .type(AnnotationType.Attribute)
                    .origin(webDavAttribute.attribute())
                    .object(object)
                    .method(method)

                    .attribute(webDavAttribute.attribute().type)
                    .build();
        }

        static class AttributeExpression {

            AttributeType type;
            Expression    expression;
            Exception     exception;

            AttributeExpression(final AttributeType type, final String phrase) {
                this.type = type;
                final SpelExpressionParser parser = new SpelExpressionParser();
                try {this.expression = parser.parseExpression(phrase);
                } catch (Exception exception) {
                    this.exception = exception;
                }
            }
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Input extends Annotation {

        private final int    contentLengthMax;
        private final String accept;

        private final Collection<Attribute.AttributeExpression> expressions;

        @Builder(access=AccessLevel.PRIVATE)
        Input(final String path, final AnnotationType type, final Object origin, final Object object, final Method method,
                final int contentLengthMax, final String accept, final Attribute.AttributeExpression... expressions) {
            super(path, type, origin, object, method);

            this.contentLengthMax = contentLengthMax;
            this.accept           = accept;
            this.expressions      = Objects.nonNull(expressions) ? Arrays.asList(expressions) : null;
        }

        static Input create(final WebDavInputMapping webDavInput, final Object object, final Method method) {
            return Input.builder()
                    .path(webDavInput.path())
                    .type(AnnotationType.Input)
                    .origin(webDavInput)
                    .object(object)
                    .method(method)

                    .contentLengthMax(webDavInput.contentLengthMax())
                    .accept(Annotation.convertText(webDavInput.accept()))
                    .expressions(Arrays.stream(webDavInput.attributeExpressions())
                            .map(attributeExpression -> new Attribute.AttributeExpression(attributeExpression.attribute().attributeType, attributeExpression.phrase()))
                            .toArray(Attribute.AttributeExpression[]::new))
                    .build();
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Mapping extends Annotation {

        private final int     contentLength;
        private final String  contentType;
        private final Date    creationDate;
        private final Date    lastModified;
        private final boolean isReadOnly;
        private final boolean isHidden;
        private final boolean isAccepted;
        private final boolean isPermitted;

        private final Collection<Attribute.AttributeExpression> expressions;

        @Builder(access=AccessLevel.PRIVATE)
        Mapping(final String path, final AnnotationType type, final Object origin, final Object object, final Method method,
                final int contentLength, final String contentType, final Date creationDate, final Date lastModified,
                final boolean isReadOnly, final boolean isHidden, final boolean isAccepted, final boolean isPermitted,
                final Attribute.AttributeExpression... expressions) {
            super(path, type, origin, object, method);

            this.contentLength = contentLength;
            this.contentType   = contentType;
            this.creationDate  = creationDate;
            this.lastModified  = lastModified;
            this.isReadOnly    = isReadOnly;
            this.isHidden      = isHidden;
            this.isAccepted    = isAccepted;
            this.isPermitted   = isPermitted;
            this.expressions   = Objects.nonNull(expressions) ? Arrays.asList(expressions) : null;
        }

        static Mapping create(final WebDavMapping webDavMapping, final Object object, final Method method)
                throws AnnotationException {

            Date creationDate;
            try {creationDate = Annotation.convertDateTime(webDavMapping.creationDate());
            } catch (ParseException exception) {
                throw new AnnotationException("Invalid value for creationDate: " + webDavMapping.creationDate().trim());
            }

            Date lastModified;
            try {lastModified = Annotation.convertDateTime(webDavMapping.lastModified());
            } catch (ParseException exception) {
                throw new AnnotationException("Invalid value for lastModified: " + webDavMapping.lastModified().trim());
            }

            return Mapping.builder()
                    .path(webDavMapping.path())
                    .type(AnnotationType.Mapping)
                    .origin(webDavMapping)
                    .object(object)
                    .method(method)

                    .contentLength(webDavMapping.contentLength())
                    .contentType(Annotation.convertText(webDavMapping.contentType()))
                    .creationDate(creationDate)
                    .lastModified(lastModified)
                    .isReadOnly(webDavMapping.readOnly())
                    .isHidden(webDavMapping.hidden())
                    .isAccepted(webDavMapping.accepted())
                    .isPermitted(webDavMapping.permitted())
                    .expressions(Arrays.stream(webDavMapping.attributeExpressions())
                            .map(attributeExpression -> new Attribute.AttributeExpression(attributeExpression.attribute().type, attributeExpression.phrase()))
                            .toArray(Attribute.AttributeExpression[]::new))
                    .build();
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Meta extends Annotation {

        @Builder(access=AccessLevel.PRIVATE)
        Meta(final String path, final AnnotationType type, final Object origin, final Object object, final Method method) {
            super(path, type, origin, object, method);
        }

        static Meta create(final WebDavMetaMapping webDavMeta, final Object object, final Method method) {
            return Meta.builder()
                    .path(webDavMeta.path())
                    .type(AnnotationType.Meta)
                    .origin(webDavMeta)
                    .object(object)
                    .method(method)
                    .build();
        }
    }
}