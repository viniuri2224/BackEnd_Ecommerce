package com.vincenzoiurilli.Ecommerce.services;

import com.cloudinary.provisioning.Account;
import com.vincenzoiurilli.Ecommerce.dto.products.*;
import com.vincenzoiurilli.Ecommerce.entities.*;
import com.vincenzoiurilli.Ecommerce.exceptions.NotFoundException;
import com.vincenzoiurilli.Ecommerce.exceptions.UnauthorizedException;
import com.vincenzoiurilli.Ecommerce.repositories.OrderProductsRepository;
import com.vincenzoiurilli.Ecommerce.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Products> getAllProducts(Users currentUser){
        if(currentUser.getRole() == Role.SELLER){
            return productsRepository.findBySeller(currentUser.getId());
        }
        else{
            return productsRepository.findAll();
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

    public UpdatedDigitalProductResponseDTO findByIdAndUpdate(UUID productId, NewDigitalProductDTO body, Users currentUser) {
        Products product = this.productsRepository.findById(productId).orElseThrow(() -> new NotFoundException(productId));

        if(!(product instanceof DigitalProduct)){
            throw new NotFoundException(productId); //DA CAMBIARE DOPO
        }
        if(!(currentUser.getId().equals(product.getSeller().getId()))) {
            throw new NotFoundException(productId);
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
            throw new NotFoundException(productId); //DA CAMBIARE DOPO
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
            throw new UnauthorizedException("You are not allowed to access this product"); //DA CAMBIARE DOPO
        }

        this.productsRepository.delete(product);
    }

    public List<ProductsStatisticDTO> getProductsStatisticBySellerId(Users currentUser){
        return this.orderProductsService.getProductsStatisticBySellerId(currentUser);
    }

    public boolean belongsToSeller(UUID productId, Users currentUser){
        return this.productsRepository.findyProductWithSeller(productId, currentUser.getId()) != null;
    }

}
