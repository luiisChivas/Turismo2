package tourism.turismo.model;

public class CityId {

    private String city_id;
    private String user;

    public CityId(String city_id, String user) {
        this.city_id = city_id;
        this.user =user;
    }

    public CityId() {
    }

    public String getCity_id() {
        return city_id;
    }

    public String getUser() {
        return user;
    }
}
