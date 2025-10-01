# P1-Concurrencia-73319
P1 - Desarrolle un software con concurrencia usando exclusión mutua con monitores y semáforos

#3. Explicacion del codigo
En nuestro sistema, la clase Monitor se encarga de controlar el acceso a los recursos compartidos. Los métodos tomar() y liberar() están declarados como synchronized, lo que asegura que solo un hilo a la vez pueda modificar la cantidad de recursos disponibles, implementando así la exclusión mutua. Cuando un usuario intenta tomar un recurso y este no está disponible (recursos == 0), el hilo entra en espera mediante wait(), liberando el monitor para que otros hilos puedan liberar recursos. Al liberar un recurso, notifyAll() despierta a todos los hilos en espera para que puedan competir nuevamente por el recurso.

Por otro lado, la clase Semaforo utiliza un semáforo contador (espacios) para limitar la cantidad de usuarios que pueden entrar simultáneamente a la zona restringida. La operación acquire() decrementa el contador y bloquea al hilo si la capacidad máxima se ha alcanzado, mientras que release() incrementa el contador y permite que otros hilos accedan. Además, se emplea un semáforo binario (mutex) para proteger la variable compartida ocupantes

#Resultados al compilar:
-- Inicialización del sistema -- 
Usuarios totales: 5 
Recursos disponibles: 3 
Capacidad máxima de la zona: 2 

Usuario 1 ocupó un recurso. Recursos disponibles: 2 
Usuario 2 ocupó un recurso. Recursos disponibles: 1 
Usuario 1 entró a la zona. Ocupantes: 1 
Usuario 2 entró a la zona. Ocupantes: 2 
Usuario 3 ocupó un recurso. Recursos disponibles: 0 
Usuario 4 esperando... recurso agotado 
Usuario 5 esperando... recurso agotado 
Usuario 1 salió de la zona. Ocupantes: 1 
Usuario 3 entró a la zona. Ocupantes: 2 
Usuario 1 liberó un recurso. Recursos disponibles: 1 
Usuario 4 ocupó un recurso. Recursos disponibles: 0 
Usuario 2 salió de la zona. Ocupantes: 1 
Usuario 4 entró a la zona. Ocupantes: 2 
Usuario 5 esperando... recurso agotado 
Usuario 2 liberó un recurso. Recursos disponibles: 1 
Usuario 5 ocupó un recurso. Recursos disponibles: 0 
Usuario 3 salió de la zona. Ocupantes: 1 
Usuario 3 liberó un recurso. Recursos disponibles: 1 
Usuario 5 entró a la zona. Ocupantes: 2 
Usuario 5 salió de la zona. Ocupantes: 1 
Usuario 5 liberó un recurso. Recursos disponibles: 2 
Usuario 4 salió de la zona. Ocupantes: 0 
Usuario 4 liberó un recurso. Recursos disponibles: 3 

Todos los usuarios terminaron. 
Recursos disponibles: 3 
Ocupantes en zona: 0

#Todos los usuarios completaron el ciclo sin ningun problema
