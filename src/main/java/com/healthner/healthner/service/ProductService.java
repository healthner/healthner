package com.healthner.healthner.service;

import com.healthner.healthner.controller.dto.ProductDto;
import com.healthner.healthner.domain.Gym;
import com.healthner.healthner.domain.Product;
import com.healthner.healthner.domain.ProductType;
import com.healthner.healthner.domain.Trainer;
import com.healthner.healthner.repository.GymRepository;
import com.healthner.healthner.repository.ProductRepository;
import com.healthner.healthner.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final GymRepository gymRepository;
    private final TrainerRepository trainerRepository;

    @Transactional
    public Long save(ProductDto.Request requestDto) {
        Gym gym = getGym(requestDto.getGymId());
        Trainer trainer = getTrainer(requestDto.getTrainerId());

        return productRepository.save(requestDto.toEntity(gym, trainer)).getId();
    }

    @Transactional
    public Long update(Long id, ProductDto.Request updateDto) {
        Gym gym = getGym(updateDto.getGymId());
        Trainer trainer = getTrainer(updateDto.getTrainerId());
        Product product = getProduct(id);
        product.updateProduct(updateDto.toEntity(gym, trainer));

        return product.getId();
    }

    @Transactional
    public Long updateNormal(Long id, ProductDto.Request updateDto) {
        Product product = getProduct(id);
        Gym gym = product.getGym();
        product.updateProduct(updateDto.toEntity(gym, null));

        return product.getId();
    }

    public ProductDto.Response findById(Long id) {
        Product product = getProduct(id);

        return new ProductDto.Response(product);
    }

    public ProductDto.ResponseNormal findByIdToNormal(Long id) {
        Product product = getProduct(id);

        return new ProductDto.ResponseNormal(product);
    }

    public List<ProductDto.Response> findByTrainerId(Long id) {
        List<Product> products = productRepository.findByTrainerId(id);
        return products
                .stream()
                .map(product -> new ProductDto.Response(product))
                .collect(Collectors.toList());
    }

    public List<ProductDto.Response> findByTrainerIdAndDeleteStatus(Long id, Boolean deleteStatus) {
        List<Product> products = productRepository.findByTrainerIdAndDeleteStatus(id, deleteStatus);
        return products
                .stream()
                .map(product -> new ProductDto.Response(product))
                .collect(Collectors.toList());
    }

    @Transactional
    public void changeDeleteStatus(Long id) {
        Product product = getProduct(id);
        product.changeDeleteStatus();
    }

    public boolean existsByTrainerId(Long trainerId) {
        return productRepository.existsByTrainerId(trainerId);
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 product id 입니다."));
    }

    public Gym getGym(Long gymId) {
        return gymRepository.findById(gymId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 gym id 입니다."));
    }

    public Trainer getTrainer(Long id) {
        return trainerRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 trainer id 입니다."));
    }

    public List<ProductDto.ResponseNormal> findByGymId(Long id) {
        return productRepository.findByGymId(id)
                .stream()
                .map(product -> new ProductDto.ResponseNormal(product))
                .collect(Collectors.toList());
    }

    public List<ProductDto.Response> findByGymIdAndType(Long gymId, ProductType type) {
        List<Product> ptProducts = productRepository.findByGymIdAndType(gymId, type);
        List<ProductDto.Response> list = ptProducts.stream()
                .map(product -> new ProductDto.Response(product))
                .collect(Collectors.toList());
        return list;
    }

    @Transactional
    public void delete(Long productId) {
        Product product = getProduct(productId);
        product.changeDeleteStatus();
    }
}