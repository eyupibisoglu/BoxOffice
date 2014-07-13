package model;
import javafx.scene.image.Image;
import lombok.Data;

@Data
public class Movie implements java.io.Serializable
{
	private static final long	serialVersionUID	= 2572008009938701580L;
	private String name;
	private String imagePath;
	
	public Movie(String name, String imagePath)
	{
		this.name = name;
		this.imagePath = imagePath;
	}
	
	public Image getImage()
	{
		return new Image(getImagePath());
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}
