/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.exception;

/**
 *
 * @author Vutomi Ngobeni
 */
public class InterstellarException extends Exception {

    /**
     * Creates a new instance of <code>InterstellarException</code> without
     * detail message.
     */
    public InterstellarException() {
    }

    /**
     * Constructs an instance of <code>InterstellarException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InterstellarException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
	return "Source and Destination cannot be the same";
    }
}
