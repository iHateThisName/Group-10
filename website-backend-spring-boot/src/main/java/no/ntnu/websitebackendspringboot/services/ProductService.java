package no.ntnu.websitebackendspringboot.services;

import no.ntnu.websitebackendspringboot.entity.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAll();

    Product getById(int id);

    String update(Integer id, Product product);

    String delete(Integer id);

    String add(Product product);

}
