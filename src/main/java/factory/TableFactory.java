package factory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.javafx.css.converters.StringConverter;

import controller.HomeController;
import lombok.Data;
import model.Person;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@Data
public class TableFactory
{
	private Pane updateContainer;
	private Button update = new Button("Update");
	private Button cancel = new Button("Cancel");
	private Field[] fields;
	private ArrayList<TextField> textFields = new ArrayList<TextField>();
	private Integer objectIndex;
	
	public TableFactory(Pane pane)
	{
		this.updateContainer = pane;
	}
	
	@SuppressWarnings("unchecked")
	public TableView<?> generateTable(ArrayList<?> d)
	{
		final TableView table = new TableView();
		table.setId("tableView");
		table.setMinWidth(600d);
	
		table.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				if(table.getFocusModel().getFocusedIndex()!=-1)
				{
					getTextFields().clear();
					getUpdateContainer().getChildren().clear();
					setFields(table.getFocusModel().getFocusedItem().getClass().getDeclaredFields());
					setObjectIndex(table.getFocusModel().getFocusedIndex());
					
					for (Field field : getFields())
					{
						
						TextField textField = new TextField();
						textField.setPromptText(field.getName());
						
						getTextFields().add(textField);
						
						if (!field.getName().equals("serialVersionUID"))
							getUpdateContainer().getChildren().add(textField);
						
					}
					
					getUpdateContainer().getChildren().add(getUpdate());
					getUpdateContainer().getChildren().add(getCancel());
		        }
			}
	
		});
		
		getUpdate().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				for (int i = 0; i < getFields().length; i++)
				{
					Field field = fields[i];
					try
					{
						if (!field.getName().equals("serialVersionUID"))
						{
							
							String capField = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
							table.getItems().get(getObjectIndex()).getClass().getMethod("set" + turkishToEnglishChars(capField) , field.getType()).invoke(table.getItems().get(getObjectIndex()), getTextFields().get(i).getText());
						}
					}
					catch (NoSuchMethodException e)
					{
						e.printStackTrace();
					}
					catch (SecurityException e)
					{
						e.printStackTrace();
					}
					catch (IllegalAccessException e)
					{
						e.printStackTrace();
					}
					catch (IllegalArgumentException e)
					{
						e.printStackTrace();
					}
					catch (InvocationTargetException e)
					{
						e.printStackTrace();
					}
				}
				
			}
		});
		
		getCancel().setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			public void handle(MouseEvent event)
			{
				getUpdateContainer().getChildren().clear();
			}
		});
		        
		ObservableList<?> data = FXCollections.observableArrayList(d);
		
		/** Getting super and base class fields. **/
		ArrayList<Field> fields = new ArrayList<Field>(Arrays.asList(d.get(0).getClass().getSuperclass().getDeclaredFields()));
		fields.addAll(Arrays.asList(d.get(0).getClass().getDeclaredFields()));
		
		/** Clear unnecessary fields. (serialVersionUID) **/
		for (int i = 0; i < fields.size(); i++)
		{
			if (fields.get(i).getName().equals("serialVersionUID"))
				fields.remove(i);
		}
		
		for (Field field : fields)
		{
			String capField = field.getName().substring(0,1).toUpperCase() + field.getName().substring(1);
			String typeName = field.getType().getSimpleName();
			
			
			TableColumn column = new TableColumn(capField);
			column.setCellValueFactory(new PropertyValueFactory(field.getName()));
			
//			Callback cellFactory = new Callback() 
//             {
//            
//
//				 public Object call(Object param)
//				 {
//					 return new EditingCell();
//				 }
//             };
			
//			if (typeName.equals("Integer"))
//				column.setCellFactory((Callback) new EditingIntegerCell()); // TextFieldTableCell.forTableColumn()
//			else if (typeName.equals("String"))
//				column.setCellFactory((Callback) new EditingStringCell());
//				column.setCellFactory(cellFactory); // ChoiceBoxTableCell.forTableColumn(FXCollections.observableArrayList(new Car(1900), new Car(1500)))
			
			
			
//			/** Change Event **/
//			column.setOnEditCommit(new EventHandler<CellEditEvent<?, ?>>() 
//			{
//				public void handle(CellEditEvent<?, ?> event)
//				{
//					String fieldName = event.getTableView().getColumns().get(event.getTablePosition().getColumn()).getText();
//					String methodName = "set" + fieldName;
//					Class type = null;
//					
//					try
//					{
//						type = event.getTableView().getItems().get(event.getTablePosition().getRow()).getClass().getDeclaredField(fieldName.toLowerCase()).getType().getClass();
//						Method method = event.getTableView().getItems().get(event.getTablePosition().getRow()).getClass().getMethod(methodName, event.getNewValue().getClass());
//						method.invoke(event.getTableView().getItems().get(event.getTablePosition().getRow()), event.getNewValue());
//					}
//					catch (NoSuchFieldException e)
//					{
//						e.printStackTrace();
//					}
//					catch (SecurityException e)
//					{
//						e.printStackTrace();
//					}
//					catch (NoSuchMethodException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalAccessException e)
//					{
//						e.printStackTrace();
//					}
//					catch (IllegalArgumentException e)
//					{
//						e.printStackTrace();
//					}
//					catch (InvocationTargetException e)
//					{
//						e.printStackTrace();
//					}
//				}
//		        
//		    });
			

			column.setPrefWidth(100d);
			
			table.getColumns().add(column);
		}
		
	
		
		table.setItems(data);
		
		return table;
	}
	
//	
//	class EditingIntegerCell extends TableCell<Person, Integer>
//	{
//		 
//		 @Override
//	        public void updateItem(Integer item, boolean empty) 
//		 	{
//	            super.updateItem(item, empty);
//	            setText(empty ? null : getString());
//	            setGraphic(null);
//	        }
//	 
//	        private String getString() 
//	        {
//	            return getItem() == null ? "" : getItem().toString();
//	        }
//	}
//	
//	class EditingStringCell extends TableCell<Person, String>
//	{
//		 
//		 @Override
//	        public void updateItem(String item, boolean empty) 
//		 	{
//	            super.updateItem(item, empty);
//	            setText(empty ? null : getString());
//	            setGraphic(null);
//	        }
//	 
//	        private String getString() 
//	        {
//	            return getItem() == null ? "" : getItem().toString();
//	        }
//	}
	
	public boolean isWrapper(Object obj)
	{
		String[] wrappers = new String[] {"Integer", "Short", "Byte", "Boolean", "Float", "Double", "Character", "Long", "String"};
		
		for (int i = 0; i < wrappers.length; i++)
		{
			System.out.println(obj.getClass().getSimpleName());
			if (obj.getClass().getTypeName().equals(wrappers[i]))
				return true;
		}
		
		return false;
	}
	
	public String turkishToEnglishChars(String text)
	{
		String[] turkish = new String[] {"ç", "Ç", "ğ", "Ğ", "İ", "ı", "ö", "Ö", "Ü", "ü", "ş", "Ş"};
		String[] english = new String[] {"c", "C", "g", "G", "I", "i", "o", "O", "U", "u", "s", "S"};
		
		for (int i = 0; i < turkish.length; i++)
			text = text.replace(turkish[i], english[i]);
		
		return text;
		
	}
}
