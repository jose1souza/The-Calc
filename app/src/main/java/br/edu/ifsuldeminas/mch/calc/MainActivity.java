package br.edu.ifsuldeminas.mch.calc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Calculadora";

    private TextView tvResultado;
    private TextView tvExpressaoAnterior;
    private String expressaoAtual = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        tvResultado = findViewById(R.id.textViewResultadoID);
        tvExpressaoAnterior = findViewById(R.id.textViewUltimaExpressaoID);
    }

    private void configurarEventos() {
        int[] botoesNumericos = {
                R.id.buttonZeroID, R.id.buttonUmID, R.id.buttonDoisID,
                R.id.buttonTresID, R.id.buttonQuatroID, R.id.buttonCincoID,
                R.id.buttonSeisID, R.id.buttonSeteID, R.id.buttonOitoID,
                R.id.buttonNoveID
        };

        for (int id : botoesNumericos) {
            Button botao = findViewById(id);
            botao.setOnClickListener(v -> adicionarValor(botao.getText().toString()));
        }

        findViewById(R.id.buttonSomaID).setOnClickListener(v -> adicionarOperador("+"));
        findViewById(R.id.buttonSubtracaoID).setOnClickListener(v -> adicionarOperador("-"));
        findViewById(R.id.buttonMultiplicacaoID).setOnClickListener(v -> adicionarOperador("*"));
        findViewById(R.id.buttonDivisaoID).setOnClickListener(v -> adicionarOperador("/"));

        findViewById(R.id.buttonVirgulaID).setOnClickListener(v -> adicionarValor(","));

        findViewById(R.id.buttonIgualID).setOnClickListener(v -> calcular());

        findViewById(R.id.buttonPorcentoID).setOnClickListener(v -> calcularPorcentagem());

        findViewById(R.id.buttonResetID).setOnClickListener(v -> limpar());

        findViewById(R.id.buttonDeleteID).setOnClickListener(v -> removerUltimo());
    }

    private void adicionarValor(String valor) {
        expressaoAtual += valor;
        tvResultado.setText(expressaoAtual);
        Log.d(TAG, "Valor adicionado: " + valor + " | Expressão: " + expressaoAtual);
    }

    private void adicionarOperador(String operador) {
        if (expressaoAtual.isEmpty()) return;

        char ultimo = expressaoAtual.charAt(expressaoAtual.length() - 1);
        if (ultimo == '+' || ultimo == '-' || ultimo == '*' || ultimo == '/') {
            expressaoAtual = expressaoAtual.substring(0, expressaoAtual.length() - 1);
        }

        expressaoAtual += operador;
        tvResultado.setText(expressaoAtual);
        Log.d(TAG, "Operador: " + operador + " | Expressão: " + expressaoAtual);
    }

    private void calcular() {
        try {
            String exprFormatada = expressaoAtual.replace(",", ".");
            Calculable calculo = new ExpressionBuilder(exprFormatada).build();
            double resultado = calculo.calculate();

            String resultadoFinal;
            if (resultado % 1 == 0) {
                resultadoFinal = String.valueOf((long) resultado);
            } else {
                resultadoFinal = String.format(Locale.US, "%.2f", resultado);
            }

            tvExpressaoAnterior.setText(expressaoAtual + " =");
            tvResultado.setText(resultadoFinal);
            expressaoAtual = resultadoFinal;

            Log.d(TAG, "Resultado: " + resultadoFinal);
        } catch (Exception e) {
            Log.e(TAG, "Erro: " + e.getMessage());
            tvResultado.setText("Erro");
        }
    }

    private void calcularPorcentagem() {
        try {
            String exprFormatada = expressaoAtual.replace(",", ".");
            double valor = Double.parseDouble(exprFormatada);
            double resultado = valor / 100;

            String resultadoFinal = String.format(Locale.US, "%.2f", resultado);
            tvExpressaoAnterior.setText(expressaoAtual + "%");
            tvResultado.setText(resultadoFinal);
            expressaoAtual = resultadoFinal;

            Log.d(TAG, "Porcentagem: " + resultadoFinal);
        } catch (Exception e) {
            tvResultado.setText("Erro");
        }
    }

    private void limpar() {
        expressaoAtual = "";
        tvResultado.setText("0");
        tvExpressaoAnterior.setText("");
        Log.d(TAG, "Resetado");
    }

    private void removerUltimo() {
        if (!expressaoAtual.isEmpty()) {
            expressaoAtual = expressaoAtual.substring(0, expressaoAtual.length() - 1);
            tvResultado.setText(expressaoAtual.isEmpty() ? "0" : expressaoAtual);
            Log.d(TAG, "Delete | Expressão: " + expressaoAtual);
        }
    }
}
