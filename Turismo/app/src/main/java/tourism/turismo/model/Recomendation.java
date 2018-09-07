package tourism.turismo.model;

public class Recomendation {
    private String id;
    private String name;
    private String description;
    private String id_city;
    private String latitude;
    private String longitude;
    private String valoration;

    public Recomendation() {
    }

    public Recomendation(String id, String name, String description, String id_city,
                         String latitude, String longitude, String valoration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.id_city = id_city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.valoration = valoration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
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

    @Override
    public String toString() {
        return name;
    }
}
