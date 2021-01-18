package security;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import dto.UserGetDto;
import repositories.UsersRepository;
import services.UsersService;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class InMemoryIdentityStore4Authentication implements IdentityStore {

    private static final Logger LOG = Logger.getLogger(InMemoryIdentityStore4Authentication.class.getName());

    @Inject
    private UsersRepository usersService;

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        var user = usersService.findUserByLogin(credential.getCaller());
        if (user == null)
            return INVALID_RESULT;

        String password = user.getPassword();
        LOG.log(Level.INFO, "User pass: '" + user.getPassword() +"'" + ",Cred pass: '" + credential.getPasswordAsString() + "'");
        if (password != null && password.equals(credential.getPasswordAsString()) && user.isActive()) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }
}
