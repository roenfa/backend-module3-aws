package org.example;

import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.S3Object;
import org.example.services.IAWSS3Service;

import java.io.File;
import java.util.ArrayList;

public class S3Application {
    private IAWSS3Service awsS3Service;
    private final String bucketName = "smm-springboot";

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
                    "Documents/files/example.txt",
                    new File("C:/Users/USER/Documents/example.txt"));

            // get object and copy in Desktop/bootcamp.txt

            //copying from s3 bucket 1 to s3 bucket 2
//            this.awsS3Service.copyObject("bootcamp-backend-bucket", "picture/pic.png",
//                    "bootcamp-backend-bucket2", "Documents/picture.png");
        }
    }
    public void upload(){
        if (this.awsS3Service.isBucketExist(bucketName)) {
            System.out.println("Bucket name exists, uploading file");
            this.awsS3Service.uploadObject(bucketName,
                    "Documents/files/smm-file.txt",
                    new File("C:/Users/USER/Documents/example.txt"));
        }
        else{
            System.out.println("couldn't found bucket");
        }
    }

    public void create(){
        if (this.awsS3Service.isBucketExist(bucketName)) {
            System.out.println("Bucket name exists!!!");
        } else {
            this.awsS3Service.createBucket(bucketName);
        }
    }

    public void copy(){
        this.awsS3Service.copyObject("smm-springboot", "Documents/files/example.txt",
                    "smm-springboot2", "Documents/copied/new.txt");
    }

    public void getObject(){
        S3Object data = this.awsS3Service.getObject("smm-springboot","Documents/files/smm-file.txt");
        System.out.println("bucket: "+ data.getBucketName());
        System.out.println("key: "+ data.getKey());
    }
    public void deleteSingleObject(){
        this.awsS3Service.deleteObject("smm-springboot","Documents/files/example.txt");
    }

    public void deleteMultipleObjects(){
        String key1 = "Documents/files/smm-file1.txt";
        String key2 = "Documents/files/smm-file2.txt";
        String key3 = "Documents/files/smm-file3.txt";

        ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
        keys.add(new KeyVersion(key1));
        keys.add(new KeyVersion(key2));
        keys.add(new KeyVersion(key3));

        this.awsS3Service.deleteObjects(bucketName,keys);
    }
}
