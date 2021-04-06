package ports.primary;

import exceptions.UnknownResourceException;
import model.Resource;
import model.values.AccessionNumber;

import java.util.UUID;

public interface ResourceManageCommandPort {

    void create(Resource resource) throws UnknownResourceException;
    void update(Resource resource);
    void remove(AccessionNumber id);
}
