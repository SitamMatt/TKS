package ports.primary;

import exceptions.ResourceNotFoundException;
import model.Resource;
import model.values.AccessionNumber;

import java.util.UUID;

public interface ResourceQueryPort {
    Resource getDetails(AccessionNumber accessionNumber) throws ResourceNotFoundException;
}
