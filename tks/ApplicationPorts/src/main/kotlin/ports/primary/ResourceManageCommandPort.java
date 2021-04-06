package ports.primary;

import domain.exceptions.UnknownResourceException;
import domain.model.Resource;
import domain.model.values.AccessionNumber;

public interface ResourceManageCommandPort {

    void create(Resource resource) throws UnknownResourceException;
    void update(Resource resource);
    void remove(AccessionNumber id);
}
