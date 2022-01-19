package com.christinchick;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

public class ZipFile1 {
    private ClassLoader cl = ZipFile1.class.getClassLoader();
    @Test
  void parsePdf() throws Exception {
        open("https://junit.org/junit5/docs/current/user-guide/");
        File pdfDownload = $(By.linkText("PDF download")).download();
        PDF parsed = new PDF(pdfDownload);
        assertThat(parsed.author).contains("Marc Philipp");
    }
    @Test
    void parsePdfV() throws Exception {
       try (InputStream stream = cl.getResourceAsStream("Ecola Price_2021-10-15.xlsx"))

        {
            XLS parsed = new XLS(stream);
            System.out.println(" ");
        }

    }
    @Test
    void parseCsv() throws Exception {
        try
            (InputStream stream = cl.getResourceAsStream("tereer.csv"))
        {
        CSVReader reader = new CSVReader(new InputStreamReader(stream));
            List<String[]> list = reader.readAll();
            assertThat(list).hasSize(3).contains(new String[]{"Autor", "Blok"},
                                                new String[]{"Block", "Apteka"},
                                                new String[]{"Esenin"}
            );
        }}

    @Test
    void zipTestj() throws Exception {
try
    (InputStream stream = cl.getResourceAsStream("example.zip");
     ZipInputStream zis = new ZipInputStream(stream)){
    ZipEntry zipEntry;
    while ((zipEntry = zis.getNextEntry()) != null){
        assertThat(zipEntry.getName()).isEqualTo("qw.txt");
    }
    }

    }
}
