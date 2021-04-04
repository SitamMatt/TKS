package adapters;

import data.UserEntity;
import mappers.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repositories.RepositoryBase;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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