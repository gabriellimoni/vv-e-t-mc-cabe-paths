package com.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Link;
import com.Graph;
import com.Node;

// TODO: test
public class CustomDotParser {
  public static Graph parse(String path) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line = reader.readLine();

    Graph graph = new Graph();
    List<Node> nodes = new ArrayList<>();
    List<Link> edges = new ArrayList<>();

    Integer lineCount = 0;
    while (line != null) {
      if (lineCount == 0 && !line.contains("digraph")) {
        throw new IOException("Graph is not direct");
      }
      if (line.contains("digraph")) {
        graph.setName(line.replace("digraph ", ""));
      } else if (line.contains("->")) {
        String[] split = line.split("->");
        String nodeSourceId = split[0].trim();
        Node sourceNode;
        if (nodes.contains(new Node(nodeSourceId, nodeSourceId))) {
          sourceNode = nodes.get(nodes.indexOf(new Node(nodeSourceId, nodeSourceId)));
        } else {
          sourceNode = new Node(nodeSourceId, nodeSourceId);
        }
        if (!nodes.contains(sourceNode)) {
          nodes.add(sourceNode);
        }

        String[] targetNodeIds = split[1].trim().split(";");
        List<Node> targetNodes = new ArrayList<>();
        for (String targetNodeId : targetNodeIds) {
          targetNodeId = targetNodeId.replace("{", "").trim();
          targetNodeId = targetNodeId.replace("}", "").trim();
          targetNodeId = targetNodeId.replaceAll("[^0-9]", "").trim();
          if (targetNodeId.isBlank()) continue;

          Node targetNode;
          if (nodes.contains(new Node(targetNodeId, targetNodeId))) {
            targetNode = nodes.get(nodes.indexOf(new Node(targetNodeId, targetNodeId)));
          } else {
            targetNode = new Node(targetNodeId, targetNodeId);
          }
          targetNodes.add(targetNode);
          
          Link edge = new Link(nodeSourceId + "->" + targetNodeId)
            .from(sourceNode)
            .to(targetNode);
          edges.add(edge);
        }
        for (Node targetNode: targetNodes) {
          if (!nodes.contains(targetNode)) {
            nodes.add(targetNode);
          }
        }
      }
      line = reader.readLine();
      lineCount++;
    }
    reader.close();

    graph.setNodes(nodes);
    graph.setLinks(edges);
    return graph;
  }
}
