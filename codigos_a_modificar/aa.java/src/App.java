import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class App {
    public static void main(String[] args) {
        // Cargar el archivo FCL (Fuzzy Control Language) que contiene las reglas difusas
        String fileName = "src/temperature_controller.fcl";
        FIS fis = FIS.load(fileName, true);

        // Establecer la temperatura medida
        double temperatura = 75; // Por ejemplo, 75 grados Celsius
        fis.setVariable("temperatura", temperatura);

        // Evaluar el sistema difuso
        fis.evaluate();

        // Obtener la variable de salida "tiempo_encendido"
        Variable tiempoEncendido = fis.getVariable("tiempo_encendido");

        // Obtener el valor defuzzificado de la variable de salida
        double valorTiempoEncendido = tiempoEncendido.defuzzify();

        // Imprimir el tiempo de encendido del horno
        System.out.println("El tiempo de encendido del horno es: " + valorTiempoEncendido + " minutos");
    }
}
