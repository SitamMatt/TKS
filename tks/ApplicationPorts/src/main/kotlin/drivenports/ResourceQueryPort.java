package drivenports;

import model.Resource;

import java.util.UUID;

public interface ResourceQueryPort {
    Resource findById(UUID id);
}
