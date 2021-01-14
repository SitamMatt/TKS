package repositories.interfaces;


import model.kto.User;

public interface IUsersRepository extends IRepositoryBase<User> {
    User findUserByLogin(String login);
}
