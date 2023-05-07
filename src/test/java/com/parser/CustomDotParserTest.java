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
  @Test
  public void shouldReturnThreNodesAndTwoLink() throws IOException {
    Graph graph = CustomDotParser.parse("src/test/java/com/parser/assets/simple.dot");
    assertEquals(graph.links().size(), 2);
    assertEquals(graph.nodes().size(), 3);

    assertEquals(graph.links().get(1).toString(), "0->1");
    assertEquals(graph.links().get(0).toString(), "1->2");

    assertEquals(graph.nodes().get(0).toString(), "1");
    assertEquals(graph.nodes().get(1).toString(), "2");
    assertEquals(graph.nodes().get(2).toString(), "0");
  }
  @Test
  public void shouldReturnNodeZeroAsFirst() throws IOException {
    Graph graph = CustomDotParser.parse("src/test/java/com/parser/assets/simple.dot");
    assertEquals(graph.getFirstNode().toString(), "0");
  }
  @Test
  public void shouldThrowIfThereAreMoreThanOneStartingNode() throws IOException {
    try {
      Graph graph = CustomDotParser.parse("src/test/java/com/parser/assets/two-starting-nodes.dot");
      graph.getFirstNode();
      assertTrue(false);
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Graph has more than one starting nodes");
    }
  }
}
