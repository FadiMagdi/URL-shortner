package com.FadiMagdi.URL_Shortner;

import com.FadiMagdi.URL_Shortner.Domain.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shorten")

public class URLController {
    final UrlService urlService;


    @PostMapping
    public ResponseEntity<?> getShortLink(@RequestBody String mainURL){


       URL response =  this.urlService.createURL(mainURL);

return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortCode}")
public ResponseEntity<?> getOriginalUrl(@PathVariable("shortCode") String shortCode){

        String OriginalUrl = this.urlService.getURLByShortCode(shortCode);
        return ResponseEntity.ok(OriginalUrl);
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<?> updateURL(@PathVariable("shortCode") String shortCode , @RequestBody String newURL){

        URL updated = this.urlService.updateURL(shortCode, newURL);
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
