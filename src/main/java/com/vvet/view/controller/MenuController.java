package com.vvet.view.controller;

import static guru.nidi.graphviz.model.Factory.*;

import com.Graph;
import com.Node;
import com.Path;
import com.parser.CustomDotParser;
import com.vvet.mccabe.McCabeRunner;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.LinkTarget;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.parse.Parser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuController {

  @FXML
  private MenuBar menuBar;

  @FXML
  private ImageView originalGraph;

  private Stage secondaryStage = new Stage();

  protected MutableGraph displayGraphOnMainView(
    MutableGraph g,
    String path,
    Stage stage
  ) throws IOException {
    Graphviz
      .fromGraph(g)
      .height(900)
      .render(Format.PNG)
      .toFile(new File(path.replace(".dot", ".png")));

    Image outImage = new Image("file://" + path.replace(".dot", ".png"));

    originalGraph.setImage(outImage);

    stage.setWidth(outImage.getWidth() + 30);
    stage.setHeight(outImage.getHeight() + 100);

    return g;
  }

  protected List<Path> getMcCabePaths(String selectedFilePath) {
    try {
      Graph mccabe = CustomDotParser.parse(selectedFilePath);
      List<Path> paths = new McCabeRunner(mccabe, "").run();
      return paths;
    } catch (Exception e) {
      System.out.println(e);
    }

    return null;
  }

  protected void displayPaths(
    List<Path> paths,
    Stage mainStage,
    String selectedFilePath,
    MutableGraph g
  ) throws IOException {
    secondaryStage.setTitle("Caminhos de McCabe");

    List<Image> images = new ArrayList<Image>();

    int index = 0;
    for (Path path : paths) {
      images.add(generatePathImage(g, path, index++, selectedFilePath));
    }

    ImageView pathImageView = new ImageView();
    pathImageView.setImage(images.get(0));

    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setFill(Color.WHITE);
    HBox box = new HBox();
    box.getChildren().add(pathImageView);
    root.getChildren().add(box);

    Button buttonNext = new Button("->");
    Button buttonPrev = new Button("<-");

    Label label = new Label("1 / " + images.size());

    buttonNext.setOnAction(event -> {
      int currentIndex = images.indexOf(pathImageView.getImage());
      if (currentIndex < images.size() - 1) {
        pathImageView.setImage(images.get(currentIndex + 1));
        secondaryStage.setWidth(images.get(currentIndex + 1).getWidth());
        secondaryStage.setHeight(images.get(currentIndex + 1).getHeight() + 80);
        buttonPrev.setDisable(false);
        label.setText((currentIndex + 2) + " / " + images.size());
      }
      if (currentIndex == images.size() - 2) {
        buttonNext.setDisable(true);
      }
    });

    buttonPrev.setOnAction(event -> {
      int currentIndex = images.indexOf(pathImageView.getImage());
      if (currentIndex > 0) {
        pathImageView.setImage(images.get(currentIndex - 1));
        secondaryStage.setWidth(images.get(currentIndex - 1).getWidth());
        secondaryStage.setHeight(images.get(currentIndex - 1).getHeight() + 80);
        buttonNext.setDisable(false);
        label.setText((currentIndex) + " / " + images.size());

        if (currentIndex - 1 == 0) {
          buttonPrev.setDisable(true);
        }
      }
    });

    root.getChildren().add(buttonNext);
    root.getChildren().add(buttonPrev);
    root.getChildren().add(label);

    secondaryStage.setWidth(images.get(0).getWidth());
    secondaryStage.setHeight(images.get(0).getHeight() + 80);

    buttonNext.setTranslateY(5);
    buttonNext.setTranslateX(55);
    buttonPrev.setTranslateY(5);
    buttonPrev.setTranslateX(5);
    label.setTranslateX(100);
    label.setTranslateY(10);

    pathImageView.setTranslateY(40);
    buttonPrev.setDisable(true);

    secondaryStage.setScene(scene);
    secondaryStage.show();
  }

  protected Image generatePathImage(
    MutableGraph g,
    Path path,
    int index,
    String filePath
  ) throws IOException {
    MutableGraph graph = g.copy().setDirected(true);
    List<Node> nodes = path.nodes();

    for (Node node : nodes) {
      g.add(mutNode(node.toString()));
    }

    for (int i = 0; i < nodes.size() - 1; i++) {
      String originNodeName = nodes.get(i).toString();
      String destinationNodeName = nodes.get(i + 1).toString();

      MutableNode newLinkMutableNode = mutNode(originNodeName)
        .addLink(
          to(mutNode(destinationNodeName))
            .add(guru.nidi.graphviz.attribute.Color.RED)
        )
        .add(guru.nidi.graphviz.attribute.Color.RED);

      graph.add(newLinkMutableNode);

      // Color last node
      if (i == nodes.size() - 2) {
        graph.add(
          mutNode(destinationNodeName)
            .add(Style.FILLED)
            .add(guru.nidi.graphviz.attribute.Color.RED)
        );
      }
    }

    String outputImageFile = filePath.replace(
      ".dot",
      "_path_" + index + ".png"
    );

    String outDot = Graphviz.fromGraph(graph).render(Format.DOT).toString();
    for (int i = 0; i < nodes.size() - 1; i++) {
      {
        String originNodeName = nodes.get(i).toString();
        String destinationNodeName = nodes.get(i + 1).toString();

        // Remove original link
        for (Link link : graph.edges()) {
          if (
            link.from().name().toString().equals(originNodeName) &&
            link.to().toString().contains(destinationNodeName) &&
            link.to().toString().contains("digraph")
          ) {
            String parsedOriginalDestination = link
              .to()
              .toString()
              .replace("digraph", "\"" + originNodeName + "\" ->");

            String newDestination = parsedOriginalDestination
              .replace("\"" + destinationNodeName + "\"", "")
              .replaceAll("(?m)^[ \t]*\r?\n", "");

            outDot = outDot.replace(parsedOriginalDestination, newDestination);
          }
        }
      }
    }

    MutableGraph outGraph = new Parser().read(outDot);

    Graphviz
      .fromGraph(outGraph)
      .height(900)
      .render(Format.PNG)
      .toFile(new File(outputImageFile));
    Image outImage = new Image("file://" + outputImageFile);

    return outImage;
  }

  @FXML
  protected void openFileChooser(ActionEvent event) throws IOException {
    secondaryStage.hide();
    secondaryStage = new Stage();

    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Abrir arquivo .dot");

    fileChooser
      .getExtensionFilters()
      .addAll(new FileChooser.ExtensionFilter("Arquivos DOT", "*.dot"));

    Stage stage = (Stage) menuBar.getScene().getWindow();
    File selectedFile = fileChooser.showOpenDialog(stage);
    MutableGraph g = new Parser().read(selectedFile).setDirected(true);

    displayGraphOnMainView(g, selectedFile.getAbsolutePath(), stage);
    List<Path> mccabePaths = getMcCabePaths(selectedFile.getAbsolutePath());
    displayPaths(mccabePaths, stage, selectedFile.getAbsolutePath(), g);
  }

  @FXML
  protected void processExit(ActionEvent event) {
    System.exit(0);
  }
}
