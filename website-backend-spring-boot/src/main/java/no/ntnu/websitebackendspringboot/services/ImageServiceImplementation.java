package no.ntnu.websitebackendspringboot.services;

import no.ntnu.websitebackendspringboot.entity.Image;
import no.ntnu.websitebackendspringboot.repositories.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Handles business logic for images
 */
@Service
@Transactional
public class ImageServiceImplementation implements ImageService {
    private final Logger log = LoggerFactory.getLogger(getClass().getName());


    private final ImageRepository imageRepository;

    public ImageServiceImplementation(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Save the provided image to the storage
     *
     * @param imageData The image file data, as received from the web client
     * @return ID of the newly created image or -1 on error
     */
    @Override
    public int save(MultipartFile imageData) {
        if (!isImage(imageData)) {
            return -1;
        }
        Image image = null;
        try {
            image = new Image(imageData.getBytes(), getFileExtension(imageData), imageData.getContentType());
            imageRepository.save(image);
        } catch (IOException e) {
            log.error("Could not store image: " + e.getMessage());
            return -1;
        }

        return image.getImageId();
    }

    /**
     * Check if the given file is an image
     *
     * @param file File to check
     * @return True if it looks like image, false if not
     */
    private static boolean isImage(MultipartFile file) {
        return file != null && isImageContentType(file.getContentType());
    }

    /**
     * Types of content which are considered images
     */
    private static final String[] IMAGE_CONTENT_TYPES = {
            "image/jpg", "image/png", "image/jpeg", "image/webp", "image/svg+xml"};

    /**
     * Checks if a given content-type of a file is an image-type
     *
     * @param contentType The content type to check
     * @return True if it is an image-tuype, false if it is not
     */
    private static boolean isImageContentType(String contentType) {
        return Arrays.asList(IMAGE_CONTENT_TYPES).contains(contentType);
    }

    /**
     * Get extension of the file (.jpg, .png, ...)
     *
     * @param imageData Image data as received from the web client
     * @return Image file extension
     */
    private static String getFileExtension(MultipartFile imageData) {
        String filename = imageData.getOriginalFilename();
        if (filename == null) return "";
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition > 0) {
            return filename.substring(dotPosition + 1);
        } else {
            return "";
        }
    }

    /**
     * Get an image from database
     *
     * @param id ID of the image
     * @return Image or null if none found
     */
    @Override
    public Image getById(Integer id) {
        return imageRepository.findById(id).orElse(null);
    }

    /**
     * Delete an image from database
     *
     * @param id ID of the image to delete
     * @return True when deleted, false on error
     */
    @Override
    public boolean delete(Integer id) {
        boolean deleted = false;
        if (imageRepository.findById(id).isPresent()) {
            imageRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<Image> getAllImages() {

        return imageRepository.findAll();
    }

}
