package com.pranav.ecommers.product.service;

import com.pranav.ecommers.product.DTO.ProductRequest;
import com.pranav.ecommers.product.DTO.ProductResponse;
import com.pranav.ecommers.product.entity.Product;
import com.pranav.ecommers.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public  ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product,productRequest);
        Product saveProduct = productRepository.save(product);
        return mapToProductResponse(saveProduct);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponse> UpdateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(exsistigProduct -> {
                    updateProductFromRequest(exsistigProduct,productRequest);
                    Product saveProduct = productRepository.save(exsistigProduct);
                    return mapToProductResponse(saveProduct);
                });
    }

    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setActive(false);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }

    private ProductResponse mapToProductResponse(Product saveProduct) {
        ProductResponse response = new ProductResponse();
        response.setId(saveProduct.getId());
        response.setName(saveProduct.getName());
        response.setDescription(saveProduct.getDescription());
        response.setPrice(saveProduct.getPrice());
        response.setCategory(saveProduct.getCategory());
        response.setStockQuantity(saveProduct.getStockQuantity());
        response.setImageUrl(saveProduct.getImageUrl());
        response.setActive(saveProduct.getActive());
        return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }


    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
