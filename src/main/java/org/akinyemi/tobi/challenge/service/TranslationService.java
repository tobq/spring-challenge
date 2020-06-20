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
     * Translates a Characters information, using a given languageCode.
     *
     * @param character          to be translated
     * @param targetLanguageCode to translate the characters information to
     * @return translated character
     * @see <a href="https://cloud.google.com/translate/docs/languages">Supported language codes</a>
     */
    public Character translateCharacter(Character character, String targetLanguageCode) {
//         After a quick search, the Super Hero name translations seem to be unorganised.
//         Most countries use the English super hero names, and some of those who don't have multiple translations
//         - Google's translation API does not have special entries for Marvel Super Heroes
//         I've opted to turn off translation of Hero names, however, some uncommenting will give some translation

        String translatedDescription;
//        String translatedName;
        try {
//            translatedName = translateEnglish(character.name(), targetLanguageCode);
            translatedDescription = translateEnglish(character.description(), targetLanguageCode);
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
//                translatedName,
                translatedDescription,
                character.thumbnail()
        );
    }

    private String translateEnglish(String sentence, String targetLanguageCode) {
        String translatedDescription;
        translatedDescription = translate.translate(
                sentence,
                ENGLISH_SOURCE_LANGUAGE,
                Translate.TranslateOption.targetLanguage(targetLanguageCode)
        ).getTranslatedText();
        return translatedDescription;
    }
}
