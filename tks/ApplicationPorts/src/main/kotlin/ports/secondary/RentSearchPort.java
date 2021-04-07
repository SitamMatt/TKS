package ports.secondary;

import domain.model.Rent;
import domain.model.values.AccessionNumber;

public interface RentSearchPort {

    Rent findActiveByResourceId(AccessionNumber resourceId);
}
