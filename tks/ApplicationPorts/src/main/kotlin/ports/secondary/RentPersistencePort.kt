package ports.secondary;

import domain.model.Rent;

public interface RentPersistencePort {
    void save(Rent rent);
}
