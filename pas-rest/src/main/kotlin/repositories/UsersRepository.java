package repositories;


import fillers.NewUsersFiller;
import mappers.Mapper;
import exceptions.LoginAlreadyTakenException;
import model.User;
import repositories.interfaces.IUsersRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UsersRepository extends RepositoryBase<User> implements IUsersRepository {
    @Inject private NewUsersFiller usersFiller;
    @Inject private Mapper mapper;

    @PostConstruct
    public void usersInit() {
        this.items = usersFiller.fill();
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
