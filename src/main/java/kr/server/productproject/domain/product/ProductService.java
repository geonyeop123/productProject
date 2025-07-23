package kr.server.productproject.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static kr.server.productproject.domain.product.ProductCommand.Create;
import static kr.server.productproject.domain.product.ProductCommand.Update;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Page<Product> findAll(ProductCommand.FindAll command) {
        // Pageable 을 활용한 페이징 처리 시 0부터 페이지를 count 하기 때문에 1 감소
        int pageNo = command.page() - 1;
        Pageable pageable = PageRequest.of(pageNo, command.size());

        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Product find(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하는 상품이 없습니다."));
    }

    public Product create(Create command){
        Product product = Product.create(command.name(), command.price(), command.stock());
        productRepository.save(product);

        return product;
    }

    public Product update(Update command){
        Product product = find(command.productId());
        product.modify(command.name(), command.price(), command.stock());
        return product;
    }

}
