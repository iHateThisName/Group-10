package no.ntnu.websitebackendspringboot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import no.ntnu.websitebackendspringboot.models.Image;
import no.ntnu.websitebackendspringboot.models.Product;
import no.ntnu.websitebackendspringboot.models.Role;
import no.ntnu.websitebackendspringboot.models.User;
import no.ntnu.websitebackendspringboot.repositories.ImageRepository;
import no.ntnu.websitebackendspringboot.repositories.ProductRepository;
import no.ntnu.websitebackendspringboot.repositories.RoleRepository;
import no.ntnu.websitebackendspringboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;

/**
 * @author "https://github.com/iHateThisName/Group-10"
 * @version 1.0
 */
@Component
public class DummyDataInitializer implements ApplicationListener<ApplicationReadyEvent> {

  private final Logger log = LoggerFactory.getLogger(getClass().getName());

  @Value("${DummyDataInitializer.user.password}")
  String password;

  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final ProductRepository productRepository;
  private final ImageRepository imageRepository;

  public DummyDataInitializer(UserRepository userRepository,
                              RoleRepository roleRepository, ProductRepository productRepository, ImageRepository imageRepository) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.productRepository = productRepository;
    this.imageRepository = imageRepository;
  }


  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    Optional<User> arnold = userRepository.findByUsername("arnold");

    if (arnold.isEmpty()) {
      log.info("Adding users to database...");
      addData(password);
      log.info("Done adding users to database");
      StringBuilder stringBuilder = new StringBuilder();
      userRepository.findAll().forEach(user -> {
        stringBuilder
                .append("\n----------------------------\n")
                .append(user.getUsername())
                .append("\n")
                .append(user);
      });
      log.info("information about all the users: {}", stringBuilder);

    } else {
      log.info("Users already exist in the database");
    }

    Product backpack = productRepository.findByNameIgnoreCase("SALOMON - Backpack");
    if (backpack == null) {

      log.info("Adding products to database...");
      addProducts();
      log.info("Done adding products to database");
    } else {
      log.info("Products already exist in the database");
    }

    StringBuilder imageStringBuilder = new StringBuilder();
    imageRepository.findAll().forEach(image -> {
      imageStringBuilder
              .append("\n----------------------------\n")
              .append(image.getId())
              .append("\n")
              .append(image.getFileName());
    });
    log.info("information about all the users: {}", imageStringBuilder);

//    log.info(imageRepository.getAll().toString());

  }

  private void addData(String password) {

    Role user = new Role("user");
    Role manager = new Role("manager");
    Role admin = new Role("admin");
    roleRepository.save(user);
    roleRepository.save(manager);
    roleRepository.save(admin);


    User john = new User("john", password, "John Travolta");
    User will = new User("will", password, "Will Smith");
    User jim = new User("jim", password, "Jim Carry");
    User arnold = new User("arnold", password, "Arnold Schwarzenegger");

    john.addRole(user);
    will.addRole(manager);
    jim.addRole(admin);
    arnold.addRole(user);
    arnold.addRole(manager);
    arnold.addRole(admin);

    userRepository.save(john);
    userRepository.save(will);
    userRepository.save(jim);
    userRepository.save(arnold);

  }

  private void addProducts() {


    productRepository.save(new Product("SALOMON - Backpack", "E9 Premium Backpack", 599.0));

    productRepository.save(new Product("MAMMUT - Hiking Boots", "X100 Hiking Boots 2022", 899.0));

    productRepository.save(new Product("BERGANS - ALLWEATHER Jacket", "ALLWEATHER Jacket", 999.00));

    productRepository.save(new Product("DEVOLD - Winter Sweater", "Winter Sweater 2021", 499.00));


    Image SALOMON_Backpack_Image = null;
    Image MAMMUT_Shoes_Image =null;
    Image BERGANS_Jacket_Image = null;
    Image DEVOLD_Winter_Sweater = null;
    try {
      SALOMON_Backpack_Image = new Image(imageToByte("SALOMON_Backpack.png", "png"), "png", MediaType.IMAGE_PNG_VALUE);
      MAMMUT_Shoes_Image = new Image(imageToByte("MAMMUT_Shoes.png", "png"), "png", MediaType.IMAGE_PNG_VALUE);
      BERGANS_Jacket_Image = new Image(imageToByte("BERGANS_Jacket.png", "png"), "png", MediaType.IMAGE_PNG_VALUE);
      DEVOLD_Winter_Sweater = new Image(imageToByte("DEVOLD_WinterSweater.png", "png"), "png", MediaType.IMAGE_PNG_VALUE);





    } catch (IOException e) {
      e.printStackTrace();
    }

    imageRepository.save(SALOMON_Backpack_Image);
    imageRepository.save(MAMMUT_Shoes_Image);
    imageRepository.save(BERGANS_Jacket_Image);
    imageRepository.save(DEVOLD_Winter_Sweater);



  }

  private byte[] imageToByte(String imageName, String formatName) throws IOException {

    String currentWorkingDir = System.getProperty("user.dir");
    String imageProductsDir = "/src/main/resources/static/images/products/";
    String fullPath = currentWorkingDir + imageProductsDir + imageName;


    // read the image from the file
    BufferedImage bufferedImage = ImageIO.read(new File(fullPath));

    // create the object of ByteArrayOutputStream class
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    // write the image into the object of ByteArrayOutputStream class
    ImageIO.write(bufferedImage, formatName, byteArrayOutputStream);

    // create the byte array from image
    byte [] byteArray = byteArrayOutputStream.toByteArray();

//    ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(fullPath)));


    System.out.println("erkgmegnegeig\n" + byteArray);

    return byteArray;
  }

}
