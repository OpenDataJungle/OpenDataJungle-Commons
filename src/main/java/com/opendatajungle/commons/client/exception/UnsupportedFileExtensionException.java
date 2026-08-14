package com.opendatajungle.commons.client.exception;

import com.opendatajungle.commons.shared.util.StringUtils;

public class UnsupportedFileExtensionException extends RuntimeException {

    public UnsupportedFileExtensionException(String extension) {
        super("Unsupported file extension: " + StringUtils.sanitizeForLog(extension));
    }
}
