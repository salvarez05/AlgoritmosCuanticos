import org.redfx.strange.*;
import org.redfx.strange.gate.*;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class QAOA_MaxCut2_Strange {

    public static void main(String[] args) {
        // Grafo simple: dos nodos conectados por una arista
        // MaxCut busca separar los nodos (|01⟩ o |10⟩) => energía mínima

        Program program = new Program(2);

        // Paso 1: Superposición inicial
        Step init = new Step();
        init.addGate(new Hadamard(0));
        init.addGate(new Hadamard(1));
        program.addStep(init);

        // Parámetros QAOA
        double gamma = Math.PI / 3; // controla el cost Hamiltonian
        double beta = Math.PI / 6;  // controla el mixer Hamiltonian

        // --- Cost Hamiltonian para MaxCut(1,2) ---
        // H_C = (I - Z0 * Z1) / 2
        // Implementamos con CNOT + Rz + CNOT
        Step cost1 = new Step();
        cost1.addGate(new Cnot(0, 1));
        program.addStep(cost1);

        Step cost2 = new Step();
        cost2.addGate(new RotationZ(2 * gamma, 1));
        program.addStep(cost2);

        Step cost3 = new Step();
        cost3.addGate(new Cnot(0, 1));
        program.addStep(cost3);

        // --- Mixer Hamiltonian ---
        Step mixer = new Step();
        mixer.addGate(new RotationX(2 * beta, 0));
        mixer.addGate(new RotationX(2 * beta, 1));
        program.addStep(mixer);

        // Ejecutar
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(program);

        // Mostrar resultados
        Complex[] amplitudes = result.getProbability();
        System.out.println("=== QAOA para MaxCut (2 nodos) ===");
        for (int i = 0; i < amplitudes.length; i++) {
            double prob = amplitudes[i].abssqr();
            System.out.printf("Estado |%2s⟩: %.4f%n", Integer.toBinaryString(i | 0b100).substring(1), prob);
        }

        // Interpretación
        System.out.println("\n👉 Estados |01⟩ y |10⟩ representan el corte óptimo (nodos en lados opuestos)");
    }
}
