package tourism.turismo.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
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

import tourism.turismo.AttractionActivity;
import tourism.turismo.InfoAttractionActivity;
import tourism.turismo.R;
import tourism.turismo.model.Attraction;

public class AttractionList extends ArrayAdapter<Attraction> {
    private Activity context;
    private List<Attraction> attractions;
    private DatabaseReference database_reference;
    private EditText name_attraction;
    private EditText description_attraction;
    private String user;
    private String id_city;
    private EditText edt_latitude;
    private EditText edt_longitude;
    private ProgressDialog progressDialog;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS ATTRACTION ACTIVITY FOR CREATE THE LIST
     * @param context
     * @param attractions
     * @param database_reference
     * @param name_attraction
     * @param description_attraction
     * @param user
     * @param id_city
     */
    public AttractionList(@NonNull Activity context, List<Attraction> attractions,
                          DatabaseReference database_reference, EditText name_attraction,
                          EditText description_attraction, String user, String id_city,
                          EditText latitude, EditText longitude) {
        super(context, R.layout.layout_attraction_list, attractions);
        this.context = context;
        this.database_reference = database_reference;
        this.attractions = attractions;
        this.name_attraction = name_attraction;
        this.description_attraction = description_attraction;
        this.user = user;
        this.id_city = id_city;
        this.edt_latitude = latitude;
        this.edt_longitude = longitude;
    }


    /**
     * VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF CITIES
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View list_view_item;
        if(user.equals("luis@gmail.com")) {
            list_view_item = inflater.inflate(R.layout.layout_attraction_list,
                                              null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_attraction);
            Button btn_update = (Button) list_view_item.findViewById(R.id.btn_update_attraction);
            Button btn_delete = (Button) list_view_item.findViewById(R.id.btn_delete_attraction);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_attraction);
            progressDialog = new ProgressDialog(getContext());
            final Attraction attraction = attractions.get(position);
            if(attraction.getId_city().equals(id_city)) {
                txt_name.setText(attraction.getName_attraction());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_update.setVisibility(View.GONE);
                btn_delete.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }

            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setMessage("Espere un momento...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    database_reference.child(attraction.getAttraction_id()).removeValue();
                    waiting(5000);
                    Toast.makeText(context, "Attraction deleted successfully",
                                  Toast.LENGTH_SHORT).show();
                }
            });

            btn_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name_attraction.setText(attraction.getName_attraction());
                    description_attraction.setText(attraction.getDescription_attraction());
                    edt_longitude.setText(attraction.getLongitude());
                    edt_latitude.setText(attraction.getLatitude());
                    AttractionActivity.valoration = attraction.getValoration();
                    AttractionActivity.attraction_id = attraction.getAttraction_id();
                }
            });

            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoAttractionActivity.class);
                    for (int i = 0; i < attractions.size(); i++) {
                        if(i == position){
                            Attraction attraction = attractions.get(i);
                            String id_attraction = attraction.getAttraction_id();
                            String attraction_name = attraction.getName_attraction();
                            String attraction_description= attraction.getDescription_attraction();
                            String latitude_place = attraction.getLatitude();
                            String longitude_place = attraction.getLongitude();
                            intent.putExtra("name", attraction_name);
                            intent.putExtra("description", attraction_description);
                            intent.putExtra("id", id_attraction);
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
            list_view_item = inflater.inflate(R.layout.layout_attraction_list2,
                                             null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_attraction_2);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_attraction_2);

            final Attraction attraction = attractions.get(position);
            if(attraction.getId_city().equals(id_city)) {
                txt_name.setText(attraction.getName_attraction());
            }else{
                txt_name.setVisibility(View.GONE);;
                btn_go.setVisibility(View.GONE);
            }

            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoAttractionActivity.class);
                    for (int i = 0; i < attractions.size(); i++) {
                        if(i == position){
                            Attraction attraction = attractions.get(i);
                            String id_attraction = attraction.getAttraction_id();
                            String attraction_name = attraction.getName_attraction();
                            String attraction_description= attraction.getDescription_attraction();
                            String latitude_place = attraction.getLatitude();
                            String longitude_place = attraction.getLongitude();
                            intent.putExtra("name", attraction_name);
                            intent.putExtra("description", attraction_description);
                            intent.putExtra("id", id_attraction);
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

    /**
     * THIS METHOD DO TIME WAIT FOR DELETE A OBJECT OF ATTRACTION
     * @param milisegundos
     */
    public void waiting(int milisegundos) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, milisegundos);
    }
}
