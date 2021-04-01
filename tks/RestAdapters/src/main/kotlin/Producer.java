import adapters.UserRepositoryAdapter;
import data.UserEntity;
import drivenports.UserQueryPort;
import drivenports.UserSavePort;
import mappers.UserMapper;
import repositories.RepositoryBase;
import services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.ArrayList;

@ApplicationScoped
public class Producer {

    @Produces
    public UserService produceUserService(UserSavePort userSavePort, UserQueryPort userQueryPort){
        return new UserService(userSavePort, userQueryPort);
    }

    @Produces
    public UserRepositoryAdapter produceUserSavePort(RepositoryBase<UserEntity> repository, UserMapper mapper){
        return new UserRepositoryAdapter(repository, mapper);
    }

    @Produces
    public RepositoryBase<UserEntity> produceUserRepository(){
        var list = new ArrayList<UserEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    public UserMapper produceUserMapper(){
        return UserMapper.INSTANCE;
    }
}
