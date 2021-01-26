package br.com.planejizze.resource;

import br.com.planejizze.service.RelatorioService;
import br.com.planejizze.utils.TokenUtils;
import io.swagger.annotations.Api;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;

@Api(tags = "Relatórios")
@RestController
@RequestMapping(path = "/relatorios")
public class RelatorioResource {

    private final RelatorioService relatorioService;

    public RelatorioResource(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @PostAuthorize(value = "hasPermission('report', 'read')")
    @GetMapping(path = "/receitas/download")
    public void relatorioReceitaDownload(HttpServletResponse response) {
        JasperPrint jasperPrint = relatorioService.imprimeRelatorioDownload("relatorio_receitas", new HashMap<>());
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"receitas.pdf\"");
        try {
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostAuthorize(value = "hasPermission('report', 'read')")
    @GetMapping(path = "/receitas/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> relatorioReceitaPdf() {
        byte[] relatorio = relatorioService.imprimeRelatorioNavegador("relatorio_receitas", new HashMap<>());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=relatorio.pdf");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(relatorio);
    }

    @PostAuthorize(value = "hasPermission('report', 'read')")
    @GetMapping(path = "/despesasXreceita")
    public void relatorioReceitaDownload(HttpServletResponse response, HttpServletRequest request,
                                         @RequestParam("startDate") String startDate,
                                         @RequestParam("endDate") String endDate) {
        var data = new HashMap<String, Object>();
        data.put("p_usuario", TokenUtils.from(request).getUserId());
        data.put("p_dataIni", startDate);
        data.put("p_dataFim", endDate);
        JasperPrint jasperPrint = relatorioService.imprimeRelatorioDownload("relatorio_receitasXdespesas", data);
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename=\"receitasXdespesas.pdf\"");
        try {
            OutputStream outputStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
