package repository.repositories

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import repository.data.UserEntity
import java.util.*

class UserRepositoryTest {
    private val store: MutableList<UserEntity> = ArrayList<UserEntity>()
    private val sampleUser1: UserEntity = UserEntity(null, "mszewc@edu.pl", "ADMIN", "####", true)
    private val sampleUser2: UserEntity = UserEntity(null, "mzab@edu.pl", "CLIENT", "####", true)
    private val repository: RepositoryBase<UserEntity> = RepositoryBase(store)

    @Test
    fun `Given entity with id, add should fail`() {
        sampleUser1.guid = UUID.randomUUID()
        Assertions.assertThrows(Exception::class.java) { repository.add(sampleUser1) }
        Assertions.assertEquals(0, store.size)
    }

    @Test
    fun `Given duplicated entity, add should fail`() {
        repository.add(sampleUser1)
        sampleUser2.guid = sampleUser1.guid
        Assertions.assertThrows(Exception::class.java) { repository.add(sampleUser2) }
        Assertions.assertEquals(1, store.size)
        Assertions.assertNotSame(sampleUser2, store[0])
    }

    @Test
    fun `Given nonexistent entity, update should fail`() {
        Assertions.assertThrows(Exception::class.java) { repository.update(sampleUser1) }
        sampleUser1.guid = UUID.randomUUID()
        Assertions.assertThrows(Exception::class.java) { repository.update(sampleUser1) }
        repository.add(sampleUser2)
        Assertions.assertThrows(Exception::class.java) { repository.update(sampleUser1) }
    }

    @Test
    fun `Given valid entity, update should success`() {
        repository.add(sampleUser1)
        Assertions.assertSame(sampleUser1, store[0])
        sampleUser2.guid = sampleUser1.guid
        Assertions.assertEquals(sampleUser1, sampleUser2)
        repository.update(sampleUser2)
        Assertions.assertSame(sampleUser2, store[0])
    }

    @Test
    fun `Given nonexistent entity, remove should fail`() {
        sampleUser1.guid = UUID.randomUUID()
        Assertions.assertThrows(Exception::class.java) { repository.remove(sampleUser1) }
    }

    @Test
    fun `Given valid predicate, fin should return entity`() {
        val res1 = repository.find { user: UserEntity? -> user!!.email == "mszewc@edu.pl" }
        Assertions.assertNull(res1)
        repository.add(sampleUser1)
        val res2 = repository.find { user: UserEntity? -> user!!.email == "mszewc@edu.pl" }
        Assertions.assertNotNull(res2)
        Assertions.assertSame(sampleUser1, res2)
    }
}