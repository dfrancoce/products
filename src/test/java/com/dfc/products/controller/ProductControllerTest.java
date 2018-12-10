package com.dfc.products.controller;

import com.dfc.products.service.ProductService;
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

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService productService;

    @Before
    public void setUp() {
        given(productService.create(any())).willReturn(getProductResource());
        given(productService.get()).willReturn(Collections.singletonList(getProductResource()));
        given(productService.get(1L)).willReturn(getProductResource());
        given(productService.update(any(), eq(1L))).willReturn(getProductResource());
        willDoNothing().given(productService).delete(1L);
    }

    @Test
    public void create() throws Exception {
        mvc.perform(post("/api/products")
                .content(getProductResourceAsJson(getProductResource()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAll() throws Exception {
        mvc.perform(get("/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getById() throws Exception {
        mvc.perform(get("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        mvc.perform(put("/api/products/1")
                .content(getProductResourceAsJson(getProductResource()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteById() throws Exception {
        mvc.perform(delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ProductResource getProductResource() {
        final ProductResource productResource = new ProductResource();
        productResource.setId(1L);
        productResource.setName("Iphone X");
        productResource.setPrice(1200.0);

        return productResource;
    }

    private String getProductResourceAsJson(final ProductResource productResource) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(productResource);
    }
}