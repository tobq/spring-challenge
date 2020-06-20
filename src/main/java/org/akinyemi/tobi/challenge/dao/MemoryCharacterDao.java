package org.akinyemi.tobi.challenge.dao;

import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.model.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository("memoryDao")
public class MemoryCharacterDao implements CharacterDao {
    private final List<Character> characters = new ArrayList<>(List.of(
            new Character(99394342, "Tobi", "The goat", new Image("https://en.gravatar.com/userimage/59344989/1e5f640a791a47b3bd4520a51de89dbb.png", ".jpeg")),
            new Character(99394343, "Tobias", "The goat", new Image("https://en.gravatar.com/userimage/59344989/1e5f640a791a47b3bd4520a51de89dbb.png", ".jpeg"))
    ));

    @Override
    public Optional<Character> selectCharacter(int id) {
        return characters.stream()
                .filter(character -> character.id() == id)
                .findAny();
    }

    @Override
    public List<Character> selectCharacters() {
        return characters;
    }

    @Override
    public List<Integer> selectCharacterIds() {
        return characters.stream()
                .map(Character::id)
                .collect(Collectors.toList());
    }

    @Override
    public void insertCharacter(Character character) {
        characters.add(character);
    }

    @Override
    public void insertCharacters(List<Character> characters) {
        characters.forEach(this::insertCharacter);
    }
}
