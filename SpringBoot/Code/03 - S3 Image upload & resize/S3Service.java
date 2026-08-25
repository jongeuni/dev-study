package InTechs.InTechs.file;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class S3Service {
    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName, String folder){
        amazonS3Client.putObject(new PutObjectRequest(bucket+folder, fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String getFileUrl(String fileName){
        return String.valueOf(amazonS3Client.getUrl(bucket, fileName));
    }

    public void deleteFile(String folder, String oriName){
        amazonS3Client.deleteObject(bucket+folder, oriName);
    }
}
