import services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class Producer {

    @Produces
    public UserService produceUserService(){
        return new UserService();
    }
}
