package drivenports;

import model.Rent;
import model.values.AccessionNumber;

import java.util.UUID;

public interface RentQueryPort {

    Rent findActiveByResourceId(AccessionNumber resourceId);
}
