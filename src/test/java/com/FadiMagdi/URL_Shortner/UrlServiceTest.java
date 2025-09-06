package com.FadiMagdi.URL_Shortner;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import com.FadiMagdi.URL_Shortner.Repositories.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UrlService unit test")
class UrlServiceTest {

    @InjectMocks // injects the object and its dependencies the same way used inside the class
    private UrlService urlService;

    //first step is to provide mock objects for all the dependencies in the class
    // we do not want to use the real implementation for the objects
@Mock
private UrlRepository urlRepository;







// tests can be grouped in classes where using @Nested annotation


@Test

void createURL(){
    //Given
    String LongUrl = "https://www.msn.com/ar-eg/news/world/%D9%84%D9%8A%D8%A8%D9%8A%D8%A7-%D9%87%D9%84-%D8%AA%D8%A4%D8%AF%D9%8A-%D8%A7%D9%84%D8%AA%D8%B9%D8%A8%D8%A6%D8%A9-%D8%A7%D9%84%D8%B9%D8%B3%D9%83%D8%B1%D9%8A%D8%A9-%D8%A5%D9%84%D9%89-%D9%85%D9%88%D8%A7%D8%AC%D9%87%D8%A9-%D9%85%D8%B3%D9%84%D8%AD%D8%A9/vi-AA1LOUeU?ocid=msedgntp&pc=U531&cvid=68b98baab93f4b25b36d302892b38141&ei=43";

    //when

    URL testURL = new URL(this.urlService.generateShortCode(),LongUrl,new Date());


when(this.urlRepository.save(any(URL.class))).thenReturn(testURL);

    URL newURL = this.urlService.createURL(LongUrl);


    System.out.println(newURL.getShortCode());
    //Then
    assertNotNull(newURL);
    assertEquals(LongUrl,newURL.getMainURL());


}

    @Test
    @DisplayName("URL retreival Test")
    void getURLByShortCode(){
//Given
        String LongUrl = "https://www.msn.com/ar-eg/news/world/%D9%84%D9%8A%D8%A8%D9%8A%D8%A7-%D9%87%D9%84-%D8%AA%D8%A4%D8%AF%D9%8A-%D8%A7%D9%84%D8%AA%D8%B9%D8%A8%D8%A6%D8%A9-%D8%A7%D9%84%D8%B9%D8%B3%D9%83%D8%B1%D9%8A%D8%A9-%D8%A5%D9%84%D9%89-%D9%85%D9%88%D8%A7%D8%AC%D9%87%D8%A9-%D9%85%D8%B3%D9%84%D8%AD%D8%A9/vi-AA1LOUeU?ocid=msedgntp&pc=U531&cvid=68b98baab93f4b25b36d302892b38141&ei=43";

        //when

        URL mockURL = new URL("zrd811",LongUrl,new Date());
        mockURL.setAccessCount(0);
        when(this.urlRepository.findByShortCode("zrd811")).thenReturn(Optional.of(mockURL));
mockURL.setAccessCount(mockURL.getAccessCount()+1);
when(this.urlRepository.save(any(URL.class))).thenReturn(mockURL);


//when

        String mainMockUrl = this.urlService.getURLByShortCode("zrd811");
       // verify(urlRepository,times(1)).save(argThat(newurl-> newurl.getAccessCount() == 1));
//then

        assertEquals(mockURL.getMainURL(),mainMockUrl);

    }

    @Test
    @DisplayName("check throwing IllegalArgumentException")
    void shouldThrowEntityNotFoundException(){
    //Given
        //using a fake shortCode

        String fakeShortCode = "kkk111";


        String unseenURL = "https://www.msn.com/ar-eg/news/featured/%D9%88%D8%B2%D9%8A%D8%B1-%D8%A7%D9%84%D8%AE%D8%A7%D8%B1%D8%AC%D9%8A%D8%A9-%D8%A7%D9%84%D8%A3%D9%85%D8%B1%D9%8A%D9%83%D9%8A-%D8%A7%D9%84%D8%A7%D8%B9%D8%AA%D8%B1%D8%A7%D9%81-%D8%A8%D9%81%D9%84%D8%B3%D8%B7%D9%8A%D9%86-%D8%B3%D9%8A%D8%AE%D9%84%D9%82-%D9%85%D8%B4%D8%A7%D9%83%D9%84-%D9%83%D8%A8%D9%8A%D8%B1%D8%A9-%D9%88%D9%8A%D8%A4%D8%AF%D9%8A-%D9%84%D8%A5%D8%AB%D8%A7%D8%B1%D8%A9-%D8%AE%D8%B7%D8%B7-%D8%A7%D9%84%D8%B6%D9%85/ar-AA1LTo0n?ocid=msedgntp&pc=U531&cvid=68babc13cbd1499e922275526c70e91b&ei=35";

        when(this.urlRepository.findByShortCode(fakeShortCode)).thenReturn(Optional.empty());


        URL fakeURL = new URL(this.urlService.generateShortCode(),unseenURL,new Date());

        final EntityNotFoundException IAE = assertThrows(
                EntityNotFoundException.class,
                ()-> this.urlService.getURLByShortCode(fakeShortCode)

        );

        assertEquals("shortCode not found",IAE.getMessage());

        verify(this.urlRepository,never()).save(fakeURL); //to check the access count was  not updated in any entity
    }

}