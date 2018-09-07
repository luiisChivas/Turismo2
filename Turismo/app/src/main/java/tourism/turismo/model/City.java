package tourism.turismo.model;

public class City {

    private String city_id;
    private String name;

    public City() {

    }

    public City(String city_id, String name) {
        this.city_id = city_id;
        this.name = name;
    }
    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return name;
    }
}
