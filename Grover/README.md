# AlgoritmosCuanticos
# Algoritmo Grover

Una de las mayores ventajas de la computación cuántica en problemas de búsqueda es su capacidad de usar la superposición, lo que permite que el registro cuántico represente todas las posiciones posibles al mismo tiempo. Esto no implica que el sistema evalúe simultáneamente todas las opciones como lo haría un computador clásico en paralelo, sino que utiliza amplitudes cuánticas que pueden ser manipuladas mediante operaciones como el oráculo y el operador de difusión para amplificar la probabilidad de la respuesta correcta.

# Circuito general

El circuito utiliza un conjunto de qubits que se inicializan en una superposición uniforme mediante puertas Hadamard. A continuación se aplica el oráculo, una operación que modifica la fase de la solución correcta sin revelar cuál es. Después se ejecuta el operador de difusión, que amplifica la amplitud de esa solución reflejándola alrededor del promedio de todas las amplitudes. Esta combinación de oráculo y difusión se repite varias veces, según el tamaño del espacio de búsqueda. Finalmente, se mide el registro, obteniendo con alta probabilidad la respuesta correcta usando muchas menos iteraciones que cualquier método clásico.