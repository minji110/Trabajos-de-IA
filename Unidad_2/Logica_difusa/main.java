package Unidad_2.Logica_difusa;
import Unidad_2.fuzzylogic.*;
public class main {
    public static void main(String[] args) {
        // Cargar el archivo FCL (Fuzzy Control Language) que contiene las reglas difusas
        String fileName = "temperature_controller.fcl";
        FIS fis = FIS.load(fileName, true);

        // Obtener el conjunto de reglas difusas
        FuzzyRuleSet fuzzyRuleSet = fis.getFuzzyRuleSet();

        // Establecer la temperatura medida
        double temperatura = 75; // Por ejemplo, 75 grados Celsius

        // Asignar la temperatura al controlador difuso
        fuzzyRuleSet.setVariable("temperatura", temperatura);

        // Evaluar el controlador difuso
        fuzzyRuleSet.evaluate();

        // Obtener el tiempo de encendido del horno
        double tiempoEncendido = fuzzyRuleSet.getVariable("tiempo_encendido").defuzzify();

        // Imprimir el tiempo de encendido del horno
        System.out.println("El tiempo de encendido del horno es: " + tiempoEncendido + " minutos");
    }
}
