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
    /**
     * Fetches a translation service using your configured `GOOGLE_APPLICATION_CREDENTIALS` environment variable
     * Consult the README.md at the project root for more information
     */
    Translate translate = TranslateOptions.getDefaultInstance().getService();
    public static final Translate.TranslateOption ENGLISH_SOURCE_LANGUAGE = Translate.TranslateOption.sourceLanguage("en");

    /**
     * Translates a Character appropriately, using a given languageCode.
     * <p>
     * *Does not translate {@link Character#name}*
     *
     * @param character
     * @param languageCode
     * @return translated character
     * @see <a href="https://cloud.google.com/translate/docs/languages">Supported language codes</a>
     */
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
            // Catches the "invalid" error returned by the Google API, if the language code is invalid
            // and returns a standard 400, with an appropriate error
            if (e.getReason().equals("invalid"))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid language Code");

            // relays any other errors (unlikely)
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
