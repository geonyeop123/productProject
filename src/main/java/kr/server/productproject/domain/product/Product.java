package kr.server.productproject.domain.product;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import kr.server.productproject.domain.common.BaseEntity;
import kr.server.productproject.domain.common.IntegerConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = IntegerConverter.class)
    private Integer price;

    @Convert(converter = IntegerConverter.class)
    private Integer stock;

    private Product(String name, int price, int stock) {
        validation(price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Product create(String name, int price, int stock) {
        return new Product(name, price, stock);
    }

    private void validation(int price, int stock){
        if(price < 1) throw new IllegalArgumentException("상품의 금액은 1원 이하로 설정할 수 없습니다.");
        if(stock < 0) throw new IllegalArgumentException("상품의 재고는 0개 미만으로 설정할 수 없습니다.");
    }

    public void modify(String name, Integer price, Integer stock) {
        validation(price, stock);
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
