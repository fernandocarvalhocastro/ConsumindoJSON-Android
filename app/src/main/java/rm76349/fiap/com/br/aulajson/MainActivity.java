package rm76349.fiap.com.br.aulajson;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

    private EditText txtCEP;
    private TextView lblRua;
    private TextView lblCidade;
    private ImageView imgLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCEP = (EditText)findViewById(R.id.txtCep);
        lblRua = (TextView)findViewById(R.id.lblRua);
        lblCidade = (TextView)findViewById(R.id.lblCidade);
        imgLoading = (ImageView)findViewById(R.id.imgLoading);
        Button btnConsultar = (Button)findViewById(R.id.btnConsultar);
        btnConsultar.setOnClickListener(this);
    }

    public void consultar(View v){
        String cep = txtCEP.getText().toString();
        String url = "http://viacep.com.br/ws/"+ cep +"/json/";
        JsonObjectRequest req = new JsonObjectRequest(url, null, new BuscaCep(), new Erro());
        RequestQueue reqQueue = Volley.newRequestQueue(this);
        reqQueue.add(req);
        imgLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        consultar(v);
    }

    class Erro implements Response.ErrorListener{

        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            imgLoading.setVisibility(View.INVISIBLE);
        }
    }

    class BuscaCep implements Response.Listener<JSONObject>{

        @Override
        public void onResponse(JSONObject response) {
            Log.i("JSON_CEP", response.toString());
            try {
                lblRua.setText(response.getString("logradouro"));
                lblCidade.setText(response.getString("localidade"));
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                imgLoading.setVisibility(View.INVISIBLE);
            }
        }
    }
}
