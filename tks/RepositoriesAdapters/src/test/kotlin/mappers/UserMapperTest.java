package mappers;

import data.UserEntity;
import model.User;
import model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    UserMapper mapper;

    @BeforeEach
    public void init(){
        mapper = UserMapper.INSTANCE;
    }

    @Test
    public void UserToEntityTest(){
        var user = new User("mszewc@edu.pl", UserRole.ADMIN, "####", true);
        var entity = mapper.mapDomainObjectToEntity(user);
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRole().toString(), entity.getRole());
        assertEquals(user.getActive(), entity.getActive());
    }

    @Test
    public void EntityToUserTest(){
        var entity = new UserEntity("mszewc@edu.pl", UserRole.ADMIN.toString(), "####", true);
        entity.setGuid(UUID.randomUUID());
        var user = mapper.mapEntityToDomainObject(entity);
        assertEquals(entity.getEmail(), user.getEmail());
        assertEquals(entity.getPassword(), user.getPassword());
        assertEquals(UserRole.valueOf(entity.getRole()), user.getRole());
        assertEquals(entity.getActive(), user.getActive());
    }

    @Test
    public void UserToExistingEntityTest(){
        var user = new User("mszewc@edu.pl", UserRole.CLIENT, "####", true);
        var entity = new UserEntity("mszewczyk@edu.pl", UserRole.ADMIN.toString(), "wwww", false);
        var guid = UUID.randomUUID();
        entity.setGuid(guid);
        mapper.mapDomainObjectToEntity(user, entity);
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRole().toString(), entity.getRole());
        assertEquals(user.getActive(), entity.getActive());
        assertEquals(guid, entity.getGuid());
    }

}