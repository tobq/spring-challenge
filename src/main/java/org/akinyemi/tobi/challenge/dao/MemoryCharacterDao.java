package org.akinyemi.tobi.challenge.dao;

import org.akinyemi.tobi.challenge.model.Character;
import org.akinyemi.tobi.challenge.model.Image;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository("memoryDao")
public class MemoryCharacterDao implements CharacterDao {
    private final List<Character> characters = new ArrayList<>(List.of(
            new Character(99394342, "Tobi", "The goat", new Image("https://en.gravatar.com/userimage/59344989/1e5f640a791a47b3bd4520a51de89dbb.png", ".jpeg")),
            new Character(99394343, "Tobias", "The goat", new Image("https://en.gravatar.com/userimage/59344989/1e5f640a791a47b3bd4520a51de89dbb.png", ".jpeg"))
    ));

    @Override
    public Optional<Character> selectCharacter(int id) {
        return Optional.ofNullable(characters.get(id));
    }

    @Override
    public Collection<Character> selectCharacters() {
        return characters.values();
    }

    @Override
    public Collection<Integer> selectCharacterIds() {
        return characters.values()
                .stream()
                .map(Character::id)
                .collect(Collectors.toList());
    }

    @Override
    public void insertCharacter(Character character) {
        characters.put(character.id(), character);
    }

    @Override
    public void insertCharacters(List<Character> characters) {
        characters.forEach(this::insertCharacter);
    }

    @Override
    public long countCharacters() {
        return characters.size();
    }
}
