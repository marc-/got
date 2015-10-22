package org.github.got;

import static org.github.got.TextUtil.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TextUtilTest {

  @Test
  public final void testToEmpty() {
    assertNotNull(TextUtil.toEmpty(null));
    assertEquals(Resources.BKSPACE, TextUtil.toEmpty(null));
    assertNotNull(Resources.BKSPACE, TextUtil.toEmpty("TEST"));
    assertEquals("TEST", TextUtil.toEmpty("TEST"));
  }

  @Test
  public final void testIsEmpty() {
    assertTrue(TextUtil.isEmpty(""));
    assertTrue(TextUtil.isEmpty(null));
  }

  @Test
  public final void testIsEmpty_NEGATIVE() {
    assertFalse(TextUtil.isEmpty("TEST"));
  }

  @Test
  public final void testCapitalize() {
    assertEquals("Test", TextUtil.capitalize("TEST"));
    assertEquals("Test", TextUtil.capitalize("test"));
    assertEquals("Test", TextUtil.capitalize("Test"));
  }

  @Test
  public final void testMobHumanoidName() {
    assertNotNull(TextUtil.mobHumanoidName());
  }

  @Test
  public final void testMobAnimalName() {
    assertNotNull(TextUtil.mobAnimalName());
  }

  @Test
  public final void testGender() {
    assertNotNull(TextUtil.gender());
  }

  @Test
  public final void testCenter() {
    final String text = TextUtil.center("a");
    assertNotNull(text);
    assertEquals(119, text.length());
    assertEquals('a', text.charAt(59));
    assertTrue(text.matches("^\\s{59}a\\s{59}"));
  }

  @Test
  public final void testCenter1() {
    final String text = TextUtil.center("aa");
    assertNotNull(text);
    assertEquals(120, text.length());
    assertEquals('a', text.charAt(59));
    assertEquals('a', text.charAt(60));
    assertTrue(text.matches("^\\s{59}aa\\s{59}"));
  }

  @Test
  public final void testRightPad() {
    final String text = TextUtil.rightPad("a", 5);
    assertEquals(6, text.length());
    assertEquals('a', text.charAt(0));
    assertTrue(text.matches("^a\\s{5}$"));
  }

  @Test
  public final void testLeftPad() {
    final String text = TextUtil.leftPad("a", 6);
    assertEquals(6, text.length());
    assertEquals('a', text.charAt(5));
    assertTrue(text.matches("^\\s{5}a$"));
  }

  @Test
  public final void testRepeat() {
    final String text = TextUtil.repeat("a", 7);
    assertEquals(7, text.length());
    assertTrue(text.matches("^a{7}$"));
  }

  @Test
  public final void testStripFirstPart() {
    final String text = TextUtil.stripFirstPart("a b c d");
    assertNotNull(text);
    assertEquals("b c d", text);
  }

  @Test
  public final void testStripFirstPart_NEGATIVE() {
    final String text = TextUtil.stripFirstPart("a");
    assertNotNull(text);
    assertEquals(EMPTY, text);
  }

  @Test
  public final void testCollapseSpaces() {
    final String text = TextUtil.collapseSpaces("   a a  a    a    ");
    assertNotNull(text);
    assertEquals(" a a a a ", text);
  }

  @Test
  public final void testCollapseSpaces1() {
    final String text = TextUtil.collapseSpaces(" aaa aaa ");
    assertNotNull(text);
    assertEquals(" aaa aaa ", text);
  }

}
