package org.loose.fis.sre.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.LoggedUser;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.FileSystemService;
import org.loose.fis.sre.services.UserService;

import java.io.IOException;



import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String IASMINA = "Iasmina";
    public static final String MARIA = "Maria";

    @BeforeAll
    static void beforeAll() {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        FileSystemService.initDirectory();

    }

    @AfterAll
    static void afterAll() throws IOException {
        System.out.println("After class");
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());

    }

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        FileUtils.cleanDirectory(FileSystemService.getapplicationhomefolder().toFile());
        UserService.initDatabase();


    }

    @AfterEach
    void tearDown() {
    UserService.closeDatabase();
    }


    @Test
    @DisplayName("Database is initialized and there are no users")
    void DataBaseIsInitializedAndNoUserIsInIt()
    {

        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();


    }
    @Test
    @DisplayName("Client is added to database")
    void TestClientisAddedToDatabase() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addUser(IASMINA, IASMINA,"Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(IASMINA);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(IASMINA,IASMINA));
        assertThat(user.getEmail()).isEqualTo("purcar.iasminamaria@yahoo.com");
        assertThat(user.getAddress()).isEqualTo("Dobra");
        assertThat(user.getPhone()).isEqualTo("078632382");
        assertThat(user.isAdmin()).isEqualTo(false);
    }
    @Test
    @DisplayName("Admin is added to database")
    void TestAdminisAddedToDatabase() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addAdmin("Maria", "Maria","Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user=UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(MARIA);
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword(MARIA,MARIA));
        assertThat(user.getEmail()).isEqualTo("purcar.iasminamaria@yahoo.com");
        assertThat(user.getAddress()).isEqualTo("Dobra");
        assertThat(user.getPhone()).isEqualTo("078632382");
        assertThat(user.isAdmin()).isEqualTo(true);
    }
    @Test
    @DisplayName("User cannot be added twice")
    void UserAlreadyExists(){
        assertThrows(UsernameAlreadyExistsException.class, ()->{

            UserService.addUser("Maria", "Maria","Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
            UserService.addUser("Maria", "Maria","Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
                });

    }
    @Test
    @DisplayName("Password must contain at least one upper case")
    void  NoUpperCaseInPassword()
    {
        assertThrows(NoUpperCaseException.class,()->
        {
            UserService.addUser("Maria", "maria","Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
        });
    }
    @Test
    @DisplayName("All fields must be completed")
    void  EmptyFields()
    {
        assertThrows(UncompletedFieldsException.class,()->
        {
            UserService.addUser("Maria", "maria","","purcar.iasminamaria@yahoo.com","Dobra","078632382");
            UserService.addUser("Maria", "maria","ana","purcar.iasminamaria@yahoo.com","D","078632382");
        });
    }
    @Test
    @DisplayName("Incorrect username at log in")
    void IncorrectUsername() {
        assertThrows(InvalidUsernameException.class, () ->
        {  UserService.addUser("Ayana","Alina","Purcar Alina","purcar.alina@yahoo.com","Dobra","0733959475");
            UserService.userExists("ayana","Alina");
        });
    }

    @Test
    @DisplayName("Incorrect password at log in")
    void IncorrectPassword() {
        assertThrows(IncorrectPasswordException.class, () ->
        {    UserService.addUser("Alina","Alina","Purcar Alina","purcar.alina@yahoo.com","Dobra","0733959475");
             UserService.userExists("Alina","altceva");
        });
    }

    @Test
    @DisplayName("Check if the user is admin")
    void CheckIsAdmin() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addAdmin("Ana", "Maria","Purcar Iasmina","purcar.iasminamaria@yahoo.com","Dobra","078632382");
        assertThat(UserService.checkIsAdmin("Ana")).isEqualTo(true);
    }
    @Test
    @DisplayName("Password is encoded correctly")
    void checkEncoding() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addUser("Florina","Florina","Popescu Florina","florina@yahoo.com","Deva","07839232");
        assertThat(UserService.getAllUsers().get(0).getPassword()).isEqualTo(UserService.encodePassword("Florina","Florina"));
    }
    @Test
    @DisplayName("List of all clients")
    void CheckIfReturnsAllClients() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException {
        UserService.addUser("Sandra","Sandra","Popescu Sandra","sandra@yahoo.com","Deva","083792130");
        UserService.addUser("Andra","Andra","Popescu Andra","andra@yahoo.com","Deva","0792130");
        UserService.addAdmin("Andrei","Andrei","Popescu Andrei","Andrei@yahoo.com","Deva","087392");
        assertThat(UserService.AllUsers().size()).isEqualTo(2);
    }
    @Test
    @DisplayName("Deleting works ok")
    void CheckIfDeletingIsOk() throws NoUpperCaseException, UsernameAlreadyExistsException, UncompletedFieldsException, IncorrectPasswordException, InvalidUsernameException {
        UserService.addUser("Sandra","Sandra","Popescu Sandra","sandra@yahoo.com","Deva","083792130");
        UserService.addUser("Andra","Andra","Popescu Andra","andra@yahoo.com","Deva","0792130");
        UserService.deleteRecord("Andra");
        assertThat(UserService.AllUsers().size()).isEqualTo(1);
        assertThat(UserService.userExists("Sandra","Sandra")).isEqualTo(UserService.getAllUsers().get(0));
    }
}