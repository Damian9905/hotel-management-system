package pl.hotel.tobiczyk.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    public AmazonS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public PutObjectResult uploadToS3(final String path,
                                      final String fileName,
                                      final Optional<Map<String, String>> optionalMetaData,
                                      final InputStream inputStream) {

        ObjectMetadata objectMetadata = new ObjectMetadata();

        optionalMetaData.ifPresent(metaData -> metaData.forEach(objectMetadata::addUserMetadata));

        return amazonS3.putObject(path, fileName, inputStream, objectMetadata);
    }

    public URL getPhotoURL(final String bucketName, final Long roomTypeId) {
        return amazonS3.getUrl(bucketName, roomTypeId.toString());
    }

    public S3Object downloadFromS3(String path, String fileName) {
        return amazonS3.getObject(path, fileName);
    }
}
