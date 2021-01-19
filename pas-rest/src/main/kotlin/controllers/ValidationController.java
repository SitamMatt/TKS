package controllers;

import dto.ResourceBaseDto;
import dto.UserBaseDto;
import dto.UserCreateDto;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class ValidationController {

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static Response validate(final ResourceBaseDto model){

        var errors = new ArrayList<String>();
        StringBuilder errorBuilder = new StringBuilder();
        validator.validate(model).forEach(violation -> errors.add(violation.getMessage()));
        if (!errors.isEmpty()) {
            errorBuilder.append("Invalid order, reason: ");
            for (String error: errors ) {
                errorBuilder.append(error);
            }
            return Response.status(400, errorBuilder.toString()).build();
        }
        return null;
    }

    public static Response validate(final UserCreateDto model){
        var errors = new ArrayList<String>();
        StringBuilder errorBuilder = new StringBuilder();
        validator.validate(model).forEach(violation -> errors.add(violation.getMessage()));
        if (!errors.isEmpty()) {
            errorBuilder.append("Invalid order, reason: ");
            for (String error: errors ) {
                errorBuilder.append(error);
            }
            return Response.status(400, errorBuilder.toString()).build();
        }
        return null;
    }

}
