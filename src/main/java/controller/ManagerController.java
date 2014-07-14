package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.BoxOffice;
import factory.DataFactory;
import factory.TableFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import lombok.Data;
import model.Customer;
import model.Manager;
import model.Movie;
import model.Person;
import model.Scene;
import model.Theater;
import model.Ticket;

@Data
public class ManagerController extends Controller implements Initializable
{
	@FXML private HBox movieBox;
	@FXML private HBox sceneBox;
	@FXML private HBox theaterBox;
	@FXML private HBox ticketBox;
	@FXML private HBox updateBox;

	private List<TableView> tableViews = new ArrayList<TableView>();

	public void initialize(URL location, ResourceBundle resources)
	{
		DataFactory dataFactory = new DataFactory();

		getTableViews().add(new TableFactory(getUpdateBox()).generateTable((ArrayList<?>) dataFactory.getData("db/Theater.data")));
		getTableViews().add(new TableFactory(getUpdateBox()).generateTable((ArrayList<?>) dataFactory.getData("db/Scene.data")));
		getTableViews().add(new TableFactory(getUpdateBox()).generateTable((ArrayList<?>) dataFactory.getData("db/Movie.data")));
		getTableViews().add(new TableFactory(getUpdateBox()).generateTable((ArrayList<?>) dataFactory.getData("db/Ticket.data")));

		getTheaterBox().getChildren().add(getTableViews().get(0));
		getSceneBox().getChildren().add(getTableViews().get(1));
		getMovieBox().getChildren().add(getTableViews().get(2));
		getTicketBox().getChildren().add(getTableViews().get(3));

		BoxOffice.stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			public void handle(WindowEvent event)
			{
				TableView tableView0 = tableViews.get(0);
				ArrayList<Theater> theaters = new ArrayList<Theater>();

				for (int i = 0; i < tableView0.getItems().size(); i++)
				{
					theaters.add((Theater) tableView0.getItems().get(i));
				}

				new DataFactory().addData("db/Theater.data", theaters);

				TableView tableView1 = tableViews.get(1);
				ArrayList<Scene> scenes = new ArrayList<Scene>();

				for (int i = 0; i < tableView1.getItems().size(); i++)
				{
					scenes.add((model.Scene) tableView1.getItems().get(i));
				}

				new DataFactory().addData("db/Scene.data", scenes);



				TableView tableView2 = tableViews.get(2);
				ArrayList<Movie> movies = new ArrayList<Movie>();

				for (int i = 0; i < tableView2.getItems().size(); i++)
				{
					movies.add((Movie) tableView2.getItems().get(i));
				}

				new DataFactory().addData("db/Movie.data", movies);


				TableView tableView3 = tableViews.get(3);
				ArrayList<Ticket> tickets = new ArrayList<Ticket>();

				for (int i = 0; i < tableView3.getItems().size(); i++)
				{
					tickets.add((Ticket) tableView3.getItems().get(i));
				}

				new DataFactory().addData("db/Ticket.data", tickets);



			}

		});

	}

}
