package services;

import model.User;
import model.exceptions.ObjectAlreadyStoredException;
import model.exceptions.ObjectNotFoundException;
import model.exceptions.RepositoryException;
import repositories.interfaces.IUsersRepository;
import services.dto.UserDto;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
public class UsersService {
    @Inject
    private IUsersRepository usersRepository;

    public List<UserDto> getAllUsers() {
        return usersRepository.getAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public UserDto find(String login) {
        User user = usersRepository.findUserByLogin(login);
        return map(user);
    }

    public void save(UserDto user) throws ObjectNotFoundException, ObjectAlreadyStoredException, RepositoryException {
        if (usersRepository.has(user.getId())) {
            usersRepository.update(mapBack(user));
        } else {
            usersRepository.add(mapBack(user));
        }
    }

    protected UserDto map(User user) {
        if(user == null) {
            return new UserDto();
        }
        return new UserDto(
                user.getId(),
                user.isActive(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getLogin(),
                user.getPassword()
        );
    }

    protected User mapBack(UserDto dto) {
        return new User(
                dto.getId(),
                dto.isActive(),
                dto.getRole(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getLogin(),
                dto.getPassword()
        );
    }
}