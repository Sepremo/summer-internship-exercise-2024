package com.premiumminds.internship.teknonymy;

import java.util.concurrent.Future;

interface ITeknonymyService {
  /**
   * Method to get a Person Teknonymy Name
   *
   * @param Person person
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymy(Person person);
}
