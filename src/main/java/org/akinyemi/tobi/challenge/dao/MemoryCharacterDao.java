package org.akinyemi.tobi.challenge.dao;

import org.akinyemi.tobi.challenge.model.Character;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * DAO implementation used for DEBUGGING
 */
@Repository("memoryDao")
public class MemoryCharacterDao implements CharacterDao {
    private final Map<Integer, Character> characters = new ConcurrentHashMap<>();

    @Override
    public Optional<Character> selectCharacter(int id) {
        return Optional.ofNullable(characters.get(id));
    }

    @Override
    public Collection<Character> selectAllCharacters() {
        return characters.values();
    }

    @Override
    public Collection<Integer> selectAllCharacterIds() {
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
