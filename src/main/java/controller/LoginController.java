package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import exception.WrongLoginDataException;
import factory.DataFactory;
import application.BoxOffice;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lombok.Data;
import model.Customer;
import model.Person;

@Data
public class LoginController extends Controller  implements Initializable
{
	private String pageName;
	private static Person person; // Person that login.

	@FXML private TextField nameField;
	@FXML private TextField surnameField;
	@FXML private Button formButton;

	public void initialize(URL location, ResourceBundle resources)
	{
		setComponentsEvents();
	}

	public Person getLoginPerson()
	{
		Person person = new Person(getNameField().getText(), getSurnameField().getText());

		ArrayList<Person> people = (ArrayList<Person>) new DataFactory().getData("db/" + getPageName() + ".data");



		if (people.contains(person))
		{
			Integer i = 0;
			Integer size = people.size();

			while ((!people.get(i).getName().equals(person.getName()) || !people.get(i).getSurname().equals(person.getSurname())))
				i++;

			return people.get(i);
		}
		else
		{
			try
			{
				throw new WrongLoginDataException("Kullanıcı adı yada şifre hatalı.");
			}
			catch (WrongLoginDataException e1)
			{
				System.out.println(e1.getMessage());
			}

			return null;
		}


	}

	public void setComponentsEvents()
	{
		getFormButton().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{



				setPerson(getLoginPerson());


				FXMLLoader fxmlLoader = new FXMLLoader();

				Parent root = null;
				try
				{
					root = (Parent) fxmlLoader.load(getClass().getResource("/view/" + getPageName() + ".fxml").openStream());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				Controller controller = fxmlLoader.getController();
				BoxOffice.stage.setScene(new javafx.scene.Scene(root, 1400, 875));
			}
		});
	}


	public static Person getPerson()
	{
		return person;
	}


	public static void setPerson(Person person)
	{
		LoginController.person = person;
	}

	public void setPageName(String pageName)
	{
		this.pageName = pageName;

		if (pageName.equals("Customer"))
		{
			getNameField().setText("Eyüp");
			getSurnameField().setText("İbişoğlu");
		}
		else if (pageName.equals("Manager"))
		{
			getNameField().setText("Birtan");
			getSurnameField().setText("Birsin");
		}
	}
}
