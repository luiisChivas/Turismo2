package tourism.turismo.model;

public class TourismPlace {
    private String tourism_id;
    private String name_place;
    private String description_place;
    private String city_id;
    private String latitude;
    private String longitude;
    private String valoration;

    public TourismPlace() {
    }

    public TourismPlace(String tourism_id, String name_place, String description_place,
                        String city_id, String latitude, String longitude, String valoration) {
        this.tourism_id = tourism_id;
        this.name_place = name_place;
        this.description_place = description_place;
        this.city_id = city_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.valoration = valoration;
    }

    public String getValoration() {
        return valoration;
    }

    public void setValoration(String valoration) {
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

    public String getTourism_id() {
        return tourism_id;
    }

    public void setTourism_id(String tourism_id) {
        this.tourism_id = tourism_id;
    }

    public String getName_place() {
        return name_place;
    }

    public void setName_place(String name_place) {
        this.name_place = name_place;
    }

    public String getDescription_place() {
        return description_place;
    }

    public void setDescription_place(String description_place) {
        this.description_place = description_place;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    @Override
    public String toString() {
        return name_place;
    }
}
