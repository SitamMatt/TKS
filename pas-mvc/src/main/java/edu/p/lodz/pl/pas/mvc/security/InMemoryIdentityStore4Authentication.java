package edu.p.lodz.pl.pas.mvc.security;

import edu.p.lodz.pl.pas.mvc.services.UsersService;
import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;

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

    @Inject
    private UsersService usersService;

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        UserDto user = usersService.find(credential.getCaller());
        if (user == null)
            return INVALID_RESULT;
        String password = user.getPassword();
        if (password != null && password.equals(credential.getPasswordAsString())) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }
}
