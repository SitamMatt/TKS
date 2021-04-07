package application.helpers;

import dto.ErrorDto;

import javax.ws.rs.core.Response;

public class ErrorHelper {

    public static Response notFound(int code, String message){
        return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorDto(message, code))
                .build();
    }

    public static Response conflict(int code, String message){
        return Response
                .status(Response.Status.CONFLICT)
                .entity(new ErrorDto(message, code))
                .build();
    }

    public static Response badRequest(int code, String message){
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorDto(message, code))
                .build();
    }
}
