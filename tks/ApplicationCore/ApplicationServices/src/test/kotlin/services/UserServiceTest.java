package services;

import interfaces.UserFilterPort;
import interfaces.UserSavePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    @Mock
    UserSavePort userSavePort;
    @Mock
    UserFilterPort userFilterPort;

    @BeforeEach
    public void init(){
        userService = new UserService(userSavePort, userFilterPort);
    }

    @Test
    public void GivenValidUser_RegistrationShouldSuccess(){

    }

    @Test
    public void GivenUser_With_DuplicatedEmail_RegistrationShouldFail(){

    }

    @Test
    public void GivenValidEmailAndNewRole_ShouldSuccess(){

    }

    @Test
    public void GivenValidEmailAndSameRole_ShouldSuccess_ButNotPersist(){

    }

    @Test
    public void GivenInvalidEmailAndAnyRole_ShouldFail(){

    }

    @Test
    public void GivenInvalidEmailAndAnyState_ShouldFail(){

    }

    @Test
    public void GivenValidEmailAndSameState_ShouldSuccess_ButNotPersist(){

    }

    @Test
    public void GivenValidEmailAndNewState_ShouldSuccess(){

    }

    @Test
    public void GivenValidEmail_ShouldReturnUserDetails(){

    }

    @Test
    public void GivenInvalidEmail_ShouldFail(){

    }
}