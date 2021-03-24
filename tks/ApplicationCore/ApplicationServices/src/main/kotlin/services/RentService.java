package services;

import exceptions.*;
import interfaces.RentManagePort;
import interfaces.RentQueryPort;
import interfaces.ResourceQueryPort;
import interfaces.UserQueryPort;
import model.Rent;

import java.util.Date;
import java.util.UUID;

public class RentService {

   private final RentManagePort rentManagePort;
   private final RentQueryPort rentQueryPort;
   private final UserQueryPort userQueryPort;
   private final ResourceQueryPort resourceQueryPort;

    public RentService(RentManagePort rentManagePort, RentQueryPort rentQueryPort, UserQueryPort userQueryPort, ResourceQueryPort resourceQueryPort) {
        this.rentManagePort = rentManagePort;
        this.rentQueryPort = rentQueryPort;
        this.userQueryPort = userQueryPort;
        this.resourceQueryPort = resourceQueryPort;
    }

    public void rent(String email, UUID resourceId) throws UserNotFoundException, ResourceNotFoundException, UserNotActiveException, ResourceAlreadyRentException {
        var user = userQueryPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceQueryPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        if(!user.getActive()) throw new UserNotActiveException();
        var existingRent = rentQueryPort.findActiveByResourceId(resourceId);
        if(existingRent != null) throw new ResourceAlreadyRentException();
        var rent = new Rent(UUID.randomUUID(), new Date(), null, user.getEmail(), resourceId);
        rentManagePort.save(rent);
    }

    public void returnResource(String email, UUID resourceId) throws UserNotFoundException, ResourceNotFoundException, ResourceNotRentException, InvalidUserException {
        var user = userQueryPort.findByEmail(email);
        if(user == null) throw new UserNotFoundException();
        var resource = resourceQueryPort.findById(resourceId);
        if(resource == null) throw new ResourceNotFoundException();
        var rent = rentQueryPort.findActiveByResourceId(resourceId);
        if(rent == null) throw new ResourceNotRentException();
        if(!rent.getUserEmail().equals(user.getEmail())) throw new InvalidUserException();
        rent.setEndDate(new Date());
        rentManagePort.save(rent);
    }
}
