import java.util.concurrent.*; 
import java.util.Random;

// ============= MONITOR PARA RECURSO COMPARTIDO =============
class Monitor {
    private int recursos = 3; // cantidad inicial disponible

    public synchronized void tomar(int id) throws InterruptedException {
        while (recursos == 0) {
            System.out.println("Usuario " + id + " esperando... recurso agotado");
            wait();
        }
        recursos--;
        System.out.println("Usuario " + id + " ocupó un recurso. Recursos disponibles: " + recursos);
        Thread.sleep(100);
    }

    public synchronized void liberar(int id) throws InterruptedException {
        recursos++;
        System.out.println("Usuario " + id + " liberó un recurso. Recursos disponibles: " + recursos);
        notifyAll();
        Thread.sleep(100);
    }

    public synchronized int getRecursos() {
        return recursos;
    }
}

// ============= SEMÁFORO PARA ZONA LIMITADA =============
class Semaforo {
    private final Semaphore espacios;
    private final Semaphore mutex = new Semaphore(1);
    private int ocupantes = 0;

    public Semaforo(int capacidad) {
        espacios = new Semaphore(capacidad, true);
    }

    public void entrar(int id) throws InterruptedException {
        espacios.acquire();
        mutex.acquire();
        ocupantes++;
        System.out.println("Usuario " + id + " entró a la zona. Ocupantes: " + ocupantes);
        mutex.release();
        Thread.sleep(new Random().nextInt(1000) + 500);
    }

    public void salir(int id) throws InterruptedException {
        mutex.acquire();
        ocupantes--;
        System.out.println("Usuario " + id + " salió de la zona. Ocupantes: " + ocupantes);
        mutex.release();
        espacios.release();
    }

    public int getOcupantes() throws InterruptedException {
        mutex.acquire();
        int count = ocupantes;
        mutex.release();
        return count;
    }
}

// ============= HILO USUARIO =============
class Usuario extends Thread {
    private final int id;
    private final Monitor recurso;
    private final Semaforo zona;

    public Usuario(int id, Monitor recurso, Semaforo zona) {
        this.id = id;
        this.recurso = recurso;
        this.zona = zona;
    }

    @Override
    public void run() {
        try {
            recurso.tomar(id);
            zona.entrar(id);
            zona.salir(id);
            recurso.liberar(id);
        } catch (InterruptedException e) {
            System.err.println("Usuario " + id + " fue interrumpido");
            Thread.currentThread().interrupt();
        }
    }
}

// Clase main
public class SistemaExclusionMutua {
    public static void main(String[] args) throws InterruptedException {
        int recursosIniciales = 3;       // recursos disponibles
        int capacidadZona = 2;           // ocupantes máximos en la zona
        int totalUsuarios = 5;           // total de usuarios concurrentes

        // Mostrar resumen inicial
        System.out.println("-- Inicialización del sistema --");
        System.out.println("Usuarios totales: " + totalUsuarios);
        System.out.println("Recursos disponibles: " + recursosIniciales);
        System.out.println("Capacidad máxima de la zona: " + capacidadZona + "\n");

        Monitor recurso = new Monitor();
        Semaforo zona = new Semaforo(capacidadZona);

        Usuario[] usuarios = new Usuario[totalUsuarios];

        for (int i = 0; i < totalUsuarios; i++) {
            usuarios[i] = new Usuario(i + 1, recurso, zona);
            usuarios[i].start();
            Thread.sleep(100);
        }

        for (Usuario u : usuarios) {
            u.join();
        }

        System.out.println("\nTodos los usuarios terminaron.");
        System.out.println("Recursos disponibles: " + recurso.getRecursos());
        System.out.println("Ocupantes en zona: " + zona.getOcupantes());
    }
}
