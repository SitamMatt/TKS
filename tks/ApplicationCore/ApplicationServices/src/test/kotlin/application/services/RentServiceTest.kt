package application.services;

import domain.exceptions.*;
import application.helpers.AccessionNumberHelper;
import ports.secondary.RentPersistencePort;
import ports.secondary.RentSearchPort;
import lombok.SneakyThrows;
import domain.model.values.AccessionNumber;
import domain.model.values.Email;
import ports.secondary.ResourceSearchPort;
import ports.secondary.UserSearchPort;
import domain.model.*;
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
    Email sampleEmail;
    Email sampleEmail2;
    AccessionNumber sampleResId;
    UUID sampleRentId;

    @Mock
    ResourceSearchPort resourceSearchPort;
    @Mock
    UserSearchPort userSearchPort;
    @Mock
    RentSearchPort rentSearchPort;
    @Mock
    RentPersistencePort rentPersistencePort;

    @SneakyThrows
    @BeforeEach
    public void init(){
        sampleEmail = new Email("mszewc@edu.pl");
        sampleEmail2 = new Email("mzab@edu.pl");
        sampleResId = AccessionNumberHelper.generate();
        sampleRentId = UUID.randomUUID();
        sampleUser = new User(sampleEmail, UserRole.CLIENT, "####", true);
        sampleResource = new Book(sampleResId, "Diuna", "Frank Herbert");
        sampleRent = new Rent(sampleRentId, new Date(), null, sampleEmail2, sampleResId);
        rentService = new RentService(rentPersistencePort, rentSearchPort, userSearchPort, resourceSearchPort);
    }

    @Test
    public void GivenInvalidUserId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleUser.getEmail()))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getAccessionNumber()));
        verify(rentPersistencePort, never()).save(any());
    }

    @Test
    public void GivenInvalidResourceId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getAccessionNumber()));
        verify(rentPersistencePort, never()).save(any());
    }

    @Test
    public void GivenNonActiveUserId_Rent_ShouldFail(){
        sampleUser.setActive(false);
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        assertThrows(UserNotActiveException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getAccessionNumber()));
        verify(rentPersistencePort, never()).save(any());
    }

    @Test
    public void GivenAlreadyRentResId_Rent_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        when(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent);
        assertThrows(ResourceAlreadyRentException.class, () -> rentService.rent(sampleUser.getEmail(), sampleResource.getAccessionNumber()));
        verify(rentPersistencePort, never()).save(any());
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
        when(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(null);
        assertThrows(ResourceNotRentException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

    @Test
    public void GivenResourceRentByOtherUser_Return_ShouldFail(){
        when(userSearchPort.findByEmail(eq(sampleEmail))).thenReturn(sampleUser);
        when(resourceSearchPort.findById(eq(sampleResId))).thenReturn(sampleResource);
        sampleRent = new Rent(sampleRentId, new Date(), null, sampleEmail2, sampleResId);
        when(rentSearchPort.findActiveByResourceId(sampleResId)).thenReturn(sampleRent);
        assertThrows(InvalidUserException.class, () -> rentService.returnResource(sampleEmail, sampleResId));
    }

}