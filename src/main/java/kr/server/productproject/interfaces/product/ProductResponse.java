package kr.server.productproject.interfaces.product;

import kr.server.productproject.domain.product.Product;

import java.time.LocalDateTime;

public record ProductResponse (
        Long productId,
        String name,
        int price,
        int stock,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public static ProductResponse from(Product product){
        return new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getStock(), product.getCreatedAt(), product.getModifiedAt());
    }
}
