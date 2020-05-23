package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText NUMERO_UNO, NUMERO_DOS;
    private TextView RESULTADO;
    private Spinner COMBO_OPERACIONES;
    private String[] OPERACIONES;
    private ArrayAdapter<String> ADAPTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NUMERO_UNO = findViewById(R.id.txtNumeroUno);
        NUMERO_DOS = findViewById(R.id.txtNumeroDos);
        RESULTADO = findViewById(R.id.lblResultado);
        COMBO_OPERACIONES = findViewById(R.id.cmbOperaciones);
        OPERACIONES = getResources().getStringArray(R.array.operaciones);
        ADAPTER = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, OPERACIONES);
        COMBO_OPERACIONES.setAdapter(ADAPTER);
    }

    public void btnLimpiarCampos(View view) {
        NUMERO_UNO.setText("");
        NUMERO_DOS.setText("");
        RESULTADO.setText("");
        NUMERO_UNO.requestFocus();
    }

    public void btnCalcular(View view) {
        try {
            int op;
            double numeroUno, numeroDos, res = 0;
            if (validarCamposNumeros()){
                numeroUno = Double.parseDouble(NUMERO_UNO.getText().toString());
                numeroDos = Double.parseDouble(NUMERO_DOS.getText().toString());
                op = COMBO_OPERACIONES.getSelectedItemPosition();
                switch (op){
                    case 0:
                        res = numeroUno + numeroDos;
                        break;
                    case 1:
                        res = numeroUno - numeroDos;
                        break;
                    case 2:
                        res = numeroUno * numeroDos;
                        break;
                    case 3:
                        res = numeroUno / numeroDos;
                        break;
                }
                RESULTADO.setText(Double.toString(res));
            }
        }catch (Exception e){
            Toast toast = Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public boolean validarCamposNumeros(){
        String errorNumeroUno, errorNumeroDos, errorDivision;
        int op;
        double numeroUno, numeroDos, res = 0;
        errorNumeroUno = getResources().getString(R.string.errorCampoNumeroUno);
        errorNumeroDos = getResources().getString(R.string.errorCampoNumeroDos);
        errorDivision = getResources().getString(R.string.errorDividirNumeroDos);

        if (NUMERO_UNO.getText().toString().isEmpty()){
            NUMERO_UNO.setError(errorNumeroUno);
            NUMERO_UNO.requestFocus();
            return false;
        }else {
            if (NUMERO_DOS.getText().toString().isEmpty()){
                NUMERO_DOS.setError(errorNumeroDos);
                NUMERO_DOS.requestFocus();
                return  false;
            }
        }

        numeroDos = Double.parseDouble(NUMERO_DOS.getText().toString());
        op = COMBO_OPERACIONES.getSelectedItemPosition();
        switch (op){
            case 3:
                if (numeroDos == 0){
                    RESULTADO.setText("");
                    NUMERO_DOS.setError(errorDivision);
                    NUMERO_DOS.requestFocus();
                    return  false;
                }
                break;
        }
        return true;
    }

}
