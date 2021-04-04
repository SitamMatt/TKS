package services;

import exceptions.*;
import drivenports.RentManagePort;
import drivenports.RentQueryPort;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserSearchPort;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentServiceTest {

    RentService rentService;

    Resource sampleResource;
    User sampleUser;
    Rent sampleRent;
    String sampleEmail;
    String sampleEmail2;
    UUID sampleResId;
    UUID sampleRentId;

    @Mock
    ResourceSearchPort resourceSearchPort;
    @Mock
    UserSearchPort userSearchPort;
    @Mock
    RentQueryPort rentQueryPort;
    @Mock
    RentManagePort rentManagePort;

    @BeforeEach
    public void init(){
        sampleEmail = "mszewc@edu.pl";
        sampleEmail2 = "mzab@edu.pl";
        sampleResId = UUID.randomUUID();
        sampleRentId = UUID.randomUUID();
        sampleUser = new User(sampleEmail, UserRole.CLIENT, "####", true);
        sampleResource = new Book(sampleResId, "Diuna", "Frank Herbert");
        sampleRent = new Rent(sampleRentId, new Date(), null, sampleEmail2, sampleResId);
        rentService = new RentService(rentManagePort, rentQueryPort, userSearchPort, resourceSearchPort);
    }

    @Test
    public void GivenInvalidUserId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleUser.getEmail()))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getId()));
        verify(rentManagePort, never()).save(any());
    }

    @Test
    public void GivenInvalidResourceId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getId()));
        verify(rentManagePort, never()).save(any());
    }

    @Test
    public void GivenNonActiveUserId_Rent_ShouldFail(){
        sampleUser.setActive(false);
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        assertThrows(UserNotActiveException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getId()));
        verify(rentManagePort, never()).save(any());
    }

    @Test
    public void GivenAlreadyRentResId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        when(rentQueryPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent);
        assertThrows(ResourceAlreadyRentException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getId()));
        verify(rentManagePort, never()).save(any());
    }

    @Test
    public void GivenInvalidUserId_Return_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

    @Test
    public void GivenInvalidResourceId_Return_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

    @Test
    public void GivenNotRentResourceId_Return_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        when(rentQueryPort.findActiveByResourceId(sampleResId)).thenReturn(null);
        assertThrows(ResourceNotRentException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

    @Test
    public void GivenResourceRentByOtherUser_Return_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        sampleRent = new Rent(sampleRentId, new Date(), null, sampleEmail2, sampleResId);
        when(rentQueryPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent);
        assertThrows(InvalidUserException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

}