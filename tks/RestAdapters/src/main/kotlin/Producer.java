import adapters.UserRepositoryAdapter;
import data.UserEntity;
import ports.secondary.UserSearchPort;
import ports.secondary.UserPersistencePort;
import mappers.UserMapper;
import repositories.RepositoryBase;
import services.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.ArrayList;

@ApplicationScoped
public class Producer {

    @Produces
    public UserService produceUserService(UserPersistencePort userPersistencePort, UserSearchPort userSearchPort){
        return new UserService(userPersistencePort, userSearchPort);
    }

    @Produces
    public UserRepositoryAdapter produceUserSavePort(RepositoryBase<UserEntity> repository, UserMapper mapper){
        return new UserRepositoryAdapter(repository, mapper);
    }

    @Produces
    @Singleton
    public RepositoryBase<UserEntity> produceUserRepository(){
        var list = new ArrayList<UserEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    public UserMapper produceUserMapper(){
        return UserMapper.Companion.getINSTANCE();
    }

//    @Produces
//    public UserMapperDto produceUserMapperDto(){
//        return UserMapperDto.Companion.getINSTANCE();
//    }
}
