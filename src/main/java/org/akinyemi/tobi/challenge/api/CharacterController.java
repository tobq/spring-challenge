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

@RequestMapping("characters")
@RestController
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
        return characterService.getCharacterIds();
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
        if (languageCode == null) return character;
        return translationService.translateCharacter(character,languageCode);
    }

}

