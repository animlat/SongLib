package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Collections;



public class SongLibController {
	
    @FXML
    private ListView<String> listView;
	
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
    
    @FXML
    private Label songdetails;
    
    @FXML
    private Label songdetails2;
    
    @FXML
    private Label songdetails3;
    
    @FXML
    private Label songdetails4;
    
	
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
		
		// select the first item
	    listView.getSelectionModel().select(0);
	    editSongWindowUpdate(listView.getSelectionModel().getSelectedItem());
	    
	      listView
	        .getSelectionModel()
	        .selectedIndexProperty()
	        .addListener(
	           (obs, oldVal, newVal) -> 
	               selectsong());
	    
    }

    
    
    
	private void selectsong() {
		String item = listView.getSelectionModel().getSelectedItem();
		int index = listView.getSelectionModel().getSelectedIndex();

		String content = "Index: " + 
		          listView.getSelectionModel()
		                   .getSelectedIndex() + 
		          "\nValue: " + 
		          listView.getSelectionModel()
		                   .getSelectedItem();
		 //System.out.println(content);
		 //call a function that changes edit a song window
		 editSongWindowUpdate(item);
	}
	
	private void editSongWindowUpdate(String songstring) {
        String[] data = songstring.split("-",4);
        editsongName.setText(data[0].trim());
		editartist.setText(data[1].trim());
		editalbum.setText(data[2].trim());
		edityear.setText(data[3].trim());
		
		songdetails.setText("Song: "+data[0]);
		songdetails2.setText("Artist: "+data[1]);
		songdetails3.setText("Album: "+data[2]);
		songdetails4.setText("Year: "+data[3]);
	}
    
    
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
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
    }
    
    
    //writes sorted song library to songlist.txt so there is persistence
    public static void writeToText(ArrayList<SongArtist> librarylist) {
    	
    	//delete txt and write new one
    	File fold = new File("src/view/songlist.txt");
    	fold.delete();
    	
    	File fnew = new File("src/view/songlist.txt");
    	
    	try {
    		FileWriter f2 = new FileWriter(fnew,false);

    		//convert list of objects to string list in the songlist.txt format and write to songlist.txt
    		ArrayList<String> textlist = objectToText(librarylist);
    		int n = textlist.size();
    			
    		for(int i=0;i<n;i++) {
    			f2.write(textlist.get(i).toString()+"\n");
    		}
    		f2.close();
    	}catch(Exception e){
    		System.err.println(e);
    	}
    }
    
    //convert object list to string list
    public static ArrayList<String> objectToList(ArrayList<SongArtist> librarylist){
    	
    	int n = librarylist.size();
    	ArrayList<String> listlist = new ArrayList<String>();
    	
    	for(int i=0;i<n;i++) {
    		listlist.add(librarylist.get(i).toString());
    	}
    	
    	//System.out.println(listlist);
    	return listlist;
    	
    }
    
    //convert object list to string list
    public static ArrayList<String> objectToText(ArrayList<SongArtist> librarylist){
    	
    	int n = librarylist.size();
    	ArrayList<String> listlist = new ArrayList<String>();
    	
    	for(int i=0;i<n;i++) {
    		listlist.add(librarylist.get(i).toText());
    	}
    	
    	//System.out.println(listlist);
    	return listlist;
    	
    }    

    //adding a SongArtist to obsList
    public boolean add(SongArtist toAdd) {
    	//not sure if check for null means anything addsongName.getText().equals(null) || addartist.getText().equals(null) || 
    	if(addsongName.getText().equals("") || addartist.getText().equals("")) {
    		return false;
    	}
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(toAdd.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(toAdd.artist.toLowerCase())) {
        		return false;
    		}
    	}  	
    	
    	librarylist.add(toAdd);
        Collections.sort(librarylist);   
   		obsList = FXCollections.observableArrayList(objectToList(librarylist)); 
  		listView.setItems(obsList); 
  		//select added song
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(toAdd.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(toAdd.artist.toLowerCase())) {
    		    listView.getSelectionModel().select(i);
    		    return true;
    		}
    	}
    	return true;
    }
    
    public boolean delete(SongArtist toDelete) {
    	//find toDelete in librarylist
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(toDelete.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(toDelete.artist.toLowerCase())) {
        		librarylist.remove(i);
                Collections.sort(librarylist);   
           		obsList = FXCollections.observableArrayList(objectToList(librarylist)); 
          		listView.setItems(obsList);       
           		writeToText(librarylist);
        		return true;
    		}
    	}
    	
    	//should never reach here, toDelete always exists in librarylist bc the user picks it from the list.
    	//we need to take into account if the user hits delete without selecting a song
    	return false;
    }
    
    //creates an error pop-up, fill with errormessage String
    void errorpopup(String errormessage) {
    	Alert a = new Alert(AlertType.ERROR);
    	a.setTitle("Error");
    	a.setContentText(errormessage);
    	a.showAndWait();
    }
    
	// add button click - confirm with pop up!
	@FXML
    void addsong(ActionEvent event) {
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to ADD this song?",ButtonType.YES,ButtonType.CANCEL);
		//a.setHeaderText(null);
		a.setTitle("Confirm Add Song");
		a.showAndWait();
		if(a.getResult() == ButtonType.YES) {
			
			//get songName, artist, album, year
			//create new songArtist
			//add to listlibrary and update obList
			
	    	SongArtist toadd = new SongArtist(addsongName.getText(), addartist.getText(), addalbum.getText(), addyear.getText());
	    	//System.out.println(toadd);
	    	if(!add(toadd)) {
	    		//send out an error message
	    		errorpopup("This song and artist already exists, or you did not enter anything into the field. Please fix and try again!");
	    		System.out.println("EITHER SONG ARTIST TEXT FIELD IS NULL OR SONG ARTIST ALREADY EXISTS");
	    	}
	    	
	    	//reset textboxes
	    	addsongName.clear();
	    	addartist.clear();
	    	addalbum.clear();
	    	addyear.clear();
	    	
	    	writeToText(librarylist);
		}
		
    }
	
	
	@FXML
	void deletesong(ActionEvent event) {
		//are you sure popup?
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to DELETE this song?",ButtonType.YES,ButtonType.CANCEL);
		a.setTitle("Confirm Delete Song");
		a.showAndWait();
		if(a.getResult() == ButtonType.YES) {
			
			int index = listView.getSelectionModel().getSelectedIndex();
			if(index==-1) 
				return;

			//call delete
			delete(librarylist.get(index));
			
			//select the song after the deleted song, if there is no song after, select the song before.
			if(index==librarylist.size()) {
				listView.getSelectionModel().select(index-1);
			}else {
				listView.getSelectionModel().select(index);
			}
			
		}
		
	}
	
	
	@FXML
	void editsong(ActionEvent event) {
		//are you sure popup?
		Alert a = new Alert(AlertType.CONFIRMATION, "Are you sure you want to EDIT this song?",ButtonType.YES,ButtonType.CANCEL);
		a.setTitle("Confirm Edit Song");
		a.showAndWait();
		if(a.getResult() == ButtonType.YES) {
			
			//ALL THE CODE TO EDIT THIS SONG GOES HERE
			int index = listView.getSelectionModel().getSelectedIndex();
	    	SongArtist editedsong = new SongArtist(editsongName.getText(), editartist.getText(), editalbum.getText(), edityear.getText());
	    	
	    	if(!edit(editedsong,index)) {
	    		Alert b = new Alert(AlertType.ERROR,"Song already exists",ButtonType.OK);
	    		b.setTitle("Confirm Edit Song");
	    		b.showAndWait();
	    	}
	    	writeToText(librarylist);
		}
		
	}
	
	
	
	//to edit something, i have to check if its being edited into something that already exists
	//to do so, save unedited object, remove from librarylist, check if something else matches edited
	// if it does, then put the original song back and reprot a failure
	// also check to see if the song was edited at all, and if not give an erros and say nothing changed?
    public boolean edit(SongArtist edited,int originalindex) {
    	//not sure if check for null means anything addsongName.getText().equals(null) || addartist.getText().equals(null) || 
    	if(editsongName.getText().equals("") || editartist.getText().equals("")) {
    		return false;
    	}
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(edited.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(edited.artist.toLowerCase())&&i!=originalindex) {
        		return false;
    		}
    	}  	
    	
    	librarylist.remove(originalindex);
    	librarylist.add(edited);
        Collections.sort(librarylist);   
   		obsList = FXCollections.observableArrayList(objectToList(librarylist)); 
  		listView.setItems(obsList); 
  		//select added song
    	for(int i=0;i<librarylist.size();i++) {
    		if(librarylist.get(i).song.toLowerCase().equals(edited.song.toLowerCase())&&librarylist.get(i).artist.toLowerCase().equals(edited.artist.toLowerCase())) {
    		    listView.getSelectionModel().select(i);
    		    return true;
    		}
    	}
    	return true;
    }
	

	
}
