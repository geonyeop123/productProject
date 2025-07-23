package kr.server.productproject.interfaces.product;

import kr.server.productproject.domain.product.Product;
import kr.server.productproject.domain.product.ProductCommand;
import kr.server.productproject.domain.product.ProductService;
import kr.server.productproject.interfaces.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/api/v1/products")
    public ResponseEntity<PageResponse<ProductResponse>> products( ProductRequest.FindAll request) {
        Page<Product> result = productService.findAll(request.toCommand());
        List<ProductResponse> responses = result.getContent().stream().map(ProductResponse::from).toList();
        return ResponseEntity.ok(new PageResponse<>(responses, result.getNumber() + 1, result.getSize(), result.getTotalElements(), result.getTotalPages()));
    }

    @GetMapping("/api/v1/products/{productId}")
    public ResponseEntity<ProductResponse> find(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(ProductResponse.from(productService.find(productId)));
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest.Create request) {
        ProductCommand.Create command = request.toCommand();
        return ResponseEntity.ok(ProductResponse.from(productService.create(command)));
    }

    @PutMapping("/api/v1/products/{productId}")
    public ResponseEntity<ProductResponse> update(@PathVariable("productId") Long productId, @RequestBody ProductRequest.Update request){
        return ResponseEntity.ok(ProductResponse.from(productService.update(request.toCommand(productId))));
    }

}
