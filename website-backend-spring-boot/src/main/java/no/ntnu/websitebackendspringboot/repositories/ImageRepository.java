package no.ntnu.websitebackendspringboot.repositories;

import no.ntnu.websitebackendspringboot.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, Integer> {

}
