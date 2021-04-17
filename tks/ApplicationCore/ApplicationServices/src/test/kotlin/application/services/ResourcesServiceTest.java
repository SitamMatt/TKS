package application.services;

import domain.exceptions.IncompatibleResourceFormatException;
import domain.exceptions.ResourceBlockedByRentException;
import domain.exceptions.ResourceNotFoundException;
import domain.exceptions.UnknownResourceException;
import application.helpers.AccessionNumberHelper;
import ports.secondary.RentSearchPort;
import lombok.SneakyThrows;
import domain.model.values.AccessionNumber;
import domain.model.values.Email;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
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
public class ResourcesServiceTest {

    ResourcesService resourcesService;

    Resource sampleBook;
    Resource sampleMagazine;
    Resource invalidResource;
    AccessionNumber sampleAccessionNumber;

    @Mock
    ResourcePersistencePort resourcePersistencePort;
    @Mock
    ResourceSearchPort resourceSearchPort;
    @Mock
    RentSearchPort rentSearchPort;

    @BeforeEach
    public void init() {
        sampleAccessionNumber = AccessionNumberHelper.generate();
        resourcesService = new ResourcesService(resourcePersistencePort, resourceSearchPort, rentSearchPort);
        sampleBook = new Book(null, "Diuna", "Frank Herbert");
        sampleMagazine = new Magazine(null, "Świerszczyk", "Nowa era");
        invalidResource = new InvalidResource(null, "invalid");
    }

    @Test
    public void GivenResourceOfValidType_Create_ShouldSuccess() throws UnknownResourceException {
        resourcesService.create(sampleBook);
        verify(resourcePersistencePort).add(eq(sampleBook));
        assertNotNull(sampleBook.getAccessionNumber());
    }

    @Test
    public void GivenResourceOfInvalidType_Create_ShouldFail() {
        assertThrows(UnknownResourceException.class, () -> resourcesService.create(invalidResource));
        verify(resourcePersistencePort, never()).add(any());
    }

    @Test
    public void GivenResourceWithNotNullId_Create_ShouldFail() {
        sampleBook.setAccessionNumber(sampleAccessionNumber);
        assertThrows(UnknownResourceException.class, () -> resourcesService.create(sampleBook));
        verify(resourcePersistencePort, never()).add(any());
    }

    @Test
    public void GivenValidResourceWithNotNullId_Update_ShouldSuccess() {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleAccessionNumber))).thenReturn(sampleMagazine);
        resourcesService.update(sampleMagazine);
        verify(resourcePersistencePort).save(sampleMagazine);
    }

    @Test
    public void GivenNonExistingResource_Update_ShouldFail() {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleMagazine.getAccessionNumber()))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.update(sampleMagazine));
        verify(resourcePersistencePort, never()).save(any());
    }

    @Test
    public void GivenOtherResourceType_Update_ShouldFail() {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        sampleBook.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleAccessionNumber))).thenReturn(sampleMagazine);
        assertThrows(IncompatibleResourceFormatException.class, () -> resourcesService.update(sampleBook));
        verify(resourcePersistencePort, never()).save(any());
    }

    @Test
    public void GivenValidResourceId_Remove_ShouldSuccess() {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleAccessionNumber))).thenReturn(sampleMagazine);
        when(rentSearchPort.findActiveByResourceId(eq(sampleAccessionNumber))).thenReturn(null);
        resourcesService.remove(sampleAccessionNumber);
        verify(resourcePersistencePort).remove(sampleMagazine);
    }

    @Test
    public void GivenInvalidResourceId_Remove_ShouldFail() {
        when(resourceSearchPort.findById(eq(sampleAccessionNumber))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.remove(sampleAccessionNumber));
        verify(resourcePersistencePort, never()).remove(any());
    }

    @SneakyThrows
    @Test
    public void GivenRentResourceId_Remove_ShouldFail() {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleAccessionNumber))).thenReturn(sampleMagazine);
        when(rentSearchPort.findActiveByResourceId(eq(sampleAccessionNumber))).thenReturn(new Rent(UUID.randomUUID(), new Date(), null, new Email("mszewc@edu.pl"), sampleAccessionNumber));
        assertThrows(ResourceBlockedByRentException.class, () -> resourcesService.remove(sampleAccessionNumber));
        verify(resourcePersistencePort, never()).remove(any());
    }

    @Test
    public void GivenValidResourceId_GetDetails_ShouldSuccess() throws ResourceNotFoundException {
        sampleMagazine.setAccessionNumber(sampleAccessionNumber);
        when(resourceSearchPort.findById(eq(sampleMagazine.getAccessionNumber()))).thenReturn(sampleMagazine);
        var result = resourcesService.getDetails(sampleMagazine.getAccessionNumber());
        verify(resourceSearchPort).findById(eq(sampleMagazine.getAccessionNumber()));
        assertEquals(sampleMagazine, result);
    }

    @Test
    public void GivenInvalidResourceId_GetDetails_ShouldFail() {
        when(resourceSearchPort.findById(eq(ResourcesServiceTest.this.sampleAccessionNumber))).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> resourcesService.getDetails(ResourcesServiceTest.this.sampleAccessionNumber));
    }
}
