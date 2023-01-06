package pl.hotel.tobiczyk.service;

import com.amazonaws.services.glue.model.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.hotel.tobiczyk.domain.model.Photo;
import pl.hotel.tobiczyk.repository.PhotoRepository;

import java.io.IOException;
import java.util.*;

@Service
public class PhotoService {

    private static final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    private final AmazonS3Service amazonS3Service;
    private final PhotoRepository photoRepository;
    private final RoomService roomService;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public PhotoService(final AmazonS3Service amazonS3Service, final PhotoRepository photoRepository, RoomService roomService) {
        this.amazonS3Service = amazonS3Service;
        this.photoRepository = photoRepository;
        this.roomService = roomService;
    }

    public void upload(MultipartFile file, final Long roomTypeId) throws IOException {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, roomTypeId);
        String fileName = String.format("%s", file.getOriginalFilename());

        // Uploading file to s3
        amazonS3Service.uploadToS3(
                path, fileName, Optional.of(metadata), file.getInputStream());

        // Saving metadata to db
        photoRepository.save(Photo.builder()
                .fileName(fileName)
                .filePath(amazonS3Service.getPhotoURL(this.bucketName, roomTypeId).toString() + "/" + fileName)
                .roomType(roomService.findRoomTypeById(roomTypeId).orElseThrow(() ->
                        new IllegalArgumentException("Room type with that id does not exists!")
                ))
                .build());
    }

    public List<Photo> showAllPhotos() {
        return photoRepository.findAll();
    }

    public void deletePhoto(Long id) {
        Photo photo = photoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No photo with that id!"));
        photoRepository.deleteById(id);

        String key = photo.getRoomType().getId() + "/" + photo.getFileName();
        amazonS3Service.deleteFromS3(bucketName, key);
        logger.info("Photo with key: %s has been deleted from AmazonS3", key);
    }
}
