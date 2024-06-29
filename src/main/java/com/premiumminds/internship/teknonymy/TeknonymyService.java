package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

class TeknonymyService implements ITeknonymyService {

  // Global variables to be acessed in recursive DFS
  private Person oldestDescendant;
  private int oldestGeneration = 0;

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
    oldestDescendant = person;

    recursiveDFS(person, 0);

    return buildRelationshipString(oldestGeneration, person.sex()) + " of " + oldestDescendant.name();
  };

  private void recursiveDFS(Person person, int generation){
    if (person.children() != null){
      for(Person child : person.children()){
        recursiveDFS(child, generation + 1);
      }
    } else if (generation > oldestGeneration){
      oldestGeneration = generation;
      oldestDescendant = person;
    } else if (generation == oldestGeneration && person.dateOfBirth().isBefore(oldestDescendant.dateOfBirth())) {
      oldestDescendant = person;
    }
  }

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