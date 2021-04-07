package rest.api.adapters;

import rest.api.dto.LibraryItemDto;
import domain.exceptions.ResourceNotFoundException;
import domain.exceptions.TypeValidationFailedException;
import domain.exceptions.UnknownResourceException;
import rest.api.mappers.LibraryItemMapper;
import domain.model.values.AccessionNumber;
import ports.primary.IResourceService;

import javax.inject.Inject;

public class LibraryItemResourceAdapter {

    @Inject
    private IResourceService resourceService;
    @Inject
    private LibraryItemMapper mapper;

    public AccessionNumber add(LibraryItemDto dto) throws UnknownResourceException {
        var resource = mapper.toDomainObject(dto);
        resourceService.create(resource);
        return resource.getAccessionNumber();
    }

    public LibraryItemDto query(String id) throws TypeValidationFailedException, ResourceNotFoundException {
        var accessionNumber = new AccessionNumber(id);
        var resource = resourceService.getDetails(accessionNumber);
        return mapper.toDto(resource);
    }
}
