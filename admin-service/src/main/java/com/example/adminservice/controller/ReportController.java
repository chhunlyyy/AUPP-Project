package com.example.adminservice.controller;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminservice.entity.ExamEntity;
import com.example.adminservice.entity.ResultEntity;
import com.example.adminservice.service.exam.ExamService;
import com.example.adminservice.service.result.ResultService;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("admin/report")
public class ReportController {

    @Autowired
    ResultService service;
    @Autowired
    ExamService examService;

    @GetMapping(value = "/result")
    public ResponseEntity<byte[]> result(@RequestParam int examId, @RequestParam int classId, @RequestParam String className) throws IOException {
        List<ResultEntity> resultData = service.getResult(examId, classId);
        ExamEntity examEntity = examService.examById(examId);
        // Create a new document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        // Add content to the page
        // ...
        String examName = "Module Exame 1";
        String date = "01-01-2023";
        String maxScore = "";

        if(examEntity != null){
            examName = examEntity.getName();
            date = examEntity.getDate().toString();
            maxScore = String.valueOf(examEntity.getMax_score());
        }

        int pageWidth = (int) page.getTrimBox().getWidth(); // get width of the page
        int pageHeight = (int) page.getTrimBox().getHeight(); // get height of the page

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setStrokingColor(Color.DARK_GRAY);
        contentStream.setLineWidth(1);
        int initX = 50;
        int initY = pageHeight - 80;

        PDImageXObject pdImage = PDImageXObject.createFromFile("admin-service/src/main/resources/assets/logo.jpg",
                document);
        float scale = 0.15f;

        contentStream.drawImage(pdImage, initX, initY - 50, pdImage.getWidth() * scale, pdImage.getHeight() * scale);

        setText(contentStream, "AUPP TECHNOLOGY CENTER", initX + 140, initY - 10, 20);

        setText(contentStream, "Class Name : " + className, initX, initY -= 80, 13);
        setText(contentStream, "Exam Name : " + examName + " ,  Max Score : " + maxScore, initX, initY -= 20, 13);
        setText(contentStream, "Date  : " + date, initX, initY -= 20, 13);

        // table

        initY -= 30;
        initX = 10;
        int colCount = 3;
        int rowCount = 1 + resultData.size();
        int cellHeight = 20;
        int cellWidth = pageWidth / colCount;
        for (int i = 1; i <= rowCount; i++) {
            for (int j = 1; j <= colCount; j++) {
                if (i == 1) {
                    if (j == 1) {
                        contentStream.addRect(initX, initY, cellWidth + 30, -cellHeight);
                        setText(contentStream, "Student ID", initX + 30, initY - 5 - cellHeight + 10, 12);
                        initX += cellWidth + 30;
                    } else if (j == 2) {
                        contentStream.addRect(initX, initY, cellWidth, -cellHeight);

                        setText(contentStream, "Marks Obtained", initX + 10, initY - 5  - cellHeight + 10, 12);

                        initX += cellWidth;
                    } else {
                        contentStream.addRect(initX, initY, 140, -cellHeight);

                        setText(contentStream, "Result", initX + 10, initY - 5  - cellHeight + 10, 12);

                        initX += cellWidth;
                    }

                } else {

                    int index = i - 2;

                    if (j == 1) {
                        contentStream.addRect(initX, initY, cellWidth + 30, -cellHeight);
                        setText(contentStream, String.valueOf(resultData.get(index).getStudent_id()), initX + 30,
                                initY - 5  - cellHeight + 10, 12);
                        initX += cellWidth + 30;
                    } else if (j == 2) {
                        contentStream.addRect(initX, initY, cellWidth, -cellHeight);
                        setText(contentStream, String.valueOf(resultData.get(index).getScore()), initX + 10,
                                initY - 5  - cellHeight + 10, 12);
                        initX += cellWidth;
                    } else {
                        contentStream.addRect(initX, initY, 140 , -cellHeight);

                        setText(contentStream, resultData.get(index).getGrade(), initX + 10, initY - 5  - cellHeight + 10,
                                12);

                        initX += cellWidth;
                    }
                }
            }
            initX = 10;
            initY -= cellHeight;
        }

        // Save the document to a byte array
        contentStream.stroke();
        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        // Set the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "result.pdf");

        // Return the PDF as a byte array
        return ResponseEntity.ok()
                .headers(headers)
                .body(baos.toByteArray());
    }

    public static void setText(PDPageContentStream contentStream, String text, float x, float y, float fontSize)
            throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.setFont(PDType1Font.TIMES_ROMAN, fontSize);
        contentStream.showText(text);
        contentStream.endText();

    }

}
