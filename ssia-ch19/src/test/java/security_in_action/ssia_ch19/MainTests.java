package security_in_action.ssia_ch19;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient // 테스트용 WebTestClient를 자동 구성하도록 스프링 부트에 요청
class MainTests {

    @Autowired
    private WebTestClient client;

    @Test
    void testCallHelloWithValidUser() {
        client.get()
                .uri("/hello")
                .headers(headers -> headers.setBasicAuth("john", "12345"))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCallHelloWithValidUserWithMutate() {
        client.mutateWith(mockUser().roles("ADMIN"))   // 리액티브 환경 인증 처리, ADMIN 역할 부여
                .get()
                .uri("/hello")
                .exchange()
                .expectStatus().isOk();
    }
}
