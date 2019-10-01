package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
<<<<<<< HEAD
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;

=======
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

<<<<<<< HEAD
=======
import javafx.collections.FXCollections;
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50


public class SongLibController {
	
    @FXML
    private ListView<String> listView;
<<<<<<< HEAD
	
	@FXML
	private TextField addsongName;
	
	@FXML
	private TextField addartist;
	
	@FXML
	private TextField addalbum;
	
	@FXML
	private TextField addyear;
	
    @FXML
    private TextField editsongName;

    @FXML
    private TextField editartist;

    @FXML
    private TextField editalbum;

    @FXML
    private TextField edityear;

	
    private ObservableList<String> obsList;
    ArrayList<SongArtist> librarylist = new ArrayList<SongArtist>();
    
    ArrayList<String> stringlist = new ArrayList<String>();

    
    
    
    
    
    public void initialize(){        
        
        //read from songlist.txt and create array of SongArtist objects, also creates according array of String for listview
        readSongArtist(librarylist,stringlist);
        
		// create an ObservableList 
		// from an ArrayList  
		obsList = FXCollections.observableArrayList(stringlist); 

		listView.setItems(obsList); 
		
=======

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
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
		// select the first item
	    listView.getSelectionModel().select(0);
	    
    }
<<<<<<< HEAD

    
    
    
    
    
    //reads from file and populates librarylist!
    public static void readSongArtist(ArrayList<SongArtist> librarylist, ArrayList<String> stringlist) {
    	//read from text file
        try {
            Scanner s = new Scanner(new File("src/view/songlist.txt"));
            
            while (s.hasNextLine()) {

            	String thisline = s.nextLine();
            	String[] arrOfStr = thisline.split("\\*",4);
            	String thissong = arrOfStr[0];
            	String thisartist = arrOfStr[1];
            	String thisalbum = arrOfStr[2];
            	String thisyear = arrOfStr[3];
            	stringlist.add(thissong+" - "+thisartist+" - "+thisalbum+" - "+thisyear);
            	
            	librarylist.add(new SongArtist(thissong,thisartist,thisalbum,thisyear));
            	
            }
            s.close();
            System.out.println(librarylist);
=======
    
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
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
<<<<<<< HEAD
    //convert object list to string list
=======
    public void sortAndPrint(ArrayList<String> songlist) {
    	Collections.sort(songlist);
    	System.out.println(songlist);
    }
    
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
    public ArrayList<String> objectToList(ArrayList<SongArtist> librarylist){
    	
    	int n = librarylist.size();
    	ArrayList<String> listlist = new ArrayList<String>();
    	
    	for(int i=0;i<n;i++) {
    		listlist.add(librarylist.get(i).toString());
    	}
    	
    	//System.out.println(listlist);
    	return listlist;
    	
<<<<<<< HEAD
    }    

    //adding a SongArtist to obsList
    public boolean add(SongArtist toAdd) {
    	//not sure if check for null means anything addsongName.getText().equals(null) || addartist.getText().equals(null) || 
    	if(addsongName.getText().equals("") || addartist.getText().equals("")) {
    		return false;
    	}
=======
    }
    
    //adding a SongArtist to obsList
    public boolean add(SongArtist toAdd) {
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
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
<<<<<<< HEAD
    
	// Button was clicked, do something…
	//get songName, artist, album, year
	//create new songArtist
	//add to listlibrary and update obList
	@FXML
    void addbutton(ActionEvent event) {

    	SongArtist toadd = new SongArtist(addsongName.getText(), addartist.getText(), addalbum.getText(), addyear.getText());
    	System.out.println(toadd);
    	if(!add(toadd)) {
    		//send out an error message
    		System.out.println("EITHER SONG ARTIST TEXT FIELD IS NULL OR SONG ARTIST ALREADY EXISTS");
    	}
    	
    	//reset textboxes
    	addsongName.clear();
    	addartist.clear();
    	addalbum.clear();
    	addyear.clear();
    }
	
	
	@FXML
	void deletebutton(ActionEvent event) {

	}
=======
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50

	
}
