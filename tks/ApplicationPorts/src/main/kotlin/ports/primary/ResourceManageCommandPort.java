package ports.primary;

import model.Resource;

import java.util.UUID;

public interface ResourceManageCommandPort {

    void create(Resource resource);
    void update(Resource resource);
    void remove(UUID id);
}
