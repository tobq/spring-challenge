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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TranslationService {
    public static final Logger SERVICE_LOGGER = Logger.getLogger("translation-service");
    Translate translate = TranslateOptions.newBuilder().setApiKey("myKey").build().getService();

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
