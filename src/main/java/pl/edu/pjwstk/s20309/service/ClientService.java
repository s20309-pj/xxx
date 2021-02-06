package pl.edu.pjwstk.s20309.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.s20309.dto.ClientDTO;
import pl.edu.pjwstk.s20309.entity.Client;
import pl.edu.pjwstk.s20309.entity.Task;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.exception.notFound.ClientNotFoundException;
import pl.edu.pjwstk.s20309.repository.ClientRepository;
import pl.edu.pjwstk.s20309.repository.TaskRepository;
import pl.edu.pjwstk.s20309.repository.UserRepository;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final TaskService taskService;
    private final TaskRepository taskRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getById(Long id) {
        return clientRepository.findById(id);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    private Client mapInputToClient(ClientDTO client, User user) {
        String loggedUserName = userRepository.findFullNameByUsername(user.getUsername())
                .orElseGet(user::getUsername);

        return Client.builder()
                .addingUser(loggedUserName)
                .creationDateTime(LocalDate.now())
                .clientName(client.getClientName())
                .description(client.getDescription())
                .notes(client.getNotes())
                .build();
    }

    public Client addNewClient(ClientDTO newClient, User user) {
        return clientRepository.save(mapInputToClient(newClient, user));
    }

    public Client updateClient(Long id, ClientDTO updateClient) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with id " + id + " does not exist"));

        if (updateClient.getClientName() != null) {
            client.setClientName(updateClient.getClientName());
        }
        if (updateClient.getNotes() != null) {
            client.setNotes(updateClient.getNotes());
        }
        if (updateClient.getDescription() != null) {
            client.setDescription(updateClient.getDescription());
        }
        if (updateClient.getAddingUser() != null) {
            client.setAddingUser(updateClient.getAddingUser());
        }

        return clientRepository.save(client);
    }

    public Task assignClientToTask(Long taskId, Long clientId) {
        Client existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Client with id" + clientId + "does not exist"));

        Task existingTask = taskService.getOneTask(taskId);

        existingTask.assignClientToTask(existingClient);
        return taskRepository.saveAndFlush(existingTask);
    }
}
