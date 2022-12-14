package org.example;

import org.example.services.IAWSS3Service;

import java.io.File;

public class S3Application {
    private IAWSS3Service awsS3Service;
    private final String bucketName = "bootcamp-backend-bucket2";

    public S3Application(IAWSS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public void run() {
        if (this.awsS3Service.isBucketExist(bucketName)) {
            System.out.println("Bucket name exists!!!");
        } else {
            this.awsS3Service.createBucket(bucketName);

            //uploading
            this.awsS3Service.uploadObject(bucketName,
                    "Documents/files/bootcamp.txt",
                    new File("C:/Users/rocio.encinas/Documents/bootcamp.txt"));

            // get object and copy in Desktop/bootcamp.txt

            //copying from s3 bucket 1 to s3 bucket 2
            this.awsS3Service.copyObject("bootcamp-backend-bucket", "picture/pic.png",
                    "bootcamp-backend-bucket2", "Documents/picture.png");
        }
    }
}
