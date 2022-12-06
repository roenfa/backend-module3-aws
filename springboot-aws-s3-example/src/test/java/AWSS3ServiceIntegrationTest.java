import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CopyObjectResult;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AWSS3ServiceIntegrationTest {
    private final String BUCKET_NAME = "bucket_name";
    private AmazonS3 s3;
    private AWSS3Service service;

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
        verify(s3).doesBucketExistV2(BUCKET_NAME);
        Assertions.assertTrue(result);
    }

    @Test
    public void whenVerifyUploadObject_thenCorrect() {
        when(this.s3.putObject(anyString(), anyString(), any(File.class))).thenReturn( new PutObjectResult());
        var result = this.service.uploadObject(BUCKET_NAME, "key", new File("file"));
        Assertions.assertNotNull(result);
    }

    @Test
    public void whenVerifyCopyObject_thenCorrect() {
        //CopyObjectResult
        when(this.s3.copyObject(anyString(), anyString(), anyString(), anyString())).thenReturn( new CopyObjectResult());
        var result = this.service.copyObject(BUCKET_NAME, "key", BUCKET_NAME, "key");
        Assertions.assertNotNull(result);
    }

    @Test
    public void whenVerifyDeleteObject_thenCorrect() {
        this.service.deleteObject(BUCKET_NAME, "key");
        verify(s3).deleteObject(BUCKET_NAME, "key");
    }

    @Test
    public void getObjects() {
        when(this.s3.listObjects(anyString())).thenReturn( new ObjectListing());
        var result = this.service.getObjects(BUCKET_NAME);
        Assertions.assertNotNull(result);
    }

    @Test
    public void deleteObjects() {
        this.service.deleteObjects(BUCKET_NAME, new ArrayList<>());
        verify(s3).deleteObjects(any());
    }

    @Test
    public void deleteBucket() {
        this.service.deleteBucket(BUCKET_NAME);
        verify(s3).deleteBucket(BUCKET_NAME);
    }

}
