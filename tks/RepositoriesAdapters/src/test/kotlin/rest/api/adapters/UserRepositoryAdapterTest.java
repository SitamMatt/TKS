package rest.api.adapters;

import repository.adapters.UserRepositoryAdapter;
import repository.data.UserEntity;
import repository.mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import repository.repositories.RepositoryBase;

import java.util.List;

class UserRepositoryAdapterTest {

    UserRepositoryAdapter adapter;
    List<UserEntity> store;
    UserEntity sampleUser1;
    UserEntity sampleUser2;

    @BeforeEach
    public void init(){
        store = List.of(sampleUser1, sampleUser2);
        var repository = new RepositoryBase<>(store);
        adapter = new UserRepositoryAdapter(repository, UserMapper.Companion.getINSTANCE());
    }
}