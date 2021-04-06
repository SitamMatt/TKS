package helpers;

import lombok.SneakyThrows;
import domain.model.values.AccessionNumber;

import java.util.Random;

public class AccessionNumberHelper {

    @SneakyThrows
    public static AccessionNumber generate(){
        int leftLimit = 65; // letter 'A'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 8;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < 4; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        buffer.append('-');
        buffer.append(random.nextInt(900) + 100);
        String generatedString = buffer.toString();
        return new AccessionNumber(generatedString);
    }
}
