package adapters;

import data.AbstractResourceEntity;
import model.values.AccessionNumber;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
import mappers.ResourceMapper;
import model.Resource;
import org.jetbrains.annotations.NotNull;
import repositories.RepositoryBase;

import java.util.Objects;
import java.util.UUID;

public class ResourceRepositoryAdapter implements ResourcePersistencePort, ResourceSearchPort {
    private final RepositoryBase<AbstractResourceEntity> repository;
    private final ResourceMapper mapper;

    public ResourceRepositoryAdapter(RepositoryBase<AbstractResourceEntity> repository, ResourceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void add(@NotNull Resource resource) {
        var entity = mapper.mapDomainObjectToEntity(resource);
        repository.add(entity);
    }

    @Override
    public void save(@NotNull Resource resource) {
        var accessionNumberValue = resource.getId().getValue();
        var entity = repository.find(x -> Objects.equals(x.getId(), accessionNumberValue));
        assert entity != null; // todo prepare proper exception
        mapper.mapDomainObjectToEntity(resource, entity);
        repository.update(entity);
    }

    @Override
    public void remove(@NotNull Resource resource) {
        var accessionNumberValue = resource.getId().getValue();
        var entity = repository.find(x -> Objects.equals(x.getId(), accessionNumberValue));
        assert entity != null; // todo prepare proper exception
        repository.remove(entity);
    }

    @Override
    public Resource findById(AccessionNumber id) {
        var accessionNumberValue = id.getValue();
        var entity = repository.find(x -> Objects.equals(x.getId(), accessionNumberValue));
        return mapper.mapEntityToDomainObject(entity);
    }
}
