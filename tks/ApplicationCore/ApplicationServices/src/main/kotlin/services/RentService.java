package services;

import exceptions.*;
import drivenports.RentManagePort;
import drivenports.RentQueryPort;
import model.values.Email;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserSearchPort;
import model.Rent;

import java.util.Date;
import java.util.UUID;

public class RentService {

   private final RentManagePort rentManagePort;
   private final RentQueryPort rentQueryPort;
   private final UserSearchPort userSearchPort;
   private final ResourceSearchPort resourceSearchPort;

    public RentService(RentManagePort rentManagePort, RentQueryPort rentQueryPort, UserSearchPort userSearchPort, ResourceSearchPort resourceSearchPort) {
        this.rentManagePort = rentManagePort;
        this.rentQueryPort = rentQueryPort;
        this.userSearchPort = userSearchPort;
        this.resourceSearchPort = resourceSearchPort;
    }

    public void rent(Email email, UUID resourceId) throws UserNotFoundException, ResourceNotFoundException, UserNotActiveException, ResourceAlreadyRentException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceSearchPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        if(!user.getActive()) throw new UserNotActiveException();
        var existingRent = rentQueryPort.findActiveByResourceId(resourceId);
        if(existingRent != null) throw new ResourceAlreadyRentException();
        var rent = new Rent(UUID.randomUUID(), new Date(), null, user.getEmail(), resourceId);
        rentManagePort.save(rent);
    }

    public void returnResource(Email email, UUID resourceId) throws UserNotFoundException, ResourceNotFoundException, ResourceNotRentException, InvalidUserException {
        var user = userSearchPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceSearchPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        var rent = rentQueryPort.findActiveByResourceId(resourceId);
        if(rent == null) throw new ResourceNotRentException();
        if(!rent.getUserEmail().equals(user.getEmail())) throw new InvalidUserException();
        rent.setEndDate(new Date());
        rentManagePort.save(rent);
    }
}
