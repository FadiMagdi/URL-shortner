package com.FadiMagdi.URL_Shortner;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import com.FadiMagdi.URL_Shortner.Repositories.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlService {

    final UrlRepository urlRepository;


    public URL createURL( String mainURL){


        URL generatedURL = new URL(this.generateShortCode(),mainURL,new Date());

        log.debug("URL {} was shortened with short Code {}",generatedURL.getMainURL(),generatedURL.getShortCode());

        return urlRepository.save(generatedURL);
    }

    public String generateShortCode(){
        String shortCode= "";

        String[] chars = new String[]{"a","b","c","d","e","f","g",
        "h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","W","x","y","z"};

        Random selectChar = new Random();
        shortCode += chars[selectChar.nextInt(chars.length)];
        shortCode += chars[selectChar.nextInt(chars.length)];
        shortCode += chars[selectChar.nextInt(chars.length)];

        shortCode+= String.valueOf(selectChar.nextInt(0,9));

        shortCode+= String.valueOf(selectChar.nextInt(0,9));
        shortCode+= String.valueOf(selectChar.nextInt(0,9));

        return shortCode;

    }

    String getURLByShortCode(String shortCode){
        Optional<URL> found = this.urlRepository.findByShortCode(shortCode);


        URL actualURL = found.orElseThrow(()-> new EntityNotFoundException("shortCode not found")); // incrementing number of visits
log.error("Short code {} does not exist in DB",shortCode);
        actualURL.setAccessCount(actualURL.getAccessCount()+1);

this.urlRepository.save(actualURL);
        return actualURL.getMainURL();
    }

    URL updateURL(String shortCode,String newURL){
       Optional<URL> found= this.urlRepository.findByShortCode(shortCode);
       URL actualURL = found.orElseThrow(()-> new EntityNotFoundException("shortCode not found"));



        actualURL.setMainURL(newURL);
        actualURL.setUpdatedAt(new Date());
        log.info("URL with code {} has been updated to {}",shortCode,newURL);
        return this.urlRepository.save(actualURL);

    }

    void deleteURL(String shortCode){
        Optional<URL> search = this.urlRepository.findByShortCode(shortCode);

        URL todelete = search.orElseThrow(()-> new EntityNotFoundException("shortCode not found"));
        this.urlRepository.delete(todelete);

        log.info("URL with {} has been deleted",shortCode);
    }

    Integer getNumberofvisits (String shortCode){

        Optional<URL> url = this.urlRepository.findByShortCode(shortCode);

        URL stats = url.orElseThrow(()-> new EntityNotFoundException("short code is not found"));
 log.info("URL with {} has {} visits",shortCode,stats.getAccessCount());
        return stats.getAccessCount();
    }

}
