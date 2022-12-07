import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;

import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AWSS3ServiceIntegrationTest {
    private final String BUCKET_NAME = "bootcamp-backend-jesus";
    private final String FILE_KEY = "test/bootcamp.txt";

    private AmazonS3 s3;
    private AWSS3Service service;

    @Before
    public void setUp() {
        s3 = mock(AmazonS3.class);
        service = spy(new AWSS3Service(s3));
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
    public void whenGetObjectFromS3Bucket_thenNotNull() {
        this.service.getObject(BUCKET_NAME,FILE_KEY);

        doReturn(new S3Object()).when(this.service).getObject(BUCKET_NAME, FILE_KEY);
        verify(this.service).getObject(BUCKET_NAME, FILE_KEY);
    }

    @Test
    public void whenCreateS3Bucket_thenCorrect() {
        this.service.createBucket(BUCKET_NAME);

        doReturn(new Bucket()).when(this.service).createBucket(BUCKET_NAME);
        verify(this.service).createBucket(BUCKET_NAME);
    }

    @Test
    public void whenUploadObjectToS3Bucket_thenCorrect() {
        String filePath = "C:/Users/Gatito/Documents/bootcamp.txt";

        this.service.uploadObject(
                BUCKET_NAME,
                FILE_KEY,
                new File(filePath)
        );

        doReturn(new PutObjectResult())
                .when(this.service)
                .uploadObject(
                        BUCKET_NAME,
                        FILE_KEY,
                    new File(filePath)
                );

        verify(this.service).uploadObject(
                BUCKET_NAME,
                FILE_KEY,
                new File(filePath)
        );
    }

    @Test
    public void whenCopyObjectFromS3Bucket_thenNotNull() {
        this.service.copyObject(
                BUCKET_NAME,
                FILE_KEY,
                BUCKET_NAME,
                FILE_KEY
        );

        doReturn(new CopyObjectResult()).when(this.service).copyObject(
                BUCKET_NAME,
                FILE_KEY,
                BUCKET_NAME,
                FILE_KEY
       );

        verify(this.service).copyObject(
                BUCKET_NAME,
                FILE_KEY,
                BUCKET_NAME,
                FILE_KEY

        );
    }

    @Test
    public void whenDeleteObjectFromS3Bucket_thenCorrect() {
        this.service.deleteObject(BUCKET_NAME, FILE_KEY);

        doNothing().when(this.service).deleteObject(BUCKET_NAME, FILE_KEY);
        verify(this.service).deleteObject(BUCKET_NAME, FILE_KEY);
    }

    @Test
    public void whenDeleteObjectsFromS3Bucket_thenCorrect() {
        List<KeyVersion> deleteObjects = List.of(new KeyVersion(FILE_KEY));

        this.service.deleteObjects(BUCKET_NAME, deleteObjects);

        doNothing().when(this.service).deleteObjects(BUCKET_NAME, deleteObjects);
        verify(this.service).deleteObjects(BUCKET_NAME, deleteObjects);
    }

    @Test
    public void whenVerifyingUploadObject_thenCorrect() {
        File file = mock(File.class);
        PutObjectResult result = mock(PutObjectResult.class);
        when(s3.putObject(anyString(), anyString(), (File) any())).thenReturn(result);

        assertThat(service.uploadObject(BUCKET_NAME, FILE_KEY, file)).isEqualTo(result);
        verify(s3).putObject(BUCKET_NAME,FILE_KEY, file);
    }
}
