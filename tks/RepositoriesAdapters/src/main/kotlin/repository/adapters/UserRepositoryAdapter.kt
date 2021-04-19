//package repository.adapters;
//
//import repository.data.UserEntity;
//import domain.model.values.Email;
//import org.jetbrains.annotations.NotNull;
//import ports.secondary.UserSearchPort;
//import ports.secondary.UserPersistencePort;
//import repository.mappers.UserMapper;
//import domain.model.User;
//import repository.repositories.RepositoryBase;
//
//import java.util.Objects;
//
//public class UserRepositoryAdapter implements UserPersistencePort, UserSearchPort {
//
//    private final RepositoryBase<UserEntity> repository;
//    private final UserMapper mapper;
//
//    public UserRepositoryAdapter(RepositoryBase<UserEntity> repository, UserMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    @Override
//    public User findByEmail(@NotNull Email email) {
//        var emailValue = email.getValue();
//        var entity = repository.find(x -> x.getEmail().equals(emailValue));
//        return mapper.mapEntityToDomainObject(entity);
//    }
//
//    @Override
//    public void add(User user) {
//        var entity = mapper.mapDomainObjectToEntity(user);
//        repository.add(entity);
//    }
//
//    @Override
//    public void update(User user) {
//        var emailValue = user.getEmail().getValue();
//        var entity = repository.find(x -> Objects.equals(x.getEmail(), emailValue));
//        assert entity != null; // todo prepare proper exception
//        mapper.mapDomainObjectToEntity(user, entity);
//        repository.update(entity);
//    }
//}
