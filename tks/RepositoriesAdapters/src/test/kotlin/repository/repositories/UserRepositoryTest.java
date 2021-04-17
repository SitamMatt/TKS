package repository.repositories;

import repository.data.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    RepositoryBase<UserEntity> repository;
    List<UserEntity> store;
    UserEntity sampleUser1;
    UserEntity sampleUser2;

    @BeforeEach
    public void init(){
        sampleUser1 = new UserEntity("mszewc@edu.pl", "ADMIN", "####", true);
        sampleUser2 = new UserEntity("mzab@edu.pl", "CLIENT", "####", true);
        store = new ArrayList<>();
        repository = new RepositoryBase<>(store);
    }

    @Test
    public void AddNewUserWithSetId_ShouldFail(){
        sampleUser1.setGuid(UUID.randomUUID());
        assertThrows(Exception.class, () -> repository.add(sampleUser1));
        assertEquals(0, store.size());
    }

    @Test
    public void AddDuplicatedUser_ShouldFail(){
        repository.add(sampleUser1);
        sampleUser2.setGuid(sampleUser1.getGuid());
        assertThrows(Exception.class, () -> repository.add(sampleUser2));
        assertEquals(1, store.size());
        assertNotSame(sampleUser2, store.get(0));
    }

    @Test
    public void UpdateNonExistingEntity_ShouldFail(){
        assertThrows(Exception.class, () -> repository.update(sampleUser1));
        sampleUser1.setGuid(UUID.randomUUID());
        assertThrows(Exception.class, () -> repository.update(sampleUser1));
        repository.add(sampleUser2);
        assertThrows(Exception.class, () -> repository.update(sampleUser1));
    }

    @Test
    public void UpdateValidUser_ShouldSuccess(){
        repository.add(sampleUser1);
        assertSame(sampleUser1, store.get(0));
        sampleUser2.setGuid(sampleUser1.getGuid());
        repository.update(sampleUser2);
        assertSame(sampleUser2, store.get(0));
    }

    @Test
    public void RemoveNonExistingEntity_ShouldFail(){
        sampleUser1.setGuid(UUID.randomUUID());
        assertThrows(Exception.class, () -> repository.remove(sampleUser1));
    }

    @Test
    public void FindTests(){
        var res1 = repository.find(user -> user.getEmail().equals("mszewc@edu.pl"));
        assertNull(res1);
        repository.add(sampleUser1);
        var res2 = repository.find(user -> user.getEmail().equals("mszewc@edu.pl"));
        assertNotNull(res2);
        assertSame(sampleUser1, res2);
    }
}
