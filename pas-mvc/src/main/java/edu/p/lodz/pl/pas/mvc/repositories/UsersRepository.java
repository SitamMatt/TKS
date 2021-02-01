package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.fillers.UsersFiller;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.repositories.interfaces.IUsersRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
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

    @Override
    public synchronized ArrayList<User> filterByLogin(String login) {
        ArrayList<User> resultList = new ArrayList<>();
        for (User item: this.items){
            if (item.getLogin().contains(login)){
                resultList.add(item);
            }
        }
        return resultList;
    }
}
