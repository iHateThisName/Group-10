package no.ntnu.websitebackendspringboot.controllers;

import no.ntnu.websitebackendspringboot.models.Image;
import no.ntnu.websitebackendspringboot.models.Product;
import no.ntnu.websitebackendspringboot.services.ImageService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Controller for image handling endpoints
 */
@Controller
@CrossOrigin //enables cross-origin resource sharing
@RequestMapping(path = "/api")
public class ImageController {

    final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    /**
     * Return image content from the database
     *
     * @return Image content (and correct content type) or NOT FOUND
     */
    @GetMapping("/images")
    public String getAllImages(Model model) {
        model.addAttribute("imageAttribute", imageService.getAllImages());
        return "Images";
    }

    /**
     * Return image content from the database
     *
     * @param id ID of the image to locate
     * @return Image content (and correct content type) or NOT FOUND
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> get(@PathVariable Integer id) {
        ResponseEntity<byte[]> response;
        Image image = imageService.getById(id);
        if (image != null) {
            response = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, image.getContentType())
                    .body(image.getData());
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    @PostMapping("/images")
    public ResponseEntity<String> upload(@RequestParam("fileContent") MultipartFile multipartFile) {

        ResponseEntity<String> response = null;
        int imageId = imageService.save(multipartFile);
        if (imageId > 0) {
            response = new ResponseEntity<>("" + imageId, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    /**
     * Delete image content from the database
     *
     * @param id ID of the image to delete
     * @return HTTP OK on success, NOT FOUND when image not found
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        ResponseEntity<String> response;
        if (imageService.delete(id)) {
            response = ResponseEntity.ok("");
        } else {
            response = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return response;
    }
}
