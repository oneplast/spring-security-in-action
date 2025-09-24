package security_in_action.ssia_ch11_business;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;
import security_in_action.ssia_ch11_business.security.authentication.proxy.AuthenticationServerProxy;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WebClient webClient;

    @MockitoBean
    private AuthenticationServerProxy proxy;

    @Test
    @DisplayName("Test /login with username and password")
    public void testLoginWithUsernameAndPassword() throws Exception {
        mvc.perform(get("/login")
                        .header("username", "bill")
                        .header("password", "12345"))
                .andExpect(status().isOk());

        verify(proxy).sendAuth("bill", "12345");
    }

    @Test
    @DisplayName("Test /login with username and otp")
    public void testLoginWithUsernameAndOtp() throws Exception {
        when(proxy.sendOtp("bill", "5555"))
                .thenReturn(true);

        mvc.perform(get("/login")
                        .header("username", "bill")
                        .header("code", "5555"))
                .andExpect(header().exists("Authorization"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test /test with Authorization header")
    public void testRequestWithAuthorizationHeader() throws Exception {
        when(proxy.sendOtp("bill", "5555"))
                .thenReturn(true);

        String authorizationHeaderValue = mvc.perform(get("/login")
                        .header("username", "bill")
                        .header("code", "5555"))
                .andReturn().getResponse().getHeader("Authorization");

        mvc.perform(get("/test")
                        .header("Authorization", authorizationHeaderValue))
                .andExpect(status().isOk());
    }
}
