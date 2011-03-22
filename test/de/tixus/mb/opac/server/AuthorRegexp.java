package de.tixus.mb.opac.server;

import junit.framework.TestCase;

public class AuthorRegexp extends TestCase {

  public void testRegexp() throws Exception {
    final String regex = "(\\w)/";

    //    author=Schmidt, Helmut / Stern, Fritz -> Helmut Schmidt
    final String testA = "Schmidt, Helmut / Stern, Fritz";
    final String[] splitTestA = testA.split(regex);
    for (int i = 0; i < splitTestA.length; i++) {
      System.out.println(i + ": '" + splitTestA[i] + "'");
    }
    assertEquals("Schmidt", splitTestA[0]);
    assertEquals("Helmut", splitTestA[1]);

    //      Klein Georg -> Vorname Nachname
    //      Loriot ->Nachname

    //    author=Raabe, Wilhelm -> Vorname Nachname 
    //    André-Lang/Rast, Harald -> Nachname

    //    author=Hampel, Thomas (Hrsg.) => -> Vorname Nachname (Hrsg.)

  }
}
