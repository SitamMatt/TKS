package webservice.adapters;

import domain.exceptions.ResourceNotFoundException;
import domain.exceptions.TypeValidationFailedException;
import domain.model.values.AccessionNumber;
import ports.primary.combined.IResourceService;
import webservice.dto.LibraryItemSoapDto;
import webservice.mappers.IResourceMapper;

import javax.inject.Inject;

public class LibraryItemWebServiceAdapter {

    @Inject
    private IResourceService resourceService;
    @Inject
    private IResourceMapper mapper;

    public LibraryItemSoapDto getLibraryItem(String accessionNumber) throws TypeValidationFailedException, ResourceNotFoundException {
        var key = new AccessionNumber(accessionNumber);
        var item = resourceService.getDetails(key);
        return mapper.toDto(item);
    }
}
