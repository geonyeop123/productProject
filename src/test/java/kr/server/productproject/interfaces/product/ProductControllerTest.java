package kr.server.productproject.interfaces.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.server.productproject.domain.product.Product;
import kr.server.productproject.domain.product.ProductCommand;
import kr.server.productproject.domain.product.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("상품 목록 조회를 요청하면 페이징된 상품 목록을 받는다.")
    @Test
    void products() throws Exception {
        //given
        ProductRequest.FindAll request = new ProductRequest.FindAll(1, 10);

        List<Product> content = List.of(Product.create("product1", 1000, 100), Product.create("product2", 1000, 100)
                , Product.create("product3", 1000, 100), Product.create("product4", 1000, 100), Product.create("product5", 1000, 100));
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> page = new PageImpl<>(content, pageable, 5);
        when(productService.findAll(request.toCommand())).thenReturn(page);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .queryParam("page", String.valueOf(request.page()))
                        .queryParam("size", String.valueOf(request.size())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.page").value(request.page()))
                .andExpect(jsonPath("$.size").value(request.size()))
                .andExpect(jsonPath("$.totalCount").value(5))
                .andExpect(jsonPath("$.totalPages").value(1))
        ;
    }

    @DisplayName("상품 조회를 요청하면 productId에 해당하는 상품을 받는다.")
    @Test
    void find() throws Exception {
        //given
        Long productId = 1L;
        when(productService.find(productId)).thenReturn(Product.create("사과", 10000, 100));
        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{productId}", productId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("사과"))
                .andExpect(jsonPath("$.price").value(10000))
                .andExpect(jsonPath("$.stock").value(100))
        ;
    }

    @DisplayName("올바른 파라미터로 상품 생성을 요청하면 생성된 상품이 반환된다.")
    @Test
    void create() throws Exception {
        //given
        ProductRequest.Create request = new ProductRequest.Create("사과", 10000, 100);
        ProductCommand.Create command = request.toCommand();
        when(productService.create(command)).thenReturn(Product.create(command.name(), command.price(), command.stock()));

        // when then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("사과"))
                .andExpect(jsonPath("$.price").value(10000))
                .andExpect(jsonPath("$.stock").value(100))
        ;
    }

    @DisplayName("올바른 파라미터로 상품 수정을 요청하면 수정된 상품이 반환된다.")
    @Test
    void update() throws Exception {
        //given
        ProductRequest.Update request = new ProductRequest.Update("사과", 10000, 100);
        Long productId = 1L;
        when(productService.update(request.toCommand(productId))).thenReturn(Product.create(request.name(), request.price(), request.stock()));

        // when then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("사과"))
                .andExpect(jsonPath("$.price").value(10000))
                .andExpect(jsonPath("$.stock").value(100))
        ;
    }

}