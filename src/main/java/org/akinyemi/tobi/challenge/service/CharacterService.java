package org.akinyemi.tobi.challenge.service;

import at.favre.lib.bytes.Bytes;
import org.akinyemi.tobi.challenge.dao.CharacterDao;
import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.model.MarvalCharactersData;
import org.akinyemi.tobi.challenge.model.MarvelCharactersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Service
public class CharacterService {
    public static final Logger SERVICE_LOGGER = Logger.getLogger("character-service");
    private final CharacterDao dao;
    private static final String MARVEL_API_CHARACTERS_ENDPOINT = "https://gateway.marvel.com/v1/public/characters";
    private static final String marvelApiPublicKey = System.getenv("MARVEL_API_PUBLIC_KEY");
    private static final String marvelApiPrivateKey = System.getenv("MARVEL_API_PRIVATE_KEY");
    private static final WebClient webClient = WebClient.builder().baseUrl(MARVEL_API_CHARACTERS_ENDPOINT).build();


    @Autowired
    public CharacterService(@Qualifier("memoryDao") CharacterDao dao) {
        this.dao = dao;
        populateCharacterDatabase();
    }

    /**
     * Asynchronously populates the character database
     *
     * @see #fetchCharacters(int, Consumer)
     */
    private void populateCharacterDatabase() {
        SERVICE_LOGGER.info("Fetching character data from Marvel characters API...");
//        SERVICE_LOGGER.fine("Fetching page of characters with offset: " + offset + "/" + total);

        populateCharacterDatabase(0);
        SERVICE_LOGGER.info("Finished fetching " + dao.countCharacters() + " characters from Marvel characters API");

    }


    /**
     * Asynchronously populates the character database
     * <p>
     * Results are fetched in series to avoid rate limiting
     * - this also prevents desynchronisation of total / limit values
     *
     * @param offset The requested offset (number of skipped results) of the call.
     * @see #fetchCharacters(int, Consumer)
     */
    private void populateCharacterDatabase(int offset) {
        //
        fetchCharacters(offset, response -> {
            if (response.code() != 200) return;

            MarvalCharactersData data = response.data();
            dao.insertCharacters(data.results());
            int nextOffset = offset + data.count();
            if (nextOffset < data.total()) populateCharacterDatabase(nextOffset);
        });
    }

    /**
     * Fetches a page from the marvel characters REST API
     *
     * @param offset   The requested offset (number of skipped results) of the call.
     * @param callback handles the asynchronously fetched {@link MarvelCharactersResponse}
     * @see MarvelCharactersResponse
     */
    private void fetchCharacters(int offset, Consumer<? super MarvelCharactersResponse> callback) {
        long ts = System.currentTimeMillis();
        String requestHash = getRequestHash(ts);
        webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("offset", offset)
                        .queryParam("ts", ts)
                        .queryParam("apikey", marvelApiPublicKey)
                        .queryParam("hash", requestHash)
                        .build()
                )
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MarvelCharactersResponse.class)
                .subscribe(callback);
    }


    /**
     * Hash computed according to the marvel REST API specification
     *
     * @param timestamp timestamp of current request
     * @return a request hash, attached as a query parameter for requests to the Marvel API
     * @see <a href="https://developer.marvel.com/docs#!/public/getCreatorCollection_get_0">Marvel Docs</a>
     */
    private String getRequestHash(long timestamp) {
        String requestId = timestamp + marvelApiPrivateKey + marvelApiPublicKey;
        byte[] requestIdBytes = requestId.getBytes(StandardCharsets.UTF_8);

        return Bytes.wrap(requestIdBytes)
                .hashMd5()
                .encodeHex();
    }

    public Collection<Integer> getAllCharacterIds() {
        return dao.selectAllCharacterIds();
    }

    public Optional<Character> getCharacter(int id) {
        return dao.selectCharacter(id);
    }
}
