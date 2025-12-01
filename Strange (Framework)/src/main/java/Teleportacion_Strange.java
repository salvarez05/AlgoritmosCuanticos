import org.redfx.strange.*;
import org.redfx.strange.gate.*;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;

public class Teleportacion_Strange {

    public static void main(String[] args) {

        Program program = new Program(3);

        // --- Paso 1: Preparar estado arbitrario en q0 (por ejemplo, |+> = (|0> + |1>)/√2)
        Step prepare = new Step();
        prepare.addGate(new Hadamard(0)); // Crea superposición en q0
        program.addStep(prepare);

        // --- Paso 2: Crear par de Bell entre q1 y q2 ---
        Step bell1 = new Step();
        bell1.addGate(new Hadamard(1));
        program.addStep(bell1);

        Step bell2 = new Step();
        bell2.addGate(new Cnot(1, 2));
        program.addStep(bell2);

        // --- Paso 3: Entrelazar q0 con q1 (teleportación)
        Step entangle1 = new Step();
        entangle1.addGate(new Cnot(0, 1));
        program.addStep(entangle1);

        Step entangle2 = new Step();
        entangle2.addGate(new Hadamard(0));
        program.addStep(entangle2);

        // --- Paso 4: Medición de q0 y q1 (Alice)
        Step measure = new Step();
        measure.addGates(new Measurement(0), new Measurement(1));
        program.addStep(measure);

        // --- Paso 5: Corrección simbólica en q2 (Bob)
        Step correction1 = new Step();
        correction1.addGate(new X(2));
        program.addStep(correction1);

        Step correction2 = new Step();
        correction2.addGate(new Z(2));
        program.addStep(correction2);

        SimpleQuantumExecutionEnvironment squee = new SimpleQuantumExecutionEnvironment();
        Result result = squee.runProgram(program);

        System.out.println("\n📡 Resultados de Teleportación Cuántica (Strange)");
        Qubit[] qubits = result.getQubits();

        for (int i = 0; i < qubits.length; i++) {
            System.out.println("Qubit " + i + ": prob(1) = " + qubits[i].getProbability() +
                    ", medida = " + qubits[i].measure());
        }
    }
}
