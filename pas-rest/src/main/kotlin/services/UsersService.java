package services;

import dto.UserBaseDto;
import dto.UserGetDto;
import mappers.Mapper;
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
    @Inject
    private Mapper mapper;

    public void add(UserBaseDto model) throws ObjectAlreadyStoredException, RepositoryException {
        var user = mapper.getMapper().map(model, User.class);
        usersRepository.add(user);
    }

    public void update(UUID guid, UserBaseDto model) throws ObjectAlreadyStoredException, RepositoryException, ObjectNotFoundException {
        var user = mapper.getMapper().map(model, User.class);
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

    public UserGetDto find(UUID uuid) throws ObjectNotFoundException {
        var user = usersRepository.getByGuid(uuid);
        if (user == null) throw new ObjectNotFoundException();
        return mapper.getMapper().map(user, UserGetDto.class);
    }

    public UserGetDto find(String login) throws ObjectNotFoundException {
        var user = usersRepository.findUserByLogin(login);
        if (user == null) throw new ObjectNotFoundException();
        return mapper.getMapper().map(user, UserGetDto.class);
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
                result |= x.getFirstName().contains(search);
                result |= x.getLastName().contains(search);
                result |= x.getLogin().contains(search);
                result |= x.getRole().toString().contains(search);
                return result;
            });
        }
        return stream.map(x -> mapper.getMapper().map(x, UserGetDto.class))
                .collect(Collectors.toList());
    }
}
