package pl.cinema.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Movie")
public class Movie{
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private String name;
    private String premiere;
    private String duration;
    private String subtitles;
    private String td;
    private String director;
    private String type;
    private String production;
    private double rating;
    private int numberofvotes;

    public String getType() {return type;}
    public void setType(String type) {this.type = type;}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPremiere() {
        return premiere;
    }
    public void setPremiere(String premiere) {
        this.premiere = premiere;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getSubtitles() {
        return subtitles;
    }
    public void setSubtitles(String subtitles) {
        this.subtitles = subtitles;
    }
    public String getTd() {return td;}
    public void setTd(String td) {
        this.td = td;
    }
    public String getDirector() {
        return director;
    }
    public void setDirector(String director) {
        this.director = director;
    }
    public String getProduction() {
        return production;
    }
    public void setProduction(String production) {
        this.production = production;
    }
    public void setRating(double rating){this.rating=rating;}
    public double getRating(){return rating;}
    public int getNumberofvotes() {return numberofvotes;}
    public void setNumberofvotes(int numberofvotes) {this.numberofvotes = numberofvotes;}
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        result = prime * result + (int)rating;
        result = prime * result + numberofvotes;
        result = prime * result + ((subtitles == null) ? 0 : subtitles.hashCode());
        result = prime * result + ((td == null) ? 0 : td.hashCode());
        result = prime * result + ((production == null) ? 0 : production.hashCode());
        result = prime * result + ((premiere == null) ? 0 : premiere.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (director == null) {
            if (other.director != null)
                return false;
        } else if (!director.equals(other.director))
            return false;
        if (rating != other.rating)
            return false;
        if (numberofvotes != other.numberofvotes)
            return false;
        if (premiere == null) {
            if (other.premiere != null)
                return false;
        } else if (!premiere.equals(other.premiere))
            return false;
        if (subtitles == null) {
            if (other.subtitles != null)
                return false;
        } else if (!subtitles.equals(other.subtitles))
            return false;
        if (td == null) {
            if (other.td != null)
                return false;
        } else if (!td.equals(other.td))
            return false;
        if (production == null) {
            if (other.production != null)
                return false;
        } else if (!production.equals(other.production))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", premiere='" + premiere + '\'' +
                ", duration='" + duration + '\'' +
                ", subtitles='" + subtitles + '\'' +
                ", td='" + td + '\'' +
                ", director=" + director +
                ", production='" + production + '\'' +
                ", rating=" + rating +
                ", numberofvotes=" + numberofvotes +
                '}';
    }
}