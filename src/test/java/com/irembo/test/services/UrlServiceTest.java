package com.irembo.test.services;

import com.irembo.test.dto.ShortUrl;
import com.irembo.test.exceptions.InvalidUrlException;
import com.irembo.test.exceptions.UrlNotFoundException;
import com.irembo.test.models.Url;
import com.irembo.test.persistence.UrlRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @MockBean
    private UrlRepository urlRepository;

    @Test
    public void testShortenUrlWithOutCustomUrl() {

        Url url =
                new Url();
        url.setExpiryDate(LocalDateTime.now().plusDays(7));
        url.setOriginalUrl("https://www.google.com");
        url.setShortUrl("valid_short_url");

        when(urlRepository.save(any(Url.class))).thenReturn(url);
        String originalUrl = "https://www.google.com";
        String customUrl = "";
        ShortUrl shortUrl = urlService.shortenUrl(originalUrl, customUrl);

        assertNotNull(shortUrl.getUrl());
        assertTrue(shortUrl.getUrl().length() > 0);
    }

    @Test
    public void testShortenUrlWithCustomUrl() {
        String originalUrl = "https://www.google.com";
        String customUrl = "google_short";
        ShortUrl shortUrl = urlService.shortenUrl(originalUrl, customUrl);
        assertNotNull(shortUrl.getUrl());
        assertEquals(shortUrl.getUrl(), customUrl);
    }

    @Test
    public void testShortenUrlWithInvalidOriginalUrl() {

        Url url = new Url();
        url.setOriginalUrl("invalid_url");
        url.setShortUrl("invalid_short_url");
        url.setId(1L);
        url.setClickCount(0L);

        when(urlRepository.save(any(Url.class))).thenReturn(url);

        String invalidOriginalUrl = "invalid_url";
        assertThrows(InvalidUrlException.class, () -> urlService.shortenUrl(invalidOriginalUrl, ""));

        // verify It didn't interact with my mock repository, db in real life
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    public void testGetOriginalUrl() {
        // Mock the repository to return a known URL mapping
        when(urlRepository.findByShortUrl("http://my_valid_short_url.com")).thenReturn(
                Optional.of(new Url(1L, "http://my_valid_short_url.com", "https://www.google.com", (LocalDateTime.now().plusDays(7)), 0L)));

        String originalUrl = urlService.getOriginalUrl("http://my_valid_short_url.com");

        assertNotNull(originalUrl);
        assertEquals("https://www.google.com", originalUrl);

        // Verify interactions with the mock repository
        verify(urlRepository, times(1)).findByShortUrl("http://my_valid_short_url.com");
    }

    @Test
    public void testGetOriginalUrlWithInvalidShortUrl() {
        // Mock the repository to return an empty result for the short URL
        when(urlRepository.findByShortUrl("http://www.invalid_short_url")).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> urlService.getOriginalUrl("http://www.invalid_short_url"));

        // Verify interactions with the mock repository
        verify(urlRepository, times(1)).findByShortUrl("http://www.invalid_short_url");
    }

}
