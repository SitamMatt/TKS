package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.Resource;
import edu.p.lodz.pl.pas.mvc.services.RentsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.UUID;

@RequestScoped
@Named
public class RentingController {
    @Inject
    private RentsService rentsService;

    public List<Resource> getAvailableResources(){
        return rentsService.getAvailableResources();
    }

    public void rent(UUID id){
        rentsService.rent(id);
    }
}
