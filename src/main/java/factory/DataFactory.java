package factory;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class DataFactory
{	
	public static List<?> getData(String path)
	{
		List<?> dataHolder = null;
		
		try
		{			
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path));
			dataHolder = (ArrayList<?>) reader.readObject();
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

		return dataHolder;
	}
	
	public static void addData(String path, List<?> data)
	{
		try
		{
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path));
			writer.writeObject(data);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void appendData(String path, List<?> data)
	{
		try
		{
			ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(path, true));
			writer.writeObject(data);
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
