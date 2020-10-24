package br.com.planejizze;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.service.CategoriaDespesaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CadastroCategoriaDespesaIT {
    @Autowired
    private CategoriaDespesaService categoriaDespesaService;

    @Test
    public void testarCadastroCategoriaDespesaComSucesso() {
        // cenário
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setNome("Teste");
        categoriaDespesa.setAtivo(true);
        // ação
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y");
        categoriaDespesa = categoriaDespesaService.save(categoriaDespesa, mockHttpServletRequest);
        // validação
        Assertions.assertThat(categoriaDespesa).isNotNull();
        Assertions.assertThat(categoriaDespesa.getId()).isNotNull();
    }

    @Test(expected = TransactionSystemException.class)
    public void testarCadastroCategoriaDespesaSemNome() {
        // cenário
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setNome(null);
        categoriaDespesa.setAtivo(true);
        // ação
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y");
        categoriaDespesaService.save(categoriaDespesa, mockHttpServletRequest);
    }

    @Test(expected = NotFoundException.class)
    public void deveFalharQuandoCategoriaNãoEncontrada() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y");
        categoriaDespesaService.deleteById(200L, mockHttpServletRequest);
    }
}
