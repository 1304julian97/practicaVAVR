import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Option;
import org.junit.Test;
import java.util.NoSuchElementException;

import static io.vavr.collection.List.empty;
import static org.junit.Assert.*;

public class Listas {


    /**
     * Lo que sucede cuando se intenta crear un lista de null
     */
    @Test(expected = NullPointerException.class)
    public void testListOfNull() {
        List.of(null);
    }

    /**
     * Lo que sucede cuando se crea una lista vacia y se llama un método
     */
    @Test
    public void testZipOnEmptyList() {
        List<String> list = List.of();
        assertTrue(list.isEmpty());

    }

    @Test
    public void testListaEnlazadaconGet(){
        List<Integer> lista1 = List.of(1,2,3);
        List<Integer> lista2 = lista1.tail().prepend(0);
        Integer integer = lista2.get(2);
        assertEquals(3,integer.intValue());
    }

    @Test
    public void testListaAppendAll(){
        List<Integer> lista = List.of(1,2,3,4);
        lista = lista.appendAll(List.of(5,6,7));
        assertEquals(7,lista.size());
    }

    @Test
    public void testHashCode(){
        // hash code, evalua contenido
        List<Integer> lista = List.of(1,2,3,4,5);
        System.out.println("hash code: "+lista.hashCode());
        List<Integer> lista2 = lista;
        System.out.println("hash code: "+lista2.hashCode());

        assertEquals(lista,lista2);
    }

    @Test
    public void testAsJava(){
        //Tener Cuidado, lista no mutable dentro de lambda
        List<Integer> lista = List.of(1,2,3,4);
        List<Integer> integers = lista.asJava(x -> {
            System.out.println(x.size());
        });

        assertEquals(4,integers.size());
    }

    @Test
    public void testAsJavaMutable(){
        //Tener Cuidado, lista no mutable dentro de lambda
        List<Integer> lista = List.of(1,2,3,4);
        List<Integer> integers = lista.asJavaMutable(x -> {
            x.add(5);
        });
        System.out.println(integers);
        assertEquals(5,integers.size());
    }

    @Test
    public void testDrop(){
        List<Integer> lista =List.of(1,2,3,4);
        List<Integer> integers = lista.drop(3);
        assertEquals(List.of(4),integers);
    }

    @Test
    public void testDropConArgumentoMayorAlNumeroDeElementos(){
        List<Integer> lista = List.of(1,2,3);
        List<Integer> integers = lista.drop(4);
        assertEquals(List.empty(),integers);
    }

    @Test
    public void testConDropRight(){
        List<Integer> lista = List.of(1,2,3,4);
        List<Integer> integers = lista.dropRight(3);
        assertEquals(List.of(1),integers);
    }

    @Test
    public void testConDropUntil(){
        List<Integer> lista = List.of(1,2,3,4);
        List<Integer> integers = lista.dropUntil(x->{
            System.out.println(x);
            return x>=3;});
        assertEquals(List.of(3,4),integers);
    }

    @Test
    public void testConDropWhile(){
        List<Integer> lista = List.of(1,2,3,4);
        List<Integer> integers = lista.dropWhile(x->{
            System.out.println(x);
            return x<=3;});
        assertEquals(List.of(4),integers);
    }


    @Test
    public void testFill()
    {
        int[] i = {0};
        List<Integer> lista1 = List.fill(4,()->{
            i[0]++;
            return i[0];
        });
        assertEquals(List.of(1,2,3,4),lista1);
    }

    @Test
    public void testGroupBy(){

        List<Integer> lista = List.of(1,2,3,4);
        Map<String, List<Integer>> map = lista.groupBy(x -> {
            switch (x){
                case 1:
                    return "uno";
                case 2:
                    return "dos";
                case 3:
                    return "tres";
                default:
                    return null;
            }
        });
        System.out.println(map);
        assertEquals(List.of(1),map.get("uno").get());
        assertNotEquals(Option.none(),map.get(null));
    }

    @Test
    public void testingZip(){
        List<Integer> li = List.of(1,2,3,4);
        List<Integer> l2 = List.of(1,2,3);

        List<Tuple2<Integer,Integer>> zip = li.zip(l2);
        System.out.println("El Zip es:" + zip);

        assertEquals(zip.headOption().getOrElse(new Tuple2(0,0)), new Tuple2(1,1));
    }


    @Test
    public void testHead(){
        List<Integer> list1 = List.of(1,2,3);
        Integer head = list1.head();
        assertEquals(head, new Integer(1));
    }

    @Test
    public void testTail(){
        List<Integer> list1 = List.of(1,2,3);
        List<Integer> expectedTail = List.of(2,3);
        List<Integer> tail = list1.tail();
        assertEquals(tail, expectedTail);
    }

