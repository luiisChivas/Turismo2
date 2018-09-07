package tourism.turismo.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import tourism.turismo.CityActivity;
import tourism.turismo.MenuActivity;
import tourism.turismo.R;
import tourism.turismo.model.City;
import tourism.turismo.model.CityId;

public class CityList extends ArrayAdapter<City>{

    private Activity context;
    private List<City> cities;
    private DatabaseReference databaseReference;
    private EditText edt_name;
    private String user;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS ATTRACTION ACTIVITY FOR CREATE THE LIST
     * @param context
     * @param cities
     * @param databaseReference
     * @param edt_name_city
     * @param user
     */
    public CityList(@NonNull Activity context, List<City> cities,
                    DatabaseReference databaseReference, EditText edt_name_city, String user) {
        super(context, R.layout.layout_city_list, cities);
        this.context = context;
        this.cities = cities;
        this.databaseReference = databaseReference;
        this.edt_name = edt_name_city;
        this.user = user;
    }

    /**
     * VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF CITIES
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem;
        if(user.equals("luis@gmail.com")){
            listViewItem = inflater.inflate(R.layout.layout_city_list,null, true);
            TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name);
            Button btn_delete = (Button) listViewItem.findViewById(R.id.btn_delete);
            Button btn_update = (Button) listViewItem.findViewById(R.id.btn_update);
            Button btn_go = (Button) listViewItem.findViewById(R.id.btn_go);

            final City city = cities.get(position);
            txt_name.setText(city.getName());

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(city.getCity_id()).removeValue();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edt_name.setText(city.getName());
                    CityActivity.city_id = city.getCity_id();
                }
            });
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),MenuActivity.class);
                    for (int i = 0; i < cities.size(); i++) {
                        if(i == position){
                            City city = cities.get(i);
                            String id_city = city.getCity_id();
                            intent.putExtra("id_city", id_city);
                            intent.putExtra("user", user);
                            new CityId(id_city, user);
                        }
                    }
                    getContext().startActivity(intent);
                    context.finish();
                }
            });
        }else{
            listViewItem = inflater.inflate(R.layout.layout_city_list2,
                    null, true);
            TextView txt_name = (TextView) listViewItem.findViewById(R.id.txt_name_2);
            Button btn_ir = (Button) listViewItem.findViewById(R.id.btn_ir_2);
            final City city = cities.get(position);
            txt_name.setText(city.getName());
            btn_ir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),MenuActivity.class);
                    for (int i = 0; i < cities.size(); i++) {
                        if(i == position){
                            City city = cities.get(i);
                            String id_city = city.getCity_id();
                            intent.putExtra("id_city", id_city);
                            intent.putExtra("user", user);
                            new CityId(id_city, user);
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
