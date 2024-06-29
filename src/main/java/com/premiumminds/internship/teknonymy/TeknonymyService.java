package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

class TeknonymyService implements ITeknonymyService {
  class PersonWithGeneration {
    Person person;
    int generation;

    PersonWithGeneration(Person person, int generation) {
      this.person = person;
      this.generation = generation;
    }
  }

  /**
   * Method to get a Person Teknonymy Name
   *
   * @param Person person
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymy(Person person) {
    if (person.children() == null) {
      return "";
    }

    Person oldestDescendant = person;
    int oldestGeneration = 0;

    ArrayDeque<PersonWithGeneration> descendantsStack = new ArrayDeque<>();
    Arrays.stream(person.children()).forEach(
        child -> descendantsStack.add(new PersonWithGeneration(child, 1)));

    while (!descendantsStack.isEmpty()) {
      PersonWithGeneration current = descendantsStack.pop();
      if (current.person.children() != null) {
        Arrays.stream(current.person.children()).forEach(
            child -> descendantsStack.add(new PersonWithGeneration(child, current.generation + 1)));
      } else if (current.generation > oldestGeneration
          || current.person.dateOfBirth().isBefore(oldestDescendant.dateOfBirth())) {
        oldestDescendant = current.person;
        oldestGeneration = current.generation;
      }
    }

    return buildRelationshipString(oldestGeneration, person.sex()) + " of " + oldestDescendant.name();
  };

  /**
   * Generates a string describing a familial relationship based on generation and
   * sex.
   *
   * @param generation the number of generations away (1 for parent, 2 for
   *                   grandparent, etc.)
   * @param sex        'M' for male, 'F' for female
   * @return the relationship as a string (e.g., "great-grandfather")
   */
  private String buildRelationshipString(int generation, char sex) {
    StringBuilder relationship = new StringBuilder();

    int greatCount = Math.max(0, generation - 2);
    for (int i = 0; i < greatCount; i++) {
      relationship.append("great-");
    }
    if (generation >= 2) {
      relationship.append("grand");
    }
    relationship.append(sex == 'M' ? "father" : "mother");

    return relationship.toString();
  };
}