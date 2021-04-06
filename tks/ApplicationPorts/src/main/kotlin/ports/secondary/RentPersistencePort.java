package ports.secondary;

import model.Rent;

public interface RentPersistencePort {
    void save(Rent rent);
}
