import com.sun.org.apache.xpath.internal.operations.Bool;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Main {

    //    default T foldUntil(T zero, BiFunction<? super T, ? super T, ? extends T> combine, io.vavr.Function1<T,Boolean> until)
    public static Integer foldUntil2(Integer zero, BiFunction<Integer, Integer, Integer> function, Function1<Integer, Boolean> until, List<Integer> list)
    {
        int[]  position= {0};
        int[] result = {0};
        Boolean[] itsOk = {true};
        list.forEach(x->{
            Boolean validation = until.apply(x);
            System.out.println(x);
            if (validation.booleanValue() == true && itsOk[0].booleanValue())
            {
                result[0] = position[0]+1;
                itsOk[0] = false;
            }
            position[0] = position[0]+1;

        });
        int dropN = (list.size() - result[0]+1)==list.size() ? list.size()-1:list.size() - result[0]+1;
        List<Integer> finalList = list.dropRight(dropN>list.size()?0:dropN);

        return finalList.fold(0,(x,y)->x+y);
    }

    public static Try<Integer> foldUntil(Integer zero, BiFunction<Integer, Integer, Integer> function, Function1<Integer, Boolean> until, List<Integer> list)
    {
        int result = 0;
        for(int i = 0; i<list.size(); i++)
        {
            Boolean validation = until.apply(list.get(i));
            if (validation.booleanValue())
            {
                if (i==0) return Try.failure(new NoSuchFieldException("The first element is the final condition"));
                result = i+1;
                break;
            }
        }
        int dropN = (list.size() - result+1)==list.size() ? list.size()-1:list.size() - result+1;
        List<Integer> finalList = list.dropRight(dropN>list.size()?0:dropN);
        return Try.of(()->finalList.fold(zero,(x,y)->x+y));
    }



   public static void main(String[] args) {
        Futbol futbol = new SeleccionColombia();
        Deporte deporte = new SeleccionColombia();
        List<Integer> lista = List.of(1,2,3);
        lista.fold(0,(x,y)->x+y);
        //Supplier<Boolean> supplier;
        Function1<String,Boolean> function1 = x -> true;
        lista.filter(x->x.equals(x));
        Try<String> trys = Try.of(()->"d");
        Futbol futbol1 = null;

        System.out.println("*********************FUTBOL*********************");
        futbol.expulsarAUnJugador();
        futbol.gritarGol();
        futbol.cambiarColorCamiseta("Azul");
        System.out.println("Futobol: "+futbol.esLatino());
        System.out.println("*******************FUTBOL***********************");

        System.out.println();
        System.out.println("*******************DEPORTE********************");


        deporte.cambiarColorCamiseta("Amarillo");
        System.out.println(deporte.esLatino());

        System.out.println("*******************DEPORTE********************");
        System.out.println();
        System.out.println("************Futbol sin instancia************");
        //futbol1.gritarGol();
        System.out.println("************Futbol sin instancia************");

       System.out.println();
       System.out.println("****************FOLDUNTIL**********************");
        List<Integer> enteros  = List.of(1,2,3,4,5,6,7);
        Try<Integer> resultado = foldUntil(0,(x, y)->x+y, x->x>0,enteros);
        Integer resultado2 = foldUntil2(0,(x, y)->x+y, x->x>0,enteros);
       System.out.println("El resultado de foldUntil es: "+resultado.getOrElse(555252525));
       System.out.println("El resultado de foldUntil2 es: "+resultado2);


    }
}
