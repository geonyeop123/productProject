package kr.server.productproject.domain.product;

import kr.server.productproject.infrastructure.product.JpaProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class ProductServiceIntegrationTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private JpaProductRepository jpaProductRepository;

    @DisplayName("상품 정보를 받아 해당 상품을 저장한 후 반환한다.")
    @Test
    void create() {
        // given
        ProductCommand.Create command = new ProductCommand.Create("name", 1000, 10);

        // when
        Product product = productService.create(command);

        // then
        assertThat(product).isNotNull();
        jpaProductRepository.findById(product.getId()).ifPresent(p -> assertThat(p.getId()).isEqualTo(product.getId()));
    }

    @Nested
    class Find {
        @DisplayName("id를 받아 상품을 조회하여 반환한다.")
        @Test
        void success() {
            // given
            Product product = jpaProductRepository.save(Product.create("name", 1000, 10));

            // when
            Product findProduct = productService.find(product.getId());

            // then
            assertThat(findProduct).isEqualTo(product);
        }

        @DisplayName("id를 받아 상품을 조회했을 때 조회된 상품이 없는 경우 Exception 이 발생한다.")
        @Test
        void fail() {
            // given // when // then
            assertThatThrownBy(() -> productService.find(1L))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Nested
        class Update {
            @DisplayName("상품 정보를 받아 상품 정보를 수정한 후 반환한다.")
            @Test
            void success() {
                // given
                Product product = jpaProductRepository.save(Product.create("name", 1000, 10));
                ProductCommand.Update command = new ProductCommand.Update(product.getId(), "newName", 2000, 20);

                // when
                Product updatedProduct = productService.update(command);

                // then
                assertThat(updatedProduct.getName()).isEqualTo(command.name());
                assertThat(updatedProduct.getPrice()).isEqualTo(command.price());
                assertThat(updatedProduct.getStock()).isEqualTo(command.stock());
            }

            @DisplayName("상품 수정 요청 시 id에 해당하는 상품이 없는 경우 Exception 이 발생한다.")
            @Test
            void fail() {
                // given
                ProductCommand.Update command = new ProductCommand.Update(1L, "newName", 2000, 20);

                // when // then
                assertThatThrownBy(() -> productService.update(command))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }
    }

    @Nested
    class FindAll {
        @DisplayName("page와 size를 받아 페이징 된 상품 목록을 조회한다.")
        @Test
        void success() {
            // given
            ProductCommand.FindAll command = new ProductCommand.FindAll(1, 10);
            jpaProductRepository.saveAll(List.of(Product.create("name1", 1000, 10),
                    Product.create("name2", 1000, 10),
                    Product.create("name3", 1000, 10)));

            // when
            Page<Product> page = productService.findAll(command);

            // then
            assertThat(page.getTotalElements()).isEqualTo(3);
            assertThat(page.getSize()).isEqualTo(10);
            assertThat(page.getTotalPages()).isEqualTo(1);
            assertThat(page.getContent()).hasSize(3);
        }

        @DisplayName("조회할 상품 목록이 없는 경우 빈 목록이 조회된다.")
        @Test
        void emptyList() {
            // given
            ProductCommand.FindAll command = new ProductCommand.FindAll(1, 10);

            // when
            Page<Product> page = productService.findAll(command);

            // then
            assertThat(page.getContent()).isEmpty();
        }
    }
}