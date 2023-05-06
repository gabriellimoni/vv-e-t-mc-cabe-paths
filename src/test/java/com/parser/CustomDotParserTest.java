package com.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.Graph;

public class CustomDotParserTest {
  @Test
  public void shouldReturnGraphWithCorrectName() throws IOException {
    Graph graph = CustomDotParser.parse("src/test/java/com/parser/assets/simple.dot");
    assertEquals(graph.getName(), "anyGraphName");
  }
  @Test
  public void shouldThrowBecauseItIsNotDirectGraph() throws IOException {
    try {
      CustomDotParser.parse("src/test/java/com/parser/assets/not-direct.dot");
      assertTrue(false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Graph is not direct");
    }
  }
}
