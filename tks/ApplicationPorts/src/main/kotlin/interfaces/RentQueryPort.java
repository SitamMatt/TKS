package interfaces;

import model.Rent;

import java.util.UUID;

public interface RentQueryPort {

    Rent findActiveByResourceId(UUID resourceId);
}
