import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Etapa 2: version completa con validaciones, division, temperaturas y monedas.
// Cada seccion tiene su propio recuadro de resultado.
public class Calculadora extends JFrame implements ActionListener {

    // --- Seccion matematicas ---
    JTextField mate1;
    JTextField mate2;
    JButton bSumar, bRestar, bMultiplicar, bDividir;
    JLabel resMate;

    // --- Seccion temperatura ---
    JTextField campoTemp;
    JButton bCaF, bFaC;
    JLabel resTemp;

    // --- Seccion monedas ---
    JTextField campoDinero;
    JButton bUsdCop, bCopUsd;
    JLabel resDinero;

    // tasa fija del dolar
    final int TASA = 3800;

    public Calculadora() {
        setTitle("Calculadora y conversor");
        setSize(560, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ===== Titulo =====
        JLabel titulo = new JLabel("Calculadora y conversor");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBounds(150, 10, 300, 25);
        add(titulo);

        // ===== Seccion 1: Matematicas =====
        JLabel t1 = new JLabel("Operaciones matematicas");
        t1.setFont(new Font("Arial", Font.BOLD, 13));
        t1.setBounds(20, 45, 250, 20);
        add(t1);

        mate1 = new JTextField();
        mate1.setBounds(20, 70, 250, 28);
        ponerPlaceholder(mate1, "Escribe aqui el primer numero");
        add(mate1);

        mate2 = new JTextField();
        mate2.setBounds(280, 70, 250, 28);
        ponerPlaceholder(mate2, "Escribe aqui el segundo numero");
        add(mate2);

        bSumar = new JButton("Sumar");
        bSumar.setBounds(20, 105, 120, 28);
        add(bSumar);

        bRestar = new JButton("Restar");
        bRestar.setBounds(150, 105, 120, 28);
        add(bRestar);

        bMultiplicar = new JButton("Multiplicar");
        bMultiplicar.setBounds(280, 105, 120, 28);
        add(bMultiplicar);

        bDividir = new JButton("Dividir");
        bDividir.setBounds(410, 105, 120, 28);
        add(bDividir);

        resMate = new JLabel("Resultado: ");
        resMate.setBorder(BorderFactory.createTitledBorder("Resultado matematicas"));
        resMate.setBounds(20, 140, 510, 55);
        resMate.setHorizontalAlignment(SwingConstants.CENTER);
        resMate.setFont(new Font("Arial", Font.PLAIN, 14));
        add(resMate);

        // ===== Seccion 2: Temperatura =====
        JLabel t2 = new JLabel("Conversion de temperatura");
        t2.setFont(new Font("Arial", Font.BOLD, 13));
        t2.setBounds(20, 210, 250, 20);
        add(t2);

        campoTemp = new JTextField();
        campoTemp.setBounds(20, 235, 250, 28);
        ponerPlaceholder(campoTemp, "Escribe los grados a convertir");
        add(campoTemp);

        bCaF = new JButton("Celsius a Fahrenheit");
        bCaF.setBounds(280, 235, 120, 28);
        add(bCaF);

        bFaC = new JButton("Fahrenheit a Celsius");
        bFaC.setBounds(410, 235, 120, 28);
        add(bFaC);

        resTemp = new JLabel("Resultado: ");
        resTemp.setBorder(BorderFactory.createTitledBorder("Resultado temperatura"));
        resTemp.setBounds(20, 275, 510, 55);
        resTemp.setHorizontalAlignment(SwingConstants.CENTER);
        resTemp.setFont(new Font("Arial", Font.PLAIN, 14));
        add(resTemp);

        // ===== Seccion 3: Monedas =====
        JLabel t3 = new JLabel("Conversion de monedas (tasa fija: 1 USD = " + TASA + " COP)");
        t3.setFont(new Font("Arial", Font.BOLD, 13));
        t3.setBounds(20, 345, 500, 20);
        add(t3);

        campoDinero = new JTextField();
        campoDinero.setBounds(20, 370, 250, 28);
        ponerPlaceholder(campoDinero, "Escribe la cantidad de dinero");
        add(campoDinero);

        bUsdCop = new JButton("USD a COP");
        bUsdCop.setBounds(280, 370, 120, 28);
        add(bUsdCop);

        bCopUsd = new JButton("COP a USD");
        bCopUsd.setBounds(410, 370, 120, 28);
        add(bCopUsd);

        resDinero = new JLabel("Resultado: ");
        resDinero.setBorder(BorderFactory.createTitledBorder("Resultado monedas"));
        resDinero.setBounds(20, 410, 510, 55);
        resDinero.setHorizontalAlignment(SwingConstants.CENTER);
        resDinero.setFont(new Font("Arial", Font.PLAIN, 14));
        add(resDinero);

        // listeners
        bSumar.addActionListener(this);
        bRestar.addActionListener(this);
        bMultiplicar.addActionListener(this);
        bDividir.addActionListener(this);
        bCaF.addActionListener(this);
        bFaC.addActionListener(this);
        bUsdCop.addActionListener(this);
        bCopUsd.addActionListener(this);

        setVisible(true);
    }

    // pone un texto guia dentro del campo (placeholder simple)
    private void ponerPlaceholder(final JTextField campo, final String texto) {
        campo.setForeground(Color.GRAY);
        campo.setText(texto);
        campo.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campo.getText().equals(texto)) {
                    campo.setText("");
                    campo.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (campo.getText().equals("")) {
                    campo.setForeground(Color.GRAY);
                    campo.setText(texto);
                }
            }
        });
    }

    // devuelve el texto real del campo (vacio si tiene el placeholder)
    private String leer(JTextField campo) {
        if (campo.getForeground().equals(Color.GRAY)) {
            return "";
        }
        return campo.getText().trim();
    }

    // quita el .0 cuando el resultado es entero
    private String formatear(double valor) {
        if (valor == (long) valor) {
            return String.valueOf((long) valor);
        }
        return String.valueOf(valor);
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        // ----- Matematicas -----
        if (o == bSumar || o == bRestar || o == bMultiplicar || o == bDividir) {
            String t1 = leer(mate1);
            String t2 = leer(mate2);
            if (t1.equals("") || t2.equals("")) {
                JOptionPane.showMessageDialog(this, "Debes llenar los dos numeros");
                return;
            }
            double n1, n2;
            try {
                n1 = Double.parseDouble(t1);
                n2 = Double.parseDouble(t2);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Solo se permiten numeros");
                return;
            }

            double r = 0;
            String simbolo = "";
            if (o == bSumar)        { r = n1 + n2; simbolo = "+"; }
            else if (o == bRestar)  { r = n1 - n2; simbolo = "-"; }
            else if (o == bMultiplicar) { r = n1 * n2; simbolo = "*"; }
            else if (o == bDividir) {
                if (n2 == 0) {
                    JOptionPane.showMessageDialog(this,
                        "Error: no se puede dividir por cero",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                r = n1 / n2;
                simbolo = "/";
            }
            resMate.setText(formatear(n1) + " " + simbolo + " " + formatear(n2) + " = " + formatear(r));
            return;
        }

        // ----- Temperatura -----
        if (o == bCaF || o == bFaC) {
            String t = leer(campoTemp);
            if (t.equals("")) {
                JOptionPane.showMessageDialog(this, "Debes escribir los grados");
                return;
            }
            double grados;
            try {
                grados = Double.parseDouble(t);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Solo se permiten numeros");
                return;
            }
            if (o == bCaF) {
                double r = (grados * 9 / 5) + 32;
                resTemp.setText(formatear(grados) + " C = " + formatear(r) + " F");
            } else {
                double r = (grados - 32) * 5 / 9;
                resTemp.setText(formatear(grados) + " F = " + formatear(r) + " C");
            }
            return;
        }

        // ----- Monedas (solo numeros enteros positivos) -----
        if (o == bUsdCop || o == bCopUsd) {
            String t = leer(campoDinero);
            if (t.equals("")) {
                JOptionPane.showMessageDialog(this, "Debes escribir la cantidad");
                return;
            }
            int cantidad;
            try {
                cantidad = Integer.parseInt(t);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Error: en monedas solo se aceptan numeros enteros positivos",
                    "Error", JOptionPane.ERROR_MESSAGE);
                // Cambio Etapa 3: Optimizacion y revision de flujos de control.
                return;
            }
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Error: la cantidad debe ser un numero entero positivo",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (o == bUsdCop) {
                long r = (long) cantidad * TASA;
                resDinero.setText(cantidad + " USD = " + r + " COP");
            } else {
                double r = (double) cantidad / TASA;
                resDinero.setText(cantidad + " COP = " + formatear(r) + " USD");
            }
        }
    }

    public static void main(String[] args) {
        new Calculadora();
    }
}


