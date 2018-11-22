package Integration;

import com.github.abigail830.wishlist.WishListApplication;
import com.github.abigail830.wishlist.domainv1.WishDashboardDTO;
import com.github.abigail830.wishlist.service.UserService;
import com.github.abigail830.wishlist.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;


@SpringBootTest(classes = WishListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration
@DirtiesContext
public class WishListIntegrationTest {

    protected static final String PORT = "8080";
    private final String SERVER_URL = "http://localhost";
    @Autowired
    protected WishService wishService;

    @Autowired
    protected UserService userService;

    protected ResponseEntity<WishDashboardDTO> wishDashboardDTOResponseEntity;
    private RestTemplate restTemplate;

    public WishListIntegrationTest() {
        restTemplate = new RestTemplate();
    }

    private String serverEndpoint() {
        return SERVER_URL + ":" + PORT;
    }

    public int post(final String content) {
        return restTemplate.postForEntity(serverEndpoint(), content, Void.class).getStatusCodeValue();
    }

    public void getWishDashboardDTO(String queryParam) {
        wishDashboardDTOResponseEntity = restTemplate.getForEntity(serverEndpoint() + queryParam, WishDashboardDTO.class);
    }

    void clean() {
        restTemplate.delete(serverEndpoint());
    }
}
