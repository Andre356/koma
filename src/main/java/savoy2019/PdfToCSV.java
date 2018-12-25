package savoy2019;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfToCSV {
    public static void main(String args[]) throws IOException {

        //Loading an existing document
        File file = new File("Savoy House_2019_Price List_precios.pdf");
        PDDocument document = PDDocument.load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper striper = new PDFTextStripper();

        //Retrieving text from PDF document
        striper.setStartPage(1);

        String documentText = striper.getText(document);
        //System.out.println(documentText);

        String[] tablica = documentText.split("\n");

        try {
            FileWriter writer = new FileWriter("savoy2019.txt", true);
            writer.write("Referencia,colección,Catalogo,Distributor Price EXW-Valencia (Listo/Ready pickup 50~65" +
                    " días/days),Distributor Price EXW-Valencia (Listo/Ready pickup 20~30 días/days),uds por caja,Peso " +
                    "bruto,imap price\n" +
                    "sku #,Family,Catalogue,CE 2019 [€],CE 2019 [€],Pkg size,Packed weight [kg],online " +
                    "(Valid until June, 30th 2019)\n");
            for (int i = 0; i < tablica.length; i++) {
                if (tablica[i].trim().endsWith("€")) {
                    writer.write(tablica[i].trim().replaceAll("\\s€|\\skg", "").replaceAll(" ", ",") + "\n");
                }
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i=0; i<tablica.length; i++){
            if (tablica[i].trim().endsWith("€") ){
                System.out.println(tablica[i].trim().replaceAll("\\s€|\\skg", "").replaceAll(" ", ","));
                System.out.println(tablica[i].split(" ").length);
                System.out.println(tablica[i].substring(9,25));
            }
        }

        //Closing the document
        document.close();

    }
}
