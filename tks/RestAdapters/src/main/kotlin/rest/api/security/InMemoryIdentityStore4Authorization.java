package rest.api.security;

import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UserNotFoundException;
import domain.model.User;
import domain.model.UserRole;
import domain.model.values.Email;
import ports.primary.UserQueryPort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class InMemoryIdentityStore4Authorization implements IdentityStore {

    @Inject
    private UserQueryPort usersQueryService;

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
        Email email = null;
        try {
            email = new Email(validationResult.getCallerPrincipal().getName());
        } catch (TypeValidationFailedException e) {
            e.printStackTrace();
        }
        User user = null;
        try {
            user = usersQueryService.getDetails(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        if (user == null)
            return null;
        return getRolesFromEnum(user.getRole());
    }

    private static Set<String> getRolesFromEnum(UserRole possibleUserRole){
        return new HashSet<>(Collections.singleton(possibleUserRole.name()));
    }
}