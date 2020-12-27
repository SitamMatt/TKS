/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import model.ExampleModel;

import java.util.Date;

/**
 *
 * @author sitam
 */
public class ExampleService {
    public String getMessage(){
        return "Hello from service!";
    }

    public ExampleModel getModel(){
        var model = new ExampleModel();
        model.date = new Date();
        model.message = "Hello from model!";
        return model;
    }
}
