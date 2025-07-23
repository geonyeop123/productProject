package kr.server.productproject.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("name, price, stock의 값을 받아 Product를 생성 후 저장한다.")
    @Test
    void create() {
        // given
        ProductCommand.Create command = new ProductCommand.Create("name", 1000, 10);
        Product product = Product.create(command.name(), command.price(), command.stock());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // when
        Product savedProduct = productService.create(command);

        // then
        assertThat(savedProduct).isNotNull();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Nested
    class Find{
        @DisplayName("id로 상품 조회를 요청하면 id에 해당하는 상품이 반환된다.")
        @Test
        void success() {
            // given
            Product product = Product.create("name", 1000, 10);
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

            // when
            Product findProduct = productService.find(product.getId());
            // then
            assertThat(findProduct).isNotNull();
            verify(productRepository, times(1)).findById(product.getId());
        }

        @DisplayName("id로 상품 조회를 요청했을 때 해당하는 상품이 없는 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void fail() {
            // given
            Product product = Product.create("name", 1000, 10);

            // when // then
            assertThatThrownBy(() -> productService.find(product.getId()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class Update{
        @DisplayName("name, price, stock의 값을 받아 Product를 수정 후 갱신 및 반환한다.")
        @Test
        void success() {
            // given
            Product product = Product.create("name", 1000, 10);
            when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
            ProductCommand.Update command = new ProductCommand.Update(product.getId(), "newName", 2000, 20);

            // when
            productService.update(command);

            // then
            assertThat(product).isNotNull();
            verify(productRepository, times(1)).findById(product.getId());
        }

        @DisplayName("수정 요청 시 id에 해당하는 Product가 없는 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void fail() {
            // given
            Product product = Product.create("name", 1000, 10);
            ProductCommand.Update command = new ProductCommand.Update(product.getId(), "newName", 2000, 20);

            // when // then
            assertThatThrownBy(() -> productService.update(command)).isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class FindAll {

    }


}