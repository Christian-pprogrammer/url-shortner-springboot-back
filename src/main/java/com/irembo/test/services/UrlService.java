package com.irembo.test.services;

import com.irembo.test.dto.ShortUrl;
import com.irembo.test.exceptions.InvalidUrlException;
import com.irembo.test.exceptions.UrlNotFoundException;
import com.irembo.test.models.Url;
import com.irembo.test.persistence.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService implements IUrlService {

    @Autowired
    UrlRepository urlRepository;

    @Value("${url.shortener.key}")
    String urlShortenerKey;

    @Override
    @Transactional
    public ShortUrl shortenUrl(String originalUrl, String customUrl) {

        try {
            new URL(originalUrl);
        }catch (MalformedURLException e) {
            throw new InvalidUrlException("Invalid long url entered");
        }

        if(customUrl != null && !customUrl.isEmpty()) {
            //check if the custom url doesn't exist
            Optional<Url> isCustomUrlExist = urlRepository.findByShortUrl(customUrl);
            if(isCustomUrlExist.isPresent()) {
                throw new InvalidUrlException("Custom url already registered");
            }else{
                //save custom user url
                String shortUrl = customUrl;
                return this.saveShortUrl(originalUrl, shortUrl);
            }

        }else {
            //if no custom url entered, I just generate random unique short url
            String shortUrl =  urlShortenerKey + generateRandomString();
            return this.saveShortUrl(originalUrl, shortUrl);
        }
    }

    public ShortUrl saveShortUrl(String originalUrl, String shortUrl) {
        Url url = new Url();
        url.setShortUrl(shortUrl);
        url.setOriginalUrl(originalUrl);
        url.setExpiryDate(LocalDateTime.now().plusDays(7));
        url.setClickCount(0L);
        urlRepository.save(url);
        return new ShortUrl(shortUrl);
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        Optional<Url> urlOptional = urlRepository.findByShortUrl(shortUrl);
        if(urlOptional.isEmpty()) {
            throw new UrlNotFoundException("Invalid short url");
        }

        Url url = urlOptional.get();
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    @Override
    public String generateRandomString() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        int length = 8;
        return uuid.substring(0, length);
    }

}
