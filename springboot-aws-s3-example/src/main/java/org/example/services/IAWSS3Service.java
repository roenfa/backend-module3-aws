package org.example.services;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.List;

public interface IAWSS3Service {
    boolean isBucketExist(String bucketName);

    S3Object getObject(String bucketName, String key);
    Bucket createBucket(String bucketName);
    PutObjectResult uploadObject(String bucketName, String key, File file);
    //getObject()
    CopyObjectResult copyObject(String sourceBucketName, String sourceObjKey, String destinationBucketName, String destinationObjKey);
    void deleteObject(String bucketName, String objectKey);
    // deleteObjects (objects)

    void deleteObjects(String bucketName, List<DeleteObjectsRequest.KeyVersion> keys);
}
