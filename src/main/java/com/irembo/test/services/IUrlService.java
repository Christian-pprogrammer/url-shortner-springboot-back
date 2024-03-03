package com.irembo.test.services;

import com.irembo.test.dto.ShortUrl;

public interface IUrlService {
    ShortUrl shortenUrl(String originalUrl, String customUrl);
    String getOriginalUrl(String shortUrl);
    String generateRandomString();
    ShortUrl saveShortUrl(String originalUrl, String shortUrl);
}
