package com.iecommerce.ecommerce.service;

import com.iecommerce.ecommerce.entity.Product;
import com.iecommerce.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final String uploadDir =
            "src/main/resources/static/images/products/";

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void saveProduct(
            Product product,
            MultipartFile imageFile,
            String imageUrl) {

        try {

            /*
             * If editing an existing product
             * and no new image is selected,
             * keep the old image.
             */
            if (product.getId() != null) {

                Product existingProduct =
                        productRepository.findById(product.getId())
                                .orElse(null);

                if (existingProduct != null) {

                    if ((imageFile == null || imageFile.isEmpty())
                            && (imageUrl == null || imageUrl.isBlank())) {

                        product.setImage(existingProduct.getImage());
                    }
                }
            }

            /*
             * Priority:
             * 1. Uploaded file
             * 2. Image URL
             */
            if (imageFile != null && !imageFile.isEmpty()) {

                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFileName =
                        imageFile.getOriginalFilename();

                if (originalFileName != null
                        && !originalFileName.isBlank()) {

                    String fileName =
                            System.currentTimeMillis()
                                    + "_"
                                    + originalFileName;

                    Path filePath =
                            uploadPath.resolve(fileName);

                    Files.copy(
                            imageFile.getInputStream(),
                            filePath,
                            StandardCopyOption.REPLACE_EXISTING
                    );

                    product.setImage(
                            "/images/products/" + fileName
                    );
                }

            } else if (imageUrl != null
                    && !imageUrl.isBlank()) {

                product.setImage(imageUrl.trim());
            }

            productRepository.save(product);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Image upload failed!",
                    e
            );
        }
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
