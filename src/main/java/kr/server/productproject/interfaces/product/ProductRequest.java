package kr.server.productproject.interfaces.product;

import kr.server.productproject.domain.product.ProductCommand;

public record ProductRequest(

) {

    public record Create(
            String name,
            int price,
            int stock
    ) {
        public ProductCommand.Create toCommand(){
            return new ProductCommand.Create(this.name, this.price, this.stock);
        }
    }

    public record Update(
            String name,
            int price,
            int stock
    ) {
        public ProductCommand.Update toCommand(Long productId) {
            return new ProductCommand.Update(productId, name , price, stock);
        }
    }

    public record Find(
            Long productId
    ) {

    }

    public record FindAll(
            int page,
            int size
    ) {
        public ProductCommand.FindAll toCommand(){
            return new ProductCommand.FindAll(this.page, this.size);
        }
    }

}
