package repositories.interfaces;


import model.User;

public interface IUsersRepository extends IRepositoryBase<User> {
    User findUserByLogin(String login);
}
