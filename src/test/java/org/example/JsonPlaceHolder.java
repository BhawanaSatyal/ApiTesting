package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.junit.BeforeClass;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class JsonPlaceHolder {
    @BeforeClass
    public static void Setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";

    }


    @Test
    public void verify_getPosts_byId() {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .basePath("posts")
                .when()
                .get("15");

        assertEquals(200, response.statusCode());
        JsonPath responseJson = response.getBody().jsonPath();
        assertEquals("eveniet quod temporibus", responseJson.getString("title"));
        assertEquals(2, responseJson.getInt("userId"));
    }

    @Test
    public void verify_toDo_byId() {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .basePath("todos")
                .when()
                .get("29");

        assertEquals(200, response.statusCode());
        JsonPath responseJson = response.getBody().jsonPath();
        assertEquals("laborum aut in quam", responseJson.getString("title"));
        assertEquals(2, responseJson.getInt("userId"));
        assertFalse(responseJson.getBoolean("completed"));
    }

    @Test
    public void verify_user_byId() {
        Response response = given()
                .baseUri("https://jsonplaceholder.typicode.com/")
                .basePath("users")
                .when()
                .get("5");
        assertEquals(200, response.statusCode());
        System.out.println(response.body().asString());
        JsonPath responseJson = response.getBody().jsonPath();

        String name = responseJson.getJsonObject("name");
        System.out.println(name);
        assertEquals("Chelsey Dietrich", name);

        HashMap<String, String> address = responseJson.getJsonObject("address");
        System.out.println(address.get("street"));
        assertEquals("Skiles Walks", address.get("street"));
        assertEquals("-31.8129", response.getBody().jsonPath().getString("address.geo.lat"));

        HashMap<String, String> company = responseJson.getJsonObject("company");
        System.out.println(company.get("catchPhrase"));
        assertEquals("User-centric fault-tolerant solution", company.get("catchPhrase"));
    }

}