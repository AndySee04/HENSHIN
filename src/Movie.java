// Movie.java
package Movie_App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Movie {
	// Instance variable
    private String name;
    private String genre;
    private String language;
    private int duration;
    private double price;
    private String description;
    private String date;
    private String time;
    
    private static final String movieFile = "Movie List.txt";

    // Getter methods
    public String getName() {
        return name;
    }
    public String getGenre() {
        return genre;
    }
    public String getLanguage() {
        return language;
    }
    public int getDuration() {
        return duration;
    }
    public double getPrice() {
        return price;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    
    // Setter methods
    public void setDate(int selectedDate) {
    	if (selectedDate == 1)
    		date = "13/05/2024 (Thu)";
    	if (selectedDate == 2)
    		date = "14/05/2024 (Fri)";
    }
    public void setTime(int selectedTime) {
    	if (selectedTime == 1)
    		time = "Morning (8:00 am)";
    	if (selectedTime == 2)
    		time = "Evening (4:00 pm)";
    }

    // Constructor
    public Movie(String name, String genre, String language, int duration, double price, String description, String date, String time) {
        this.name = name;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.price = price;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    // Function to print selected movie details
    @Override
    public String toString() {
        String[] descriptionLines = description.split("\n"); // Split the description into lines
        StringBuilder descriptionBuilder = new StringBuilder();

        for (String line : descriptionLines) {
            descriptionBuilder.append("\t").append(line.trim()).append("\n"); // Add each line with indentation
        }

        return  "Movie Name    : " + name + "\n" +
                "Genre         : " + genre + "\n" +
                "Language      : " + language + "\n" +
                "Duration      : " + duration + " minutes\n" +
                "Price(RM)     : " + price + "\n\n" +
                "Description:\n" + descriptionBuilder.toString(); // Append formatted description
    }
    
    public static ArrayList<Movie> getMovieList() {
        ArrayList<Movie> movieList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(movieFile))) {
            String line;
            StringBuilder movieData = new StringBuilder();

            while ((line = br.readLine()) != null) {
                if (!line.isBlank()) {
                    if (line.startsWith("[Movie")) {
                        if (movieData.length() > 0) {
                            Movie movie = parseMovie(movieData.toString());
                            if (movie != null) {
                                movieList.add(movie);
                            }
                            movieData.setLength(0); // Clear the StringBuilder for the next movie
                        }
                    }
                    movieData.append(line).append("\n");
                }
            }

            // Add the last movie
            if (movieData.length() > 0) {
                Movie movie = parseMovie(movieData.toString());
                if (movie != null) {
                    movieList.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return movieList;
    }
    
    private static Movie parseMovie(String data) {
        String[] lines = data.split("\n");
        String name = null, genre = null, language = null, description = null;
        int duration = 0;
        double price = 0.0;

        StringBuilder descriptionBuilder = new StringBuilder(); // StringBuilder to build the description

        for (String line : lines) {
            if (line.startsWith("Movie Name")) {
                name = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("Genre")) {
                genre = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("Language")) {
                language = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("Duration")) {
                duration = Integer.parseInt(line.substring(line.indexOf(":") + 1, line.indexOf(" minutes")).trim());
            } else if (line.startsWith("Price")) {
                price = Double.parseDouble(line.substring(line.indexOf("RM") + 2).trim());
            } else if (line.startsWith("Description")) {
                // Start reading description from the next line
                description = ""; // Initialize description
                continue;
            } else if (!line.startsWith("[")) {
                // Append non-metadata lines to the description
                descriptionBuilder.append(line.trim()).append("\n");
            }
        }

        // Set the description from the StringBuilder
        description = descriptionBuilder.toString().trim();

        // Check if any required field is missing
        if (name != null && genre != null && language != null && description != null && duration > 0 && price > 0) {
            // Return the constructed Movie object
            return new Movie(name, genre, language, duration, price, description, "", ""); // Empty strings for date and time
        } else {
            // Return null if any required field is missing
            return null;
        }
    }
}