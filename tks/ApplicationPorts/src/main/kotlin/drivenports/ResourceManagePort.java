package drivenports;

import model.Resource;
import org.jetbrains.annotations.NotNull;

public interface ResourceManagePort {
    void add(@NotNull Resource resource);
    void save(@NotNull Resource resource);
    void remove(@NotNull Resource resource);
}
