package webservice;

import application.services.RentService;
import application.services.ResourcesService;
import application.services.UserService;
import ports.primary.ResourceRentCommandPort;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserPersistencePort;
import ports.secondary.UserSearchPort;
import repository.adapters.RentRepositoryAdapter;
import repository.adapters.ResourceRepositoryAdapter;
import repository.adapters.UserRepositoryAdapter;
import repository.data.AbstractResourceEntity;
import repository.data.RentEntity;
import repository.data.UserEntity;
import repository.mappers.RentMapper;
import repository.mappers.ResourceMapper;
import repository.mappers.UserMapper;
import repository.repositories.RepositoryBase;
import webservice.mappers.IResourceMapper;
import webservice.mappers.IUserMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import java.util.ArrayList;

@ApplicationScoped
public class Producer {

    @Produces
    public UserService produceUserService(UserPersistencePort userPersistencePort, UserSearchPort userSearchPort) {
        return new UserService(userPersistencePort, userSearchPort);
    }

    @Produces
    public ResourcesService produceResourceService(ResourcePersistencePort resourcePersistencePort, ResourceSearchPort resourceSearchPort, RentRepositoryAdapter rentRepositoryAdapter) {
        return new ResourcesService(resourcePersistencePort, resourceSearchPort, rentRepositoryAdapter);
    }

    @Produces
    public UserRepositoryAdapter produceUserSavePort(RepositoryBase<UserEntity> repository, UserMapper mapper) {
        return new UserRepositoryAdapter(repository, mapper);
    }

    @Produces
    public ResourceRepositoryAdapter produceResourceRepository(RepositoryBase<AbstractResourceEntity> repository, ResourceMapper mapper) {
        return new ResourceRepositoryAdapter(repository, mapper);
    }

    @Produces
    public ResourceRentCommandPort produceResourceRentCommandPort(RentRepositoryAdapter adapter, UserRepositoryAdapter userRepositoryAdapter, ResourceRepositoryAdapter resourceRepositoryAdapter) {
        return new RentService(adapter, adapter, userRepositoryAdapter, resourceRepositoryAdapter);
    }

    @Produces
    public RentRepositoryAdapter produceRentRepositoryAdapter(RepositoryBase<UserEntity> userRepository, RepositoryBase<AbstractResourceEntity> resourceRepository, RepositoryBase<RentEntity> rentRepository, RentMapper rentMapper) {
        return new RentRepositoryAdapter(rentRepository, resourceRepository, userRepository, rentMapper);
    }

    @Produces
    @Singleton
    public RepositoryBase<UserEntity> produceUserRepository() {
        var list = new ArrayList<UserEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    @Singleton
    public RepositoryBase<AbstractResourceEntity> produceResourceRepository() {
        var list = new ArrayList<AbstractResourceEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    @Singleton
    public RepositoryBase<RentEntity> produceRentRepository() {
        var list = new ArrayList<RentEntity>();
        return new RepositoryBase<>(list);
    }

    @Produces
    public UserMapper produceUserMapper() {
        return UserMapper.Companion.getINSTANCE();
    }

    @Produces
    public ResourceMapper produceResourceMapper() {
        return ResourceMapper.Companion.getINSTANCE();
    }

    @Produces
    public RentMapper produceRentMapper() {
        return RentMapper.Companion.getINSTANCE();
    }
}
