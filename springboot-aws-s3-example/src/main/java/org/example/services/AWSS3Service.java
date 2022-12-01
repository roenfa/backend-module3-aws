package org.example.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.PutObjectResult;

import java.io.File;
import java.util.ArrayList;

public class AWSS3Service implements IAWSS3Service {
    private final AmazonS3 s3Client;

    public AWSS3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public boolean isBucketExist(String bucketName) {
        return this.s3Client.doesBucketExistV2(bucketName);
    }

    @Override
    public Bucket createBucket(String bucketName) {
        return this.s3Client.createBucket(bucketName);
    }

    @Override
    public PutObjectResult uploadObject(String bucketName, String key, File file) {
        return this.s3Client.putObject(bucketName, key, file);
    }

    @Override
    public CopyObjectResult copyObject(String sourceBucketName, String sourceObjKey, String destinationBucketName, String destinationObjKey) {
        return this.s3Client.copyObject(sourceBucketName, sourceObjKey, destinationBucketName, destinationObjKey);
    }

    @Override
    public void deleteObject(String bucketName, String objectKey) {
        this.s3Client.deleteObject(bucketName, objectKey);
    }

    @Override
    public void deleteObjects(String bucketName, ArrayList<KeyVersion> keys) {
        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
                .withKeys(keys)
                .withQuiet(false);;
        this.s3Client.deleteObjects(multiObjectDeleteRequest);
   }
}
