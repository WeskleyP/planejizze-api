package br.com.planejizze;

import br.com.planejizze.exceptions.NotFoundException;
import br.com.planejizze.model.CategoriaDespesa;
import br.com.planejizze.service.CategoriaDespesaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.transaction.TransactionSystemException;

//@RunWith(SpringRunner.class)
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
        Assertions.assertNotNull(categoriaDespesa);
        Assertions.assertNotNull(categoriaDespesa.getId());
    }

    @Test
    public void testarCadastroCategoriaDespesaSemNome() {
        // cenário
        CategoriaDespesa categoriaDespesa = new CategoriaDespesa();
        categoriaDespesa.setNome(null);
        categoriaDespesa.setAtivo(true);
        // ação
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y");
        Assertions.assertThrows(TransactionSystemException.class, () -> {
            categoriaDespesaService.save(categoriaDespesa, mockHttpServletRequest);
        });
    }

    @Test
    public void deveFalharQuandoCategoriaNãoEncontrada() {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3ZXNrbGV5cGVkcm9AZ21haWwuY29tIiwidXNlciI6MSwiaWF0IjoxNjAwODE1Nzk1LCJleHAiOjE2MDQ0MTU3OTV9.2t_RL_rYn3dY8SCbJ0879nhpUbuvhk_-WuYR-mJgg-Y");
        Assertions.assertThrows(NotFoundException.class, () -> {
            categoriaDespesaService.deleteById(200L, mockHttpServletRequest);
        });

    }
}
