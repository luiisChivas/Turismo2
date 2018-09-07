package tourism.turismo.list;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import tourism.turismo.InfoPubRestaurantActivity;
import tourism.turismo.PubRestaurantActivity;
import tourism.turismo.R;
import tourism.turismo.model.PubRestaurant;


public class PubRestaurantList extends ArrayAdapter<PubRestaurant> {

    private Activity context;
    private List<PubRestaurant> pubs_restaurants;
    private DatabaseReference database_reference;
    private EditText name_pub_restaurant;
    private EditText description_pub_restaurant;
    private String user;
    private String id_city;
    private EditText edt_latitude;
    private EditText edt_longitude;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS ATTRACTION ACTIVITY FOR CREATE THE LIST
     * @param context
     * @param pubs_restaurants
     * @param database_reference
     * @param name_pub_restaurant
     * @param description_pub_restaurant
     * @param user
     * @param id_city
     */
    public PubRestaurantList(@NonNull Activity context, List<PubRestaurant> pubs_restaurants,
                             DatabaseReference database_reference, EditText name_pub_restaurant,
                             EditText description_pub_restaurant, String user, String id_city,
                             EditText latitude, EditText longitude) {
        super(context, R.layout.layout_pub_restaurant_list, pubs_restaurants);
        this.context = context;
        this.database_reference = database_reference;
        this.pubs_restaurants = pubs_restaurants;
        this.name_pub_restaurant = name_pub_restaurant;
        this.description_pub_restaurant = description_pub_restaurant;
        this.user = user;
        this.id_city = id_city;
        this.edt_latitude = latitude;
        this.edt_longitude = longitude;
    }

    /**
     *  VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF RESTAURANTS
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View list_view_item;

        if(user.equals("luis@gmail.com")) {
            list_view_item = inflater.inflate(R.layout.layout_pub_restaurant_list,
                                             null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_pub_restaurant);
            Button btn_update = (Button) list_view_item.findViewById(R.id.btn_update_pub_restaurant);
            Button btn_delete = (Button) list_view_item.findViewById(R.id.btn_delete_pub_restaurant);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_pub_restaurant);

            final PubRestaurant pub_restaurant = pubs_restaurants.get(position);
            if(pub_restaurant.getId_city().equals(id_city)) {
                txt_name.setText(pub_restaurant.getName_pub_restaurant());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }
            txt_name.setText(pub_restaurant.getName_pub_restaurant());

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database_reference.child(pub_restaurant.getId_pub_restaurant()).removeValue();
                    Toast.makeText(context, "Restaurante eliminado correctamente!!",
                                  Toast.LENGTH_SHORT).show();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name_pub_restaurant.setText(pub_restaurant.getName_pub_restaurant());
                    description_pub_restaurant.setText(pub_restaurant.getDescription_pub_restaurant());
                    edt_longitude.setText(pub_restaurant.getLongitude());
                    edt_latitude.setText(pub_restaurant.getLatitude());
                    PubRestaurantActivity.valoration = pub_restaurant.getValoration();
                    PubRestaurantActivity.pub_restaurant_id = pub_restaurant.getId_pub_restaurant();
                }
            });
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoPubRestaurantActivity.class);
                    for (int i = 0; i < pubs_restaurants.size(); i++) {
                        if(i == position){
                            PubRestaurant pub_restaurant = pubs_restaurants.get(i);
                            String id_pub_restaurant = pub_restaurant.getId_pub_restaurant();
                            String pub_restaurant_name = pub_restaurant.getName_pub_restaurant();
                            String pub_restaurant_description= pub_restaurant
                                                               .getDescription_pub_restaurant();
                            String latitude_place = pub_restaurant.getLatitude();
                            String longitude_place = pub_restaurant.getLongitude();
                            intent.putExtra("name", pub_restaurant_name);
                            intent.putExtra("description", pub_restaurant_description);
                            intent.putExtra("id", id_pub_restaurant);
                            intent.putExtra("id_city", id_city);
                            intent.putExtra("latitude", latitude_place);
                            intent.putExtra("longitude", longitude_place);
                            intent.putExtra("user", user);
                        }
                    }
                    getContext().startActivity(intent);
                    context.finish();
                }
            });
        }else{
            list_view_item = inflater.inflate(R.layout.layout_pub_restaurant_list2,
                                             null, true);
            TextView txt_name = (TextView)list_view_item.findViewById(R.id.txt_name_pub_restaurant_2);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_pub_restaurant_2);


            final PubRestaurant pub_restaurant = pubs_restaurants.get(position);
            if(pub_restaurant.getId_city().equals(id_city)) {
                txt_name.setText(pub_restaurant.getName_pub_restaurant());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }

            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoPubRestaurantActivity.class);
                    for (int i = 0; i < pubs_restaurants.size(); i++) {
                        if(i == position){
                            PubRestaurant pub_restaurant = pubs_restaurants.get(i);
                            String id_pub_restaurant = pub_restaurant.getId_pub_restaurant();
                            String pub_restaurant_name = pub_restaurant.getName_pub_restaurant();
                            String pub_restaurant_description= pub_restaurant
                                                               .getDescription_pub_restaurant();
                            String latitude_place = pub_restaurant.getLatitude();
                            String longitude_place = pub_restaurant.getLongitude();
                            intent.putExtra("name", pub_restaurant_name);
                            intent.putExtra("description", pub_restaurant_description);
                            intent.putExtra("id", id_pub_restaurant);
                            intent.putExtra("id_city", id_city);
                            intent.putExtra("latitude", latitude_place);
                            intent.putExtra("longitude", longitude_place);
                            intent.putExtra("user", user);
                        }
                    }
                    getContext().startActivity(intent);
                    context.finish();
                }
            });
        }
        return list_view_item;
    }
}
