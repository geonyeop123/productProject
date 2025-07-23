package kr.server.productproject.domain.product;

public record ProductCommand(

) {
    public record Create(
            String name,
            Integer price,
            Integer stock
    ) {

    }

    public record Update(
            Long productId,
            String name,
            Integer price,
            Integer stock
    ) {

    }

    public record FindAll(
            int page,
            int size
    ) {

    }
}
