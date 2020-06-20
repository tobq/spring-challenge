package org.akinyemi.tobi.challenge.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateException;
import com.google.cloud.translate.TranslateOptions;
import org.akinyemi.tobi.challenge.model.Character;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TranslationService {
    Translate translate = TranslateOptions.getDefaultInstance().getService();
    public static final Translate.TranslateOption ENGLISH_SOURCE_LANGUAGE = Translate.TranslateOption.sourceLanguage("en");

    public Character translateCharacter(Character character, String languageCode) {
        Translate.TranslateOption translateOption = Translate.TranslateOption.targetLanguage(languageCode);

        String translatedDescription;
        try {
            translatedDescription = translate.translate(
                    character.description(),
                    ENGLISH_SOURCE_LANGUAGE,
                    translateOption
            ).getTranslatedText();
        } catch (TranslateException e) {
            if (e.getReason().equals("invalid"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid language Code");
            throw e;
        }

        return new Character(
                character.id(),
                character.name(),
                translatedDescription,
                character.thumbnail()
        );
    }
}
