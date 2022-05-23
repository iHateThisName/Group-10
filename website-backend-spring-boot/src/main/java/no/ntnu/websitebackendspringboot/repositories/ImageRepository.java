package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    Optional<Image> findImageByFileName(String name);


}
