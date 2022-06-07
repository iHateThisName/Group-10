package no.ntnu.websitebackendspringboot;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import no.ntnu.websitebackendspringboot.entity.Image;
import no.ntnu.websitebackendspringboot.entity.Product;
import no.ntnu.websitebackendspringboot.entity.Role;
import no.ntnu.websitebackendspringboot.entity.User;
import no.ntnu.websitebackendspringboot.repositories.ProductRepository;
import no.ntnu.websitebackendspringboot.repositories.RoleRepository;
import no.ntnu.websitebackendspringboot.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
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

  public DummyDataInitializer(UserRepository userRepository,
                              RoleRepository roleRepository, ProductRepository productRepository) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.productRepository = productRepository;
  }


  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {

    Optional<User> arnold = userRepository.findByUsername("arnold");
    if (arnold.isEmpty()) {

      log.info("Adding users to database...");
      addData(password);
      userRepository.flush();
      log.info("Done adding users to database");

    } else {

      log.info("Users already exist in the database");
    }

    Optional<Product> backpack = productRepository.findByNameIgnoreCase("SALOMON - Backpack");
    if (backpack.isEmpty()) {

      log.info("Adding products to database...");
      addProducts();
      productRepository.flush();
      log.info("Done adding products to database");

    } else {
      log.info("Products already exist in the database");
    }

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

    //Creating products
    Product backpack = new Product("SALOMON", "E9 Premium Backpack", 599.0);
    Product boots = new Product("MAMMUT", "X100 Hiking Boots 2022", 899.0);
    Product jacket = new Product("BERGANS", "ALLWEATHER Jacket", 999.00);
    Product sweater = new Product("DEVOLD", "Winter Sweater 2021", 499.00);

    //Adding images to the products
    backpack.addImage(new Image(imageToByte("SALOMON_Backpack.png", "png"), "png", MediaType.IMAGE_PNG_VALUE));
    boots.addImage(new Image(imageToByte("MAMMUT_Shoes.png", "png"), "png", MediaType.IMAGE_PNG_VALUE));
    jacket.addImage(new Image(imageToByte("BERGANS_Jacket.png", "png"), "png", MediaType.IMAGE_PNG_VALUE));
    sweater.addImage(new Image(imageToByte("DEVOLD_WinterSweater.png", "png"), "png", MediaType.IMAGE_PNG_VALUE));

    //Adding Categories to the product
    //All is applied as default
    backpack.addCategory("men");
    backpack.addCategory("women");
    boots.addCategory("men");
    jacket.addCategory("men");
    sweater.addCategory("men");

    //Adding the number of products
    backpack.setProductAmount(4);
    boots.setProductAmount(247);
    jacket.setProductAmount(132);
    sweater.setProductAmount(74);

    //Saving the product to the database
    productRepository.save(backpack);
    productRepository.save(boots);
    productRepository.save(jacket);
    productRepository.save(sweater);

  }

  private byte[] imageToByte(String imageName, String formatName) {

    String currentWorkingDir = System.getProperty("user.dir");
    String imageProductsDir = "/src/main/resources/static/images/products/";
    String fullPath = currentWorkingDir + imageProductsDir + imageName;

    try {
    // read the image from the file
    BufferedImage bufferedImage = ImageIO.read(new File(fullPath));

    // create the object of ByteArrayOutputStream class
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    // write the image into the object of ByteArrayOutputStream class
    ImageIO.write(bufferedImage, formatName, byteArrayOutputStream);

    // create the byte array from image
      byte [] byteArray = byteArrayOutputStream.toByteArray();

      return byteArray;

    } catch (IOException ioException) {
      log.warn("Failed to find image file");
    }

    return null;
  }

}
