package no.ntnu.websitebackendspringboot.services;

import no.ntnu.websitebackendspringboot.models.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    int save(MultipartFile imageData);

    Image getById(Integer id);

    boolean delete(Integer id);
}
