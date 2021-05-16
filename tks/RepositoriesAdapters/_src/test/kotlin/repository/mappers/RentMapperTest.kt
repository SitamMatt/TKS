package repository.mappers

import domain.model.context.rents.Rent
import domain.model.values.AccessionNumber
import domain.model.values.Email
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import repository.data.*
import java.util.*

class RentMapperTest{
    private val mapper: RentMapper = RentMapper.INSTANCE

    @Test
    fun rentToEntityTest(){
        val guid = UUID.randomUUID()
        val rent = Rent(guid, Date(), null, Email("mszewc@edu.pl"), AccessionNumber("EEEE-456"))
        val entity = mapper.mapDomainObjectToEntity(rent);
        assertNotNull(entity!!)
        assertEquals(rent.id, entity.id)
        assertEquals(rent.startDate, entity.startDate)
        assertEquals(rent.endDate, entity.endDate)
        assertNull(entity.user)
        assertNull(entity.resource)
    }

    @Test
    fun nullableRentToEntityTest(){
        val entity = mapper.mapDomainObjectToEntity(null)
        assertNull(entity)
    }

    @Test
    fun entityToRentTest(){
        val userEntity = ClientEntity(UUID.randomUUID(), "mszewc@edu.pl", true)
        val resourceEntity = ProductEntity(UUID.randomUUID(), "EEEE-345")
        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, userEntity, resourceEntity)
        val rent = mapper.mapEntityToDomainObject(entity)
        assertNotNull(rent!!)
        assertEquals(entity.id, rent.id)
        assertEquals(entity.startDate, rent.startDate)
        assertEquals(entity.endDate, rent.endDate)
        assertEquals(AccessionNumber("EEEE-345"), rent.resourceId)
        assertEquals(Email("mszewc@edu.pl"), rent.userEmail)
    }

    @Test
    fun nullableEntityToRentTest(){
        val rent = mapper.mapEntityToDomainObject(null)
        assertNull(rent)
    }

    @Test
    fun rentToExistingEntityTest(){
        val userEntity = ClientEntity(UUID.randomUUID(), "mszewc@edu.pl", true)
        val resourceEntity = ProductEntity(UUID.randomUUID(), "EEEE-345")
        val entity = RentEntity(UUID.randomUUID(), UUID.randomUUID(), Date(), null, userEntity, resourceEntity)
        val rent = Rent(UUID.randomUUID(), Date(), Date(), Email("matzab@edu.pl"), AccessionNumber("AAAA-789"))
        mapper.mapDomainObjectToEntity(rent, entity)
        assertEquals(rent.id, entity.id)
        assertEquals(rent.startDate, entity.startDate)
        assertEquals(rent.endDate, entity.endDate)
        assertEquals(userEntity, entity.user)
        assertEquals(resourceEntity, entity.resource)
    }

    @Test
    fun nullableRentToExistingEntityTest() {
        val guid = UUID.randomUUID()
        val startDate = Date()
        val endDate = Date()
        val userEntity = ClientEntity(UUID.randomUUID(), "mszewc@edu.pl",true)
        val resourceEntity = ProductEntity(UUID.randomUUID(), "EEEE-345")
        val entity = RentEntity(UUID.randomUUID(), guid, startDate, endDate, userEntity, resourceEntity)
        mapper.mapDomainObjectToEntity(null, entity)
        assertEquals(guid, entity.id)
        assertEquals(startDate, entity.startDate)
        assertEquals(endDate, entity.endDate)
        assertEquals(userEntity, entity.user)
        assertEquals(resourceEntity, entity.resource)
    }
}