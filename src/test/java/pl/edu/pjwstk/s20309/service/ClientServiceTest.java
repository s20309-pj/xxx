package pl.edu.pjwstk.s20309.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.s20309.entity.Client;
import pl.edu.pjwstk.s20309.repository.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    
    @Mock
    ClientRepository clientRepository;
    
    @Test
    void saveClient() {
        Client client = Client.builder()
                .id((long)1)
                .clientName("ble")
                .addingUser("user")
                .creationDateTime(LocalDate.now())
                .notes("notes")
                .description("opis")
                .build();


        when(clientRepository.save(client)).thenReturn(client);
        clientRepository.save(client);
        assertThat(client.getId()).isPositive();
        assertThat(client.getClientName()).isEqualTo("ble");
        assertThat(client.getAddingUser()).isEqualTo("user");
        assertThat(client.getCreationDateTime()).isEqualTo(LocalDate.now());
        assertThat(client.getNotes()).isEqualTo("notes");
        assertThat(client.getDescription()).isEqualTo("opis");
    }

    @Test
    void getClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(new Client()));
        Optional<Client> equipment = clientRepository.findById(1L);
        assertThat(equipment).isNotEmpty();
    }

    @Test
    void getAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(
                Client.builder()
                        .id((long)1)
                        .clientName("ble")
                        .addingUser("user")
                        .creationDateTime(LocalDate.now())
                        .notes("notes")
                        .description("opis")
                        .build(),
                Client.builder()
                        .id((long)1)
                        .clientName("ble")
                        .addingUser("user")
                        .creationDateTime(LocalDate.now())
                        .notes("notes")
                        .description("opis")
                        .build(),
                Client.builder()
                        .id((long)1)
                        .clientName("ble")
                        .addingUser("user")
                        .creationDateTime(LocalDate.now())
                        .notes("notes")
                        .description("opis")
                        .build()
        ));
        List<Client> clientList = clientRepository.findAll();
        assertThat(clientList).hasSize(3);
    }

    @Test
    void deleteById() {
        clientRepository.deleteById(1L);
        clientRepository.deleteById(1L);
        verify(clientRepository, times(2)).deleteById(1L);
    }
}
