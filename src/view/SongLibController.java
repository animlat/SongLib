package view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
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
		 System.out.println(content);
		 //call a function that changes edit a song window
		 editSongWindowUpdate(item);
	}
	
	private void editSongWindowUpdate(String songdetails) {
		editsongName.setText(songdetails);
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
    	
    	writeToText(librarylist);
    }
	
	
	@FXML
	void deletebutton(ActionEvent event) {
		//are you sure popup?x
		
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
