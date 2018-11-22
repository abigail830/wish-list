package Integration.features;

import Integration.WishListIntegrationTest;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


public class RetrieveWishListTest extends WishListIntegrationTest {


    @Given("^client had not add any wishlist before$")
    public void client_had_not_add_any_wishlist_before() throws Throwable {
        wishService.deleteWishListByListID("1");
    }

    @When("^client call /v(\\d+)/wishes/lists\\?id=(\\d+)$")
    public void the_client_call_v_wishes_lists_id(int arg1, int arg2) throws Throwable {
        assertThat(arg1, is(1));
        assertThat(arg2, is(1));

        String url = "/v" + arg1 + "/wishes/lists?id=" + arg2;
        getWishDashboardDTO(url);
    }

    @Then("^client receive null wishlist$")
    public void the_client_receive_null_wishlist() throws Throwable {
        assertTrue(wishDashboardDTOResponseEntity.getBody().getWishLists().isEmpty());
    }

}
