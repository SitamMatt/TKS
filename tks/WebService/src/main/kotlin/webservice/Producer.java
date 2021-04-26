//package webservice;
//
//import application.services.RentService;
//import application.services.ResourcesService;
//import application.services.UserService;
//import ports.primary.ResourceRentCommandPort;
//import ports.secondary.ResourcePersistencePort;
//import ports.secondary.ResourceSearchPort;
//import ports.secondary.UserPersistencePort;
//import ports.secondary.UserSearchPort;
//import repository.adapters.RentRepositoryAdapter;
//import repository.adapters.ResourceRepositoryAdapter;
//import repository.adapters.UserRepositoryAdapter;
//import repository.data.AbstractResourceEntity;
//import repository.data.BookEntity;
//import repository.data.RentEntity;
//import repository.data.UserEntity;
//import repository.mappers.RentMapper;
//import repository.mappers.ResourceMapper;
//import repository.mappers.UserMapper;
//import repository.repositories.RepositoryBase;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.enterprise.inject.Produces;
//import javax.inject.Singleton;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.UUID;
//
//@ApplicationScoped
//public class Producer {
//
//    @Produces
//    public UserService produceUserService(UserPersistencePort userPersistencePort, UserSearchPort userSearchPort) {
//        return new UserService(userPersistencePort, userSearchPort);
//    }
//
//    @Produces
//    public ResourcesService produceResourceService(ResourcePersistencePort resourcePersistencePort, ResourceSearchPort resourceSearchPort, RentRepositoryAdapter rentRepositoryAdapter) {
//        return new ResourcesService(resourcePersistencePort, resourceSearchPort, rentRepositoryAdapter);
//    }
//
//    @Produces
//    public RentService produceRentService(ResourceSearchPort resourceSearchPort, RentRepositoryAdapter rentRepositoryAdapter, UserSearchPort userSearchPort) {
//        return new RentService(rentRepositoryAdapter, rentRepositoryAdapter, userSearchPort, resourceSearchPort);
//    }
//
//    @Produces
//    public UserRepositoryAdapter produceUserSavePort(RepositoryBase<UserEntity> repository, UserMapper mapper) {
//        return new UserRepositoryAdapter(repository, mapper);
//    }
//
//    @Produces
//    public ResourceRepositoryAdapter produceResourceRepository(RepositoryBase<AbstractResourceEntity> repository, ResourceMapper mapper) {
//        return new ResourceRepositoryAdapter(repository, mapper);
//    }
//
//    @Produces
//    public ResourceRentCommandPort produceResourceRentCommandPort(RentRepositoryAdapter adapter, UserRepositoryAdapter userRepositoryAdapter, ResourceRepositoryAdapter resourceRepositoryAdapter) {
//        return new RentService(adapter, adapter, userRepositoryAdapter, resourceRepositoryAdapter);
//    }
//
//    @Produces
//    public RentRepositoryAdapter produceRentRepositoryAdapter(RepositoryBase<UserEntity> userRepository, RepositoryBase<AbstractResourceEntity> resourceRepository, RepositoryBase<RentEntity> rentRepository, RentMapper rentMapper) {
//        return new RentRepositoryAdapter(rentRepository, resourceRepository, userRepository, rentMapper);
//    }
//
//    BookEntity book = new BookEntity(
//            UUID.randomUUID(),
//            "EEEE-254",
//            "Diuna",
//            "Frank Herbert"
//    );
//
//    UserEntity user = new UserEntity(
//            UUID.randomUUID(),
//            "mszewc@edu.pl",
//            "ADMIN",
//            "password",
//            true
//    );
//
//
//    @Produces
//    @Singleton
//    public RepositoryBase<UserEntity> produceUserRepository() {
//        var list = new ArrayList<UserEntity>();
//        list.add(user);
//        return new RepositoryBase<>(list);
//    }
//
//    @Produces
//    @Singleton
//    public RepositoryBase<AbstractResourceEntity> produceResourceRepository() {
//        var list = new ArrayList<AbstractResourceEntity>();
//        list.add(book);
//        return new RepositoryBase<>(list);
//    }
//
//    @Produces
//    @Singleton
//    public RepositoryBase<RentEntity> produceRentRepository() {
//        var list = new ArrayList<RentEntity>();
//        list.add(new RentEntity(
//                UUID.randomUUID(),
//                UUID.fromString("8245c01f-0a67-4f7a-b184-4af7534bb930"),
//                new Date(), null, user, book
//        ));
//        return new RepositoryBase<>(list);
//    }
//
//    @Produces
//    public UserMapper produceUserMapper() {
//        return UserMapper.Companion.getINSTANCE();
//    }
//
//
//    @Produces
//    public ResourceMapper produceResourceMapper() {
//        return ResourceMapper.Companion.getINSTANCE();
//    }
//
//    @Produces
//    public RentMapper produceRentMapper() {
//        return RentMapper.Companion.getINSTANCE();
//    }
//}
