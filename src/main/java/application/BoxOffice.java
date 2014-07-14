package application;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import controller.CustomerController;
import controller.HomeController;
import controller.ManagerController;
import factory.DataFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Data;
import model.*;



@Data
public class BoxOffice extends Application
{
	public static Stage stage;

	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setScene(new javafx.scene.Scene((BorderPane) FXMLLoader.load(getClass().getResource("/view/Home.fxml")), 600, 375));
		this.stage = stage;
		stage.show();
	}


	public static void main(String[] args)
	{
//		new BoxOffice().generateData();
		launch();
	}

	public void generateData()
	{
		DataFactory dataFactory = new DataFactory();
		ArrayList<Theater> theaters2 = (ArrayList<Theater>) dataFactory.getData("db/Theater.data");

		/**** MOVIES ****/
		ArrayList<Movie> movies = new ArrayList<Movie>();
		movies.add(new Movie("Kabadayı", "poster/kabadayi.jpg"));
		movies.add(new Movie("Eyvah Eyvah", "/poster/eyvah_eyvah.jpg"));
		movies.add(new Movie("Mission İmpossible", "/poster/mission_impossible.jpg"));
		movies.add(new Movie("X-Men First Class", "/poster/x_men.jpg"));
		movies.add(new Movie("Django Unchained", "/poster/django_unchained.jpg"));
		movies.add(new Movie("Lone Survivor", "/poster/lone_survivor.jpg"));
		dataFactory.addData("db/Movie.data", movies);

		/**** CUSTOMER ****/
		ArrayList<Customer> customers = new ArrayList<Customer>();
		customers.add(new Customer("Eyüp", "İbişoğlu"));
		customers.add(new Customer("H. Can", "Kaya"));
		customers.add(new Customer("R. Ekrem", "Çoban"));
		customers.add(new Customer("Elif", "Zorer"));
		customers.add(new Customer("Sinem", "Baba"));
		customers.add(new Customer("Osman", "Aydın"));
		dataFactory.addData("db/Customer.data", customers);

		/**** MANAGER ****/
		ArrayList<Manager> managers = new ArrayList<Manager>();
		managers.add(new Manager("Birtan", "Birsin"));
		managers.add(new Manager("Muhammet", "Nahya"));
		dataFactory.addData("db/Manager.data", managers);


		/**** TİME ****/
		ArrayList<Time> times = new ArrayList<Time>();

		for (byte i = 6; i < 10; i++)
			times.add(new Time(i, (byte) 30));

		for (byte i = 10; i < 24; i++)
			times.add(new Time(i, (byte) 30));
		dataFactory.addData("db/Time.data", times);

		/**** DATE ****/
		ArrayList<Date> dates = new ArrayList<Date>();

		for (int i = 0; i < 10; i++)
		{
			Date date = new Date().now();
			date.setDay((byte) (date.getDay() + i));
			dates.add(date);
		}
		dataFactory.addData("db/Date.data", dates);

		/**** SCENE ****/
		ArrayList<model.Scene> scenes = new ArrayList<model.Scene>();



		for (Movie movie : movies)
		{
			for (int a = 0; a < times.size(); a += 2)
			{
				for (Date date : dates)
				{
					model.Scene scene = new model.Scene(40);


					int charNumber = 64;
					int j = 1;

					for (int i = 0; i < 40; i++)
					{
						if (i % 10 == 0)
						{
							j = 1;
							charNumber++;
						}

						scene.getSeats().add(new Seat((char) charNumber + Integer.toString(j++)));
					}

					scene.setDate(date);
					scene.setMovie(movie);
					scene.setTime(times.get(a));
					scene.setPrice(new Price(10.9f));
					scenes.add(scene);
				}

			}

		}

		DataFactory.addData("db/Scene.data", scenes);

		/**** THEATER ****/
		ArrayList<Theater> theaters = new ArrayList<Theater>();

		int j = 1;

		for (Movie m : movies)
		{
			Theater theater = new Theater(40);
			theater.setNumber((byte) j++);

			for (model.Scene scene : scenes)
			{
				if (scene.getMovie().getName().equals(m.getName()))
				{
					theater.getScenes().add(scene);
				}
			}

			theaters.add(theater);
		}

		dataFactory.addData("db/Theater.data", theaters);


	}
}
