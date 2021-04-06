package services;

import domain.exceptions.*;
import ports.primary.IRentService;
import ports.primary.ResourceRentCommandPort;
import ports.secondary.RentPersistencePort;
import ports.secondary.RentSearchPort;
import domain.model.values.AccessionNumber;
import domain.model.values.Email;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserSearchPort;
import domain.model.Rent;

import java.util.Date;
import java.util.UUID;

public class RentService implements IRentService {

   private final RentPersistencePort rentPersistencePort;
   private final RentSearchPort rentSearchPort;
   private final UserSearchPort userSearchPort;
   private final ResourceSearchPort resourceSearchPort;

    public RentService(RentPersistencePort rentPersistencePort, RentSearchPort rentSearchPort, UserSearchPort userSearchPort, ResourceSearchPort resourceSearchPort) {
        this.rentPersistencePort = rentPersistencePort;
        this.rentSearchPort = rentSearchPort;
        this.userSearchPort = userSearchPort;
        this.resourceSearchPort = resourceSearchPort;
    }

    public void rent(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, UserNotActiveException, ResourceAlreadyRentException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceSearchPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        if(!user.getActive()) throw new UserNotActiveException();
        var existingRent = rentSearchPort.findActiveByResourceId(resourceId);
        if(existingRent != null) throw new ResourceAlreadyRentException();
        var rent = new Rent(UUID.randomUUID(), new Date(), null, user.getEmail(), resourceId);
        rentPersistencePort.save(rent);
    }

    public void returnResource(Email email, AccessionNumber resourceId) throws UserNotFoundException, ResourceNotFoundException, ResourceNotRentException, InvalidUserException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceSearchPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        var rent = rentSearchPort.findActiveByResourceId(resourceId);
        if(rent == null) throw new ResourceNotRentException();
        if(!rent.getUserEmail().equals(user.getEmail())) throw new InvalidUserException();
        rent.setEndDate(new Date());
        rentPersistencePort.save(rent);
    }
}
