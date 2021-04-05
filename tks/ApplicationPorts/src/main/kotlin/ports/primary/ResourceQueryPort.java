package ports.primary;

import model.Resource;
import model.values.AccessionNumber;

import java.util.UUID;

public interface ResourceQueryPort {
    Resource getDetails(AccessionNumber id);
}
