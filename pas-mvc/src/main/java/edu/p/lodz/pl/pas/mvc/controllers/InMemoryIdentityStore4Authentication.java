package edu.p.lodz.pl.pas.mvc.controllers;

import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.repositories.IUsersRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.EnumSet;
import java.util.Set;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdentityStore4Authentication implements IdentityStore {

//    private Map<String, String> users;
    @Inject
    private IUsersRepository usersRepository;

//    @PostConstruct
//    private void init() {
//        users = usersRepository.getAllUsers().stream()
//                .collect(Collectors.toMap(User::getLogin, User::getPassword));
//    }

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        User user = usersRepository.findUserByLogin(credential.getCaller());
        if(user == null)
            return INVALID_RESULT;
        String password = user.getPassword();
        if (password != null && password.equals(credential.getPasswordAsString())) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }
}
