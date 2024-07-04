package mg.neocorporation.bank.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PdfService {
//    void generateClientPdf(String numTel, String month, HttpServletResponse response) throws IOException;
    byte[] generateClientPdf(String numTel, String month) throws IOException;
}
