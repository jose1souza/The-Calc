package br.edu.ifsuldeminas.mch.pdm.thecalc;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.ifsuldeminas.mch.calc.R;

public class MainActivity extends AppCompatActivity {

    private TextView textViewExpression;
    private TextView textViewResult;
    private Button buttonAdd, buttonSub, buttonMul, buttonDiv, buttonPercent;
    private Button buttonEqual, buttonClear, buttonDelete;
    private Button button0, button1, button2, button3, button4,
            button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main_layout_id), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewExpression = findViewById(R.id.textViewExpression);
        textViewResult = findViewById(R.id.textViewResult);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSub = findViewById(R.id.buttonSub);
        buttonMul = findViewById(R.id.buttonMul);
        buttonDiv = findViewById(R.id.buttonDiv);
        buttonPercent = findViewById(R.id.buttonPercent);

        buttonEqual = findViewById(R.id.buttonEqual);
        buttonClear = findViewById(R.id.buttonClear);
        buttonDelete = findViewById(R.id.buttonDelete);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);

        // Números
        button0.setOnClickListener(v -> textViewExpression.append("0"));
        button1.setOnClickListener(v -> textViewExpression.append("1"));
        button2.setOnClickListener(v -> textViewExpression.append("2"));
        button3.setOnClickListener(v -> textViewExpression.append("3"));
        button4.setOnClickListener(v -> textViewExpression.append("4"));
        button5.setOnClickListener(v -> textViewExpression.append("5"));
        button6.setOnClickListener(v -> textViewExpression.append("6"));
        button7.setOnClickListener(v -> textViewExpression.append("7"));
        button8.setOnClickListener(v -> textViewExpression.append("8"));
        button9.setOnClickListener(v -> textViewExpression.append("9"));

        // Operações
        buttonAdd.setOnClickListener(v -> textViewExpression.append("+"));
        buttonSub.setOnClickListener(v -> textViewExpression.append("-"));
        buttonMul.setOnClickListener(v -> textViewExpression.append("*"));
        buttonDiv.setOnClickListener(v -> textViewExpression.append("/"));
        buttonPercent.setOnClickListener(v -> textViewExpression.append("%"));

        // Reset
        buttonClear.setOnClickListener(v -> {
            textViewExpression.setText("");
            textViewResult.setText("");
        });

        // Delete último caractere
        buttonDelete.setOnClickListener(v -> {
            String expr = textViewExpression.getText().toString();
            if (!expr.isEmpty()) {
                textViewExpression.setText(expr.substring(0, expr.length() - 1));
            }
        });

        // Igual (=)
        buttonEqual.setOnClickListener(v -> {
            try {
                String expr = textViewExpression.getText().toString();
                double result = calculate(expr);
                textViewResult.setText(String.valueOf(result));
                textViewExpression.setText(String.valueOf(result));
            } catch (Exception e) {
                textViewResult.setText("Erro");
            }
        });
    }

    // Método simples para calcular expressão com uma operação
    private double calculate(String expr) {
        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return Double.parseDouble(parts[0]) + Double.parseDouble(parts[1]);
        } else if (expr.contains("-")) {
            String[] parts = expr.split("-");
            return Double.parseDouble(parts[0]) - Double.parseDouble(parts[1]);
        } else if (expr.contains("*")) {
            String[] parts = expr.split("\\*");
            return Double.parseDouble(parts[0]) * Double.parseDouble(parts[1]);
        } else if (expr.contains("/")) {
            String[] parts = expr.split("/");
            return Double.parseDouble(parts[0]) / Double.parseDouble(parts[1]);
        } else if (expr.contains("%")) {
            String[] parts = expr.split("%");
            return (Double.parseDouble(parts[0]) * Double.parseDouble(parts[1])) / 100;
        }
        return 0;
    }
}