package com.seungmoo.test.springtestdemo.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
// WebMvcTest는 슬라이싱테스트 용도로 쓰임 ex)@JsonTest, @WebMvcTest, @WebFluxTest, @DataJpaTest
// Web과 관련된 컴포넌트만 class 등록 ex) @Controller, @ControllerAdvice 등등 (@Service, @Repository는 안됨)
// Web과 관련된 컴포넌트만 class 등록가능하며 그외의 의존성들은 모두 끊김
// 끊기는 의존성에대해 @MockBean으로 채워줘야함
@WebMvcTest(SampleController.class)
public class SliceSampleControllerTest {
    
    // OutputCapture란? : 로그를 비롯해서 콘솔에 찍히는 모든 것을 캡처
    // OutputCapture는 deprecated됬음 OutputCaptureRule을 쓰자
    @Rule
    public OutputCaptureRule outputCaptureRule = new OutputCaptureRule();

    @MockBean
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("seungmoo");
        mockMvc.perform(get("/hello"))
                .andExpect(content().string("hello seungmoo"));
        assertThat(outputCaptureRule.toString())
                .contains("holoman")
                .contains("skip");
    }
}
