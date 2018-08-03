package main;

import java.io.File;

import javax.swing.event.EventListenerList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import settingsMenu.RadialSettingsMenu;

public class Main extends Application{

	public static void main(final String[] args) {
		launch(args);
	}

	private Group container;
	Scene scene;
	Button paint;
	Button picture;
	Label labelSelect;
	Pane pane;
	private RadialSettingsMenu radialMenu;
	int snapshotCounter = 0;

	@Override
	public void start(final Stage primaryStage) throws Exception {

		primaryStage.setTitle("EasyPaint");
		primaryStage.getIcons().add(new Image("/image/paint.png"));
		container = new Group();
		Color cor = Color.web("#0d0b21");
		scene = new Scene(container,cor);
		
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.setWidth(1200);
		primaryStage.setHeight(700);
		primaryStage.centerOnScreen();
		primaryStage.show();

		//Barra de Menus
		javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
		Menu file = new Menu("File");
		MenuItem file_novo = new MenuItem("New");
		MenuItem file_guardar = new MenuItem("Save");
		MenuItem file_print = new MenuItem("Print");
		MenuItem file_sair = new MenuItem("Exit");
		file.getItems().addAll(file_novo,file_guardar, file_print, file_sair);
		
		Menu edit = new Menu("Edit");
		MenuItem file_undo = new MenuItem("Undo");
		MenuItem file_redo = new MenuItem("Redo");
		MenuItem file_copy = new MenuItem("Copy");
		MenuItem file_paste = new MenuItem("Paste");
		MenuItem file_procurar = new MenuItem("Search");
		edit.getItems().addAll(file_undo, file_redo, file_copy, file_paste, file_procurar);
		
		Menu help = new Menu("Help");
		
		menuBar.getMenus().addAll(file,edit,help);


		menuBar.setPrefWidth(1200);
		menuBar.setTranslateX(0);
		menuBar.setTranslateY(0);
		menuBar.getStyleClass().add("menuBar-style");
		container.getChildren().add(menuBar);

		//Menu Circular

		radialMenu = new RadialSettingsMenu();
		radialMenu.getStyleClass().add("round-menu");
		container.getChildren().addAll(radialMenu);



		//Painel de desenho

		pane = new Pane();
		pane.setPrefSize(800, 550);
		pane.setTranslateX(300);
		pane.setTranslateY(80);
		pane.getStyleClass().add("pane-style");
		String cssLayout = "-fx-border-color: black;\n";
		pane.setStyle(cssLayout);
		container.getChildren().add(pane);

		//Butão Paint
		paint = new Button();
		EventHandler<ActionEvent> buttonPaint = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				container.getChildren().removeAll(paint, picture, labelSelect);
				pane.setStyle("-fx-background-color: white;");
			}
		};
		paint.setTranslateX(630);
		paint.setTranslateY(310);
		paint.setPrefSize(60, 43);
		paint.getStyleClass().add("paint-style");
		paint.setOnAction(buttonPaint);
		container.getChildren().add(paint);

		//Butão para escolher imagem
		picture = new Button();
		EventHandler<ActionEvent> buttonPicture = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				container.getChildren().removeAll(paint, picture, labelSelect);
				FileChooser fileChooser = new FileChooser();

				File file = fileChooser.showOpenDialog(primaryStage);
				Image imagem = new Image(file.toURI().toString());
				ImageView imageView = new ImageView();
				imageView.setImage(imagem);
				imageView.setFitHeight(550);
				imageView.setFitWidth(800);
				pane.getChildren().add(imageView);

			}
		};
		picture.setTranslateX(730);
		picture.setTranslateY(310);
		picture.setPrefSize(60, 43);
		picture.getStyleClass().add("picture-style");
		picture.setOnAction(buttonPicture);
		container.getChildren().add(picture);

		//UNDO
		Button undo = new Button();
		undo.setTranslateX(1110);
		undo.setTranslateY(100);
		undo.setPrefSize(40,40);
		undo.getStyleClass().add("button-undo");
		container.getChildren().add(undo);

		//REDO
		Button redo = new Button();
		redo.setTranslateX(1110);
		redo.setTranslateY(160);
		redo.setPrefSize(40, 40);
		redo.getStyleClass().add("button-redo");
		container.getChildren().add(redo);

		//Guardar
		Button guardar = new Button();
		guardar.setTranslateX(1110);
		guardar.setTranslateY(550);
		guardar.setPrefSize(35,35);
		guardar.getStyleClass().add("button-save");
		container.getChildren().add(guardar);

		//Print
		Button print = new Button();
		//print.setTranslateX(260);
		//print.setTranslateY(100);
		print.setTranslateX(1110);
		print.setTranslateY(590);
		print.setPrefSize(35,35);
		print.getStyleClass().add("button-print");
		container.getChildren().add(print);

		//ZOOM
		final Label scalingCaption = new Label("Zoom :");
		final Slider scaling = new Slider (0.5, 1, 1);
		Image img = new Image("/image/zoom.png");
		ImageView imgView = new ImageView(img);
		

		scaling.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,Number old_val, Number new_val) {
				pane.setScaleX(new_val.doubleValue());
				pane.setScaleY(new_val.doubleValue());

			}
		});
		imgView.setTranslateX(770);
		imgView.setTranslateY(635);
		scaling.setTranslateX(620);
		scaling.setTranslateY(640);
		container.getChildren().add(scaling);
		container.getChildren().add(imgView);




		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(final MouseEvent event) {
				if (event.isSecondaryButtonDown()) {
					radialMenu.setTranslateX(event.getX());
					radialMenu.setTranslateY(event.getY());
				}
			}
		});

		container.getStylesheets().add("/style/style.css");

	}
}