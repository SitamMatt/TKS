package repositories;


import exceptions.RepositoryException;
import fillers.NewUsersFiller;
import mappers.Mapper;
import exceptions.LoginAlreadyTakenException;
import mappers.MapperHelper;
import model.User;
import repositories.interfaces.IUsersRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UsersRepository extends RepositoryBase<User> implements IUsersRepository {
    @Inject private NewUsersFiller usersFiller;
    @Inject
    private MapperHelper mapperHelper;

    @PostConstruct
    public void usersInit() {
        this.items = usersFiller.fill();
    }

    @Override
    protected void map(User source, User destination) throws RepositoryException {
        mapperHelper.getEntityMapper().map(source, destination);
    }

    @Override
    protected synchronized void validate(User item) throws LoginAlreadyTakenException {
        Optional<User> result = items.stream()
                .filter(x -> x.getLogin().equals(item.getLogin()))
                .findFirst();

        if (result.isPresent() && !result.get().getGuid().equals(item.getGuid())) {
            throw new LoginAlreadyTakenException();
        }
    }

    @Override
    public synchronized User findUserByLogin(String login) {
        return items.stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }
}
