package trandafyl.dev.hackathontest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(name = "Anna Huk, Liudmyla Minkovets, Maksym Myna, Martsin Oleksandr"),
                description = "OpenAPI documentaton for out int20h test task",
                title = "тра́ндафиль auction"
        ),
        servers = @Server(
                description = "Local Environment",
                url = "http://localhost:8080"
        )
)
@SpringBootApplication
public class HackathonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(HackathonTestApplication.class, args);
    }

}
