package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.models.Image;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Integer> {

//    @Query (
//            "SELECT Id From"
//    )
//    Optional<Image> findImageByProductName(String name);

    List<Image> findImageByProduct_Id(Integer id);

}
