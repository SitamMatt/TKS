package ports.secondary;

import model.Resource;
import model.values.AccessionNumber;

public interface ResourceSearchPort {
    Resource findById(AccessionNumber id);
}
