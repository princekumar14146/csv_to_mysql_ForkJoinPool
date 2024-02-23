package com.csvReadFile.ForkJointPool.CsvReader.Config;

import com.csvReadFile.ForkJointPool.CsvReader.Entity.Student;
import com.csvReadFile.ForkJointPool.CsvReader.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.RecursiveAction;

public class FileInsertTask extends RecursiveAction {

    public static final int threshold=100;
    private String filePath;
    private int start;
    private int end;

    @Autowired
    private StudentRepository studentRepository;
   public  FileInsertTask(String filePath, int start, int end,StudentRepository studentRepository)
    {
        this.filePath=filePath;
        this.start=start;
        this.end=end;
        this.studentRepository=studentRepository;
    }



    @Override
    protected void compute() {

       if(end - start <= threshold){
           insertDataIntoDatabase(start,end);
       }else{
           int mid = start + (end - start) / 2;
           FileInsertTask leftTask = new FileInsertTask(filePath, start, mid,studentRepository);
           FileInsertTask rightTask = new FileInsertTask(filePath, mid, end,studentRepository);
           invokeAll(leftTask, rightTask);
       }

    }
    private void insertDataIntoDatabase(int start, int end) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            br.readLine();
            while ((line = br.readLine()) != null) {

                if (lineNumber >= start && lineNumber < end) {
                    String[] data=line.split(",");
                    Student student=new Student();
                    student.setStudentId(Integer.parseInt(data[0]));
                    student.setStudentName(data[1]);
                    student.setStudentStream(data[2]);
                    student.setStudentAddress(data[3]);

                    studentRepository.save(student);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}