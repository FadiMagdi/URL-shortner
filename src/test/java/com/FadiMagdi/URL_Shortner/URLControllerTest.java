package com.FadiMagdi.URL_Shortner;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import com.FadiMagdi.URL_Shortner.Repositories.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(URLController.class)
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class URLControllerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlRepository urlRepository;

    @Test
    public void shouldCreateUrl() throws Exception {

        //Given
        String LongUrl = "https://www.msn.com/ar-eg/news/world/%D9%84%D9%8A%D8%A8%D9%8A%D8%A7-%D9%87%D9%84-%D8%AA%D8%A4%D8%AF%D9%8A-%D8%A7%D9%84%D8%AA%D8%B9%D8%A8%D8%A6%D8%A9-%D8%A7%D9%84%D8%B9%D8%B3%D9%83%D8%B1%D9%8A%D8%A9-%D8%A5%D9%84%D9%89-%D9%85%D9%88%D8%A7%D8%AC%D9%87%D8%A9-%D9%85%D8%B3%D9%84%D8%AD%D8%A9/vi-AA1LOUeU?ocid=msedgntp&pc=U531&cvid=68b98baab93f4b25b36d302892b38141&ei=43";
        URL testURL = new URL(this.urlService.generateShortCode(),LongUrl,new Date());

        when(this.urlService.createURL(LongUrl)).thenReturn(testURL);

        this.mockMvc.perform(post("/shortner"));// to be completed




    }


    @Test
    public void shouldGetShortCode

}