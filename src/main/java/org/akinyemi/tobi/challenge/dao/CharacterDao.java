package org.akinyemi.tobi.challenge.dao;

import org.akinyemi.tobi.challenge.model.Character;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CharacterDao {
    //    void insertCharacter( Character character);

    Optional<Character> selectCharacter(int id);

    Collection<Character> selectCharacters();

    Collection<Integer> selectCharacterIds();

    void insertCharacter(Character character);

    void insertCharacters(List<Character> character);
}
