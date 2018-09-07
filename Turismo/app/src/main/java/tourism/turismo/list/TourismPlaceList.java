package tourism.turismo.list;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
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

import tourism.turismo.InfoTourismPlaceActivity;
import tourism.turismo.R;
import tourism.turismo.TourismActivity;
import tourism.turismo.model.TourismPlace;

public class TourismPlaceList extends ArrayAdapter<TourismPlace>{

    private Activity context;
    private List<TourismPlace> tourisms;
    private DatabaseReference databaseReference;
    private EditText edt_name;
    private EditText edt_description;
    private String user;
    private String id_city;
    private EditText edt_latitude;
    private EditText edt_longitude;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS TOURISM PLACE ACTIVITY FOR CREATE A LIST
     * @param context
     * @param tourism_places
     * @param databaseReference
     * @param edt_name_place
     * @param edt_description_place
     * @param user
     * @param id_city
     * @param latitude
     * @param longitude
     */
    public TourismPlaceList(@NonNull Activity context, List<TourismPlace> tourism_places,
                            DatabaseReference databaseReference, EditText edt_name_place,
                            EditText edt_description_place, String user, String id_city,
                            EditText latitude, EditText longitude) {
        super(context, R.layout.layout_tourism_list, tourism_places);
        this.context = context;
        this.tourisms = tourism_places;
        this.databaseReference = databaseReference;
        this.edt_name = edt_name_place;
        this.edt_description = edt_description_place;
        this.user = user;
        this.id_city = id_city;
        this.edt_latitude = latitude;
        this.edt_longitude = longitude;
    }


    /**
     * VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF TOURISM PLACE
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem;
        if(user.equals("luis@gmail.com")){
            listViewItem = inflater.inflate(R.layout.layout_tourism_list,
                                           null, true);
            TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name_tourism);
            Button btn_delete = (Button) listViewItem.findViewById(R.id.btn_delete_tourism);
            Button btn_update = (Button) listViewItem.findViewById(R.id.btn_update_tourism);
            Button btn_go = (Button) listViewItem.findViewById(R.id.btn_go_tourism);
            final TourismPlace tourism_place = tourisms.get(position);
            if(tourism_place.getCity_id().equals(id_city)) {
                txt_name.setText(tourism_place.getName_place());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(tourism_place.getTourism_id()).removeValue();
                    Toast.makeText(context, "Lugar de turismo eliminado correctamente",
                                  Toast.LENGTH_SHORT).show();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edt_name.setText(tourism_place.getName_place());
                    edt_description.setText(tourism_place.getDescription_place());
                    edt_longitude.setText(tourism_place.getLongitude());
                    edt_latitude.setText(tourism_place.getLatitude());
                    TourismActivity.tourism_place_id = tourism_place.getTourism_id();
                    TourismActivity.valoration = tourism_place.getValoration();
                }
            });
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoTourismPlaceActivity.class);
                    for (int i = 0; i < tourisms.size(); i++) {
                        if(i == position){
                            TourismPlace tourism_place = tourisms.get(i);
                            String id_tourism_place = tourism_place.getTourism_id();
                            String name_place = tourism_place.getName_place();
                            String description_place = tourism_place.getDescription_place();
                            String latitude_place = tourism_place.getLatitude();
                            String longitude_place = tourism_place.getLongitude();
                            intent.putExtra("name", name_place);
                            intent.putExtra("description", description_place);
                            intent.putExtra("id", id_tourism_place);
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
            listViewItem = inflater.inflate(R.layout.layout_tourism_list2,
                                            null, true);
            TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name_tourism_2);
            Button btn_ir = (Button) listViewItem.findViewById(R.id.btn_go_tourism_2);
            final TourismPlace tourism_place = tourisms.get(position);
            if(tourism_place.getCity_id().equals(id_city)) {
                txt_name.setText(tourism_place.getName_place());
            }else{
                txt_name.setVisibility(View.GONE);;
                btn_ir.setVisibility(View.GONE);
            }

            btn_ir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoTourismPlaceActivity.class);
                    for (int i = 0; i < tourisms.size(); i++) {
                        if(i == position){
                            TourismPlace tourism_place = tourisms.get(i);
                            String id_tourism_place = tourism_place.getTourism_id();
                            String name_place = tourism_place.getName_place();
                            String description_place = tourism_place.getDescription_place();
                            String latitude_place = tourism_place.getLatitude();
                            String longitude_place = tourism_place.getLongitude();
                            intent.putExtra("name", name_place);
                            intent.putExtra("description", description_place);
                            intent.putExtra("id", id_tourism_place);
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
        return listViewItem;
    }

}
