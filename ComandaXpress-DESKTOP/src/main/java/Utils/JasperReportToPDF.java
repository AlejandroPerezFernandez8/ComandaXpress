package Utils;

import java.awt.HeadlessException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperReportToPDF {

    public static void saveReportsAsPDF(String directoryPath) {
        try {
            // Primer informe
            generateAndSaveReport("/InformeRendimiento_ComandaXpress.jrxml", directoryPath + "/Informe_Rendimiento.pdf");
            // Segundo informe
            generateAndSaveReport("/InformeTablas_ComandaXpress.jrxml", directoryPath + "/Informe_Tablas_Completo.pdf");

            JOptionPane.showMessageDialog(null, "Los informes se han guardado correctamente en: " + directoryPath);
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar los reportes: " + e.getMessage());
        }
    }

    private static void generateAndSaveReport(String jrxmlFilePath, String pdfFilePath) {
        Connection connection = null;
        try {
            // Configurar la conexión a la base de datos
            String dbUrl = "jdbc:mysql://localhost:3306/comandaxpress";
            String dbUser = "root";
            String dbPassword = "root";
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            // Cargar el archivo .jrxml desde el classpath
            InputStream jrxmlStream = JasperReportToPDF.class.getResourceAsStream(jrxmlFilePath);
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // Llenar el reporte
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);

            // Exportar a PDF
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(pdfFilePath));
            exporter.exportReport();

        } catch (JRException | SQLException e) {
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}
