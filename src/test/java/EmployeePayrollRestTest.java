import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class EmployeePayrollRestTest {

    @Test
    public void testGetEmployee(){
        Response response = RestAssured.get("http://localhost:3000/employees");
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToRetrieveAllEmployees() {
        Response response = RestAssured.get("http://localhost:3000/employees/list");
        System.out.println(response.asString());
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToAddEmployeeInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Saksham\",\"salary\": \"46000\"}")
                .when().post("http://localhost:3000/employees/create");
        response.then()
                .body("name", Matchers.is("Saksham"));
        Assert.assertEquals(201, response.getStatusCode());
    }

    @Test
    public void testToUpdateEmployeeInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"name\": \"Saksham\",\"salary\": \"50000\"}")
                .when().put("http://localhost:3000/employees/update/6");
        response.then()
                .body("salary", Matchers.is("50000"));
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testToDeleteEmployeeInJSONServer(){
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().delete("http://localhost:3000/employees/delete/6");
        Assert.assertEquals(200, response.getStatusCode());
    }
}