    @Test
    public void testTailListaDeUnElemento(){
        List<Integer> lista = List.of(1);
        List<Integer> listaTail = lista.tail();
        assertTrue(listaTail.isEmpty());
    }

    @Test(expected = NoSuchElementException.class)
    public void testHeadDeUnaListaVacia(){
        List<Integer> lista = List.of();
        lista.head();
    }

    @Test
    public void testHeadOptionDeUnaListaVacia(){
        List<Integer> lista = List.of();
        Option<Integer> enteroOption = lista.headOption();
        int elementoOpcional = enteroOption.getOrElse(3);
        assertEquals(3,elementoOpcional);
    }

    @Test
    public void testHeadOptionDeUnaListaNoVacia(){
        List<Integer> lista = List.of(1,2,3);
        Option<Integer> enteroOption = lista.headOption();
        int elementoOpcional = enteroOption.getOrElse(3);
        assertEquals(1,elementoOpcional);
    }

    @Test
    public void testHeadOptionDeUnaListaVaciaConOptionNone(){
        List<Integer> lista = List.of();
        Option<Integer> enteroOption = lista.headOption();
        assertEquals(enteroOption,Option.none());
    }

    @Test
    public void testZip(){
        List<Integer> list1 = List.of(1,2,3);
        List<Integer> list2 = List.of(1,2,3);
        List<Tuple2<Integer, Integer>> zippedList = list1.zip(list2);
        assertEquals(zippedList.head(), Tuple.of(new Integer(1), new Integer(1)) );
        assertEquals(zippedList.tail().head(), Tuple.of(new Integer(2), new Integer(2)) );
    }

    /**
     * Una Lista es inmutable
     */
    @Test
    public void testListIsImmutable() {
        List<Integer> list1 = List.of(0, 1, 2);
        List<Integer> list2 = list1.map(i -> i);
        assertEquals(List.of(0,1,2),list1);
        assertNotSame(list1,list2);
    }

    public String nameOfNumer(int i){
        switch(i){
            case 1: return "uno";
            case 2: return "dos";
            case 3: return "tres";
            default: return "idk";
        }
    }

    @Test
    public void testMap(){

        List<Integer> list1 = List.of(1, 2, 3);
        List<String> list2 = list1.map(i -> nameOfNumer(i));

        assertEquals(list2, List.of("uno", "dos", "tres"));
        assertEquals(list1, List.of(1,2,3));

    }


    @Test
    public void testFilter(){
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> filteredList = list.filter(i -> i % 2 == 0);
        assertTrue(filteredList.get(0)==2);

    }


    /**
     * Se revisa el comportamiento cuando se pasa un iterador vacio
     */
    @Test
    public void testZipWhenEmpty() {
        List<String> list = List.of("I", "Mario's", "Please", "me");
        List<Tuple2<String, Integer>> zipped = list.zip(empty());
        assertTrue(zipped.isEmpty());
    }

    /**
     * Se revisa el comportamiento cuando se pasa el iterador de otra lista
     */
    @Test
    public void testZipWhenNotEmpty() {
        List<String> list1 = List.of("I", "Mario's", "Please", "me", ":(");
        List<String> list2 = List.of("deleted", "test", "forgive", "!");
        List<Tuple2<String, String>> zipped2 = list1.zip(list2.iterator());
        List<Tuple2<String, String>> expected2 = List.of(Tuple.of("I", "deleted"), Tuple.of("Mario's", "test"),
                Tuple.of("Please", "forgive"), Tuple.of("me", "!"));
        assertEquals(expected2,zipped2);
    }

    /**
     * El zipWithIndex agrega numeracion a cada item
     */
    @Test
    public void testZipWithIndex() {
        List<String> list = List.of("A", "B", "C");
        List<Tuple2<String, Integer>> expected = List.of(Tuple.of("A", 0), Tuple.of("B", 1), Tuple.of("C", 2));
        assertEquals(expected,list.zipWithIndex());
    }

    /**
     *  pop y push por defecto trabajan para las pilas.
     */
    @Test
    public void testListStack() {
        List<String> list = List.of("B", "A");

        assertEquals(
                List.of("A"), list.pop());

        assertEquals(
                List.of("D", "C", "B", "A"), list.push("C", "D"));

        assertEquals(
                List.of("C", "B", "A"), list.push("C"));

        assertEquals(
                List.of("B", "A"), list.push("C").pop());

        assertEquals(
                Tuple.of("B", List.of("A")), list.pop2());
    }

    @Test
    public void popWithEmpty(){
        List<Integer> l1 = List.of();
        Option<List<Integer>> l2 = l1.popOption();
        assertEquals(l2, Option.none());
    }

