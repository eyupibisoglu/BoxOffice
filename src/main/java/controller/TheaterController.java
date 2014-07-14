package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import factory.DataFactory;
import application.BoxOffice;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lombok.Data;
import model.Movie;
import model.Scene;
import model.Seat;
import model.Theater;
import model.Ticket;

@Data
public class TheaterController extends Controller implements Initializable
{
	private Integer theaterIndex;
	private Integer sceneIndex;
	private ArrayList<Ticket> tickets = (ArrayList<Ticket>) DataFactory.getData("db/Ticket.data");
	private ArrayList<Theater> theaters = (ArrayList<Theater>) DataFactory.getData("db/Theater.data");

	@FXML private Pane movieInfoBox;
	@FXML private Label headerLabel;
	@FXML private Button backButton;
	@FXML private VBox ticketBox;

	public void initialize(URL location, ResourceBundle resources)
	{
		setBackButtonEvent();
	}
//
//	public void setTheater(Theater theater)
//	{
//		this.theater = theater;
//		System.out.println("Hi");
//	}

	public void assignData()
	{
		final Theater theater = getTheaters().get(getTheaterIndex());
		final Scene scene = theater.getScenes().get(getSceneIndex());
		final Movie movie = scene.getMovie();

		DropShadow dropShadow = new DropShadow(5.0, 3.0, 3.0, Color.color(0.4, 0.5, 0.5));

		ImageView imageView = new ImageView();
		imageView.setImage(movie.getImage());
		imageView.setFitHeight(300);
		imageView.setFitWidth(187.5);
		imageView.setEffect(dropShadow);

		VBox infoBox = new VBox();
		infoBox.setMinSize(300, 300);
		infoBox.setSpacing(5);

		infoBox.getChildren().addAll
		(
			new Label("Salon : " + theater.getNumber()),
			new Label("Tarih : " + scene.getDate().toString()),
			new Label("Saat  : " + scene.getTime().toString()),
			new Label("Fiyat : " + scene.getPrice().toString() + " TL")
		);

		getHeaderLabel().setText(movie.getName());

		FlowPane seatPane = new FlowPane(5, 5);
		seatPane.setMinSize(500, 300);



		for (Integer i = 0; i < scene.getSeats().size(); i++)
		{
			final Seat seat = scene.getSeats().get(i);
			final Button seatButton = new Button(seat.getNumber().toString());
			seatButton.setId(i.toString());
			seatButton.setMinSize(45, 45);
			seatButton.setEffect(dropShadow);

			if (seat.getIsAvailable())
				seatButton.setStyle("-fx-background-color:linear-gradient(#43C550, #4C8451);-fx-text-fill:#FFF;");
			else
				seatButton.setStyle("-fx-background-color:#582323;-fx-text-fill:#FFF;");

			seatButton.setOnMouseClicked(new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent event)
				{
					ArrayList<Theater> theaters = (ArrayList<Theater>) DataFactory.getData("db/Theater.data");
					getTicketBox().getChildren().clear();

					if (seat.getIsAvailable())
					{
						seat.setIsAvailable(false);

						theaters.get(getTheaterIndex()).getScenes().get(getSceneIndex()).getSeats().get(Integer.parseInt(seatButton.getId())).setIsAvailable(false);


						Ticket ticket = new Ticket();
						ticket.setTheater(theater);
						ticket.setMovie(movie);
						ticket.setPrice(scene.getPrice());
						ticket.setDate(scene.getDate());
						ticket.setTime(scene.getTime());
						ticket.setCustomer(LoginController.getPerson());
						ticket.setSeat(seat);
						seatButton.setStyle("-fx-background-color:#582323;-fx-text-fill:#FFF;");

						getTickets().add(ticket);

						Label movieLabel = new Label(movie.getName());
						movieLabel.setFont(Font.font(java.awt.Font.SANS_SERIF, FontWeight.EXTRA_BOLD, 20));

						getTicketBox().getChildren().addAll
						(
							movieLabel,
							new Label("Salon : " + theater.getNumber().toString()),
							new Label("Koltuk No : " + seat.getNumber()),
							new Label("Tarih :" + scene.getDate().toString()),
							new Label("Saat : " + scene.getTime().toString()),
							new Label("Fiyat : " + scene.getPrice().toString() + " TL")

						);
					}
					else
					{
						seat.setIsAvailable(true);
						getTicketBox().getChildren().addAll
						(
							new Label("Bilet alım işleminiz iptal edilmiştir.")

						);

						theaters.get(getTheaterIndex()).getScenes().get(getSceneIndex()).getSeats().get(Integer.parseInt(seatButton.getId())).setIsAvailable(true);
						getTickets().remove(getTickets().size() - 1);
						seatButton.setStyle("-fx-background-color:linear-gradient(#43C550, #4C8451);-fx-text-fill:#FFF;");
					}

					DataFactory.addData("db/Ticket.data", tickets);
					DataFactory.addData("db/Theater.data", theaters);
				}
			});

			seatPane.getChildren().add(seatButton);
		}



		getMovieInfoBox().getChildren().addAll
		(
			imageView,
			infoBox,
			seatPane
		);

	}

	public void setTheaterIndex(int theaterIndex)
	{
		this.theaterIndex = theaterIndex;
		assignData();
	}

	public void setBackButtonEvent()
	{
		getBackButton().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				try
				{
					BoxOffice.stage.setScene(new javafx.scene.Scene((BorderPane) FXMLLoader.load(getClass().getResource("/view/Customer.fxml")), 1400, 875));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}

		});
	}
}
