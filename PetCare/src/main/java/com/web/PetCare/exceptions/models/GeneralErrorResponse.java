package com.web.PetCare.exceptions.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GeneralErrorResponse {

    private String message;

    private String type;


    public GeneralErrorResponse(final String message,
                                final String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "GeneralErrorResponse{" +
                "message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
