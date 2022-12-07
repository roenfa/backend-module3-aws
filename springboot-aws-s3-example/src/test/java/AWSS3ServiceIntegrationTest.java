import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

@ExtendWith(MockitoExtension.class)
public class AWSS3ServiceIntegrationTest {
    private static final String BUCKET_NAME = "test-bucket";
    
    @Mock
    private AmazonS3 s3Client;
    @Mock
    private AWSS3Service service;

    @Before
    public void setUp() {
        s3Client = mock(AmazonS3.class);
        service = new AWSS3Service(s3Client);
        MockitoAnnotations.initMocks(service);
    }

    @Test
    public void whenInitializeAWSService_thenNotNull() {
        var result = new AWSS3Service(s3Client);
        assertThat(result).isNotNull();
    }

    @Test
    public void whenVerifyIfS3BucketExist_thenCorrect() {
        when(this.service.isBucketExist(anyString())).thenReturn(true);
        var result = this.service.isBucketExist(BUCKET_NAME);
        //verify(s3).doesBucketExistV2(BUCKET_NAME);
        Assertions.assertTrue(result);
    }

    @Test
    public void whenStringNameCorrectCreateBucket_thenCorrect(){
        Bucket bucket = new Bucket(BUCKET_NAME);
        // when(this.s3Client.createBucket(anyString())).thenReturn(bucket);
        when(this.service.createBucket(anyString()))
            .thenReturn(bucket);

        assertThat(bucket);
    }

    private static final String OBJECT_KEY = "test-key";
    private static final File NEW_FILE = new File("test-file.txt");
    @Test
    public void whenStringNameKeyFileCorrect_thenCorrect(){
        /* Bucket bucket = new Bucket(BUCKET_NAME);
        when(this.s3Client.createBucket(anyString())).thenReturn(bucket); */

        ObjectMetadata metadata = new ObjectMetadata();
        PutObjectResult putObjectResult = new PutObjectResult();
        metadata.setContentType("text/plain");
        when(s3Client.putObject(any(PutObjectRequest.class)))
            .thenReturn(putObjectResult);

        when(this.service.uploadObject(any(), any(), any()))
            .thenReturn(putObjectResult);

        verify(service).uploadObject(BUCKET_NAME, OBJECT_KEY, NEW_FILE);
        PutObjectRequest expectedRequest = new PutObjectRequest(BUCKET_NAME, OBJECT_KEY, NEW_FILE);

        verify(s3Client).putObject(expectedRequest);
    }

    @Test
    public void whenStringNameCorrect_thenCorrect(){
        S3Object object = new S3Object();

        when(service.getObject(BUCKET_NAME, "random-key"))
            .thenReturn(object);
    }

    private static final String SOURCE_BUCKET_NAME = "source-bucket";
    private static final String DESTINATION_BUCKET_NAME = "destination-bucket";
    private static final String SOURCE_OBJECT_KEY = "source-key";
    private static final String DESTINATION_OBJECT_KEY = "destination-key";
    @Test
    public void whenBucketsExists_thenCopy(){
        String etag = "test-etag";

        // Set up the mock AmazonS3 client to handle the object copy
        CopyObjectResult result = new CopyObjectResult();
        result.setETag(etag);

        when(s3Client.copyObject(any(CopyObjectRequest.class))).thenReturn(result);
        when(this.service.copyObject(any(), any(), any(), any())).thenReturn(result);
        
        // Verify that the AmazonS3 client was used to copy the object
        CopyObjectRequest expectedRequest = new CopyObjectRequest(SOURCE_BUCKET_NAME, SOURCE_OBJECT_KEY,
            DESTINATION_BUCKET_NAME, DESTINATION_OBJECT_KEY);

        verify(s3Client).copyObject(expectedRequest);
    }

    @Test
    public void whenBucketAndKey_thenDeleteObject(){
        doNothing().when(service).deleteObject(any(), any());

        verify(this.service).deleteObject(BUCKET_NAME, OBJECT_KEY);
    }
}
