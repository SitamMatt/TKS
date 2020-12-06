package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.RolesConverter;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.services.UsersService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.EnumSet;
import java.util.Set;

@ApplicationScoped
class InMemoryIdentityStore4Authorization implements IdentityStore {

    @Inject
    private UsersService usersService;

    @Override
    public int priority() {
        return 80;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.PROVIDE_GROUPS);
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        User user = usersService.find(validationResult.getCallerPrincipal().getName());
        if (user == null)
            return null;
        Set<String> roles = RolesConverter.getRolesFromEnum(user.getRole());
        return roles;
    }
}
