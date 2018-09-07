package tourism.turismo.model;

public class PubRestaurant {

    private String id_pub_restaurant;
    private String name_pub_restaurant;
    private String description_pub_restaurant;
    private String id_city;
    private String latitude;
    private String longitude;
    private String valoration;


    public PubRestaurant() {
    }

    public PubRestaurant(String id_pub_restaurant, String name_pub_restaurant,
                         String description_pub_restaurant, String id_city, String latitude,
                         String longitude, String valoration) {
        this.id_pub_restaurant = id_pub_restaurant;
        this.name_pub_restaurant = name_pub_restaurant;
        this.description_pub_restaurant = description_pub_restaurant;
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

    public String getId_pub_restaurant() {
        return id_pub_restaurant;
    }

    public void setId_pub_restaurant(String id_pub_restaurant) {
        this.id_pub_restaurant = id_pub_restaurant;
    }

    public String getName_pub_restaurant() {
        return name_pub_restaurant;
    }

    public void setName_pub_restaurant(String name_pub_restaurant) {
        this.name_pub_restaurant = name_pub_restaurant;
    }

    public String getDescription_pub_restaurant() {
        return description_pub_restaurant;
    }

    public void setDescription_pub_restaurant(String description_pub_restaurant) {
        this.description_pub_restaurant = description_pub_restaurant;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
    }

    @Override
    public String toString() {
        return name_pub_restaurant;
    }
}
