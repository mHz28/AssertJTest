package com.test;

import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.Gson;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.specification.RequestSpecification;
import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
public class RunDTOTest {
    private static RequestSpecification spec;
    private static String url = "http://localhost:9000";
    private String postPath = "/v1/posts";
    private String sampleTitle = "title 1";
    private String sampleBody = "blog post 1";
    private String sampleLink = "/v1/posts/1";
    
    @BeforeClass
    public static void initSpec(){
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(url)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }
    
	@Test
	public void createBlogAndCheckExistence(){
	    String newBlog = createDummyBlog();
	    String blogResourceLocation = createResource(postPath, newBlog);
	    String retrievedBlog = getResource(blogResourceLocation.replaceAll("999", "1"));
	    
	    Gson g = new Gson(); 
	    BlogDTO retrievedBlogObj = g.fromJson(retrievedBlog, BlogDTO.class);
	    BlogDTO newBlogObj = g.fromJson(newBlog, BlogDTO.class);
	    assertEqualBlog(newBlogObj, retrievedBlogObj);
	}

	private String createDummyBlog() {
	    BlogDTO rtn =  new BlogDTO();
	    rtn.setTitle(sampleTitle);
	    rtn.setBody(sampleBody);
	    rtn.setLink(sampleLink);
	    rtn.setId(1);
	    return new Gson().toJson(rtn).toString();
	}

	private String createResource(String path, Object bodyPayload) {
	    return given()
	            .spec(spec)
	            .body(bodyPayload)
	            .when()
	            .post(path)
	            .then()
	            .statusCode(201)
	            .extract().header("location");
	}
	private  String getResource(String locationHeader) {
		spec.contentType(ContentType.ANY);	
		return given()
	                .spec(spec)
	                .when()
	                .get(locationHeader)
	                .then()
	                .statusCode(200)
	                .extract().asString();
	}
	private <T> T getResource(String locationHeader, Class<T> responseClass) {
	    return given()
	                .spec(spec)
	                .when()
	                .get(locationHeader)
	                .then()
	                .statusCode(200)
	                .extract().as(responseClass);
	}
	private void assertEqualBlog(BlogDTO newBlog, BlogDTO retrievedBlog) {
		
	    assertThat(retrievedBlog.getId()).isEqualTo(newBlog.getId());
	    assertThat(retrievedBlog.getTitle()).isEqualTo(newBlog.getTitle());
	    assertThat(retrievedBlog.getBody()).isEqualTo(newBlog.getBody());
	    assertThat(retrievedBlog.getLink()).isEqualTo(newBlog.getLink());
	}
}