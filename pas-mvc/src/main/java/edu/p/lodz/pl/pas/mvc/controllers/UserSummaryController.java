package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.services.EventsService;
import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.EventDto;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class UserSummaryController implements Serializable {
    @Inject
    private UsersService usersService;
    @Inject
    private EventsService eventsService;
    private String login;
    private UserDto user;

    public UserDto getUser() {
        return user;
    }

    public void loadUser() throws Exception {
        if (login != null) {
            user = usersService.find(login);
            if (user == null) throw new Exception();
        }
    }

    public List<EventDto> getUserRents() {
        return eventsService.getUserActiveRents(user.getId());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void returnResource(ActionEvent event) throws Exception {
        UUID value = (UUID) event.getComponent().getAttributes().get("selected");
        eventsService.returnResource(user.getId(), value);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }
}
