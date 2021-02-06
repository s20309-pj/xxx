package pl.edu.pjwstk.s20309.service;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.repository.UserRepository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;
    
    @Test
    void saveUser() {
        User newUser = new User(12L, "Baba", "Yaga", "baba.yaga", "baba@ble.pl");
        when(userRepository.save(newUser)).thenReturn(newUser);
        userRepository.save(newUser);

        assertThat(newUser.getId()).isPositive();
        assertThat(newUser.getFirstName()).isEqualTo("Baba");
        assertThat(newUser.getLastName()).isEqualTo("Yaga");
        assertThat(newUser.getUsername()).isEqualTo("baba.yaga");
        assertThat(newUser.getEmail()).isEqualTo("baba@ble.pl");

    }

    @Test
    void getUserById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        Optional<User> equipment = userRepository.findById(1L);
        assertThat(equipment).isNotEmpty();
    }

    @Test
    void getAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(
                new User(12L, "Baba", "Yaga", "baba.yaga", "baba@ble.pl"),
                new User(12L, "Baba", "Fanga", "baba.fanga", "fanga@ble.pl"),
                new User(12L, "Baba", "XXX", "baba.xxx", "xxx@ble.pl"
                )));
        List<User> userList = userRepository.findAll();
        assertThat(userList).hasSize(3);
    }
    
    @Test
    void deleteUserById() {
        userRepository.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }
}
