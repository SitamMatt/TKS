package repositories;


import fillers.UsersFiller;
import model.User;
import model.exceptions.LoginAlreadyTakenException;
import repositories.interfaces.IUsersRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class UsersRepository extends RepositoryBase<User> implements IUsersRepository {
    @Inject
    private UsersFiller usersFiller;

    @PostConstruct
    public void usersInit() {
        this.items = usersFiller.fillUsers();
    }

    @Override
    protected synchronized void map(User source, User destination) {
        destination.map(source);
    }

    @Override
    protected synchronized void validate(User item) throws LoginAlreadyTakenException {
        Optional<User> result = items.stream()
                .filter(x -> x.getLogin().equals(item.getLogin()))
                .findFirst();

        if (result.isPresent() && result.get().getId() != item.getId()) {
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
