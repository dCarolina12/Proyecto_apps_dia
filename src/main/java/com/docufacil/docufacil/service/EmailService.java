package com.docufacil.docufacil.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendStatusChangeEmail(String toEmail, String userName, String documentTitle, String oldStatus, String newStatus, String comments) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                    + "<h2 style='color: #2c3e50; border-bottom: 2px solid #3498db; padding-bottom: 10px;'>Notificación de DocuCloud</h2>"
                    + "<p>Hola, <strong>" + userName + "</strong>:</p>"
                    + "<p>Te informamos que un documento ha cambiado de estado dentro del flujo de revisión.</p>"
                    + "<table style='width: 100%; border-collapse: collapse; margin: 20px 0;'>"
                    + "  <tr style='background-color: #f9f9f9;'><td style='padding: 8px; border: 1px solid #ddd;'><strong>Documento:</strong></td><td style='padding: 8px; border: 1px solid #ddd;'>" + documentTitle + "</td></tr>"
                    + "  <tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Estado Anterior:</strong></td><td style='padding: 8px; border: 1px solid #ddd; color: #7f8c8d;'>" + oldStatus + "</td></tr>"
                    + "  <tr style='background-color: #f9f9f9;'><td style='padding: 8px; border: 1px solid #ddd;'><strong>Nuevo Estado:</strong></td><td style='padding: 8px; border: 1px solid #ddd; color: #27ae60; font-weight: bold;'>" + newStatus + "</td></tr>"
                    + "  <tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Comentarios:</strong></td><td style='padding: 8px; border: 1px solid #ddd; font-style: italic;'>" + comments + "</td></tr>"
                    + "</table>"
                    + "<p style='color: #7f8c8d; font-size: 12px; margin-top: 30px; border-top: 1px solid #eee; padding-top: 10px;'>Este es un correo automático generado por el sistema DocuCloud. Por favor no respondas a este mensaje.</p>"
                    + "</div>";

            helper.setText(htmlContent, true);
            helper.setTo(toEmail);
            helper.setSubject("Alerta DocuCloud: Cambio de estado en " + documentTitle);
            helper.setFrom("no-reply@docucloud.com");

            mailSender.send(mimeMessage);
            System.out.println("📧 Correo de notificación enviado con éxito a " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Error al enviar el correo: " + e.getMessage());
        }
    }
}