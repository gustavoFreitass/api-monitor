package br.com.firmino.api_monitor.api;

import br.com.firmino.api_monitor.dto.ApiServiceStatusDTO; // 1. Importar nosso DTO
import br.com.firmino.api_monitor.entity.User;
import br.com.firmino.api_monitor.repository.UserRepository;
import br.com.firmino.api_monitor.service.MonitoredApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services") // 2. Corrigido para "services" (plural), que é uma convenção melhor
public class MonitoredApiController {

    @Autowired
    private MonitoredApiService monitoredApiService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<ApiServiceStatusDTO> getUserServices(@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = findUserByDetails(userDetails);
        // 3. O tipo de retorno agora é o DTO, como no serviço
        return monitoredApiService.getServicesForUser(currentUser);
    }


    @GetMapping("/public")
    public List<ApiServiceStatusDTO> getPublicServices() {
        // 4. Chamando o método correto no serviço
        return monitoredApiService.getPublicServices();
    }

    @PostMapping
    public MonitoredApi createService(@RequestBody MonitoredApi newService, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = findUserByDetails(userDetails);
        return monitoredApiService.createService(newService, currentUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = findUserByDetails(userDetails);
        monitoredApiService.deleteService(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    private User findUserByDetails(UserDetails userDetails) {
        return userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado no banco de dados."));
    }
}