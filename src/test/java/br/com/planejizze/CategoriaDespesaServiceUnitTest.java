package br.com.planejizze;

import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.repository.CategoriaDespesaRepository;
import br.com.planejizze.service.CategoriaDespesaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Optional;

public class CategoriaDespesaServiceUnitTest {

    private final CategoriaDespesaRepository categoriaDespesaRepository = Mockito.mock(CategoriaDespesaRepository.class);

    @Test
    public void shouldFindDefaultCategoryByIdWithUserGetByToken() {
        CategoriaDespesaService categoriaDespesaService = new CategoriaDespesaService(categoriaDespesaRepository);
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwicGVybWlzc2lvbnMiOnsiY2F0ZWdvcmlhRGVzcGVzYSI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwiY2FydGFvIjp7InJlYWQiOnRydWUsImNyZWF0ZSI6dHJ1ZSwiZGVsZXRlIjp0cnVlLCJ1cGRhdGUiOnRydWV9LCJwcmVmZXJlbmNlcyI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwicm9sZSI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwicmVjZWl0YSI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwiZGVzcGVzYSI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwicmVwb3J0Ijp7InJlYWQiOnRydWUsImNyZWF0ZSI6dHJ1ZSwiZGVsZXRlIjp0cnVlLCJ1cGRhdGUiOnRydWV9LCJiYW5jbyI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwidXN1YXJpbyI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwiY2F0ZWdvcmlhUmVjZWl0YSI6eyJyZWFkIjp0cnVlLCJjcmVhdGUiOnRydWUsImRlbGV0ZSI6dHJ1ZSwidXBkYXRlIjp0cnVlfSwicGxhbmVqYW1lbnRvIjp7InJlYWQiOnRydWUsImNyZWF0ZSI6dHJ1ZSwiZGVsZXRlIjp0cnVlLCJ1cGRhdGUiOnRydWV9fSwiaWF0IjoxNjA5NzkwMTE1LCJleHAiOjE2MDk4NzY1MTV9.inML8_gqC1OpCgyNQrF09y7lvEufMe8u-i9z1_YGJy0");
        CategoriaDespesa despesa = new CategoriaDespesa();
        despesa.setId(1L);
        despesa.setAtivo(true);
        despesa.setNome("Teste");
        despesa.setCor("#333666");
        Mockito.when(categoriaDespesaRepository.findByIdAndUsuarioIdOrUsuarioIsNull(1L, 1L))
                .thenReturn(Optional.of(despesa));
        categoriaDespesaService.save(despesa, mockHttpServletRequest);
        var categoria = categoriaDespesaService.findById(1L, mockHttpServletRequest);
        Assertions.assertNotNull(categoria);
        Assertions.assertEquals(categoria.getId(), 1L);
        Assertions.assertEquals(categoria.getNome(), "Teste");
    }
}
