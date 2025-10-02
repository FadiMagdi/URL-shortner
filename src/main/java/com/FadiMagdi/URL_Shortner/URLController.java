package com.FadiMagdi.URL_Shortner;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shorten")
@Slf4j
public class URLController {
    final UrlService urlService;


    @PostMapping
    public ResponseEntity<?> getShortLink(@RequestBody String mainURL){


       URL response =  this.urlService.createURL(mainURL);

       log.info("URL {} shortened to {} ",mainURL,response.getShortCode());

return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
public ResponseEntity<Void> RedirectOriginalUrl(@PathVariable("shortCode") String shortCode){

        String OriginalUrl = this.urlService.getURLByShortCode(shortCode);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).
                location(URI.create(OriginalUrl)).build();
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<?> updateURL(@PathVariable("shortCode") String shortCode , @RequestBody String newURL){

        URL updated = this.urlService.updateURL(shortCode, newURL);

        log.info("ShortCode {} redirects to url {}",shortCode,newURL);

        return ResponseEntity.ok(updated);

    }

    @DeleteMapping("/{shortCode}")
public ResponseEntity<?> deleteURL(@PathVariable("shortCode") String shortCode){

        this.urlService.deleteURL(shortCode);
        return ResponseEntity.ok("deleted");
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<Integer> shpwStats(@PathVariable("shortCode") String shortCode){

        Integer stats = this.urlService.getNumberofvisits(shortCode);
        return ResponseEntity.ok(stats);
    }
}
