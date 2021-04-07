package ports.secondary;

import domain.model.Resource;
import org.jetbrains.annotations.NotNull;

public interface ResourcePersistencePort {
    void add(@NotNull Resource resource);
    void save(@NotNull Resource resource);
    void remove(@NotNull Resource resource);
}
