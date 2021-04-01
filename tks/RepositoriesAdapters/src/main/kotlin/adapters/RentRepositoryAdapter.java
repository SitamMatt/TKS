package adapters;

import data.AbstractResourceEntity;
import data.RentEntity;
import data.UserEntity;
import drivenports.RentManagePort;
import drivenports.RentQueryPort;
import mappers.RentMapper;
import model.Rent;
import repositories.RepositoryBase;

import java.util.Objects;
import java.util.UUID;

public class RentRepositoryAdapter implements RentQueryPort, RentManagePort {

    private final RepositoryBase<RentEntity> repository;
    private final RepositoryBase<AbstractResourceEntity> resourceRepository;
    private final RepositoryBase<UserEntity> userRepository;
    private final RentMapper mapper;

    public RentRepositoryAdapter(RepositoryBase<RentEntity> repository, RepositoryBase<AbstractResourceEntity> resourceRepository, RepositoryBase<UserEntity> userRepository, RentMapper mapper) {
        this.repository = repository;
        this.resourceRepository = resourceRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Rent rent) {
        var entity = repository.find(x -> Objects.equals(x.getId(), rent.getId()));
        if(entity == null){
            entity = mapper.mapDomainObjectToEntity(rent);
            var resource = resourceRepository.find(x -> Objects.equals(x.getId(), rent.getResourceId()));
            var user = userRepository.find(x -> x.getEmail().equals(rent.getUserEmail()));
            // todo should nullability be checked ?
            entity.setResource(resource);
            entity.setUser(user);
            repository.add(entity);
        }else{
            mapper.mapDomainObjectToEntity(rent, entity);
            repository.update(entity);
        }
    }

    @Override
    public Rent findActiveByResourceId(UUID resourceId) {
        var entity = repository.find(x -> Objects.equals(x.getResource().getId(), resourceId) && x.getEndDate() == null);
        return mapper.mapEntityToDomainObject(entity);
    }
}
