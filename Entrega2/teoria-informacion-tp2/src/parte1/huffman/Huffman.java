package parte1.huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Huffman {

    private Nodo root;
    private final Map<String, Integer> frecPal;
    private final Map<String, String> huffmanCodes;


    public Huffman(Map<String, Integer> frecPal) {
        this.frecPal = frecPal;
        huffmanCodes = new HashMap<>();
    }

    public void encode() {
        //Creo pq, queue ordenados segun prioridad (menor valor, mayor prioridad)
        Queue<Nodo> queue = new PriorityQueue<>();
        //Todas las palabras codigo seran nodos hoja
        frecPal.forEach( (pal, frec) ->
                queue.add(new NodoHoja(pal, frec))
        );
        //Obtengo y saco de la pq las dos frecuencias mas bajas y añado un nuevo nodo con la suma de las frecuencias
        while(queue.size() > 1){
            queue.add(new Nodo(queue.poll(), queue.poll()));
        }
        generarCodigoHuffman(root = queue.poll(), "");
    }

    private void generarCodigoHuffman(Nodo nodo, String codigo){
        if( nodo.getIzq() == null && nodo.getDer() == null ){
            this.huffmanCodes.put( ((NodoHoja) nodo).getPalabra(), codigo );
        }else{
            generarCodigoHuffman( nodo.getIzq(), codigo.concat("0") );
            generarCodigoHuffman( nodo.getDer(), codigo.concat("1") );
        }
    }

    public String getCodigo(){

        String path = "DatosTP2.txt";
        File file = new File(path);
        StringBuilder code = new StringBuilder();
        try {
            Scanner in = new Scanner(file);
            while(in.hasNext()) {
                String word = in.next();
                code.append(huffmanCodes.get(word));
                //System.out.println(word + " -> " +huffmanCodes.get(word));
            }
            in.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return code.toString();

    }

    public double longMedia(){
        double frec;
        double longitud = 0;
        double total = 0;
        for (Map.Entry<String, String> entry : huffmanCodes.entrySet()) {
           frec =  this.frecPal.get(entry.getKey());
           longitud += frec * entry.getValue().length();
           total += frec;
        }

        return longitud/total;
    }

    public StringBuilder tablaCodificacion(){
        StringBuilder tabla = new StringBuilder();

        this.huffmanCodes.forEach((pal, codigo) -> {
                tabla.append(codigo);
            }
        );

        return tabla;
    }

    public Integer longMaxPalabraCod(){
        int longMax = 0;

        for (Map.Entry<String, String> item : this.huffmanCodes.entrySet()) {
            if( item.getValue().length() > longMax )
                longMax = item.getValue().length();
        }

        return longMax;

    }

    public Map<String, String> getHuffmanCodes() {
        return huffmanCodes;
    }
}
