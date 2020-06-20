package org.akinyemi.tobi.challenge.api;

import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.service.CharacterService;
import org.akinyemi.tobi.challenge.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("characters")
public class CharacterController {
    private final CharacterService characterService;
    private final TranslationService translationService;

    @Autowired
    public CharacterController(CharacterService characterService, TranslationService translationService) {
        this.characterService = characterService;
        this.translationService = translationService;
    }

    @GetMapping
    public Collection<Integer> getCharacters() {
        return characterService.getAllCharacterIds();
    }

    @GetMapping(path = "{id}")
    public Character getCharacter(
            @PathVariable("id") int id,
            @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        Optional<Character> characterOpt = characterService.getCharacter(id);
        if (characterOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");

        Character character = characterOpt.get();

        // If the language code is not specified, the original (english) character is returned
        if (languageCode == null) return character;
        // otherwise, a translated version is returned
        return translationService.translateCharacter(character,languageCode);
    }

}


