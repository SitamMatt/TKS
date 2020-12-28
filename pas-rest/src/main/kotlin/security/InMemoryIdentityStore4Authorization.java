//package security;
//
//import edu.p.lodz.pl.pas.mvc.RolesConverter;
//import edu.p.lodz.pl.pas.mvc.services.UsersService;
//import edu.p.lodz.pl.pas.mvc.services.dto.UserDto;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.security.enterprise.identitystore.CredentialValidationResult;
//import javax.security.enterprise.identitystore.IdentityStore;
//import java.util.EnumSet;
//import java.util.Set;
//
//@ApplicationScoped
//class InMemoryIdentityStore4Authorization implements IdentityStore {
//
//    @Inject
//    private UsersService usersService;
//
//    @Override
//    public int priority() {
//        return 80;
//    }
//
//    @Override
//    public Set<ValidationType> validationTypes() {
//        return EnumSet.of(ValidationType.PROVIDE_GROUPS);
//    }
//
//    @Override
//    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
//        UserDto user = usersService.find(validationResult.getCallerPrincipal().getName());
//        if (user == null)
//            return null;
//        return RolesConverter.getRolesFromEnum(user.getRole());
//    }
//}
