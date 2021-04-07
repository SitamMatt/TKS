package rest.api.security;

import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import domain.model.values.Email;
import ports.primary.UserQueryPort;

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
    private UserQueryPort userQueryService;

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) throws TypeValidationFailedException, UserNotFoundException {
        var email = new Email(credential.getCaller());
        var user = userQueryService.getDetails(email);
        if (user == null)
            return INVALID_RESULT;

        String password = user.getPassword();
        if (password.equals(credential.getPasswordAsString()) && user.getActive()) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }
}

