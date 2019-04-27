package com.triadsoft.exceptions;
/**
 * @author triad <flores.leonardo@gmail.com>
 * Created 26/04/19 14:55
 */
public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
