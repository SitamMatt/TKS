package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
class InMemoryIdentityStore4Authorization implements IdentityStore {

    private Map<String, List<String>> userRoles;
    @Inject
    private UsersRepository usersRepository;

    @PostConstruct
    private void init() {
        userRoles = usersRepository.getAllUsers().stream()
                .collect(Collectors.toMap(
                        User::getLogin,
                        (u) -> Collections.singletonList(u.getTyp().name()))
                );
    }

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
        List<String> roles = userRoles.get(validationResult.getCallerPrincipal().getName());
        return new HashSet<>(roles);
    }
}
