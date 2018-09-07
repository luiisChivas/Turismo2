package tourism.turismo.model;

public class Attraction {

    private String attraction_id;
    private String name_attraction;
    private String description_attraction;
    private String id_city;
    private String latitude;
    private String longitude;
    private String valoration;

    public Attraction() {
    }

    public Attraction(String attraction_id, String name_attraction, String description_attraction,
                      String id_city, String latitude, String longitude, String valoration) {
        this.attraction_id = attraction_id;
        this.name_attraction = name_attraction;
        this.description_attraction = description_attraction;
        this.id_city = id_city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.valoration = valoration;
    }


    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getValoration() {
        return valoration;
    }

    public void setValoration(String valoration) {
        this.valoration = valoration;
    }

    public String getAttraction_id() {
        return attraction_id;
    }

    public void setAttraction_id(String attraction_id) {
        this.attraction_id = attraction_id;
    }

    public String getName_attraction() {
        return name_attraction;
    }

    public void setName_attraction(String name_attraction) {
        this.name_attraction = name_attraction;
    }

    public String getDescription_attraction() {
        return description_attraction;
    }

    public void setDescription_attraction(String description_attraction) {
        this.description_attraction = description_attraction;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    @Override
    public String toString() {
        return name_attraction;
    }
}
