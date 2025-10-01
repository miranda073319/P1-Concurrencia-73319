# Practica 1 - Desarrolle un software con concurrencia usando exclusión mutua con monitores y semáforos

# 1. Introducción al Problema de la Condición de Carrera
La programación concurrente implica la ejecución simultánea de múltiples procesos o hilos (threads) que a menudo necesitan   acceder y modificar recursos compartidos, como variables, bases de datos o archivos. Si bien la concurrencia es crucial para el rendimiento y la eficiencia en sistemas modernos, introduce un desafío fundamental conocido como la condición de carrera (Race Condition).
Una condición de carrera ocurre cuando la salida de un programa depende de la secuencia o el momento en que se ejecutan las operaciones de múltiples hilos que acceden a un mismo dato compartido. Si estos hilos intentan leer, modificar y escribir el dato simultáneamente sin un control estricto, el resultado final es impredecible e incorrecto.

# 2. Funcionamiento de Semáforos y Monitores
La exclusión mutua se implementa a través de mecanismos de sincronización que fuerzan a los hilos a esperar su turno antes de acceder a la sección crítica. Dos de los mecanismos más comunes son los semáforos y los monitores.
  Semáforos
    Son variables enteras que controlan el acceso a recursos compartidos mediante dos operaciones atómicas:
    acquire() (wait/P): disminuye el contador y bloquea al hilo si llega a cero.
    release() (signal/V): incrementa el contador, liberando hilos en espera. Un semáforo binario (0 o 1) permite implementar       exclusión mutua, mientras que un semáforo contador limita cuántos hilos pueden acceder a la vez a un recurso.
  Monitores
    Son construcciones de sincronización de alto nivel que combinan datos compartidos y métodos que operan sobre ellos.         Garantizan exclusión mutua: sólo un hilo puede ejecutar un método del monitor a la vez. En Java, se implementa usando         synchronized, lo que hace que Java gestione automáticamente los bloqueos y liberaciones de forma segura.

# 3. Explicacion del codigo
En nuestro sistema, la clase Monitor se encarga de controlar el acceso a los recursos compartidos. Los métodos tomar() y liberar() están declarados como synchronized, lo que asegura que solo un hilo a la vez pueda modificar la cantidad de recursos disponibles, implementando así la exclusión mutua. 
Cuando un usuario intenta tomar un recurso y este no está disponible (recursos == 0), el hilo entra en espera mediante wait(), liberando el monitor para que otros hilos puedan liberar recursos. Al liberar un recurso, notifyAll() despierta a todos los hilos en espera para que puedan competir nuevamente por el recurso.
Por otro lado, la clase Semaforo utiliza un semáforo contador para limitar la cantidad de usuarios que pueden entrar simultáneamente a la zona restringida. La operación acquire() decrementa el contador y bloquea al hilo si la capacidad máxima se ha alcanzado, mientras que release() incrementa el contador y permite que otros hilos accedan. Además, se emplea un semáforo binario (mutex) para proteger la variable compartida ocupantes

# Ejecucion
-- Inicialización del sistema --
Usuarios totales: 5
-- Inicialización del sistema --
Usuarios totales: 5
Recursos disponibles: 3
Capacidad máxima de la zona: 2
Recursos disponibles: 3
Capacidad máxima de la zona: 2

Usuario 1 ocupó un recurso. Recursos disponibles: 2

Usuario 1 ocupó un recurso. Recursos disponibles: 2
Usuario 2 ocupó un recurso. Recursos disponibles: 1
Usuario 1 entró a la zona. Ocupantes: 1
Usuario 2 ocupó un recurso. Recursos disponibles: 1
Usuario 1 entró a la zona. Ocupantes: 1
Usuario 2 entró a la zona. Ocupantes: 2
Usuario 3 ocupó un recurso. Recursos disponibles: 0
Usuario 4 esperando... recurso agotado
Usuario 5 esperando... recurso agotado
Usuario 4 esperando... recurso agotado
Usuario 5 esperando... recurso agotado
Usuario 1 salió de la zona. Ocupantes: 1
Usuario 3 entró a la zona. Ocupantes: 2
Usuario 1 liberó un recurso. Recursos disponibles: 1
Usuario 4 ocupó un recurso. Recursos disponibles: 0
Usuario 2 salió de la zona. Ocupantes: 1
Usuario 4 entró a la zona. Ocupantes: 2
Usuario 2 salió de la zona. Ocupantes: 1
Usuario 4 entró a la zona. Ocupantes: 2
Usuario 5 esperando... recurso agotado
Usuario 5 esperando... recurso agotado
Usuario 2 liberó un recurso. Recursos disponibles: 1
Usuario 5 ocupó un recurso. Recursos disponibles: 0
Usuario 3 salió de la zona. Ocupantes: 1
Usuario 3 liberó un recurso. Recursos disponibles: 1
Usuario 5 entró a la zona. Ocupantes: 2
Usuario 5 salió de la zona. Ocupantes: 1
Usuario 5 ocupó un recurso. Recursos disponibles: 0
Usuario 3 salió de la zona. Ocupantes: 1
Usuario 3 liberó un recurso. Recursos disponibles: 1
Usuario 5 entró a la zona. Ocupantes: 2
Usuario 5 salió de la zona. Ocupantes: 1
Usuario 3 salió de la zona. Ocupantes: 1
Usuario 3 liberó un recurso. Recursos disponibles: 1
Usuario 5 entró a la zona. Ocupantes: 2
Usuario 5 salió de la zona. Ocupantes: 1
Usuario 5 entró a la zona. Ocupantes: 2
Usuario 5 salió de la zona. Ocupantes: 1
Usuario 5 salió de la zona. Ocupantes: 1
Usuario 5 liberó un recurso. Recursos disponibles: 2
Usuario 4 salió de la zona. Ocupantes: 0
Usuario 4 liberó un recurso. Recursos disponibles: 3

Todos los usuarios terminaron.
Recursos disponibles: 3
Ocupantes en zona: 0

# Conclusión

Durante la práctica se implementaron y probaron dos enfoques para manejar la concurrencia en Java, uno utilizando monitores con synchronized y otro usando semáforos. Se comprobó que, sin exclusión mutua, operaciones simples sobre recursos compartidos, como incrementar un contador, pueden generar resultados erróneos debido a condiciones de carrera.

La implementación con monitores resultó muy sencilla, ya que Java se encarga automáticamente de bloquear y desbloquear el recurso cuando es necesario, evitando errores comunes. Por su parte, los semáforos permiten controlar de manera precisa cuántos hilos pueden usar un recurso al mismo tiempo, evitando que se sobrecargue y asegurando que no haya conflicto entre hilos.

En conclusión, esta práctica reforzó que el uso de mecanismos de exclusión mutua es fundamental para el desarrollo de software concurrente. Independientemente de cómo el sistema operativo alterne la ejecución de los hilos, los semáforos y monitores aseguran que el resultado del programa sea seguro y preciso, manteniendo la integridad de los datos y evitando errores por condiciones de carrera.

