package br.com.firmino.api_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Esta é a classe principal da nossa aplicação, o ponto de entrada.
 * <p>
 * {@code @SpringBootApplication} é uma anotação de conveniência que combina três outras anotações do Spring:
 * <ul>
 * <li>{@code @EnableAutoConfiguration}: Configura automaticamente a aplicação com base nas dependências
 * que você adicionou ao projeto.</li>
 * <li>{@code @ComponentScan}: Escaneia (procura) por componentes do Spring (como {@code @Component}, {@code @Service},
 * {@code @Repository}, etc.) na classe e nos pacotes abaixo dela para registrá-los.</li>
 * <li>{@code @Configuration}: Indica que a classe pode ser usada para registrar beans e configurar a aplicação.</li>
 * </ul>
 * <p>
 * {@code @EnableScheduling} habilita o agendamento de tarefas, permitindo que a gente use a anotação
 * {@code @Scheduled} na nossa classe de serviço para executar métodos em intervalos de tempo fixos.
 */
@SpringBootApplication
@EnableScheduling
public class ApiMonitorApplication{

	/**
	 * O método 'main' é o ponto de partida padrão para qualquer aplicação Java.
	 * <p>
	 * {@code SpringApplication.run()} é o método estático que inicia a aplicação Spring Boot.
	 * Ele cria e configura o contexto da aplicação, fazendo com que tudo funcione junto.
	 *
	 * @param args Os argumentos da linha de comando que podem ser passados ao iniciar a aplicação.
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiMonitorApplication.class, args);
	}
}
