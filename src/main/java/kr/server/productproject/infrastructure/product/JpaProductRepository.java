package kr.server.productproject.infrastructure.product;

import kr.server.productproject.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, Long> {
}
