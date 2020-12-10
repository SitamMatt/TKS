package edu.p.lodz.pl.pas.mvc.repositories;

import edu.p.lodz.pl.pas.mvc.ListUtil;
import edu.p.lodz.pl.pas.mvc.model.User;
import edu.p.lodz.pl.pas.mvc.model.exceptions.LoginAlreadyTakenException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectAlreadyStoredException;
import edu.p.lodz.pl.pas.mvc.model.exceptions.ObjectNotFoundException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

@ApplicationScoped
public class InMemoryIUsersRepository implements IUsersRepository {
    private List<User> users;
    @Inject
    private UsersFiller usersFiller;

    protected void validateLogin(User user) throws LoginAlreadyTakenException {
        Optional<User> result = users.stream()
                .filter(x -> x.getLogin().equals(user.getLogin()))
                .findFirst();

        if(result.isPresent() && result.get().getId() != user.getId()){
            throw new LoginAlreadyTakenException();
        }
    }

    @PostConstruct
    public void usersInit() {
        List<User> list = usersFiller.fillUsers();
        users = new ArrayList<>(list.size());
        list.forEach(user -> {
            try {
                add(user);
            } catch (LoginAlreadyTakenException | ObjectNotFoundException e) {
                e.printStackTrace();
            }

        });
    }


    protected User getById(UUID id) {
        return users.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean has(User user) {
        return users.stream().anyMatch(x -> x.getId() == user.getId());
    }

    @Override
    public synchronized void add(User user) throws LoginAlreadyTakenException, ObjectNotFoundException {
        validateLogin(user);
        if(has(user)) throw new ObjectNotFoundException();
        if(user.getId() == null) {
            assignId(user);
        }
        users.add(user);
    }

    @Override
    public synchronized List<User> getAll() throws CloneNotSupportedException {
        return ListUtil.deepCopy(users);
    }

    @Override
    public synchronized User findUserByLogin(String login) {
        try {
            return (User) Objects.requireNonNull(users.stream()
                    .filter(x -> x.getLogin().equals(login))
                    .findFirst()
                    .orElse(null)).clone();
        } catch (CloneNotSupportedException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public synchronized void update(User user) throws ObjectNotFoundException, LoginAlreadyTakenException {
        validateLogin(user);
        User original = getById(user.getId());
        if(original == null) throw new ObjectNotFoundException();
        original.map(user);
    }

    private void assignId(User user) {
        try {
            RefUtils.setFieldValue(user, "id", UUID.randomUUID());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
