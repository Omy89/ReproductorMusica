package reproductormusic;

import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Lista {

    private Nodo inicio = null;
    private int size = 0;
    private Player reproductor;
    private Thread hiloReproduccion; 
    private boolean enPausa = false; 
    private FileInputStream flujoArchivo; 
    private long longitudTotal; 
    private long posicionPausa; 

    public boolean isEmpty() {
        return inicio == null;
    }

    public void addNodo(Nodo obj) {
        if (obj == null) {
            return;
        }
        if (isEmpty()) {
            inicio = obj;
        } else {
            Nodo tmp = inicio;
            while (tmp.siguiente != null) {
                tmp = tmp.siguiente;
            }
            tmp.siguiente = obj;
        }
        size++;
    }

    public void play(String nombre) {
        if (isEmpty()) {
            return;
        }

        Nodo tmp = inicio;
        while (tmp != null) {
            if (tmp.nombre.equals(nombre)) {
                try {
                    stop();

                    flujoArchivo = new FileInputStream(tmp.musicpath);
                    longitudTotal = flujoArchivo.available();
                    reproductor = new Player(flujoArchivo);

                    hiloReproduccion = new Thread(() -> {
                        try {
                            reproductor.play();
                        } catch (Exception e) {
                        }
                    });

                    hiloReproduccion.start();
                } catch (Exception e) {
                }
                return;
            }
            tmp = tmp.siguiente;
        }
    }

    public void pause() {
        if (reproductor != null && hiloReproduccion != null && hiloReproduccion.isAlive()) {
            try {
                enPausa = true;
                posicionPausa = longitudTotal - flujoArchivo.available();
                reproductor.close();
            } catch (Exception e) {
            }
        }
    }

    public void resume(String nombre) {
        if (enPausa) {
            try {
                flujoArchivo = new FileInputStream(findRuta(nombre));
                flujoArchivo.skip(posicionPausa);
                reproductor = new Player(flujoArchivo);

                hiloReproduccion = new Thread(() -> {
                    try {
                        reproductor.play();
                    } catch (Exception e) {
                    }
                });

                hiloReproduccion.start();
                enPausa = false;
            } catch (Exception e) {
            }
        }
    }

    public void stop() {
        if (reproductor != null) {
            try {
                reproductor.close();
                if (hiloReproduccion != null && hiloReproduccion.isAlive()) {
                    hiloReproduccion.interrupt();
                }
            } catch (Exception e) {
            }
        }
    }

    private String findRuta(String nombre) {
        Nodo tmp = inicio;
        while (tmp != null) {
            if (tmp.nombre.equals(nombre)) {
                return tmp.musicpath;
            }
            tmp = tmp.siguiente;
        }
        return null;
    }
    
    public Nodo findNodo(String nombre) {
    Nodo tmp = inicio;
    while (tmp != null) {
        if (tmp.nombre.equals(nombre)) {
            return tmp;
        }
        tmp = tmp.siguiente;
    }
    return null;  
}
}
