package repository.adapters;

import repository.data.AbstractResourceEntity;
import domain.model.values.AccessionNumber;
import ports.secondary.ResourcePersistencePort;
import ports.secondary.ResourceSearchPort;
import repository.mappers.ResourceMapper;
import domain.model.Resource;
import org.jetbrains.annotations.NotNull;
import repository.repositories.RepositoryBase;

import java.util.Objects;

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
        var accessionNumberValue = resource.getAccessionNumber().getValue();
        var entity = repository.find(x -> Objects.equals(x.getAccessionNumber(), accessionNumberValue));
        assert entity != null; // todo prepare proper exception
        mapper.mapDomainObjectToEntity(resource, entity);
        repository.update(entity);
    }

    @Override
    public void remove(@NotNull Resource resource) {
        var accessionNumberValue = resource.getAccessionNumber().getValue();
        var entity = repository.find(x -> Objects.equals(x.getAccessionNumber(), accessionNumberValue));
        assert entity != null; // todo prepare proper exception
        repository.remove(entity);
    }

    @Override
    public Resource findById(AccessionNumber accessionNumber) {
        var accessionNumberValue = accessionNumber.getValue();
        var entity = repository.find(x -> Objects.equals(x.getAccessionNumber(), accessionNumberValue));
        return mapper.mapEntityToDomainObject(entity);
    }
}
