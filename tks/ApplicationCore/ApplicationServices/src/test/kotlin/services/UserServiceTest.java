package services;

import exceptions.DuplicatedEmailException;
import exceptions.UserNotFoundException;
import drivenports.UserQueryPort;
import drivenports.UserSavePort;
import model.User;
import model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    UserService userService;

    User sampleUser;
    String sampleEmail;

    @Mock
    UserSavePort userSavePort;
    @Mock
    UserQueryPort userQueryPort;

    @BeforeEach
    public void init(){
        userService = new UserService(userSavePort, userQueryPort);
        sampleUser = new User("mszewc@edu.pl", UserRole.ADMIN, "####", true);
        sampleEmail = "mszewc@edu.pl";
    }

    @Test
    public void GivenValidUser_RegistrationShouldSuccess() throws DuplicatedEmailException {
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(null);
        userService.register(sampleUser);
        verify(userSavePort).add(eq(sampleUser));
    }

    @Test
    public void GivenUser_With_DuplicatedEmail_RegistrationShouldFail() throws DuplicatedEmailException {
        var duplicatedUser = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(duplicatedUser);
        assertThrows(DuplicatedEmailException.class, () -> userService.register(sampleUser));
        verify(userSavePort, never()).add(any());
    }

    @Test
    public void GivenValidEmailAndNewRole_ShouldSuccess() throws UserNotFoundException {
        var user = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(user);
        userService.changeRole(sampleEmail, UserRole.ADMIN);
        verify(userSavePort).update(eq(user));
        assertEquals(UserRole.ADMIN, user.getRole());
    }

    @Test
    public void GivenValidEmailAndSameRole_ShouldSuccess_ButNotPersist() throws UserNotFoundException {
        var user = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(user);
        userService.changeRole(sampleEmail, UserRole.CLIENT);
        verify(userSavePort, never()).update(any());
        assertEquals(UserRole.CLIENT, user.getRole());
    }

    @Test
    public void GivenInvalidEmailAndAnyRole_ShouldFail(){
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.changeRole(sampleEmail, UserRole.CLIENT));
        verify(userSavePort, never()).update(any());
    }

    @Test
    public void GivenInvalidEmailAndAnyState_ShouldFail(){
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.changeState(sampleEmail, true));
        verify(userSavePort, never()).update(any());
    }

    @Test
    public void GivenValidEmailAndSameState_ShouldSuccess_ButNotPersist() throws UserNotFoundException {
        var user = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(user);
        userService.changeState(sampleEmail, true);
        verify(userSavePort, never()).update(any());
        assertTrue(user.getActive());
    }

    @Test
    public void GivenValidEmailAndNewState_ShouldSuccess() throws UserNotFoundException {
        var user = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(user);
        userService.changeState(sampleEmail, false);
        verify(userSavePort).update(eq(user));
        assertFalse(user.getActive());
    }

    @Test
    public void GivenValidEmail_ShouldReturnUserDetails() throws UserNotFoundException {
        var user = new User(sampleEmail, UserRole.CLIENT, "wwww", true);
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(user);
        var result = userService.getDetails(sampleEmail);
        assertSame(result, user);
        assertEquals(result, user);
    }

    @Test
    public void GivenInvalidEmail_ShouldFail(){
        when(userQueryPort.findByEmail(eq(sampleEmail))).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.getDetails(sampleEmail));
    }
}