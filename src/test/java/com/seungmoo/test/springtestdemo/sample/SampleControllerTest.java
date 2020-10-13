package com.seungmoo.test.springtestdemo.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
/*
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
// WebEnvironment.MOCK환경에서 MOCKMVC를 사용해서 servlet을 사용하는 것처럼 TEST한다.(내장 톰캣 사용안함) mock servlet 환경
 */
// WebEnvironment.RANDOM_PORT, WebEnvironment.DEFINED_PORT --> 실제 servlet를 사용한다(내장 톰캣 사용)

// 메인 SpringBootApplication을 찾아가서 해당 componentScan에 해당되는 모든 Bean들을 다 뒤짐
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class SampleControllerTest {
    /*
    @Autowired
    MockMvc mockMvc;
    */

    /*
    @Autowired
    TestRestTemplate testRestTemplate;
     */

    @Autowired
    WebTestClient webTestClient;

    // 컨트롤러까지만 호출하고 Service단까지는 건드리고 싶지 않을 때 (MockBean 사용)
    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("seungmoo");



        // 이게 쓰기 편하다고는 하다.
        // WebTestClient는 Async(비동기) 방식이며 콜백형식으로 호출 할 것(체이닝으로 API가 구성되어있음)
        webTestClient.get().uri("/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello seungmoo");

        /*
        // TestRestTemplate을 사용한 테스트 코드
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello seungmoo");
         */

        /*
        // MOCKMVC를 사용한 테스트 코드
        mockMvc.perform(get("/hello")) // get 요청보내기
                .andExpect(status().isOk())     // status isOk -> 200 코드
                .andExpect(content().string("hello seungmoo")) // 요청 결과값 확인
                .andDo(print());            // 요청데이터 프린트

         */
    }
}