package org.akinyemi.tobi.challenge.service;

import at.favre.lib.bytes.Bytes;
import org.akinyemi.tobi.challenge.dao.CharacterDao;
import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.model.MarvelCharactersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CharacterService {
    public static final Logger SERVICE_LOGGER = Logger.getLogger("character-service");
    private final CharacterDao dao;
    private static final String MARVEL_API_CHARACTERS_ENDPOINT = "https://gateway.marvel.com/v1/public/characters";
    private static final String marvelApiPublicKey = System.getenv("MARVEL_API_PUBLIC_KEY");
    private static final String marvelApiPrivateKey = System.getenv("MARVEL_API_PRIVATE_KEY");
    private static final WebClient webClient = WebClient.builder()
            .baseUrl(MARVEL_API_CHARACTERS_ENDPOINT)
            .defaultUriVariables(Map.of("apikey", marvelApiPublicKey))
            .build();


    @Autowired
    public CharacterService(@Qualifier("memoryDao") CharacterDao dao) {
        this.dao = dao;

        int offset = 0;
        int total;
//        do {
//            MarvelCharactersResponse response = fetchCharacters(offset);
//            if (response.code() != 200) break;
//            MarvalCharactersData data = response.data();
//            dao.insertCharacters(data.results());
//            offset += data.count();
//            total = data.total();
//        } while (offset < total);
        System.out.println("dao.selectCharacters().size() = " + dao.selectCharacters().size());
        // fetched in series to avoid rate limiting
        // also prevents desynchronisation of total / limit values
        //
    }

    private MarvelCharactersResponse fetchCharacters(int offset) {
        SERVICE_LOGGER.info("Fetching page from Marvel characters API with offset: " + offset);
        long ts = System.currentTimeMillis();
        String requestHash = getRequestHash(ts);
        MarvelCharactersResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("offset", offset)
                        .queryParam("ts", ts)
                        .queryParam("hash", requestHash)
                        .build()
                )
                .retrieve()
                .bodyToMono(MarvelCharactersResponse.class)
                //                .subscribe(x -> System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+x));
                .block();
        return response;
    }

    private String getRequestHash(long ts) {
        String requestId = ts + marvelApiPrivateKey + marvelApiPublicKey;
        byte[] requestIdBytes = requestId.getBytes(StandardCharsets.UTF_8);

        return Bytes.wrap(requestIdBytes)
                .hashMd5()
                .encodeHex();
    }

    public Collection<Integer> getCharacterIds() {
        return dao.selectCharacterIds();
    }

    public Optional<Character> getCharacter(int id) {
        return dao.selectCharacter(id);
    }

    public Character translateCharacter(Character character) {
        String translatedDescription = character.description();

        return new Character(
                character.id(),
                character.name(),
                translatedDescription,
                character.thumbnail()
        );
    }
}
