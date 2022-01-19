package com.christinchick;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Pdf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.INPUT_STREAM;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

public class TestZipFile {
        @Test
    void zipTest() throws Exception {
            ZipFile zipFile = new ZipFile("src\\test\\resources\\example.zip");

            ZipEntry pdfEntry = zipFile.getEntry("index.pdf");
            try (InputStream Pdfstream = zipFile.getInputStream(pdfEntry)) {
                PDF parsed = new PDF(Pdfstream);
                assertThat(parsed.text).contains("JUnit 5 User Guide");
                }

           ZipEntry xlsxEntry = zipFile.getEntry("Ecola Price_2021-10-15.xlsx");
           try (InputStream xlsstream = zipFile.getInputStream(xlsxEntry)) {
               XLS parsed = new XLS(xlsstream);
               assertThat(parsed.excel.getSheetAt(0).getRow(1)
               .getCell(2).getStringCellValue()).isEqualTo("FB53H4ECB");
                }

           ZipEntry csvEntry = zipFile.getEntry("addresses.csv");
            try (InputStream csvstream = zipFile.getInputStream(csvEntry)) {
            CSVReader reader = new CSVReader(new InputStreamReader(csvstream));
            List<String[]> list = reader.readAll();
            assertThat(list).hasSize(3).contains(
                                                   new String[]{"John","Doe", ""},
                                                   new String[]{""},
                                                   new String[]{"Jack", "McGinnis"} );
               }
    }
}
