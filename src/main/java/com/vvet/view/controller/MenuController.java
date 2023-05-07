package com.vvet.view.controller;

import static guru.nidi.graphviz.model.Factory.*;

import com.Graph;
import com.Node;
import com.Path;
import com.parser.CustomDotParser;
import com.vvet.mccabe.McCabeRunner;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
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

  protected MutableGraph displayGraphOnMainView(
    MutableGraph g,
    String path,
    Stage stage
  ) throws IOException {
    Graphviz
      .fromGraph(g)
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
    String selectedFilePath
  ) throws IOException {
    Stage secondaryStage = new Stage();
    secondaryStage.setTitle("Caminhos de McCabe");
    secondaryStage.setWidth(mainStage.getWidth());
    secondaryStage.setHeight(mainStage.getHeight());

    List<Image> images = new ArrayList<Image>();

    int index = 0;
    for (Path path : paths) {
      images.add(generatePathImage(path, index++, selectedFilePath));
    }

    ImageView pathImageView = new ImageView();
    pathImageView.setImage(images.get(0));

    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setFill(Color.WHITE);
    HBox box = new HBox();
    box.getChildren().add(pathImageView);
    root.getChildren().add(box);

    Button buttonNext = new Button("Próximo");
    Button buttonPrev = new Button("Anterior");

    buttonNext.setOnAction(event -> {
      int currentIndex = images.indexOf(pathImageView.getImage());
      if (currentIndex < images.size() - 1) {
        pathImageView.setImage(images.get(currentIndex + 1));
      }
    });

    buttonPrev.setOnAction(event -> {
      int currentIndex = images.indexOf(pathImageView.getImage());
      if (currentIndex > 0) {
        pathImageView.setImage(images.get(currentIndex - 1));
      }
    });

    root.getChildren().add(buttonNext);
    buttonNext.setTranslateY(secondaryStage.getHeight()-100);

    root.getChildren().add(buttonPrev);
    buttonPrev.setTranslateY(secondaryStage.getHeight()-100);
    buttonPrev.setTranslateX(secondaryStage.getWidth()-60);

    secondaryStage.setScene(scene);
    secondaryStage.show();
  }

  protected Image generatePathImage(Path path, int index, String filePath)
    throws IOException {
    List<Node> nodes = path.nodes();
    MutableGraph g = mutGraph("Caminho " + index).setDirected(true);

    for (Node node : nodes) {
      g.add(mutNode(node.toString()));
    }

    for (int i = 0; i < nodes.size() - 1; i++) {
      g.add(
        mutNode(nodes.get(i).toString())
          .addLink(mutNode(nodes.get(i + 1).toString()))
      );
    }

    String outputImageFile = filePath.replace(
      ".dot",
      "_path_" + index + ".png"
    );

    Graphviz.fromGraph(g).render(Format.PNG).toFile(new File(outputImageFile));
    Image outImage = new Image("file://" + outputImageFile);

    return outImage;
  }

  @FXML
  protected void openFileChooser(ActionEvent event) throws IOException {
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
    displayPaths(mccabePaths, stage, selectedFile.getAbsolutePath());
  }

  @FXML
  protected void processExit(ActionEvent event) {
    System.exit(0);
  }
}
