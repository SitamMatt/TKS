package adapters;

import dto.LibraryItemDto;
import exceptions.ResourceNotFoundException;
import exceptions.TypeValidationFailedException;
import exceptions.UnknownResourceException;
import mappers.LibraryItemMapper;
import model.values.AccessionNumber;
import ports.primary.IResourceService;

import javax.inject.Inject;

public class LibraryItemResourceAdapter {

    @Inject
    private IResourceService resourceService;
    @Inject
    private LibraryItemMapper mapper;

    public void add(LibraryItemDto dto) throws UnknownResourceException {
        var resource = mapper.toDomainObject(dto);
        resourceService.create(resource);
    }

    public LibraryItemDto query(String id) throws TypeValidationFailedException, ResourceNotFoundException {
        var accessionNumber = new AccessionNumber(id);
        var resource = resourceService.getDetails(accessionNumber);
        return mapper.toDto(resource);
    }
}
