package com.roudforest.utils;

import java.util.concurrent.Callable;

import static com.google.common.base.Throwables.propagate;

public class ExceptionUtils {

    public static<T> T propagateCatchableException(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception ex) {
            throw propagate(ex);
        }
    }
}
