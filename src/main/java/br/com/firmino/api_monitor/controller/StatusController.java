package br.com.firmino.api_monitor.controller;

import br.com.firmino.api_monitor.entity.StatusCheck;
import br.com.firmino.api_monitor.service.ApiCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Esta classe é o nosso Controller REST. É ela quem recebe as requisições HTTP
 * da internet e as direciona para os serviços corretos.
 * {@code @RestController} é uma anotação do Spring que combina {@code @Controller} e {@code @ResponseBody},
 * indicando que os métodos desta classe retornam dados diretamente no corpo da resposta HTTP.
 * {@code @RequestMapping} define a URL base para todos os endpoints deste controlador.
 * Neste caso, todos os caminhos começarão com "/api/status".
 */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    /**
     * A anotação {@code @Autowired} é usada para Injeção de Dependência.
     * O Spring se encarrega de criar e fornecer uma instância de {@code ApiCheckService},
     * que será usada neste controlador para acessar a lógica de negócio.
     */
    @Autowired
    private ApiCheckService apiCheckService;

    /**
     * Este método lida com requisições HTTP GET.
     * {@code @GetMapping} mapeia as requisições GET para este método. Como não tem
     * um caminho específico, ele usa o caminho base do controlador ("/api/status").
     * Retorna uma lista de {@code StatusCheck}, que contém o status de todas as
     * verificações realizadas.
     *
     * @return Uma {@code List} de objetos {@code StatusCheck}.
     */
    @GetMapping
    public List<StatusCheck> listarTodos() {
        return apiCheckService.buscarTodosOsStatus();
    }
}