package ports.secondary;

import model.Resource;

import java.util.UUID;

public interface ResourceSearchPort {
    Resource findById(UUID id);
}
