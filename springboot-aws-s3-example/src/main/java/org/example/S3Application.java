package org.example;

import org.example.services.IAWSS3Service;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class S3Application {
    private IAWSS3Service awsS3Service;
    private final String sourceBucketName = "bootcamp-backend-bucket";
    private final String destinationBucketName = "bootcamp-backend-bucket2";
    
    private final String pathFile = "D:/GitHub/Jala University/BackEnd2/backend-module3-aws/springboot-aws-s3-example/src/main/resources/";
    private final String fileName1 = "diego_test.txt";
    private final String fileName2 = "diego_test_1.txt";

    public S3Application(IAWSS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public void run() {
        System.out.println("#######################################");
        bucketCreation(sourceBucketName);
        bucketCreation(destinationBucketName);

        System.out.println("Working Directory = " + System.getProperty("user.dir"));

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);

        //uploading files        
        this.awsS3Service.uploadObject(sourceBucketName,
            "Documents/files/"+fileName1,
            new File(pathFile+fileName1));

        this.awsS3Service.uploadObject(sourceBucketName,
            "Documents/files/"+fileName2,
            new File(pathFile+fileName2));

        // get object and copy in Desktop/bootcamp.txt
        /* Get Object InputStream */
        /* Arguments: Bucket Name, Object Key */
        GetObjectRequest request = new GetObjectRequest(sourceBucketName, "Documents/files/"+fileName1);
        ObjectMetadata metadata =  this.awsS3Service.getObject(request, new File(pathFile+"diego_test_new.txt"));
        System.out.println("Last Modified Date" + metadata.getLastModified());

        //copying from s3 bucket 1 to s3 bucket 2
        this.awsS3Service.copyObject(sourceBucketName, "Documents/files/"+fileName1,
        destinationBucketName, "Documents/files/"+fileName2);

        //delete objects
        ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
        keys.add(new KeyVersion("Documents/files/"+fileName1));
        keys.add(new KeyVersion("Documents/files/"+fileName2));
        this.awsS3Service.deleteObjects(sourceBucketName, keys);
    }

    private void bucketCreation(String bucketName){
        if (this.awsS3Service.isBucketExist(bucketName)) {
            System.out.println("Bucket with name '"+ bucketName+"' already exists!!!");
        } else {
            this.awsS3Service.createBucket(bucketName);
        }
    }
}
