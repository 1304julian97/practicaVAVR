public interface Futbol extends  Deporte {


    default void gritarGol() { System.out.println("Goooooooooooooooooooooool"); }

    default int expulsarAUnJugador(){
        System.out.println("El número de jugadores quequeda es 10");
        return 10;
    }

}
