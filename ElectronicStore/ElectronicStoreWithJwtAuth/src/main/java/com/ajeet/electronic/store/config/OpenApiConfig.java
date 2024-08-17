package com.ajeet.electronic.store.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


// SWAGGER CONFIGURATION
@OpenAPIDefinition(
        info = @Info(
                title = "ElectronicStore API",
                description = "Doing shopping",
                summary = "The electronicStore api will add,delete,create, and update",
                termsOfService = "T&C",
                contact = @Contact(
                        name = "Ajeet Kushwaha",
                        email = "help@abc.com"
                ),
                version = "v1",
                license = @License(
                        name = "Your Licence No"
                )
        ),
        servers = {
                @Server(
                        description = "Dev",
                        url = "http://localhost:9902"
                ),
                @Server(
                        description = "Prod",
                        url = "http://localhost:9902"
                ),
        },
        security = @SecurityRequirement(
                name = "auth"
        )
)

@SecurityScheme(
        name = "auth",
        in = SecuritySchemeIn.HEADER,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "Bearer",
        description = "Security desc"

)
public class OpenApiConfig {
}
