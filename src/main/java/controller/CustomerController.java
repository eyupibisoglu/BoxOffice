package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import application.BoxOffice;
import factory.DataFactory;
import factory.TableFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import lombok.Data;
import model.Customer;
import model.Date;
import model.Movie;
import model.Person;
import model.Scene;
import model.Theater;
import model.Time;

@Data
public class CustomerController extends Controller implements Initializable
{
	@FXML private HBox customerMoviesBox;
	@FXML private HBox customerTopBox;
	@FXML private Button timeButton;


	public void initialize(URL location, ResourceBundle resources)
	{

		DataFactory dataFactory = new DataFactory();
		final List<Theater> theaters = (ArrayList<Theater>) dataFactory.getData("db/Theater.data");



		for (Theater theater : theaters)
		{
			Movie movie = theater.getScenes().get(0).getMovie();


			DropShadow dropShadow = new DropShadow(5.0, 3.0, 3.0, Color.color(0.4, 0.5, 0.5));

			final ImageView imageView = new ImageView(movie.getImage());
			imageView.setId(movie.getName());
			imageView.setFitHeight(300);
			imageView.setFitWidth(187.5);
			imageView.setEffect(dropShadow);










//			imageView.setOnMouseEntered(new EventHandler<MouseEvent>()
//			{
//				public void handle(MouseEvent event)
//				{
//					try
//					{
//						BoxOffice.stage.setScene(new javafx.scene.Scene((BorderPane) FXMLLoader.load(getClass().getResource("/view/Manager.fxml")), 1400, 875));
//					}
//					catch (IOException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			});
//
//			imageView.setOnMouseExited(new EventHandler<MouseEvent>()
//			{
//				public void handle(MouseEvent event)
//				{
//
//				}
//			});
			FlowPane  timePane = new FlowPane();
			timePane.setMaxWidth(190d);
			VBox vBox = new VBox();
			vBox.setStyle("-fx-alignment: CENTER; -fx-spacing:10");
			vBox.getChildren().add(new Label(theater.getNumber() + ". Salon"));
			vBox.getChildren().add(imageView);
			vBox.getChildren().add(new Label(movie.getName()));

			List<Scene> scenes = theater.getScenes();

			final Date dateNow = new Date().now();
			Time time = new Time().now();
			String movieName = imageView.getId();

			for (Scene scene : scenes)
			{
				if (scene.getDate().equals(dateNow))
				{
					final Button button = new Button(scene.getTime().toString());
					button.setMinSize(60, 20);
					button.setId(theater.getNumber().toString());

					/** Click Event **/
					button.setOnMouseClicked(new EventHandler<MouseEvent>()
					{
						public void handle(MouseEvent event)
						{
							FXMLLoader fxmlLoader = new FXMLLoader();

							Parent root = null;
							try
							{
								root = (Parent) fxmlLoader.load(getClass().getResource("/view/Theater.fxml").openStream());
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}

							TheaterController controller = fxmlLoader.getController();

							int theaterNumber = Integer.parseInt(button.getId());

							int i = 0;
							while (theaters.get(i).getNumber() != theaterNumber)
							{
								i++;
							}
							Theater theater = theaters.get(i);




							int j = 0;

							while (!theater.getScenes().get(j).getTime().toString().equals(button.getText()) || !theater.getScenes().get(j).getDate().equals(dateNow))
							{
								j++;
							}


							controller.setSceneIndex(j);
							controller.setTheaterIndex(i);
							BoxOffice.stage.setScene(new javafx.scene.Scene(root, 1400, 875));

						}
					});

					if (!scene.getTime().compare(time))
						button.setDisable(true);

					timePane.getChildren().add(button);
				}
			}



			vBox.getChildren().add(timePane);
			getCustomerMoviesBox().getChildren().add(vBox);
		}
	}

}
