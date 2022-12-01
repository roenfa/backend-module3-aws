package org.example;

import org.example.services.IAWSS3Service;

import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class S3Application {
    private IAWSS3Service awsS3Service;
    private final String bucketName = "bootcamp-backend-bucket-adrian-test2";
    private final String pathResources = System.getProperty("user.dir")+"/src/main/resources/";
    private final List<String> ObjectKeys = new ArrayList<String>();
    private final byte[] read_buf = new byte[1024];

    public S3Application(IAWSS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public void run() throws IOException {
        if (!this.awsS3Service.isBucketExist(bucketName)) {
            this.awsS3Service.createBucket(bucketName);
        } else {
            System.out.println("Bucket already exists!!!");
            //----upload N same files
            // for (int i=1;i<=4;i++){
            //     uploadfile(bucketName, "Documents/files/hello_"+i+".txt", new File(pathResources+"hello.txt"));
            // }

            //----copying from s3 bucket 1 to s3 bucket 2
            // copyfile("bootcamp-backend-bucket-adrian-test","pictures/DevOps.png",bucketName,"Documents/DevOps.png");

            //----get object list of bucket
            // getobjectlist(bucketName,"Documents/");

            //----get object and copy in Desktop/bootcamp.txt
            getobjectfile(bucketName,"Documents/files",ObjectKeys.get(1));

            //----delete object and copy in Desktop/bootcamp.txt
            // deletefile(bucketName,,"Documents/files", ObjectKeys.get(1));
            // deleteallfiles(bucketName,,"Documents/files",ObjectKeys);
        }

    }
    public void uploadfile(String bucketName,String key, File file){
        this.awsS3Service.uploadObject(bucketName,key,file);
    }
    public void copyfile(String bucketName1,String key1, String bucketName2,String key2){
        this.awsS3Service.copyObject(bucketName1, key1,bucketName2,key2);
    }
    public void getobjectlist(String bucketName,String prefix){
        ObjectListing objectListing = this.awsS3Service.listObjects(bucketName,prefix);
        for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            ObjectKeys.add(objectSummary.getKey());
        }
    }
    public void getobjectfile(String bucketName,String prefix,String key) throws IOException{
        getobjectlist(bucketName, prefix);
        String [] name = key.split("/",-1);
        S3Object o = this.awsS3Service.getObject(bucketName, key);
        S3ObjectInputStream s3is = o.getObjectContent();
        String pathname = pathResources+name[name.length-1];
        FileOutputStream fos = new FileOutputStream(new File(pathname));
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
    }
    public void deletefile(String bucketName,String prefix,String key){
        getobjectlist(bucketName, prefix);
        this.awsS3Service.deleteObject(bucketName, key);
    }
    public void deleteallfiles(String bucketName,String prefix,List<String> keys){
        getobjectlist(bucketName, prefix);
        ArrayList<KeyVersion> delete_keys = new ArrayList<KeyVersion>();
        for(String key:keys){
            delete_keys.add(new KeyVersion(key));
        }
        this.awsS3Service.deleteObjects(bucketName, delete_keys);
    }
}
