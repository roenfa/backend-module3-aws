import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.example.services.AWSS3Service;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class AWSS3ServiceIntegrationTest {
    private final String BUCKET_NAME = "bucket_name";
    private final String KEY_NAME = "bucket_key";
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
        //verify(s3).doesBucketExistV2(BUCKET_NAME);
        Assertions.assertTrue(result);
    }

    @Test
    public void whenVerifyingUploadObject_thenCorrect() {
        File file = mock(File.class);
        PutObjectResult result = mock(PutObjectResult.class);
        when(s3.putObject(anyString(), anyString(), (File) any())).thenReturn(result);

        assertThat(service.uploadObject(BUCKET_NAME, KEY_NAME, file)).isEqualTo(result);
        verify(s3).putObject(BUCKET_NAME, KEY_NAME, file);
    }
}
