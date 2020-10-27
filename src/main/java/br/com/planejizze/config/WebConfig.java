package br.com.planejizze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableScheduling
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.planejizze.resource"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Auth", "Controlar autenticação"),
                        new Tag("Banco", "Controlar os bancos do usuário"),
                        new Tag("Cartão", "Controlar os cartões do usuário"),
                        new Tag("Categorias de despesas", "Controlar categorias de despesas do usuário"),
                        new Tag("Categorias de planejamento", "Controlar categorias de planejamentos do usuário"),
                        new Tag("Categorias de receitas", "Controlar categorias de receitas do usuário"),
                        new Tag("Despesa", "Controlar despesas do usuário"),
                        new Tag("Receita", "Controlar receitas do usuário"),
                        new Tag("Planejamento", "Controlar planejamentos do usuário"),
                        new Tag("Relatórios", "Gerar relatórios para o usuário"),
                        new Tag("Usuário", "Controlar dados do usuário"),
                        new Tag("File", "Faz upload de imagens e buscar por imagens"),
                        new Tag("Preferências", "Altera dados de usuário e outras preferências"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Api - Planejizze")
                .description("Api base do sistema de controle financeiro Planejizze")
                .version("1")
                .contact(new Contact("Weskley", "github.com/WeskleyP", "weskleypedro@gmail.com"))
                .build();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false)
                .favorParameter(false)
                .ignoreAcceptHeader(false)
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON)
                .mediaType("xml", MediaType.APPLICATION_XML);
    }
}
