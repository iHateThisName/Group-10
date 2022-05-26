package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

//    @Query (
//            "SELECT Id From"
//    )
//    Optional<Image> findImageByProductName(String name);


}
