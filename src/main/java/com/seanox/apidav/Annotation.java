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

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
abstract class Annotation {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private final String path;
    private final AnnotationType type;
    private final Object object;
    private final Method method;

    static Date convertDateTime(String datetime)
            throws ParseException {
        if (datetime.isBlank())
            return null;
        return new SimpleDateFormat(DATE_FORMAT).parse(datetime.trim());
    }

    static String convertText(String text) {
        if (text.isBlank())
            return null;
        return text.trim();
    }

    enum AnnotationType {
        Mapping, Input, Meta, Attribute
    }

    enum Target {
        ReadOnly(Boolean.TYPE), Hidden(Boolean.TYPE), Accepted(Boolean.TYPE), Permitted(Boolean.TYPE),
        ContentType(String.class), ContentLength(Long.class), CreationDate(Date.class), LastModified(Date.class),
        Accept(String.class), ContentLengthMax(Long.class);

        final Class<?> type;

        Target(Class<?> type) {
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

            AttributeType(Target target) {
                this.target = target;
            }
        }

        @Builder(access=AccessLevel.PRIVATE)
        Attribute(final String path, final AnnotationType type, final Object object, final Method method, final AttributeType attribute) {
            super(path, type, object, method);
            this.attributeType = attribute;
        }

        static Attribute create(final ApiDavAttributeMapping apiDavAttribute, final Object object, final Method method) {
            return Annotation.Attribute.builder()
                    .path(apiDavAttribute.path())
                    .type(AnnotationType.Attribute)
                    .object(object)
                    .method(method)

                    .attribute(apiDavAttribute.attribute().type)
                    .build();
        }

        static class AttributeExpression {

            final AttributeType type;
            final Expression expression;

            AttributeExpression(final AttributeType type, final Expression expression) {
                this.type = type;
                this.expression = expression;
            }
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Input extends Annotation {

        private final long   contentLengthMax;
        private final String accept;

        private final Collection<Attribute.AttributeExpression> expressions;

        @Builder(access=AccessLevel.PRIVATE)
        Input(final String path, final AnnotationType type, final Object object, final Method method,
                final long contentLengthMax, final String accept, Attribute.AttributeExpression... expressions) {
            super(path, type, object, method);

            this.contentLengthMax = contentLengthMax;
            this.accept           = accept;
            this.expressions      = Objects.nonNull(expressions) ? Arrays.asList(expressions) : null;
        }

        static Input create(final ApiDavInputMapping apiDavInput, final Object object, final Method method) {
            return Input.builder()
                    .path(apiDavInput.path())
                    .type(AnnotationType.Input)
                    .object(object)
                    .method(method)

                    .contentLengthMax(apiDavInput.contentLengthMax())
                    .accept(apiDavInput.accept())
                    .expressions(Arrays.stream(apiDavInput.attributeExpressions())
                            .map(attributeExpression -> {
                                final SpelExpressionParser parser = new SpelExpressionParser();
                                final Expression expression = parser.parseExpression(attributeExpression.phrase());
                                return new Attribute.AttributeExpression(attributeExpression.attribute().attributeType, expression);})
                            .toArray(Attribute.AttributeExpression[]::new))
                    .build();
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Mapping extends Annotation {

        private final long    contentLength;
        private final String  contentType;
        private final Date    creationDate;
        private final Date    lastModified;
        private final boolean isReadOnly;
        private final boolean isHidden;
        private final boolean isAccepted;
        private final boolean isPermitted;

        private final Collection<Attribute.AttributeExpression> expressions;

        @Builder(access=AccessLevel.PRIVATE)
        Mapping(final String path, final AnnotationType type, final Object object, final Method method,
                final long contentLength, final String contentType, final Date creationDate, final Date lastModified,
                final boolean isReadOnly, final boolean isHidden, final boolean isAccepted, final boolean isPermitted,
                final Attribute.AttributeExpression... expressions) {
            super(path, type, object, method);

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

        static Mapping create(final ApiDavMapping apiDavMapping, final Object object, final Method method)
                throws AnnotationException {

            Date creationDate;
            try {creationDate = Annotation.convertDateTime(apiDavMapping.creationDate());
            } catch (ParseException exception) {
                throw new AnnotationException("Invalid value for creationDate: " + apiDavMapping.creationDate().trim());
            }

            Date lastModified;
            try {lastModified = Annotation.convertDateTime(apiDavMapping.lastModified());
            } catch (ParseException exception) {
                throw new AnnotationException("Invalid value for lastModified: " + apiDavMapping.lastModified().trim());
            }

            return Mapping.builder()
                    .path(apiDavMapping.path())
                    .type(AnnotationType.Mapping)
                    .object(object)
                    .method(method)

                    .contentLength(apiDavMapping.contentLength())
                    .contentType(Annotation.convertText(apiDavMapping.contentType()))
                    .creationDate(creationDate)
                    .lastModified(lastModified)
                    .isReadOnly(apiDavMapping.isReadOnly())
                    .isHidden(apiDavMapping.isHidden())
                    .isAccepted(apiDavMapping.isAccepted())
                    .isPermitted(apiDavMapping.isPermitted())
                    .expressions(Arrays.stream(apiDavMapping.attributeExpressions())
                            .map(attributeExpression -> {
                                final SpelExpressionParser parser = new SpelExpressionParser();
                                final Expression expression = parser.parseExpression(attributeExpression.phrase());
                                return new Attribute.AttributeExpression(attributeExpression.attribute().type, expression);})
                            .toArray(Attribute.AttributeExpression[]::new))
                    .build();
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class Meta extends Annotation {

        @Builder(access=AccessLevel.PRIVATE)
        Meta(final String path, final AnnotationType type, final Object object, final Method method) {
            super(path, type, object, method);
        }

        static Meta create(final ApiDavMetaMapping apiDavMeta, final Object object, final Method method) {
            return Meta.builder()
                    .path(apiDavMeta.path())
                    .type(AnnotationType.Meta)
                    .object(object)
                    .method(method)
                    .build();
        }
    }
}