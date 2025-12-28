package com.myco.users.services;

import com.myco.users.entities.ShortLink;
import com.myco.users.repositories.ShortLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ShortLinkService {
    @Autowired
    private ShortLinkRepository shortLinkRepository;

    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int CODE_LENGTH = 7;
    private final SecureRandom random = new SecureRandom();

    public String generateShortCode() {
        StringBuilder sb = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }

    public Map<UUID, String> createBulkShortLinks(List<UUID> uuids) {
        Map<UUID, String> result = new HashMap<>();
        for (UUID uuid : uuids) {
            // In a real scenario, check if UUID already has a link to reuse it
            ShortLink link = new ShortLink();
            link.setTargetUuid(uuid);
            link.setShortCode(generateShortCode()); // Simple generation, assumes no collision for now
            shortLinkRepository.save(link);
            result.put(uuid, link.getShortCode());
        }
        return result;
    }

    public UUID resolveShortCode(String code) {
        return shortLinkRepository.findByShortCode(code)
                .map(ShortLink::getTargetUuid)
                .orElseThrow(() -> new RuntimeException("Link not found"));
    }
}