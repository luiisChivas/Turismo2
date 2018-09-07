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

import tourism.turismo.InfoNightLifeActivity;
import tourism.turismo.NightLifeActivity;
import tourism.turismo.R;
import tourism.turismo.model.NightLife;

public class NightLifeList extends ArrayAdapter<NightLife> {

    private Activity context;
    private List<NightLife> night_lifes;
    private DatabaseReference database_reference;
    private EditText name_night_life;
    private EditText description_night_life;
    private String user;
    private String id_city;
    private EditText edt_latitude;
    private EditText edt_longitude;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS ATTRACTION ACTIVITY FOR CREATE A LIST
     * @param context
     * @param night_lifes
     * @param database_reference
     * @param name_night_life
     * @param description_night_life
     * @param user
     * @param id_city
     */
    public NightLifeList(@NonNull Activity context, List<NightLife> night_lifes,
                         DatabaseReference database_reference, EditText name_night_life,
                         EditText description_night_life, String user, String id_city,
                         EditText latitude, EditText longitude) {
        super(context, R.layout.layout_night_life_list, night_lifes);
        this.context = context;
        this.database_reference = database_reference;
        this.night_lifes = night_lifes;
        this.name_night_life = name_night_life;
        this.description_night_life = description_night_life;
        this.user = user;
        this.id_city = id_city;
        this.edt_latitude = latitude;
        this.edt_longitude = longitude;
    }


    /**
     *  VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF NIGHT LIFE
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View list_view_item;

        if(user.equals("luis@gmail.com")) {
            list_view_item = inflater.inflate(R.layout.layout_night_life_list,
                                             null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_night_life);
            Button btn_update = (Button) list_view_item.findViewById(R.id.btn_update_night_life);
            Button btn_delete = (Button) list_view_item.findViewById(R.id.btn_delete_night_life);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_night_life);

            final NightLife night_life = night_lifes.get(position);
            if(night_life.getId_city().equals(id_city)) {
                txt_name.setText(night_life.getName_night_life());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    database_reference.child(night_life.getNight_life_id()).removeValue();
                    Toast.makeText(context, "Discoteca eliminada correctamente!",
                                   Toast.LENGTH_SHORT).show();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name_night_life.setText(night_life.getName_night_life());
                    description_night_life.setText(night_life.getDescription_night_life());
                    edt_longitude.setText(night_life.getLongitude());
                    edt_latitude.setText(night_life.getLatitude());
                    NightLifeActivity.valoration = night_life.getValoration();
                    NightLifeActivity.night_life_id = night_life.getNight_life_id();
                }
            });
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoNightLifeActivity.class);
                    for (int i = 0; i < night_lifes.size(); i++) {
                        if(i == position){
                            NightLife night_life = night_lifes.get(i);
                            String id_night_life = night_life.getNight_life_id();
                            String night_life_name = night_life.getName_night_life();
                            String night_life_description= night_life.getDescription_night_life();
                            String latitude_place = night_life.getLatitude();
                            String longitude_place = night_life.getLongitude();
                            intent.putExtra("name", night_life_name);
                            intent.putExtra("description", night_life_description);
                            intent.putExtra("id", id_night_life);
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
            list_view_item = inflater.inflate(R.layout.layout_night_life_list2,
                                             null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_night_life_2);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_night_life_2);

            final NightLife night_life = night_lifes.get(position);
            if(night_life.getId_city().equals(id_city)) {
                txt_name.setText(night_life.getName_night_life());
            }else{
                txt_name.setVisibility(View.GONE);;
                btn_go.setVisibility(View.GONE);
            }

            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoNightLifeActivity.class);
                    for (int i = 0; i < night_lifes.size(); i++) {
                        if(i == position){
                            NightLife night_life = night_lifes.get(i);
                            String id_night_life = night_life.getNight_life_id();
                            String night_life_name = night_life.getName_night_life();
                            String night_life_description= night_life.getDescription_night_life();
                            String latitude_place = night_life.getLatitude();
                            String longitude_place = night_life.getLongitude();
                            intent.putExtra("name", night_life_name);
                            intent.putExtra("description", night_life_description);
                            intent.putExtra("id", id_night_life);
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
