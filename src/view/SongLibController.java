package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

import javafx.collections.FXCollections;


public class SongLibController {
	
    @FXML
    private ListView<String> listView;

	@FXML
    private Label label;	
	
	@FXML
	private TextField songName;
	
	@FXML
	private TextField artist;
	
	@FXML
	private TextField album;
	
	@FXML
	private TextField year;
	
    private ObservableList<String> obsList;
    
    static ArrayList<String> stringlist = new ArrayList<String>();
    static ArrayList<SongArtist> librarylist = new ArrayList<SongArtist>();

    public void initialize(){
        //sets label
    	String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        //label.setText("Hello, JavaFX " + javafxVersion + "\nRunning on Java " + javaVersion + "\nThis is my Song Library.");
        
        //read from songlist.txt to get songs; this is how persistence is handled
        //readFromFile(listlist);
        //sortAndPrint(listlist);
        
        //way to sort objects, need to figure out how to read "object" from txt file, sort only needs to be run on "add" and "edit"
        librarylist.add(new SongArtist("Sleep On It","Galant","Album1","1990"));
        librarylist.add(new SongArtist("Honesty","PinkSweat","PinkAlbum","1997"));
        librarylist.add(new SongArtist("Kill This Love","Blackpink","Blackalbum","2013"));
        Collections.sort(librarylist);
        System.out.println(librarylist);                
        
        //move from Object ArrayList to String ArrayList to display in List View
        stringlist = objectToList(librarylist);
        
		// create an ObservableList 
		// from an ArrayList  
		obsList = FXCollections.observableArrayList(objectToList(librarylist)); 

		listView.setItems(obsList); 
		label.setText(librarylist.get(0).toString());
		// select the first item
	    listView.getSelectionModel().select(0);
	    
    }
    
    @FXML
    public void handleButtonAction() {
        // Button was clicked, do something…
    	//get songName, artist, album, year
    	//create new songArtist
    	//add to listlibrary and update obList

    	SongArtist toadd = new SongArtist(songName.getText(), artist.getText(), album.getText(), year.getText());
    	System.out.println(toadd);
    	if(!add(toadd)) {
    		//send out an error message
    	}
    	
    	//reset textboxes
    	songName.clear();
    	artist.clear();
    	album.clear();
    	year.clear();
    	
    }
    
    public void readFromFile(ArrayList<String> songlist) {
    	//read from text file
        try {
            Scanner s = new Scanner(new File("src/view/songlist.txt"));
            while (s.hasNextLine()) {
            	songlist.add(s.nextLine());
            }
            s.close();
            //System.out.println(songlist);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    public void sortAndPrint(ArrayList<String> songlist) {
    	Collections.sort(songlist);
    	System.out.println(songlist);
    }
    
    public ArrayList<String> objectToList(ArrayList<SongArtist> librarylist){
    	
    	int n = librarylist.size();
    	ArrayList<String> listlist = new ArrayList<String>();
    	
    	for(int i=0;i<n;i++) {
    		listlist.add(librarylist.get(i).toString());
    	}
    	
    	//System.out.println(listlist);
    	return listlist;
    	
    }
    
    //adding a SongArtist to obsList
    public boolean add(SongArtist toAdd) {
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(toAdd.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(toAdd.artist.toLowerCase())) {
        		return false;
    		}
    	}  	
    	
    	librarylist.add(toAdd);
        Collections.sort(librarylist);   
   		obsList = FXCollections.observableArrayList(objectToList(librarylist)); 
  		listView.setItems(obsList); 
        return true;
    	
    }

	
}
