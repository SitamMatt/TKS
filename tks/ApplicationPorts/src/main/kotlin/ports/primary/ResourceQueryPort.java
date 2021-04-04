package ports.primary;

import model.Resource;

import java.util.UUID;

public interface ResourceQueryPort {
    Resource getDetails(UUID id);
}
