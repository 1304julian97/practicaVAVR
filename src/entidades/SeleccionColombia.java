public class SeleccionColombia implements Futbol{
    public SeleccionColombia() {

    }

    @Override
    public void gritarGol(){
        System.out.println("GOOLLLLLL DE COLOMBIA");
    }

    @Override
    public String cambiarColorCamiseta(String color) {
        return "Amarillo";
    }

    @Override
    public Boolean esLatino() {
        return true;
    }




}
