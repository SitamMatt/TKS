package repository.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.UUID

class EntityTest {
    @Test
    fun equalityTest() {
        val sampleUser1 = UserEntity(null, "mszewc@edu.pl", "ADMIN", "####", true)
        val sampleUser2 = UserEntity(null, "mszewc@edu.pl", "ADMIN", "####", true)
        Assertions.assertEquals(sampleUser2, sampleUser1)
        Assertions.assertNotSame(sampleUser1, sampleUser2)
        sampleUser1.guid = UUID.randomUUID()
        sampleUser2.guid = sampleUser1.guid
        Assertions.assertEquals(sampleUser2, sampleUser1)
        Assertions.assertNotSame(sampleUser1, sampleUser2)
        sampleUser2.email = "mzab@edu.pl"
        Assertions.assertEquals(sampleUser1, sampleUser2)
        Assertions.assertNotSame(sampleUser1, sampleUser2)
    }
}