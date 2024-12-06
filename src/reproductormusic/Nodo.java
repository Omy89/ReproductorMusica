package reproductormusic;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Nodo {

    String nombre;
    String artista;
    int duracion;
    String musicpath;
    String genero;
    Image image;
    Nodo siguiente;

    public Nodo(String nombre, String artista, int duracion, String musicpath, String genero, String imagepath) {
        this.nombre = nombre;
        this.artista = artista;
        this.duracion = duracion;
        this.musicpath = musicpath;
        this.genero = genero;
        this.siguiente = null;
        this.image = cargarImagen(imagepath);
    }

    private Image cargarImagen(String imagepath) {
        try {
            return ImageIO.read(new File(imagepath));
        } catch (Exception e) {
            return null; 
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getMusicpath() {
        return musicpath;
    }

    public String getGenero() {
        return genero;
    }

    public Image getImage() {
        return image;
    }
    
    
}
