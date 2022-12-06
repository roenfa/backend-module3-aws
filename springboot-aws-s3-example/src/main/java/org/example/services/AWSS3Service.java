package org.example.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;

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
    public void deleteObjects(String bucketName, ArrayList<String> objectKey) {
        DeleteObjectsRequest deleteObjectsRequest = new DeleteObjectsRequest(bucketName)
                .withKeys(objectKey.toArray(new String[0]));
        this.s3Client.deleteObjects(deleteObjectsRequest);
    }

    @Override
    public ArrayList<String> getObjects(String bucketName) {
        ArrayList<String> objectList = new ArrayList<>();
        ObjectListing objectListing = this.s3Client.listObjects(bucketName);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            objectList.add(objectSummary.getKey());
        }
        return objectList;
    }


    @Override
    public void deleteBucket(String bucketName) {
        this.s3Client.deleteBucket(bucketName);
    }


    @Override
    public S3Object getObject(String bucketName, String objectKey) {
        return this.s3Client.getObject(bucketName, objectKey);
    }

}
