package repository.data;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserEntityTest {

    @Test
    public void EqualityTest(){
        var sampleUser1 = new UserEntity("mszewc@edu.pl", "ADMIN", "####", true);
        var sampleUser2 = new UserEntity("mszewc@edu.pl", "ADMIN", "####", true);
        assertEquals(sampleUser2, sampleUser1);
        assertNotSame(sampleUser1, sampleUser2);
        sampleUser1.setGuid(UUID.randomUUID());
        sampleUser2.setGuid(sampleUser1.getGuid());
        assertEquals(sampleUser2, sampleUser1);
        assertNotSame(sampleUser1, sampleUser2);
        sampleUser2.setEmail("mzab@edu.pl");
        assertEquals(sampleUser2, sampleUser1);
        assertNotSame(sampleUser1, sampleUser2);
    }
}
