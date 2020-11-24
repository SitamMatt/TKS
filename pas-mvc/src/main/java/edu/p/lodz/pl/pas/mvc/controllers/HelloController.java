package edu.p.lodz.pl.pas.mvc.controllers;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@SessionScoped
@ManagedBean
public class HelloController implements Serializable {

    private String message = "Hello World";

    public String getMessage(){
        return message;
    }
}
