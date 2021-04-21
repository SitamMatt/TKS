package repository.mappers

import domain.model.User
import domain.model.UserRole
import domain.model.values.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import repository.data.UserEntity
import java.util.*

class UserMapperTest {
    private val mapper: UserMapper = UserMapper.INSTANCE

    @Test
    fun userToEntityTest() {
        val user = User(Email("mszewc@edu.pl"), UserRole.ADMIN, "####", true)
        val entity = mapper.mapDomainObjectToEntity(user)
        assertNotNull(entity!!)
        assertEquals(user.email.value, entity.email)
        assertEquals(user.password, entity.password)
        assertEquals(user.role.toString(), entity.role)
        assertEquals(user.active, entity.active)
    }

    @Test
    fun nullableUserToEntity(){
        val entity = mapper.mapDomainObjectToEntity(null)
        assertNull(entity)
    }

    @Test
    fun entityToUserTest() {
        val entity = UserEntity(UUID.randomUUID(), "mszewc@edu.pl", UserRole.ADMIN.toString(), "####", true)
        val user = mapper.mapEntityToDomainObject(entity)
        assertNotNull(user!!)
        assertEquals(entity.email, user.email.value)
        assertEquals(entity.password, user.password)
        assertEquals(UserRole.valueOf(entity.role), user.role)
        assertEquals(entity.active, user.active)
    }

    @Test
    fun nullableEntityToUserTest(){
        val user = mapper.mapEntityToDomainObject(null)
        assertNull(user)
    }

    @Test
    fun userToExistingEntityTest() {
        val user = User(Email("mszewc@edu.pl"), UserRole.CLIENT, "####", true)
        val entity = UserEntity(null, "matzab@edu.pl", UserRole.ADMIN.toString(), "password", false)
        val guid = UUID.randomUUID()
        entity.guid = guid
        mapper.mapDomainObjectToEntity(user, entity)
        assertEquals(user.email.value, entity.email)
        assertEquals(user.password, entity.password)
        assertEquals(user.role.toString(), entity.role)
        assertEquals(user.active, entity.active)
        assertEquals(guid, entity.guid)
    }

    @Test
    fun nullableUserToExistingEntityTest(){
        val entity = UserEntity(null, "matzab@edu.pl", UserRole.ADMIN.toString(), "password", false)
        mapper.mapDomainObjectToEntity(null, entity)
        assertEquals(null, entity.guid)
        assertEquals("matzab@edu.pl", entity.email)
        assertEquals("ADMIN", entity.role)
        assertEquals("password", entity.password)
        assertEquals(false, entity.active)
    }
}