package view;

public class SongArtist implements Comparable<SongArtist>{
	public String song;
	public String artist;
	public String album;
	public String year;
	
	public SongArtist(String song, String artist, String album, String year) {
		this.song=song;
		this.artist=artist;
		this.album=album;
		this.year=year;
	}

	public String getSong() {
		return song;
	}

	public void setSong(String song) {
		this.song = song;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	

	@Override
	public int compareTo(SongArtist o) {
		int compare = song.toLowerCase().compareTo(o.song.toLowerCase());
		if(compare == 0) {
			compare = artist.toLowerCase().compareTo(o.artist.toLowerCase());
		}
		return compare;
	}
	
<<<<<<< HEAD
=======
	
>>>>>>> c62e2a23264ad567d47df2e82eb8496481a84d50
	public String toString() {
		return song+ " - " +artist+ " - " +album+ " - " +year;
	}
	
	public String toText() {
		return song+ "*" +artist+ "*" +album+ "*" +year;
	}
	
}

	

