package model.utlils;

import model.parte2.codigos.Codigo;
import model.parte2.huffman.Huffman;

import java.io.*;
import java.util.ArrayList;

public class Escritura {

    public static void resultadoIncisoA(double[][] matrizEstados, boolean esMemoriaNula){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/IncisoA.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso A:\n");
            pw.println("# Probabilidades condicionales: \n");
            for( int i = 0 ; i < 3 ; i++ ){
                for( int j = 0 ; j < 3 ; j++ ){
                    pw.printf("Probabilidad (%c | %c): %.4f \n", buscaSimbolo(j),buscaSimbolo(i),matrizEstados[i][j]);
                }
            }
            pw.println("\n# Determinacion del tipo de fuente: \n");

            if (esMemoriaNula)
                pw.println("Fuente de memoria nula");
            else
                pw.println("Fuente de memoria no nula");

            pw.println("\n# Matriz de estados: \n");
            for(int x=0 ; x < matrizEstados.length ; x++) {
                pw.print("|");
                for (int y=0 ; y < matrizEstados[x].length ; y++) {
                    pw.printf("%.3f", matrizEstados[x][y]);
                    if (y != matrizEstados[x].length-1) pw.print("\t");
                }
                pw.println("|");
            }
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoB(double entropiaInicial, double entropiaFuente){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/IncisoB.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.println("Inciso B:\n");
            pw.printf("# Entropia para fuente inicial: %.4f Unidades de orden 3\n", entropiaInicial);
            pw.printf("# Entropia para fuente de orden 20: %.4f Unidades de orden 3\n", entropiaFuente);
            pw.close();
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static char buscaSimbolo(int posicion){
        if( posicion == 0 )
            return 'A';

        if( posicion == 1 )
            return 'B';

        if( posicion == 2 )
            return 'C';

        return '-';
    }

    public static void resultadoIncisoC(boolean esErgodica, double[][] matrizCoefSistEc, double[] vectorEstacionario, Double entropia){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/IncisoC.txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso C:\n");
            pw.println("# Determinacion fuente ergodica o no ergodica: \n");

            if( !esErgodica ){
                pw.println("La fuente no es ergodica\n");
            }else{
                pw.println("La fuente resulta ergodica\n");
                pw.println("# Matriz de coeficientes del sistema de ecuaciones para obtener el vector estacionario: \n");
                for(int x=0 ; x < matrizCoefSistEc.length ; x++) {
                    pw.print("|");
                    for (int y=0 ; y < matrizCoefSistEc[x].length ; y++) {
                        pw.printf("%.3f", matrizCoefSistEc[x][y]);
                        if (y != matrizCoefSistEc[x].length-1) pw.print("\t");
                    }
                    pw.println("|");
                }
                pw.println("\n# Vector estacionario: \n");
                for(int i = 0 ; i < vectorEstacionario.length ; i++){
                    pw.printf("X%d = %.4f \n",i,vectorEstacionario[i]);
                }
                pw.printf("\n# Entropia de la fuente: %.4f Unidades de orden 3", entropia);
            }
            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoA(ArrayList<String> combinaciones, double[] informaciones, double entropia, String tamPalabra){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/" + tamPalabra + "-caracteres/IncisoA-" + tamPalabra + ".txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso A:\n");
            pw.println("# Cantidad de informacion: (En unidades de orden 3)");
            for( int i = 0; i < informaciones.length ; i++){
                pw.printf("%s: %.3f \n", combinaciones.get(i),informaciones[i]);
            }
            pw.printf("# Entropia: %.4f Unidades de orden 3\n", entropia);

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoB(Codigo codigo, String tamPalabra){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/"+tamPalabra+"-caracteres/IncisoB-"+tamPalabra+".txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso B:\n");
            pw.println("# Tipo de codigo obtenido: \n");
            pw.println("Codigo bloque: " + codigo.esCodigoBloque());
            pw.println("Codigo no singular: " + codigo.esNoSingular());
            pw.println("Codigo univocamente decodificable: " + codigo.esUnivocamenteDecodificable());
            pw.println("Codigo instantaneo: " + codigo.esInstantaneo());

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoC(double kraft, double longMedia, boolean esCompacto, String tamPalabra){
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/"+tamPalabra+"-caracteres/IncisoC-"+tamPalabra+".txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso C:\n");
            pw.printf("# Inecuacion de Kraft: %.2f <= 1.00 \n", kraft);
            pw.printf("# Inecuacion de McMillan: %.2f <= 1.00 \n", kraft);
            pw.printf("# Longitud media del codigo: %.2f Unidades de orden 3\n", longMedia);
            if( esCompacto )
                pw.println("# Codigo compacto");
            else
                pw.println("# Codigo no compacto");

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoD(double rendimiento, double redundancia, String tamPalabra){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/"+tamPalabra+"-caracteres/IncisoD-"+tamPalabra+".txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso D:\n");
            pw.printf("# Rendimiento: %.2f %c \n", rendimiento*100,'%');
            pw.printf("# Redundancia: %.2f %c \n", redundancia*100,'%');

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoE1(Huffman huffman, String tamPalabra){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/segunda-parte/"+tamPalabra+"-caracteres/IncisoE1-"+tamPalabra+".txt");
            PrintWriter pw = new PrintWriter(fichero);
            pw.println("Inciso D: Codificacion de simbolos\n");
            pw.printf("Longitud media de la codificacion: %.4f \n\n", huffman.longMedia());
            huffman.getHuffmanCodes().forEach((pal, codigo) ->
                    pw.println(pal + ": " + codigo)
            );

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadoIncisoE2(Huffman huffman, String tamPalabra){

        FileWriter fichero = null;


        try{
            fichero = new FileWriter("./resultados/segunda-parte/"+tamPalabra+"-caracteres/IncisoE2-"+tamPalabra+".dat");
            PrintWriter pw = new PrintWriter(fichero);

            //Escritura de tabla de codificacion en archivo binario

            StringBuilder tablaCod = huffman.tablaCodificacion();

            byte data;
            int limite;

            for( int i = 0 ; i < tablaCod.length() ; i++ ){
                if( i+8 <= tablaCod.length() )
                    limite = 8;
                else
                    limite = tablaCod.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for( int j = 0 ; j < limite ; j++){
                    if( tablaCod.charAt(i) == '1'){
                        data |= (0b00000001 << (7-j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);
            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigo = huffman.getCodigo();

            for(int i = 0; i < codigo.length() ; i++){
                if( i+8 <= codigo.length() )
                    limite = 8;
                else
                    limite = codigo.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for( int j = 0 ; j < limite ; j++){
                    if( codigo.charAt(i) == '1'){
                        data |= (0b00000001 << (7-j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);

            }

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static String toBinary(byte n, int len)
    {
        String binary = "";
        for (long i = (1L << len - 1); i > 0; i = i / 2) {
            binary += (n & i) != 0 ? "1" : "0";
        }
        return binary;
    }







}
