package com.csvReadFile.ForkJointPool.CsvReader.Controller;

import com.csvReadFile.ForkJointPool.CsvReader.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class FileController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/save")
    public String fileProcessing() throws IOException {
        studentService.fileProcessing("/home/bonami/AWS_PRACTICE/CsvReaderForkJointPool/prince.csv");
        return "File Processing Started";
    }
}
