package adapters;

import data.UserEntity;
import drivenports.UserQueryPort;
import drivenports.UserSavePort;
import mappers.UserMapper;
import model.User;
import repositories.RepositoryBase;

import java.util.Objects;

public class UserRepositoryAdapter implements UserSavePort, UserQueryPort {

    private final RepositoryBase<UserEntity> repository;
    private final UserMapper mapper;

    public UserRepositoryAdapter(RepositoryBase<UserEntity> repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User findByEmail(String email) {
        var entity = repository.find(x -> x.getEmail().equals(email));
        return mapper.mapEntityToDomainObject(entity);
    }

    @Override
    public void add(User user) {
        var entity = mapper.mapDomainObjectToEntity(user);
        repository.add(entity);
    }

    @Override
    public void update(User user) {
        var entity = repository.find(x -> Objects.equals(x.getEmail(), user.getEmail()));
        assert entity != null; // todo prepare proper exception
        mapper.mapDomainObjectToEntity(user, entity);
        repository.update(entity);
    }
}
