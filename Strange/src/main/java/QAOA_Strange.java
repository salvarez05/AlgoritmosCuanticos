import org.redfx.strange.*;
import org.redfx.strange.gate.*;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class QAOA_Strange {

    public static void main(String[] args) {

        Program program = new Program(2);

        // Paso 1: Superposición inicial (Hadamard)
        Step step1 = new Step();
        step1.addGate(new Hadamard(0));
        step1.addGate(new Hadamard(1));
        program.addStep(step1);

        // Parámetros del QAOA
        double gamma = Math.PI / 4;
        double beta = Math.PI / 8;

        // Paso 2: Primera parte del "cost Hamiltonian" (primer CNOT)
        Step step2 = new Step();
        step2.addGate(new Cnot(0, 1));
        program.addStep(step2);

        // Paso 3: Rotación Z en el qubit 1
        Step step3 = new Step();
        step3.addGate(new RotationZ(2 * gamma, 1));
        program.addStep(step3);

        // Paso 4: Segunda parte del "cost Hamiltonian" (segundo CNOT)
        Step step4 = new Step();
        step4.addGate(new Cnot(0, 1));
        program.addStep(step4);

        // Paso 5: "Mixer Hamiltonian"
        Step step5 = new Step();
        step5.addGate(new RotationX(2 * beta, 0));
        step5.addGate(new RotationX(2 * beta, 1));
        program.addStep(step5);

        // Ejecutar el programa
        QuantumExecutionEnvironment qee = new SimpleQuantumExecutionEnvironment();
        Result result = qee.runProgram(program);

        // Mostrar los resultados
        Complex[] amplitudes = result.getProbability();
        System.out.println("=== Resultados del QAOA (Strange) ===");
        for (int i = 0; i < amplitudes.length; i++) {
            double prob = amplitudes[i].abssqr();
            System.out.printf("Estado |%2s⟩: %.4f%n", Integer.toBinaryString(i | 0b100).substring(1), prob);
        }
    }
}
