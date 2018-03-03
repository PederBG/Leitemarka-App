package tdt4140.gr1875.app.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXDrawer.DrawerDirection;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AthleteMainScreenController implements Initializable{

	private JFXDrawer drawer;
	
	@FXML
	private JFXHamburger hamburger;
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private BorderPane borderPane;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initDrawer();
		/*
		try {
			
			FXMLLoader ViewAthletesLoader = new FXMLLoader(getClass().getResource("ViewAthletes.fxml"));
			ViewAthletesLoader.load();
			ViewAthletesController VAC = ViewAthletesLoader.getController();
			
			FXMLLoader ToolbarLoader = new FXMLLoader(getClass().getResource("AthleteToolbar.fxml"));
			ToolbarLoader.load();
			AthleteToolbarController TBC = ToolbarLoader.getController();
		
			
			TBC.addListener(VAC);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	
	private void initDrawer() {
		drawer = new JFXDrawer();
		drawer.setDirection(DrawerDirection.RIGHT);
		drawer.setDefaultDrawerSize(100);
		VBox toolbar;
		try {
			toolbar = FXMLLoader.load(getClass().getResource("AthleteToolbar.fxml"));
			drawer.setSidePane(toolbar);
			
			HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
			task.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event e) -> {
					task.setRate(task.getRate() * -1);
					task.play();
					
					if (drawer.isShown()) {
						borderPane.setRight(null);
						//drawer.setVisible(true);
						drawer.close();
					}
					else {
						borderPane.setRight(drawer);
						//drawer.setVisible(false);
						drawer.open();;
						
					}
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
}