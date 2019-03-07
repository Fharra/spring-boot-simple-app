package simpleApp;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import persistence.model.Book;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		Application.class }, webEnvironment = WebEnvironment.DEFINED_PORT)
public class LiveTest {
	private static final String API_ROOT = "http://localhost:8081/api/books";

	private Book createRandomBook() {
		Book book = new Book();
		book.setTitle(randomAlphabetic(10));
		book.setAuthor(randomAlphabetic(15));
		return book;
	}

	private String createBookAsUri(Book book) {
		Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
				.post(API_ROOT);
		return API_ROOT + "/" + response.jsonPath().get("id");
	}

	@Test
	public void whenUpdateCreatedBook_thenUpdated() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		book.setId(Long.parseLong(location.split("api/books/")[1]));
		book.setAuthor("newAuthor");
		Response response = RestAssured.given()
				.contentType(MediaType.APPLICATION_JSON_VALUE).body(book)
				.put(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		assertEquals("newAuthor", response.jsonPath().get("author"));
	}

	@Test
	public void whenDeleteCreatedBook_thenOk() {
		Book book = createRandomBook();
		String location = createBookAsUri(book);
		Response response = RestAssured.delete(location);

		assertEquals(HttpStatus.OK.value(), response.getStatusCode());

		response = RestAssured.get(location);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
	}
}
