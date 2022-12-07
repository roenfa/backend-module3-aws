import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.PutObjectResult;

import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import java.io.File;

public class AWSS3ServiceIntegrationTest {
    private final String BUCKET_NAME = "bucket_name";
    private final String KEY_NAME = "bucket_key";
    private AmazonS3 s3;
    private AWSS3Service service;
    private Bucket bucket;
    private String objectKey = "object/key";
    private File file;
    private S3Object object;

    @Before
    public void setUp() {
        s3 = mock(AmazonS3.class);
        service = new AWSS3Service(s3);
    }

    @Test
    public void whenInitializeAWSService_thenNotNull() {
        var result = new AWSS3Service(s3);
        assertThat(result).isNotNull();
    }

    @Test
    public void whenVerifyIfS3BucketExist_thenCorrect() {
        when(this.s3.doesBucketExistV2(anyString())).thenReturn(true);
        var result = this.service.isBucketExist(BUCKET_NAME);
        //verify(s3).doesBucketExistV2(BUCKET_NAME);
        Assertions.assertTrue(result);
    }

    @Test
    public void whenCreateBucket_thenReturnBucket() {
        bucket = mock(Bucket.class);
        when(this.s3.createBucket(anyString())).thenReturn(bucket);
        Bucket result = this.service.createBucket(BUCKET_NAME);
        Assertions.assertEquals(bucket, result);
    }

    @Test
    public void whenUploadObject_thenReturnPutObjResult() {
        file = mock(File.class);
        PutObjectResult putObjectResult = mock(PutObjectResult.class);
        when(this.s3.putObject(BUCKET_NAME, objectKey, file)).thenReturn(putObjectResult);
        PutObjectResult result = this.service.uploadObject(BUCKET_NAME, objectKey, file);
        Assertions.assertEquals(putObjectResult, result);
    }

    @Test
    public void whenCopyObject_thenReturnCopyObjResult() {
        String destinationObjKey = "dest/object/key";
        String destinationBucketName = "dest_bucket_name";
        CopyObjectResult copyObjectResult = mock(CopyObjectResult.class);
        when(this.s3.copyObject(BUCKET_NAME, objectKey, destinationBucketName, destinationObjKey))
            .thenReturn(copyObjectResult);
        CopyObjectResult result = this.service.copyObject(BUCKET_NAME, objectKey, destinationBucketName, destinationObjKey);
        Assertions.assertEquals(copyObjectResult, result);
    }

    @Test
    public void whenDeleteObjectCalled_thenVerify() {
        this.service.deleteObject(BUCKET_NAME, objectKey);
        verify(this.s3, times(1)).deleteObject(BUCKET_NAME, objectKey);
    }

    @Test
    public void whenGetObject_thenReturnObject() {
        object = mock(S3Object.class);
        when(this.s3.getObject(BUCKET_NAME, objectKey)).thenReturn(object);
        S3Object result = this.service.getObject(BUCKET_NAME, objectKey);
        Assertions.assertEquals(object, result);
    }

    @Test
    public void whenDeleteObjectsCalled_thenVerify() {
        String[] objectsKeys = new String[]{"object/key1", "object/key2"};
        this.service.deleteObjects(BUCKET_NAME, objectsKeys);
        verify(this.s3, times(1)).deleteObjects(any(DeleteObjectsRequest.class));
    }

    @Test    
    public void whenVerifyingUploadObject_thenCorrect_demo() {
        File file = mock(File.class);
        PutObjectResult result = mock(PutObjectResult.class);
        when(s3.putObject(anyString(), anyString(), (File) any())).thenReturn(result);

        assertThat(service.uploadObject(BUCKET_NAME, KEY_NAME, file)).isEqualTo(result);
        verify(s3).putObject(BUCKET_NAME, KEY_NAME, file);
    }
}
