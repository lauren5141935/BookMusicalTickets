package BookMusicalTickets.example.BookMusicalTickets.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi api() {
    return GroupedOpenApi.builder()
        .group("BookMusicalTickets")
        .pathsToMatch("/**")
        .build();
  }

  @Bean
  public OpenAPI customOpenAPI() {
    SecurityScheme securityScheme = new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("Bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    SecurityRequirement requirement = new SecurityRequirement().addList("bearerAuth");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
        .security(Arrays.asList(requirement))
        .info(new Info().title("BookMusicalTickets API")
            .description("뮤지컬 티켓팅 설계 API.")
            .version("v1.0.0"));
  }
}
