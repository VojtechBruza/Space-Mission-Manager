package cz.muni.fi.services.impl;

import org.springframework.dao.DataAccessException;

/**
 * Thrown when there is a problem with accessing the data on the lower layer.
 * @author Vojtech Bruza
 */
public class ServiceDataAccessException extends DataAccessException {
    public ServiceDataAccessException(String msg) {
        super(msg);
    }

    public ServiceDataAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
