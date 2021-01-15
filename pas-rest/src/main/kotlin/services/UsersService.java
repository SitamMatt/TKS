package services;

import dto.UserBaseDto;
import dto.UserCreateDto;
import dto.UserGetDto;
import mappers.Mapper;
import mappers.MapperHelper;
import model.UserRole;
import exceptions.ObjectAlreadyStoredException;
import exceptions.ObjectNotFoundException;
import exceptions.RepositoryException;
import model.User;
import repositories.interfaces.IUsersRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class UsersService {
    @Inject
    private IUsersRepository usersRepository;
    @Inject private MapperHelper helper;

    public void add(UserCreateDto model) throws ObjectAlreadyStoredException, RepositoryException {
        var user = helper.getMapper().mapDtoToUser(model);
        usersRepository.add(user);
    }

    public void update(UUID guid, UserCreateDto model) throws RepositoryException, ObjectNotFoundException {
        var user = helper.getMapper().mapDtoToUser(model);
        user.setGuid(guid);
        usersRepository.update(user);
    }

//    protected UserDto map(User user) {
//        if(user == null) {
//            return new UserDto();
//        }
//        return new UserDto(
//                user.getGuid(),
//                user.isActive(),
//                user.getRole(),
//                user.getFirstName(),
//                user.getLastName(),
//                user.getLogin(),
//                user.getPassword()
//        );
//    }

//    protected User mapBack(UserDto dto) {
//        return new User(
//                dto.getId(),
//                dto.isActive(),
//                dto.getRole(),
//                dto.getFirstName(),
//                dto.getLastName(),
//                dto.getLogin(),
//                dto.getPassword()
//        );
//    }

    public UserGetDto find(UUID uuid) {
        var user = usersRepository.getByGuid(uuid);
        return helper.getMapper().mapUserToDto(user);
    }

    public UserGetDto find(String login) {
        var user = usersRepository.findUserByLogin(login);
        return helper.getMapper().mapUserToDto(user);
    }

    public List<UserGetDto> filter(String type, int page, int maxResults, String search) {
        if(page != 0 && maxResults == 0) maxResults = usersRepository.count() / page;
        var stream = usersRepository.getPaged(page, maxResults).stream();
        if(type != null) switch(type){
            case "WORKER":
                stream = stream.filter(x -> x.getRole() == UserRole.WORKER);
                break;
            case "ADMIN":
                stream = stream.filter(x -> x.getRole() == UserRole.ADMIN);
                break;
            case "CLIENT":
                stream = stream.filter(x -> x.getRole() == UserRole.CLIENT);
                break;
        }
        if(search != null){
            stream = stream.filter(x -> {
                var result = x.getGuid().toString().contains(search);
                result |= x.getFirstname().contains(search);
                result |= x.getLastname().contains(search);
                result |= x.getLogin().contains(search);
                result |= x.getRole().toString().contains(search);
                return result;
            });
        }
        return stream.map(x -> helper.getMapper().mapUserToDto(x))
                .collect(Collectors.toList());
    }
}
