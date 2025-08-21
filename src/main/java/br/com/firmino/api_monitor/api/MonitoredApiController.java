package br.com.firmino.api_monitor.api;

import br.com.firmino.api_monitor.user.User;
import br.com.firmino.api_monitor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.management.monitor.MonitorMBean;
import java.util.List;

@RestController
@RequestMapping("/api/service")
public class MonitoredApiController {
    @Autowired
    private MonitoredApiService monitoredApiService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<MonitoredApi> getUserServices(@AuthenticationPrincipal UserDetails userDetails){
        User currentUser = findUserByDetails(userDetails);
        return monitoredApiService.getServicesForUser(currentUser);
    }

    @PostMapping
    public MonitoredApi createService(@RequestBody MonitoredApi newService, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = findUserByDetails(userDetails);
        return monitoredApiService.createService(newService, currentUser);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteService(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        User currentUser = findUserByDetails(userDetails);
        monitoredApiService.deleteService(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/public")
    public List<MonitoredApi> getPublicServices(){
        return monitoredApiService.getPublicService();
    }

    private User findUserByDetails(UserDetails userDetails){
        return userRepository.findByUsernameOrEmail(userDetails.getUsername(), userDetails.getUsername())
                .orElseThrow(()->new RuntimeException("Usuário não encontrado."));
    }
}
