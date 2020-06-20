package org.akinyemi.tobi.challenge.api;

import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequestMapping("characters")
@RestController
public class CharacterController {
    private final CharacterService service;

    @Autowired
    public CharacterController(CharacterService service) {
        this.service = service;
    }

    @GetMapping
    public List<Integer> getCharacters() {
        return service.getCharacterIds();
    }

    @GetMapping(path = "{id}")
    public Character getCharacter(
            @PathVariable("id") int id,
            @RequestParam(value = "languageCode", required = false) String languageCode
    ) {
        Optional<Character> characterOpt = service.getCharacter(id);
        if (characterOpt.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Character not found");

        Character character = characterOpt.get();
        if (languageCode == null) return character;
        return service.translateCharacter(character);
    }

}

