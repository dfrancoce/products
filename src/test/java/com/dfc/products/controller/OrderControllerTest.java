package com.dfc.products.controller;

import com.dfc.products.service.OrderProductService;
import com.dfc.products.service.OrderService;
import com.dfc.products.service.resource.OrderResource;
import com.dfc.products.service.resource.ProductResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;
    @MockBean
    private OrderProductService orderProductService;

    @Before
    public void setUp() {
        given(orderService.create(any())).willReturn(getOrderResource());
        given(orderService.get()).willReturn(Collections.singletonList(getOrderResource()));
        given(orderService.get(any(), any())).willReturn(Collections.singletonList(getOrderResource()));
        given(orderService.get(1L)).willReturn(getOrderResource());
        given(orderService.update(any(), eq(1L))).willReturn(getOrderResource());
        willDoNothing().given(orderService).delete(1L);
        willDoNothing().given(orderProductService).addProductsToOrder(anyList(), eq(1L));
        willDoNothing().given(orderProductService).deleteProductsFromOrder(anyList(), eq(1L));
        given(orderProductService.calculate(1L)).willReturn(1000.0);
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/api/orders")
                .content(getOrderResourceAsJson(getOrderResource()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAll() throws Exception {
        mvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getFromTo() throws Exception {
        mvc.perform(get("/api/orders/period")
                .param("fromDate", "01122018")
                .param("toDate", "12122018")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getById() throws Exception {
        mvc.perform(get("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(put("/api/orders/1")
                .content(getOrderResourceAsJson(getOrderResource()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mvc.perform(delete("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addProducts() throws Exception {
        mvc.perform(put("/api/orders/1/products")
                .content(getProductResourcesAsJson(Collections.singletonList(getProductResource())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProducts() throws Exception {
        mvc.perform(delete("/api/orders/1/products")
                .content(getProductResourcesAsJson(Collections.singletonList(getProductResource())))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void calculate() throws Exception {
        mvc.perform(get("/api/orders/1/calculate")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private OrderResource getOrderResource() {
        final OrderResource orderResource = new OrderResource();
        orderResource.setId(1L);
        orderResource.setEmail("test@gmail.com");
        orderResource.setOrderDate(new Date());

        return orderResource;
    }

    private String getOrderResourceAsJson(final OrderResource orderResource) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(orderResource);
    }

    private ProductResource getProductResource() {
        final ProductResource productResource = new ProductResource();
        productResource.setId(1L);
        productResource.setName("Iphone X");
        productResource.setPrice(1200.0);

        return productResource;
    }

    private String getProductResourcesAsJson(final List<ProductResource> productResource) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productResource);
    }
}