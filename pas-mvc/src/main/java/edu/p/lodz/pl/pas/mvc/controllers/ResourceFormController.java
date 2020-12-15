package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.RepositoryException;
import edu.p.lodz.pl.pas.mvc.services.ResourcesService;
import edu.p.lodz.pl.pas.mvc.services.dto.ResourceDto;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.UUID;

@ViewScoped
@Named
public class ResourceFormController implements Serializable {
    public enum State{
        CREATE, EDIT
    }

    @Inject
    private ResourcesService resourcesService;

    private String resId;
    private State state;

    public State getState() {
        return state;
    }

    public boolean isCreateState(){
        return state == State.CREATE;
    }

    private ResourceDto res;

    public ResourceDto getRes() {
        return res;
    }

    public void loadResource() {
        if (resId != null) {
            res = resourcesService.find(UUID.fromString(resId));
            state = State.EDIT;
        }
    }

    @PostConstruct
    public void init() {
        res = new ResourceDto();
        res.setType("Book");
        state = State.CREATE;
    }

    public String saveResource() {
        try {
            resourcesService.save(res);
            return "Resources";
        } catch (RepositoryException e) {
            e.printStackTrace();
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        } catch (ObjectAlreadyStoredException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void switchType() {
        String t = res.getType();
        if (t.equals("Book")) {
            res.setType("Magazine");
        } else {
            res.setType("Book");
        }
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }
}