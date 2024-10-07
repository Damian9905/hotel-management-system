package pl.hotel.tobiczyk.common.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    private final AmazonS3 amazonS3;

    public void uploadToS3(final String path,
                                      final String fileName,
                                      final Optional<Map<String, String>> optionalMetaData,
                                      final InputStream inputStream) {

        ObjectMetadata objectMetadata = new ObjectMetadata();

        optionalMetaData.ifPresent(metaData -> metaData.forEach(objectMetadata::addUserMetadata));

        amazonS3.putObject(path, fileName, inputStream, objectMetadata);
    }

    public URL getPhotoURL(final String bucketName, final Long roomTypeId) {
        return amazonS3.getUrl(bucketName, roomTypeId.toString());
    }

    public S3Object downloadFromS3(String path, String fileName) {
        return amazonS3.getObject(path, fileName);
    }

    public void deleteFromS3(final String bucketName, final String fileName) {
        amazonS3.deleteObject(bucketName, fileName);
    }
}
