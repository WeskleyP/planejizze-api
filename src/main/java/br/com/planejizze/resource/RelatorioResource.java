package br.com.planejizze.resource;

import br.com.planejizze.service.RelatorioService;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@RestController
@RequestMapping(path = "/relatorios")
public class RelatorioResource {

    private final RelatorioService relatorioService;

    public RelatorioResource(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping(path = "/receitas/download")
    public void relatorioReceitaDownload(HttpServletResponse response) {
        JasperPrint jasperPrint = relatorioService.imprimeRelatorioDownload("relatorio_receitas");
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"receitas.pdf\"");
        try {
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @GetMapping(path = "/receitas/pdf")
    public ResponseEntity<byte[]> relatorioReceitaPdf() {
        byte[] relatorio = relatorioService.imprimeRelatorioNavegador("relatorio_receitas");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                .body(relatorio);
    }

}
