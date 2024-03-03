package com.irembo.test.controller;

import com.irembo.test.dto.ShortUrl;
import com.irembo.test.services.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlController {

    @Autowired
    UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrl> shortenUrl(
            @RequestBody String originalUrl,
            @RequestParam(required = false) String customUrl
    ) {
        ShortUrl shortUrl = urlService.shortenUrl(originalUrl, customUrl);
        return ResponseEntity.ok(shortUrl);
    }
    @GetMapping("/{shortUrl}")
    public void redirect(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String originalUrl = urlService.getOriginalUrl(shortUrl);
        System.out.println("Redirecting to: " + originalUrl);
        response.sendRedirect(originalUrl);
    }
}
