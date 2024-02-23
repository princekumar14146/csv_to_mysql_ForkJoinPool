package com.csvReadFile.ForkJointPool.CsvReader.Service;

import com.csvReadFile.ForkJointPool.CsvReader.Config.FileInsertTask;
import com.csvReadFile.ForkJointPool.CsvReader.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public void fileProcessing(String filePath) throws IOException {

        ForkJoinPool forkJoinPool= new ForkJoinPool();

        FileInsertTask task=new FileInsertTask(filePath,0,getTotalLines(filePath),studentRepository);
        forkJoinPool.invoke(task);

    }

    private int getTotalLines(String filePath) throws IOException {
        int totalLines=0;
        try(BufferedReader br=new BufferedReader(new FileReader(filePath))){

            while(br.readLine()!=null)
            {
                totalLines++;
            }
        }catch (IOException e){
            e.printStackTrace();

        }
        return totalLines;
    }
}
