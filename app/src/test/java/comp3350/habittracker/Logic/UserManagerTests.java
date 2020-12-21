package comp3350.habittracker.Logic;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import comp3350.habittracker.Persistence.UserPersistence;


public class UserManagerTests {
    UserPersistence userPersistence;
    private UserManager userManager;

    @Before
    public void setUp(){
        //mock interface
        userPersistence = mock(UserPersistence.class);
        userManager = new UserManager(userPersistence);
    }

    @Test
    public void testLoginSuccess(){
        when(userPersistence.getUser("test_user", "test_pass")).thenReturn(true);
        assertEquals(userManager.login("test_user", "test_pass"), UserManager.SUCCESS);
        //verify that method was called with the right inputs
        verify(userPersistence).getUser("test_user", "test_pass");
    }

    @Test
    public void testLoginFail(){
        //try incorrect info
        when(userPersistence.getUser("test_user", "test_pass")).thenReturn(false);
        assertEquals(userManager.login("test_user", "test_pass"), UserManager.DB_FAIL);

        verify(userPersistence).getUser("test_user", "test_pass");

        //give invalid input
        assertEquals(userManager.login("", ""), UserManager.INPUT_FAIL);
    }

    @Test
    public void testRegisterSuccess(){
        when(userPersistence.addUser("user", "pass")).thenReturn(true);
        assertEquals(userManager.register("user", "pass"), UserManager.SUCCESS);
        //verify that method was called with the right inputs
        verify(userPersistence).addUser("user", "pass");
    }

    @Test
    public void testRegisterFail(){
        when(userPersistence.addUser("user", "pass")).thenReturn(false);
        assertEquals(userManager.register("user", "pass"), UserManager.DB_FAIL);
        //verify that method was called with the right inputs
        verify(userPersistence).addUser("user", "pass");

        //invalid input
        assertEquals(userManager.register("user12345678912345678915265", "pass"), UserManager.INPUT_FAIL);
    }

    @Test
    public void testChangePassword(){
        assertEquals(userManager.changePassword("user","p"), UserManager.SUCCESS);
        //verify that method was called with the right inputs
        verify(userPersistence).changePassword("user", "p");
        //invalid input
        assertEquals(userManager.changePassword("user","p123456789123456789123"), UserManager.INPUT_FAIL);
    }
}
