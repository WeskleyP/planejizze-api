package br.com.planejizze.config;

import br.com.planejizze.model.TipoPagamentoCartao;
import br.com.planejizze.model.TipoPagamentoMoeda;
import br.com.planejizze.model.TipoRecebimentoBanco;
import br.com.planejizze.model.TipoRecebimentoMoeda;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		return new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(TipoPagamentoCartao.class);
				objectMapper.registerSubtypes(TipoPagamentoMoeda.class);
				objectMapper.registerSubtypes(TipoRecebimentoBanco.class);
				objectMapper.registerSubtypes(TipoRecebimentoMoeda.class);
				super.configure(objectMapper);
			}
		};
    }
}