    @Test
    public void popAndTail(){
        List<Integer> l1 = List.of(1,2,3,4,5);
        assertEquals(l1.tail(),l1.pop());
        assertEquals(l1.tailOption(),l1.popOption());
    }

    @Test
    public void pop2ConListaDeMasDeDosElementos(){
        List<Integer> l1 = List.of(1,2,3,4,5,6,7,8,9,10);
        Tuple2<Integer,List<Integer>> l2 = l1.pop2();
        System.out.println(l2);
        assertEquals(l2._1.intValue(),1);
        assertEquals(l2._2,List.of(2,3,4,5,6,7,8,9,10));
    }

    @Test(expected = NoSuchElementException.class)
    public void pop2ListaVacia()
    {
        List.of().pop2();

    }
    /**
     * Una lista de vavr se comporta como una pila ya que guarda y
     * retorna sus elementos como LIFO.
     * Peek retorna el ultimo elemento en ingresar en la lista
     */
    @Test
    public void testLIFORetrieval() {
        List<String> list = empty();
        //Because vavr List is inmutable, we must capture the new list that the push method returns
        list = list.push("a");
        list = list.push("b");
        list = list.push("c");
        list = list.push("d");
        list = list.push("e");
        assertEquals( List.of("d", "c", "b", "a"), list.pop());
        assertEquals("e", list.peek());
    }

    /**
     * Una lista puede ser filtrada dado un prediacado y el resultado
     * es guardado en una tupla
     */
    @Test
    public void testSpan() {
        List<String> list = List.of("a", "b", "c");
        Tuple2<List<String>, List<String>> tuple = list.span(s -> s.equals("a"));
        assertEquals( List.of("a"), tuple._1);
        assertEquals( List.of("b", "c"), tuple._2);
    }


    /**
     * Validar dos listas con la funcion Takewhile con los predicados el elemento menor a ocho y el elemento mayor a dos
     */
    @Test
    public void testListToTakeWhile() {
        System.out.println("--------------------------------------testListToTakeWhile-------------------------------------");
        List<Integer> myList = List.ofAll(4, 6, 8, 5);
        List<Integer> myListOne = List.ofAll(2, 4, 3);
        List<Integer> myListRes = myList.takeWhile(j -> j < 8);
        System.out.println(myListRes);
        List<Integer> myListResOne = myListOne.takeWhile(j -> j > 2);
        System.out.println(myListResOne);
        assertTrue(myListRes.nonEmpty());
        assertEquals(2, myListRes.length());
        assertEquals( new Integer(6), myListRes.last());
        assertTrue( myListResOne.isEmpty());
        System.out.println("--------------------------------------testListToTakeWhile-------------------------------------");

    }

    @Test
    public void testFold(){
        List<Integer> l1 = List.of(1,2,3,4,5);
        Integer r = l1.fold(0,(acc,el)->acc+el);
        assertEquals(r.intValue(),15);
    }

    @Test
    public void testFoldIzquierdaYDerechaDistintosResultadosDivision(){
        List<Double> l1 = List.of(1.0,2.0,3.0,4.0,5.0);
        Double l = l1.foldLeft(1.0,(acc,el)->acc/el);
        Double r = l1.foldRight(1.0,(el,acc)->el/acc);
        System.out.println("left "+l+" right "+r);
        assertFalse(l.equals(r));
    }


    @Test
    public void testFoldIzquierdaYDerechaDistintosResultadosString(){
        List<String> l1 = List.of("Julian","Carvajal","Montoya");
        String l = l1.foldLeft("",(acc,el)->acc+el);
        String r = l1.foldRight("",(el,acc)->acc+el);
        System.out.println("left "+l+" right "+r);
        assertFalse(l.equals(r));
    }

    /**
     * Se puede separar una lista en ventanas de un tamaño especifico
     */
    @Test
    public void testSliding(){
        List<String> list = List.of(
                "First",
                "window",
                "!",
                "???",
                "???",
                "???");
        assertEquals(List.of("First","window","!"),list.sliding(3).head());
    }

    /**
     * Al dividir una lista en ventanas se puede especificar el tamaño del salto antes de crear la siguiente ventana
     */
    @Test
    public void testSlidingWithExplicitStep(){
        List<String> list = List.of(
                "First",
                "window",
                "!",
                "Second",
                "window",
                "!");
        List<List<String>> windows = list.sliding(3,3).toList(); // Iterator -> List
        assertEquals(
                List.of("Second","window","!"),
                windows.get(1));
        List<List<String>> windows2 = list.sliding(3,1).toList(); // Iterator -> List
        assertEquals(
                List.of("window","!","Second"),
                windows2.get(1));
    }
}
