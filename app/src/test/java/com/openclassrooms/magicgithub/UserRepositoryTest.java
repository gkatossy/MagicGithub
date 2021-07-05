   package com.openclassrooms.magicgithub;

import com.openclassrooms.magicgithub.api.FakeApiServiceGenerator;
import com.openclassrooms.magicgithub.di.Injection;
import com.openclassrooms.magicgithub.model.User;
import com.openclassrooms.magicgithub.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import java.util.stream.Collectors;

import static com.openclassrooms.magicgithub.api.FakeApiServiceGenerator.FAKE_USERS;
import static com.openclassrooms.magicgithub.api.FakeApiServiceGenerator.FAKE_USERS_RANDOM;
import static org.junit.Assert.*;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;


/**
 * Unit test, which will execute on a JVM.
 * Testing UserRepository.
 */
@RunWith(JUnit4.class)
public class UserRepositoryTest {
    
    private UserRepository userRepository;
    
    @Before
    public void setup() {
        userRepository = Injection.createUserRepository();
    }
    
    @Test
    public void getUsersWithSuccess() {
        // Assurer l'existance de la liste usersActual dans la liste usersExpected
        // donc il s'agit de comparer les liste
        List<User> usersActual = userRepository.getUsers();
        List<User> usersExpected = FAKE_USERS;

        // renvoi un defaut suite  à la verification de null dans la list expected
        assertThat(usersActual, containsInAnyOrder(usersExpected.toArray()));
    }

    @Test
    public void generateRandomUserWithSuccess() {
        // On a rencontré une erreur dans la ligne 43: le programme est entrain de vider une liste renvoyer par
        // getUsers qui est null
        userRepository.getUsers().clear();

        // Apres la correction de getUsers la fonction generateRandomUser doit au minimum apporter l'ajout
        // d'un seul element dans la liste utilisateurs
        userRepository.generateRandomUser();
        // quand generateRandom fonctionne correctement je dois pouvoir recuperer l'objet d'indice zero
        User user = userRepository.getUsers().get(0);
        assertEquals(1, userRepository.getUsers().size());
        // On verifie que l'objet recuperé provient/existe bien de la liste FAKE_USERS_RANDOM
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getAvatarUrl).collect(Collectors.toList()).contains(user.getAvatarUrl()));
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getId).collect(Collectors.toList()).contains(user.getId()));
        assertTrue(FAKE_USERS_RANDOM.stream().map(User::getLogin).collect(Collectors.toList()).contains(user.getLogin()));
        // On verifie que l'objet recuperé n'existe pas dans la liste FAKE_USERS
        assertFalse(FAKE_USERS.stream().map(User::getAvatarUrl).collect(Collectors.toList()).contains(user.getAvatarUrl()));
        assertFalse(FAKE_USERS.stream().map(User::getId).collect(Collectors.toList()).contains(user.getId()));
        assertFalse(FAKE_USERS.stream().map(User::getLogin).collect(Collectors.toList()).contains(user.getLogin()));
    }

    @Test
    public void deleteUserWithSuccess() {
        // recuperer l'utilisateur à supprimer premier element
        User userToDelete = userRepository.getUsers().get(0);
        // Appel de la fonction delete
        userRepository.deleteUser(userToDelete);
        // Verifier que l'utilisateur n'est plus existant dans la liste
        assertFalse(userRepository.getUsers().contains(userToDelete));
    }
}