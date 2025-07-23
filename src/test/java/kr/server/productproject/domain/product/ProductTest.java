package kr.server.productproject.domain.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Nested
    class Create{
        @DisplayName("name, price, stock 값을 받아 Product를 생성할 수 있다.")
        @Test
        void success() {
            // given // when
            Product product = Product.create("name", 1000, 10);

            // then
            assertThat(product.getName()).isEqualTo("name");
            assertThat(product.getPrice()).isEqualTo(1000);
            assertThat(product.getStock()).isEqualTo(10);
        }

        @DisplayName("price 값이 0원 이하인 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void failZeroUnderPrice() {
            // given
            int price = 0;
            // when // then
            assertThatThrownBy(() -> Product.create("name", price, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("stock 값이 0개 미만인 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void failToZeroUnderStock() {
            // given
            int stock = -1;

            // when
            assertThatThrownBy(() -> Product.create("name", 1000, stock))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    class Modify{
        @DisplayName("name, price, stock 값을 받아 Product를 수정할 수 있다.")
        @Test
        void success() {
            // given
            Product product = Product.create("name", 1000, 10);

            // when
            product.modify("newName", 2000, 20);

            // then
            assertThat(product.getName()).isEqualTo("newName");
            assertThat(product.getPrice()).isEqualTo(2000);
            assertThat(product.getStock()).isEqualTo(20);
        }

        @DisplayName("price 값이 0원 이하인 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void failZeroUnderPrice() {
            // given
            Product product = Product.create("name", 1000, 10);
            int price = 0;
            // when // then
            assertThatThrownBy(() -> product.modify("name", price, 10))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("stock 값이 0개 미만인 경우 IllegalArgumentException 이 발생한다.")
        @Test
        void failToZeroUnderStock() {
            // given
            Product product = Product.create("name", 1000, 10);
            int stock = -1;

            // when
            assertThatThrownBy(() -> product.modify("name", 1000, stock))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

}