package services;

import exceptions.IncompatibleResourceFormatException;
import exceptions.ResourceBlockedByRentException;
import exceptions.ResourceNotFoundException;
import exceptions.UnknownResourceException;
import drivenports.RentQueryPort;
import lombok.SneakyThrows;
import model.values.Email;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
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
public class ResourcesServiceTest {

    ResourcesService resourcesService;

    Resource sampleBook;
    Resource sampleMagazine;
    Resource invalidResource;

    @Mock
    ResourcePersistencePort resourcePersistencePort;
    @Mock
    ResourceSearchPort resourceSearchPort;
    @Mock
    RentQueryPort rentQueryPort;

    @BeforeEach
    public void init() {
        resourcesService = new ResourcesService(resourcePersistencePort, resourceSearchPort, rentQueryPort);
        sampleBook = new Book(null, "Diuna", "Frank Herbert");
        sampleMagazine = new Magazine(null, "Świerszczyk", "Nowa era");
        invalidResource = new InvalidResource(null, "invalid");
    }

    @Test
    public void GivenResourceOfValidType_Create_ShouldSuccess(){
        resourcesService.create(sampleBook);
        verify(resourcePersistencePort).add(eq(sampleBook));
        assertNotNull(sampleBook.getId());
    }

    @Test
    public void GivenResourceOfInvalidType_Create_ShouldFail(){
        assertThrows(UnknownResourceException.class, () -> resourcesService.create(invalidResource));
        verify(resourcePersistencePort, never()).add(any());
    }

    @Test
    public void GivenResourceWithNotNullId_Create_ShouldFail(){
        sampleBook.setId(UUID.randomUUID());
        assertThrows(UnknownResourceException.class, () -> resourcesService.create(sampleBook));
        verify(resourcePersistencePort, never()).add(any());
    }

    @Test
    public void GivenValidResourceWithNotNullId_Update_ShouldSuccess(){
        sampleMagazine.setId(UUID.randomUUID());
        when(resourceSearchPort.findById(eq(sampleMagazine.getId()))).thenReturn(sampleMagazine);
        resourcesService.update(sampleMagazine);
        verify(resourcePersistencePort).save(sampleMagazine);
    }

    @Test
    public void GivenNonExistingResource_Update_ShouldFail(){
        sampleMagazine.setId(UUID.randomUUID());
        when(resourceSearchPort.findById(eq(sampleMagazine.getId()))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.update(sampleMagazine));
        verify(resourcePersistencePort, never()).save(any());
    }

    @Test
    public void GivenOtherResourceType_Update_ShouldFail(){
        sampleMagazine.setId(UUID.randomUUID());
        sampleBook.setId(sampleMagazine.getId());
        when(resourceSearchPort.findById(eq(sampleMagazine.getId()))).thenReturn(sampleMagazine);
        assertThrows(IncompatibleResourceFormatException.class, () -> resourcesService.update(sampleBook));
        verify(resourcePersistencePort, never()).save(any());
    }

    @Test
    public void GivenValidResourceId_Remove_ShouldSuccess(){
        var guid = UUID.randomUUID();
        sampleMagazine.setId(guid);
        when(resourceSearchPort.findById(eq(guid))).thenReturn(sampleMagazine);
        when(rentQueryPort.findActiveByResourceId(eq(guid))).thenReturn(null);
        resourcesService.remove(guid);
        verify(resourcePersistencePort).remove(sampleMagazine);
    }

    @Test
    public void GivenInvalidResourceId_Remove_ShouldFail(){
        var guid = UUID.randomUUID();
        when(resourceSearchPort.findById(eq(guid))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.remove(guid));
        verify(resourcePersistencePort, never()).remove(any());
    }

    @SneakyThrows
    @Test
    public void GivenRentResourceId_Remove_ShouldFail(){
        var guid = UUID.randomUUID();
        sampleMagazine.setId(guid);
        when(resourceSearchPort.findById(eq(guid))).thenReturn(sampleMagazine);
        when(rentQueryPort.findActiveByResourceId(eq(guid))).thenReturn(new Rent(UUID.randomUUID(), new Date(), null, new Email("mszewc@edu.pl"), guid));
        assertThrows(ResourceBlockedByRentException.class, () -> resourcesService.remove(guid));
        verify(resourcePersistencePort, never()).remove(any());
    }

    @Test
    public void GivenValidResourceId_GetDetails_ShouldSuccess(){
        sampleMagazine.setId(UUID.randomUUID());
        when(resourceSearchPort.findById(eq(sampleMagazine.getId()))).thenReturn(sampleMagazine);
        var result = resourcesService.getDetails(sampleMagazine.getId());
        verify(resourceSearchPort).findById(eq(sampleMagazine.getId()));
        assertEquals(sampleMagazine, result);
    }

    @Test
    public void GivenInvalidResourceId_GetDetails_ShouldFail(){
        var guid = UUID.randomUUID();
        when(resourceSearchPort.findById(eq(guid))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.getDetails(guid));
    }
}
