package com.vincenzoiurilli.Ecommerce.services;

import com.cloudinary.provisioning.Account;
import com.vincenzoiurilli.Ecommerce.dto.categories.GetCategoriesResponseDTO;
import com.vincenzoiurilli.Ecommerce.dto.products.*;
import com.vincenzoiurilli.Ecommerce.entities.*;
import com.vincenzoiurilli.Ecommerce.exceptions.ForbiddenException;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.exceptions.ProductTypeException;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.OrderProductsRepository;
import com.vincenzoiurilli.Ecommerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private OrderProductsService orderProductsService;

    public NewProductResponseDTO createNewProduct(NewDigitalProductDTO body, Users currentUser){

        DigitalProduct product = new DigitalProduct(body.name(), body.description(), currentUser, body.price(), body.quantity(), body.file_url(),  body.file_name(), body.file_size(), body.format());

        DigitalProduct newProduct = productsRepository.save(product);

        return new NewProductResponseDTO(newProduct.getId());

    }

    public NewProductResponseDTO createNewProduct(NewPhysicalProductDTO body, Users currentUser){

        PhysicalProduct product = new PhysicalProduct(body.name(), body.description(), currentUser, body.price(), body.quantity(), body.weight(),  body.dimensions(), body.shipping_required());

        PhysicalProduct newProduct = productsRepository.save(product);

        return new NewProductResponseDTO(newProduct.getId());

    }

    private ProductsResponseDTO getProductDTO(Products product){
        ProductsResponseDTO dto;
        List<ProductCategories> productCategories;
        productCategories = product.getProductCategories();

        Categories category;
        List<GetCategoriesResponseDTO> categories = new ArrayList<>();

        for (ProductCategories c : productCategories) {
            category = c.getCategory();
            GetCategoriesResponseDTO categoryDto = new GetCategoriesResponseDTO(category.getId(), category.getName(), category.getDescription(), category.getType());
            categories.add(categoryDto);
        }

        if (product instanceof DigitalProduct) {
            dto = new ProductsResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    "DIGITAL",
                    product.getSeller().getEmail(),
                    product.getPrice(),
                    product.getQuantity(),
                    ((DigitalProduct) product).getFileName(),
                    ((DigitalProduct) product).getFileUrl(),
                    ((DigitalProduct) product).getFileSize(),
                    ((DigitalProduct) product).getFormat(),
                    0,
                    "",
                    false,
                    categories
            );
            return dto;
        } else if (product instanceof PhysicalProduct) {
            dto = new ProductsResponseDTO(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    "PHYSICAL",
                    product.getSeller().getEmail(),
                    product.getPrice(),
                    product.getQuantity(),
                    "",
                    "",
                    "",
                    "",
                    ((PhysicalProduct) product).getWeight(),
                    ((PhysicalProduct) product).getDimensions(),
                    ((PhysicalProduct) product).isShipping_required(),
                    categories
            );
            return dto;
        }
        throw new IllegalArgumentException(
                "Tipo di prodotto non supportato: " + product.getClass().getSimpleName()
        );
    }

    private List<ProductsResponseDTO> getProductsDTO(List<Products> products) {
        List<ProductsResponseDTO> dtos = new ArrayList<>();
        for (Products product : products) {
            dtos.add(getProductDTO(product));
        }
        return dtos;
    }

    public List<ProductsResponseDTO> getAllProducts(Users currentUser){
        List<Products> foundProducts;
        List<ProductsResponseDTO> dtos = new ArrayList<>();

        if(currentUser.getRole() == Role.SELLER) {
            foundProducts = this.productsRepository.findBySeller(currentUser.getId());
            if (foundProducts.isEmpty()) {
                throw new NotFoundException("Products for sellers not found");
            }
            dtos.addAll(getProductsDTO(foundProducts));
            return dtos;
        }
        else{
           foundProducts = this.productsRepository.findAll();
           if(foundProducts.isEmpty()){
               throw new NotFoundException("Products not found");
           }
           dtos.addAll(getProductsDTO(foundProducts));
           return dtos;
        }
    }

    public Products findById(UUID productId) {

        return this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));
    }

    public Products findById(UUID productId, Users currentUser){
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(currentUser.getRole() == Role.SELLER){
            if(!(currentUser.getId().equals(product.getSeller().getId()))){
                throw new UnauthorizedException("You are not allowed to access this product");
            }
        }

        return product;

    }

    public ProductsResponseDTO findProductById(UUID productId, Users currentUser){
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(currentUser.getRole() == Role.SELLER){
            if(!(currentUser.getId().equals(product.getSeller().getId()))){
                throw new UnauthorizedException("You are not allowed to access this product");
            }

        }
        return getProductDTO(product);

    }

    public UpdatedDigitalProductResponseDTO findByIdAndUpdate(UUID productId, NewDigitalProductDTO body, Users currentUser) {
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(!(product instanceof DigitalProduct)){
            throw new ProductTypeException("The product it is not digital");

        }
        if(!(currentUser.getId().equals(product.getSeller().getId()))) {
            throw new UnauthorizedException("You are not allowed to access this product");
        }

        DigitalProduct digitalProduct = (DigitalProduct) product;

        digitalProduct.setName(body.name());
        digitalProduct.setDescription(body.description());
        digitalProduct.setPrice(body.price());
        digitalProduct.setQuantity(body.quantity());
        digitalProduct.setFileName(body.file_name());
        digitalProduct.setFileSize(body.file_size());
        digitalProduct.setFormat(body.format());
        digitalProduct.setFileUrl(body.file_url());

        this.productsRepository.save(digitalProduct);

        return new UpdatedDigitalProductResponseDTO(digitalProduct.getId(),digitalProduct.getName(), digitalProduct.getDescription(), digitalProduct.getSeller().getEmail(), digitalProduct.getPrice(), digitalProduct.getQuantity(), digitalProduct.getFileName(), digitalProduct.getFileUrl(), digitalProduct.getFileSize(), digitalProduct.getFormat());

    }

    public UpdatedPhysicalProductResponseDTO findByIdAndUpdate(UUID productId, NewPhysicalProductDTO body, Users currentUser) {
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(!(product instanceof PhysicalProduct)){
            throw new ProductTypeException("The product it is not physical");
        }
        if(!(currentUser.getId().equals(product.getSeller().getId()))) {
            throw new NotFoundException(productId);
        }

        PhysicalProduct physicalProduct = (PhysicalProduct) product;

        physicalProduct.setName(body.name());
        physicalProduct.setDescription(body.description());
        physicalProduct.setPrice(body.price());
        physicalProduct.setQuantity(body.quantity());
        physicalProduct.setWeight(body.weight());
        physicalProduct.setDimensions(body.dimensions());
        physicalProduct.setShipping_required(body.shipping_required());

        this.productsRepository.save(physicalProduct);

        return new UpdatedPhysicalProductResponseDTO(physicalProduct.getId(),physicalProduct.getName(), physicalProduct.getDescription(), physicalProduct.getSeller().getEmail(), physicalProduct.getPrice(), physicalProduct.getQuantity(),physicalProduct.getWeight(), physicalProduct.getDimensions(), physicalProduct.isShipping_required());

    }

    public boolean hasOrders(UUID productId){
        return this.orderProductsService.countByProduct(productId) != 0;
    }

    public void findByIdAndDelete(UUID productId, Users currentUser){
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(!(currentUser.getId().equals(product.getSeller().getId()))){
            throw new UnauthorizedException("You are not allowed to access this product");
        }

        if(hasOrders(product.getId())){
            throw new ForbiddenException("This product has orders; therefor you can't delete this product");
        }

        this.productsRepository.delete(product);
    }

    public List<ProductsStatisticDTO> getProductsStatisticBySellerId(Users currentUser){
        return this.orderProductsService.getProductsStatisticBySellerId(currentUser);
    }

    public boolean belongsToSeller(UUID productId, Users currentUser){
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));
        return currentUser.getId().equals(product.getSeller().getId());
    }

}
