package org.example;

import org.example.services.IAWSS3Service;

import java.io.File;
import java.util.ArrayList;

public class S3Application {
    private IAWSS3Service awsS3Service;
    private final String bucketName = "bootcamp-backend-bucket-andresgb";
    private final String bucketCopyName = "bootcamp-backend-bucket2";
    private final String bucketSubFolder = "Documents/files/";
    private final String bucketFileName = "bootcamp_andresgb.txt";


    public S3Application(IAWSS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public void run() {
        if (this.awsS3Service.isBucketExist(bucketName)) {
            System.out.println("Bucket name exists!!!");


            ArrayList<String> files = this.awsS3Service.getObjects(bucketName);
            System.out.println("Files in bucket: " + files);

            //si bucket name está en el array de files, entonces no se crea
            if (!files.contains(bucketFileName)) {
                System.out.println("File already exists");
            } else {
                System.out.println("File does not exist");
            }
            //delete bucket
            this.awsS3Service.deleteBucket(bucketName);

            System.out.println("Bucket deleted!!!");


        } else {
            this.awsS3Service.createBucket(bucketName);

            //uploading
            this.awsS3Service.uploadObject(bucketName,
                    bucketSubFolder + bucketFileName,
                    new File("/home/andresgb/Documentos/" + bucketFileName));

            // get object and copy in Desktop/bootcamp.txt

            //copying from s3 bucket 1 to s3 bucket 2
            this.awsS3Service.copyObject(bucketName,
                    bucketSubFolder + bucketFileName,
                    bucketCopyName,
                    bucketSubFolder + bucketFileName);

        }
    }
}
