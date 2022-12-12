package org.example.services;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.ArrayList;

public interface IAWSS3Service {

    boolean isBucketExist(String bucketName);
    Bucket createBucket(String bucketName);
    PutObjectResult uploadObject(String bucketName, String key, File file);

    S3Object getObject(String bucketName, String objectKey);

    CopyObjectResult copyObject(String sourceBucketName, String sourceObjKey, String destinationBucketName, String destinationObjKey);
    void deleteObject(String bucketName, String objectKey);

    void deleteObjects(String bucketName, ArrayList<String> objectKey);

    ArrayList<String> getObjects(String bucketName);

    void deleteBucket(String bucketName);

}
