package tourism.turismo.list;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.logging.Handler;

import tourism.turismo.InfoRecomendationActivity;
import tourism.turismo.R;
import tourism.turismo.model.Recomendation;

public class RecomendationList extends ArrayAdapter<Recomendation> {
    private Activity context;
    private List<Recomendation> recomendations;
    private String user;
    private String id_city;

    /**
     * THIS METHOD RECEIVE PARAMETERS OF THE CLASS ATTRACTION ACTIVITY FOR CREATE A LIST
     * @param context
     * @param user
     * @param id_city
     */
    public RecomendationList(@NonNull Activity context, List<Recomendation> recomendations,
                             String user, String id_city) {
        super(context, R.layout.layout_attraction_list, recomendations);
        this.context = context;
        this.recomendations = recomendations;
        this.user = user;
        this.id_city = id_city;
    }

    /**
     * VIEW CREATED FOR RETURN A LIST IN THE SCREEM OF RECOMENDATION
     * @param position
     * @param view
     * @param parent
     * @return
     */
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater =context.getLayoutInflater();
        View list_view_item = inflater.inflate(R.layout.layout_recomendation_list,
                                               null, true);
            TextView txt_name = (TextView) list_view_item.findViewById(R.id.txt_name_recomendation);
            Button btn_go = (Button) list_view_item.findViewById(R.id.btn_go_recomendation);

            final Recomendation recomendation = recomendations.get(position);
            float valoration = Float.parseFloat(recomendation.getValoration());
            if(recomendation.getId_city().equals(id_city) && valoration >= 3.0) {
                txt_name.setText(recomendation.getName());
            }else{
                txt_name.setVisibility(View.GONE);
                btn_go.setVisibility(View.GONE);
            }

            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( getContext(),InfoRecomendationActivity.class);
                    for (int i = 0; i < recomendations.size(); i++) {
                        if(i == position){
                            Recomendation recomendation = recomendations.get(i);
                            String id = recomendation.getId();
                            String name = recomendation.getName();
                            String description= recomendation.getDescription();
                            String latitude_place = recomendation.getLatitude();
                            String longitude_place = recomendation.getLongitude();
                            intent.putExtra("name", name);
                            intent.putExtra("description", description);
                            intent.putExtra("id", id);
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

        return list_view_item;
    }
}
