package pl.edu.pjwstk.s20309;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
public class UserServiceTestIT {

    @Autowired
    private UserService userService;
    
    @Test
    void shouldNotFindAll() {
        List<User> all = userService.getAllUsers();
        assertThat(all).isEmpty();

    }
    @Test
    void shouldFindAllUsers() {
        userService.save(new User(1L,"Hopsasa", "BleBleBle", "baba.yaga","baba@ble.pl"));
        List<User> all = userService.getAllUsers();
        assertThat(all).isNotEmpty();
    }

    @Test
    void shouldSavePlayer(){
        User player = userService.save(new User(1L,"Hopsasa", "BleBleBle", "baba.yaga","baba@ble.pl"));
        assertThat(player.getId()).isPositive();
    }

    @Test
    void shouldThrowException() {
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> userService.getOneUser(10L));
    }
}
