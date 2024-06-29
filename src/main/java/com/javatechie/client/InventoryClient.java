package com.javatechie.client;

import com.javatechie.dto.Item;
import com.javatechie.dto.ItemRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InventoryClient {
    @Autowired
    private HttpGraphQlClient httpGraphQlClient;

    public List<Item> viewProducts(){
        String graphQlQuery = "query GetAllProductsByCategory {\n" +
                "    getAllProducts {\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "    }\n" +
                "}\n";
        return httpGraphQlClient.document(graphQlQuery)
                .retrieve("getAllProducts")
                .toEntityList(Item.class).block();
    }

    public List<Item> viewProductsByCategory(String category){
        String graphQlQuery = String.format("query GetAllProductsByCategory {\n" +
                "    getAllProductsByCategory(category: \"%s\") {\n" +
                "        id\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "        stock\n" +
                "    }\n" +
                "}\n", category);
        return httpGraphQlClient.document(graphQlQuery)
                .retrieve("getAllProductsByCategory")
                .toEntityList(Item.class).block();
    }

    public Item createProduct(ItemRequestDto itemRequestDto){
        String graphQlQuery = String.format("mutation CreateProduct {\n" +
                "    createProduct(name: \"%s\", category: \"%s\", price: %f, stock: %d) {\n" +
                "        id\n" +
                "        name\n" +
                "        category\n" +
                "        price\n" +
                "        stock\n" +
                "    }\n" +
                "}\n", itemRequestDto.getName(), itemRequestDto.getCategory(), itemRequestDto.getPrice(), itemRequestDto.getStock());

        return httpGraphQlClient.document(graphQlQuery)
                .retrieve("createProduct")
                .toEntity(Item.class).block();
    }
}
