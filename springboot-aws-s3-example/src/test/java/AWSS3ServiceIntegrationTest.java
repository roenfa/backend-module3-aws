import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AWSS3ServiceIntegrationTest {
    private final String BUCKET_NAME = "bucket_name";
    private final String KEY = "Documents/files/smm-file.txt";
    private final String DESTINATION_BUCKET = "bucket_name_2";
    private final String DESTINATION_KEY = "Documents/copied/new.txt";

    private AmazonS3 s3;
    private AWSS3Service service;
    private Bucket mockBucket;
    private File file;
    @Before
    public void setUp() {
        s3 = mock(AmazonS3.class);
        service = spy(new AWSS3Service(s3));
        mockBucket = new Bucket(BUCKET_NAME);
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
    public void whenCreateBucket_thenCorrect(){
        when(this.s3.createBucket(anyString())).thenReturn(mockBucket);
        assertThat(mockBucket);
    }

    @Test
    public void whenUploadObject_thenCorrect(){
        file = new File("C:/Users/USER/Documents/example.txt");
        PutObjectResult putObjectResult = new PutObjectResult();
        when(this.s3.putObject(BUCKET_NAME,KEY,file)).thenReturn(putObjectResult);
        var result = this.service.uploadObject(BUCKET_NAME,KEY,file);
        Assertions.assertNotNull(result);
    }

    @Test
    public void whenCopyObject_thenCorrect(){
        CopyObjectResult copyObjectResult = new CopyObjectResult();
        when(this.s3.copyObject(BUCKET_NAME,KEY,DESTINATION_BUCKET,DESTINATION_KEY)).thenReturn(copyObjectResult);
        var result = this.service.copyObject(BUCKET_NAME,KEY,DESTINATION_BUCKET,DESTINATION_KEY);
        Assertions.assertNotNull(result);
    }

    @Test
    public void whenGetObject_thenCorrect(){
        S3Object s3Object = new S3Object();
        when(this.s3.getObject(BUCKET_NAME,KEY)).thenReturn(s3Object);
        var result = this.service.getObject(BUCKET_NAME,KEY);
        Assertions.assertNotNull(result);
    }

    @Test
    public void whenDeleteObject_thenCorrect() {
        doNothing().when(this.service).deleteObject(BUCKET_NAME, KEY);
        this.service.deleteObject(BUCKET_NAME, KEY);
        verify(this.service).deleteObject(BUCKET_NAME,KEY);
    }

    @Test
    public void whenDeleteObjects_thenCorrect() {
        ArrayList<KeyVersion> deleteObjects = new ArrayList<>();
        doNothing().when(this.service).deleteObjects(BUCKET_NAME, deleteObjects);
        this.service.deleteObjects(BUCKET_NAME, deleteObjects);
        verify(this.service).deleteObjects(BUCKET_NAME, deleteObjects);
    }

}
