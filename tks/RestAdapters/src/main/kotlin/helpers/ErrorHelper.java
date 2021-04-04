package helpers;

import dto.Error;

import javax.ws.rs.core.Response;

public class ErrorHelper {

    public static Response notFound(int code, String message){
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new Error(message, code))
                .build();
    }

    public static Response conflict(int code, String message){
        return Response
                .status(Response.Status.CONFLICT)
                .entity(new Error(message, code))
                .build();
    }
}
