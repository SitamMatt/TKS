import adapters.ResourceRepositoryAdapter;
import adapters.UserRepositoryAdapter;
import data.AbstractResourceEntity;
import data.UserEntity;
import mappers.ResourceMapper;
import model.Resource;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserSearchPort;
import ports.secondary.UserPersistencePort;
import mappers.UserMapper;
import repositories.RepositoryBase;
import services.ResourcesService;
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
    public ResourcesService produceResourceService(ResourcePersistencePort resourcePersistencePort, ResourceSearchPort resourceSearchPort){
        return new ResourcesService(resourcePersistencePort, resourceSearchPort, null);
    }

    @Produces
    public UserRepositoryAdapter produceUserSavePort(RepositoryBase<UserEntity> repository, UserMapper mapper){
        return new UserRepositoryAdapter(repository, mapper);
    }

    @Produces
    public ResourceRepositoryAdapter produceResourceRepository(RepositoryBase<AbstractResourceEntity> repository, ResourceMapper mapper){
        return new ResourceRepositoryAdapter(repository, mapper);
    }

    @Produces
    @Singleton
    public RepositoryBase<UserEntity> produceUserRepository(){
        var list = new ArrayList<UserEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    @Singleton
    public RepositoryBase<AbstractResourceEntity> produceResourceRepository(){
        var list = new ArrayList<AbstractResourceEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    public UserMapper produceUserMapper(){
        return UserMapper.Companion.getINSTANCE();
    }

    @Produces
    public ResourceMapper produceResourceMapper(){
        return ResourceMapper.Companion.getINSTANCE();
    }

//    @Produces
//    public UserMapperDto produceUserMapperDto(){
//        return UserMapperDto.Companion.getINSTANCE();
//    }
}
