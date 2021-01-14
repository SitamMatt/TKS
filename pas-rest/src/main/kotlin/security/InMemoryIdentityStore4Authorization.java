package security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import model.UserRole;
import repositories.UsersRepository;

@ApplicationScoped
class InMemoryIdentityStore4Authorization implements IdentityStore {

    @Inject
    private UsersRepository usersService;

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
        var user = usersService.findUserByLogin(validationResult.getCallerPrincipal().getName());
        if (user == null)
            return null;
        return getRolesFromEnum(user.getRole());
    }

    private static Set<String> getRolesFromEnum(UserRole possibleUserRole){
        return new HashSet<>(Collections.singleton(possibleUserRole.name()));
    }
}
