package mg.neocorporation.bank.controller;

import mg.neocorporation.bank.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/generate-pdf")
    @ResponseBody
    public HttpEntity<InputStreamResource> generatePdf(
            @RequestParam("numTel") String numTel,
            @RequestParam("month") String month) throws IOException {

        byte[] pdfBytes = pdfService.generateClientPdf(numTel, month);

        ByteArrayInputStream bis = new ByteArrayInputStream(pdfBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=generated.pdf");
        headers.setContentType(new MediaType("application", "pdf", StandardCharsets.UTF_8));

        return new HttpEntity<>(new InputStreamResource(bis), headers);
    }
//    @GetMapping("/generate-pdf")
//    public void generatePdf(@RequestParam("numTel") String numTel, @RequestParam("month") String month, HttpServletResponse response) throws IOException {
//        pdfService.generateClientPdf(numTel, month, response);
//    }
    @GetMapping("/generate-pdf-page")
    public String showGeneratePdfPage() {
        // Renvoie le nom du template Thymeleaf (sans l'extension .html)
        return "GeneratePdf";
    }
}
