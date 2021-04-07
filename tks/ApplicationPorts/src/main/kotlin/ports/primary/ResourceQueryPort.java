package ports.primary;

import domain.exceptions.ResourceNotFoundException;
import domain.model.Resource;
import domain.model.values.AccessionNumber;

public interface ResourceQueryPort {
    Resource getDetails(AccessionNumber accessionNumber) throws ResourceNotFoundException;
}
