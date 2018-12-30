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
        String header = "Referencia,colección,Catalogo,Distributor Price EXW-Valencia,Distributor Price EXW-Valencia," +
                "uds por caja,Peso bruto,imap price\n" +
                "sku #,Family,Catalogue,CE 2019 [€] (Ready pickup 50~65 days),CE 2019 [€] (Ready pickup 20~30 days)," +
                "Pkg size,Packed weight [kg],online (Valid until June 30th 2019)\n";

        StringBuilder sb = new StringBuilder(header);

        for (int i = 0; i < tablica.length; i++) {
            if (tablica[i].trim().endsWith("€")) {
                int lineLength = tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ").length;

                // check if 'Pkg size' is 1 (is not empty)
                if (tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[lineLength - 3].equals("1")) {

                    // join collection name into one record in the row
                    if (lineLength == 8) {
                        sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").replaceAll(" ", ",") + "\n");
                    } else if (lineLength > 8) {
                        sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[0] + ",");
                        for (int j = 1; j < lineLength - 6; j++) {
                            if (j < lineLength - 7) {
                                sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[j] + " ");
                            } else {
                                sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[j]);
                            }
                        }
                        sb.append(",");

                        // append other records into the row
                        for (int j = lineLength - 6; j < lineLength; j++) {
                            if (j < lineLength - 1) {
                                sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[j] + ",");
                            } else {
                                sb.append(tablica[i].trim().replaceAll("\\s€|\\skg|,", "").split(" ")[j]);
                            }
                        }
                        sb.append("\n");
                    }
                } else {
                    sb.append("Data missing\n");
                }
            }
        }
        System.out.println(sb);

        // write sb string as csv file
        try {
            FileWriter writer = new FileWriter("savoy2019.csv", true);
            writer.write(sb.toString());
            writer.close();
            System.out.println("'savoy2019.csv' file created successfully");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Closing the document
        document.close();
    }
}

