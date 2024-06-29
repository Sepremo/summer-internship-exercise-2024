package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonFiveChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{
            new Person("Holy", 'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("Grace", 'F', null, LocalDateTime.of(1047, 2, 2, 0, 0)),
            new Person("Brad", 'M', null, LocalDateTime.of(1048, 3, 3, 0, 0)),
            new Person("Hope", 'F', null, LocalDateTime.of(1049, 4, 4, 0, 0)),
            new Person("Charity", 'F', null, LocalDateTime.of(1050, 5, 5, 0, 0))
        },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneGreatGreatGreatGrandchildTest() {
    Person greatGreatGreatGrandchild = new Person("Joy", 'F', null, LocalDateTime.of(1120, 1, 1, 0, 0));
    Person greatGreatGrandchild = new Person("Joy", 'F', new Person[]{greatGreatGreatGrandchild}, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person greatGrandchild = new Person("Albert", 'M', new Person[]{greatGreatGrandchild}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person grandchild = new Person("Faith", 'F', new Person[]{greatGrandchild}, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person child = new Person("Grace", 'F', new Person[]{grandchild}, LocalDateTime.of(1040, 1, 1, 0, 0));
    Person person = new Person("John", 'M', new Person[]{child}, LocalDateTime.of(1020, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-great-grandfather of Joy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonFourGrandchildrenTest() {
    Person grandchild1 = new Person("Alice", 'F', null, LocalDateTime.of(1070, 1, 1, 0, 0));
    Person grandchild2 = new Person("Bob", 'M', null, LocalDateTime.of(1071, 2, 2, 0, 0));
    Person grandchild3 = new Person("Charlie", 'M', null, LocalDateTime.of(1060, 3, 3, 0, 0));
    Person grandchild4 = new Person("Diana", 'F', null, LocalDateTime.of(1061, 4, 4, 0, 0));
    Person child1 = new Person("Eve", 'F', new Person[]{grandchild1, grandchild2}, LocalDateTime.of(1040, 1, 1, 0, 0));
    Person child2 = new Person("Frank", 'M', new Person[]{grandchild3, grandchild4}, LocalDateTime.of(1041, 2, 2, 0, 0));
    Person person = new Person("John", 'M', new Person[]{child1, child2}, LocalDateTime.of(1020, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Charlie";
    assertEquals(result, expected);
  }

  @Test
  public void PersonThreeChildrenTwoGrandchildrenOneGreatGrandchildTest() {
    Person greatGrandchild = new Person("Lily", 'F', null, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild1 = new Person("Emma", 'F', new Person[]{greatGrandchild}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person grandchild2 = new Person("Noah", 'M', null, LocalDateTime.of(1082, 2, 2, 0, 0));
    Person child1 = new Person("Olivia", 'F', new Person[]{grandchild1}, LocalDateTime.of(1060, 1, 1, 0, 0));
    Person child2 = new Person("Liam", 'M', null, LocalDateTime.of(1062, 2, 2, 0, 0));
    Person child3 = new Person("Sophia", 'F', new Person[]{grandchild2}, LocalDateTime.of(1064, 3, 3, 0, 0));
    Person person = new Person("Mary", 'F', new Person[]{child1, child2, child3}, LocalDateTime.of(1040, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Lily";
    assertEquals(result, expected);
  }

  @Test
  public void PersonFourChildrenEightGrandchildrenSixteenGreatGrandchildrenTest() {
    // Creating great-grandchildren
    Person[] greatGrandchildren1 = new Person[4];
    Person[] greatGrandchildren2 = new Person[4];
    Person[] greatGrandchildren3 = new Person[4];
    Person[] greatGrandchildren4 = new Person[4];
    for (int i = 0; i < 4; i++) {
      greatGrandchildren1[i] = new Person("Target" + (i + 1), 'F', null, LocalDateTime.of(1120, 1, 1, 0, 0));
      greatGrandchildren2[i] = new Person("GreatGrandchild" + (i + 5), 'M', null, LocalDateTime.of(1120, 2, 2, 0, 0));
      greatGrandchildren3[i] = new Person("GreatGrandchild" + (i + 9), 'F', null, LocalDateTime.of(1120, 3, 3, 0, 0));
      greatGrandchildren4[i] = new Person("GreatGrandchild" + (i + 13), 'M', null, LocalDateTime.of(1120, 4, 4, 0, 0));
    }

    // Creating grandchildren
    Person grandchild1 = new Person("Grandchild1", 'F', greatGrandchildren1, LocalDateTime.of(1100, 1, 1, 0, 0));
    Person grandchild2 = new Person("Grandchild2", 'M', greatGrandchildren2, LocalDateTime.of(1100, 2, 2, 0, 0));
    Person grandchild3 = new Person("Grandchild3", 'F', greatGrandchildren3, LocalDateTime.of(1100, 3, 3, 0, 0));
    Person grandchild4 = new Person("Grandchild4", 'M', greatGrandchildren4, LocalDateTime.of(1100, 4, 4, 0, 0));
    Person grandchild5 = new Person("Grandchild5", 'F', null, LocalDateTime.of(1100, 5, 5, 0, 0));
    Person grandchild6 = new Person("Grandchild6", 'M', null, LocalDateTime.of(1100, 6, 6, 0, 0));
    Person grandchild7 = new Person("Grandchild7", 'F', null, LocalDateTime.of(1100, 7, 7, 0, 0));
    Person grandchild8 = new Person("Grandchild8", 'M', null, LocalDateTime.of(1100, 8, 8, 0, 0));

    // Creating children
    Person child1 = new Person("Child1", 'F', new Person[]{grandchild1, grandchild2}, LocalDateTime.of(1080, 1, 1, 0, 0));
    Person child2 = new Person("Child2", 'M', new Person[]{grandchild3, grandchild4}, LocalDateTime.of(1080, 2, 2, 0, 0));
    Person child3 = new Person("Child3", 'F', new Person[]{grandchild5, grandchild6}, LocalDateTime.of(1080, 3, 3, 0, 0));
    Person child4 = new Person("Child4", 'M', new Person[]{grandchild7, grandchild8}, LocalDateTime.of(1080, 4, 4, 0, 0));

    // Creating the test person
    Person person = new Person("John", 'M', new Person[]{child1, child2, child3, child4}, LocalDateTime.of(1060, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandfather of Target1";
    assertEquals(result, expected);
  }

  @Test
  public void PersonWithHundredsOfDescendants() {
    // Creating 625 great-great-grandchildren
    Person[][][][] greatGreatGrandchildren = new Person[5][5][5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        for (int k = 0; k < 5; k++) {
          for (int l = 0; l < 5; l++) {
            greatGreatGrandchildren[i][j][k][l] = new Person(
              "GGGChild" + (i*125 + j*25 + k*5 + l + 1),
              'F',
              null,
              LocalDateTime.of(1140, 1, 1, 0, 0).plusDays(l) // Unique birthdate between siblings
            );
          }
        }
      }
    }

    // Creating 125 great-grandchildren
    Person[][][] greatGrandchildren = new Person[5][5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        for (int k = 0; k < 5; k++) {
          greatGrandchildren[i][j][k] = new Person(
            "GGChild" + (i*25 + j*5 + k + 1),
            'M',
            greatGreatGrandchildren[i][j][k],
            LocalDateTime.of(1120, 1, 1, 0, 0).plusDays(k) // Unique birthdate between siblings
          );
        }
      }
    }

    // Creating 25 grandchildren
    Person[][] grandchildren = new Person[5][5];
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        grandchildren[i][j] = new Person(
          "GChild" + (i*5 + j + 1),
          'F',
          greatGrandchildren[i][j],
          LocalDateTime.of(1100, 1, 1, 0, 0).plusDays(j) // Unique birthdate between siblings
        );
      }
    }

    // Creating 5 children
    Person[] children = new Person[5];
    for (int i = 0; i < 5; i++) {
      children[i] = new Person(
        "Child" + (i + 1),
        'M',
        grandchildren[i],
        LocalDateTime.of(1080, 1, 1, 0, 0).plusDays(i) // Unique birthdate between siblings
      );
    }

    // Creating the test person
    Person person = new Person("Mary", 'F', children, LocalDateTime.of(1060, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandmother of GGGChild1";
    assertEquals(result, expected);
  }
}