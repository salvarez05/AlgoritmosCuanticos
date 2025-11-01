package org.example;

import org.redfx.strange.*;
import org.redfx.strange.gate.*;
import org.redfx.strange.local.SimpleQuantumExecutionEnvironment;
import java.util.Arrays;

public class GroverStrangeDemo {

    public static void main(String[] args) {

        Program p = new Program(2); // 2 qubits

        // --- Paso 1: Superposición inicial ---
        Step step1 = new Step();
        step1.addGates(new Hadamard(0), new Hadamard(1));
        p.addStep(step1);

        // --- Paso 2: Oráculo (marca |11⟩ mediante Cz) ---
        Step oracle = new Step();
        oracle.addGate(new Cz(0, 1));
        p.addStep(oracle);

        // --- Paso 3: Difusión (inversión sobre la media) ---
        // Secuencia: H⊗H → X⊗X → Cz → X⊗X → H⊗H
        Step d1 = new Step();
        d1.addGates(new Hadamard(0), new Hadamard(1));
        p.addStep(d1);

        Step d2 = new Step();
        d2.addGates(new X(0), new X(1));
        p.addStep(d2);

        Step d3 = new Step();
        d3.addGate(new Cz(0, 1));
        p.addStep(d3);

        Step d4 = new Step();
        d4.addGates(new X(0), new X(1));
        p.addStep(d4);

        Step d5 = new Step();
        d5.addGates(new Hadamard(0), new Hadamard(1));
        p.addStep(d5);

        // --- Ejecutar el programa ---
        SimpleQuantumExecutionEnvironment sqee = new SimpleQuantumExecutionEnvironment();
        Result res = sqee.runProgram(p);
        Qubit[] qubits = res.getQubits();

        // --- Mostrar resultados ---
        System.out.println("\nResultados de Grover (estado buscado = |11⟩):");
        Arrays.asList(qubits).forEach(q ->
                System.out.println("Qubit con probabilidad de 1 = " + q.getProbability() +
                        ", medida = " + q.measure())
        );
    }
}
