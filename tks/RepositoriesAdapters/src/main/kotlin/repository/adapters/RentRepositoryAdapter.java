package repository.adapters;

import repository.data.AbstractResourceEntity;
import repository.data.RentEntity;
import repository.data.UserEntity;
import ports.secondary.RentPersistencePort;
import ports.secondary.RentSearchPort;
import repository.mappers.RentMapper;
import domain.model.Rent;
import domain.model.values.AccessionNumber;
import repository.repositories.RepositoryBase;

import java.util.Objects;

public class RentRepositoryAdapter implements RentSearchPort, RentPersistencePort {

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
            var resource = resourceRepository.find(x -> Objects.equals(x.getAccessionNumber(), rent.getResourceId()));
            var emailValue = rent.getUserEmail().getValue();
            var user = userRepository.find(x -> x.getEmail().equals(emailValue));
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
    public Rent findActiveByResourceId(AccessionNumber resourceId) {
        var accessionNumberValue = resourceId.getValue();
        var entity = repository.find(x -> Objects.equals(x.getResource().getAccessionNumber(), accessionNumberValue) && x.getEndDate() == null);
        return mapper.mapEntityToDomainObject(entity);
    }
}
