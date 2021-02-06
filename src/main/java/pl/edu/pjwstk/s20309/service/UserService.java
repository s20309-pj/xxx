package pl.edu.pjwstk.s20309.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.exception.notFound.NotEmptyException;
import pl.edu.pjwstk.s20309.exception.notFound.UserNotFoundException;
import pl.edu.pjwstk.s20309.repository.UserRepository;



@Service
@AllArgsConstructor
public class UserService  {

    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getOneUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public User updateUser(Long id, User updateUser) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User with id " + id + " does not exist"));

        if (updateUser.getFirstName() != null) {
            user.setFirstName(updateUser.getFirstName());
            if (updateUser.getLastName() != null) {
                user.setLastName(updateUser.getLastName());
            }
            if (updateUser.getEmail() != null) {
                user.setEmail(updateUser.getEmail());
            }
            if(updateUser.getUsername() != null){
                user.setUsername(updateUser.getUsername());
            }
        }
        return userRepository.save(user);
    }
    
    public User save(User saveUser) {
        if (saveUser.getFirstName() == null || saveUser.getLastName() == null || saveUser.getEmail() == null) {
            throw new NotEmptyException("name");
        }
        List<User> userList = getAllUsers().stream()
                .filter(existUser->existUser.getUsername().equals(saveUser.getUsername())).collect(Collectors.toList());
        if (!userList.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return userRepository.save(saveUser);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
