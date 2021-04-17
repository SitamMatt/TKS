package webservice.webservices;

import domain.exceptions.ResourceNotFoundException;
import domain.exceptions.TypeValidationFailedException;
import webservice.adapters.LibraryItemWebServiceAdapter;
import webservice.dto.LibraryItemSoapDto;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class LibraryItemWebService {

    @Inject
    private LibraryItemWebServiceAdapter serviceAdapter;

    @WebMethod
    public LibraryItemSoapDto getLibraryItem(String accessionNumber) throws TypeValidationFailedException, ResourceNotFoundException {
        try{
            return serviceAdapter.getLibraryItem(accessionNumber);
        } catch (TypeValidationFailedException | ResourceNotFoundException e) {
            throw e;
        }
    }
}
