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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.Data;
import model.Ticket;

@Data
public class HomeController extends Controller implements Initializable
{
	private javafx.scene.Scene scene;

	@FXML private Button customerButton;
	@FXML private Button managerButton;

	public void initialize(URL location, ResourceBundle resources)
	{
//		setCustomerButton(new Button("Customer", new ImageView(new Image("/icon/customer.png"))));
		setButtonEvents();
	}

	private void setButtonEvents()
	{
		getCustomerButton().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				FXMLLoader fxmlLoader = new FXMLLoader();

				Parent root = null;
				try
				{
					root = (Parent) fxmlLoader.load(getClass().getResource("/view/Login.fxml").openStream());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				LoginController controller = fxmlLoader.getController();
				controller.setPageName(getCustomerButton().getText());

				BoxOffice.stage.setScene(new javafx.scene.Scene(root, 600, 375));
			}
		});

		getManagerButton().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				FXMLLoader fxmlLoader = new FXMLLoader();

				Parent root = null;
				try
				{
					root = (Parent) fxmlLoader.load(getClass().getResource("/view/Login.fxml").openStream());
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

				LoginController controller = fxmlLoader.getController();

				controller.setPageName(getManagerButton().getText());

				BoxOffice.stage.setScene(new javafx.scene.Scene(root, 600, 375));
			}
		});
	}

}
