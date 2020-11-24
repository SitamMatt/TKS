package edu.p.lodz.pl.pas.mvc.controllers;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "hello")
@SessionScoped
public class HelloController implements Serializable {

    private String message = "Hello World";

    public String getMessage(){
        return message;
    }

    public void setMessage(String msg){
        message = msg;
    }
}
