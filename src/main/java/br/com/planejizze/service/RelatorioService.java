package br.com.planejizze.service;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class RelatorioService {

    private static final String DIR_RELATORIOS = "/reports/";
    private final DataSource dataSource;
    private final Map<String, Object> params = new HashMap<>();

    @Autowired
    public RelatorioService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JasperPrint imprimeRelatorioDownload(String file) {
        this.params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
        try {
            InputStream arquivo = this.getClass().getResourceAsStream(DIR_RELATORIOS + file + ".jasper");
            return JasperFillManager.fillReport(arquivo, params, conexao());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection conexao() throws SQLException {
        return this.dataSource.getConnection();
    }

    public byte[] imprimeRelatorioNavegador(String file) {
        this.params.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
        try {
            InputStream arquivo = this.getClass().getResourceAsStream(DIR_RELATORIOS + file + ".jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(arquivo, params, conexao());
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
