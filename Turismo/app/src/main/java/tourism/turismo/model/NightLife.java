package tourism.turismo.model;

public class NightLife {

    private String night_life_id;
    private String name_night_life;
    private String description_night_life;
    private String id_city;
    private String latitude;
    private String longitude;
    private String valoration;

    public NightLife() {
    }

    public NightLife(String night_life_id, String name_night_life, String description_night_life,
                     String id_city, String latitude, String longitude, String valoration) {
        this.night_life_id = night_life_id;
        this.name_night_life = name_night_life;
        this.description_night_life = description_night_life;
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

    public String getNight_life_id() {
        return night_life_id;
    }

    public void setNight_life_id(String night_life_id) {
        this.night_life_id = night_life_id;
    }

    public String getName_night_life() {
        return name_night_life;
    }

    public void setName_night_life(String name_night_life) {
        this.name_night_life = name_night_life;
    }

    public String getDescription_night_life() {
        return description_night_life;
    }

    public void setDescription_night_life(String description_night_life) {
        this.description_night_life = description_night_life;
    }

    public String getId_city() {
        return id_city;
    }

    public void setId_city(String id_city) {
        this.id_city = id_city;
        }

    @Override
    public String toString() {
        return name_night_life;
    }
}
