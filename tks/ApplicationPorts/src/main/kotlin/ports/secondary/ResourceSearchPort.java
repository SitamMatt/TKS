package ports.secondary;

import domain.model.Resource;
import domain.model.values.AccessionNumber;

public interface ResourceSearchPort {
    Resource findById(AccessionNumber accessionNumber);
}
