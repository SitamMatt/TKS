package ports.secondary;

import model.Rent;
import model.values.AccessionNumber;

import java.util.UUID;

public interface RentSearchPort {

    Rent findActiveByResourceId(AccessionNumber resourceId);
}
