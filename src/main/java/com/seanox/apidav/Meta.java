package com.seanox.apidav;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Meta {

    private final Long contentLength;
    private final String contentType;
    private final Date lastModified;
    private final Date creationDate;
    private final boolean isReadOnly;
    private final boolean isHidden;
    private final boolean isPermitted;
}