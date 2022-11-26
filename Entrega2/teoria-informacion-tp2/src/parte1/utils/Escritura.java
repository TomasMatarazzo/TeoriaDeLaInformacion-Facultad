package parte1.utils;

import parte1.huffman.Huffman;
import parte1.huffman.MetodosCodigoHuffman;
import parte1.shannonFano.MetodosCodigoShannon;
import parte1.shannonFano.ShannonFano;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

public class Escritura {

    public static void resultadoParte1Huffman(Huffman huffman, int cantSimbolos, int longMaxPalFuente, int longMaxPalCodigo, double tasaCompresion, double rendimiento, double redundancia) {

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/resultados-huffman.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.printf("Tasa compresion: %.4f \n" , tasaCompresion);
            pw.printf("Rendimiento: %.4f \n" , rendimiento);
            pw.printf("Redundancia: %.4f \n" , redundancia);
            pw.printf("Cantidad de simbolos: %d \n", cantSimbolos);
            pw.printf("Longitud maxima de palabra fuente: %d \n", longMaxPalFuente);
            pw.printf("Longitud maxima de palabra codigo: %d \n", longMaxPalCodigo );


            pw.close();

            fichero = new FileWriter("./resultados/primera-parte/compresion.huf");
            pw = new PrintWriter(fichero);

            //Escritura de tabla de codificacion en archivo binario

            Map<String, String> tablaCodigo = huffman.getHuffmanCodes();

            short codigo;
            int palabra;

            for (Map.Entry<String, String> entry : tablaCodigo.entrySet()) {

                palabra = 0b0;
                String palabraTabla = entry.getKey();

                /*
                Tabla de decodificacion compuesta por:
                    Codigo (2 bytes)
                    Longitud de bytes a leer ( 1 byte = Numero al multiplicar: 2 (tam char)  * long pal )
                    Palabra ( 2 bytes (char)  * long pal ) -> Alojada en forma dinamica
                 */
                for (int i = 0; i < palabraTabla.length() ; i++) {
                    if (palabraTabla.charAt(i) == '1') {
                        palabra |= (0b1 << (32 - i));
                    }
                }

                pw.write(palabraTabla);
                System.out.println(palabra);


                codigo = 0b0;
                String codigoTabla = entry.getValue();

                for (int i = 0; i < codigoTabla.length(); i++) {
                    if (codigoTabla.charAt(i) == '1') {
                        codigo |= (0b1 << (15 - i));
                    }
                }

                pw.write(codigo);

            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigoHuf = huffman.getCodigo();

            int limite;

            for (int i = 0; i < codigoHuf.length(); i++) {
                if (i + 8 <= codigoHuf.length())
                    limite = 8;
                else
                    limite = codigoHuf.length() - i;

                byte data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (codigoHuf.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);

            }

            pw.close();

            Escritura.palabrasCodigoHuffman(huffman);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void resultadosParte1Shannon(ShannonFano shannonFano,int cantSimbolos, int longMaxPalFuente, int longMaxPalCodigo, double tasaCompresion, double rendimiento, double redundancia ){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/resultados-shannon.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.printf("Tasa compresion: %.4f \n" , tasaCompresion);
            pw.printf("Rendimiento: %.4f \n" , rendimiento);
            pw.printf("Redundancia: %.4f \n" , redundancia);
            pw.printf("Cantidad de simbolos: %d\n", cantSimbolos);
            pw.printf("Longitud maxima de palabra fuente: %d\n", longMaxPalFuente);
            pw.printf("Longitud maxima de palabra codigo: %d\n", longMaxPalCodigo );

            pw.close();

            fichero = new FileWriter("./resultados/primera-parte/compresion.fan");
            pw = new PrintWriter(fichero);

            //Escritura de tabla de codificacion en archivo binario

            StringBuilder tablaCod = shannonFano.tablaCodificacion();

            byte data;
            int limite;

            for (int i = 0; i < tablaCod.length(); i++) {
                if (i + 8 <= tablaCod.length())
                    limite = 8;
                else
                    limite = tablaCod.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (tablaCod.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);
            }

            //Escritura de codificacion del archivo original en archivo binario

            String codigo = shannonFano.getCodificacion();

            for (int i = 0; i < codigo.length(); i++) {
                if (i + 8 <= codigo.length())
                    limite = 8;
                else
                    limite = codigo.length() - i;

                data = 0b00000000;
                //Lectura del octeto o lo que quede del octeto (codigo.length - i)
                for (int j = 0; j < limite; j++) {
                    if (codigo.charAt(i) == '1') {
                        data |= (0b00000001 << (7 - j));
                    }
                    i++;
                }
                i--; //Con el i++ de la ultima iteracion del for del octeto me salteo una posicion de i

                //Print en file binario del octeto
                pw.write(data);

            }

            pw.close();

            Escritura.palabrasCodigoShannon(shannonFano);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void palabrasCodigoHuffman(Huffman huffman){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/palabras-codigo-huffman.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.println("Palabras codigo asociadas a Huffman \n");

            huffman.getHuffmanCodes().forEach( (pal, code ) -> {

                pw.printf("%s -> %s \n", pal, code);

            } );

            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void palabrasCodigoShannon(ShannonFano shannonFano){

        FileWriter fichero = null;
        try {
            fichero = new FileWriter("./resultados/primera-parte/palabras-codigo-shannon.txt");
            PrintWriter pw = new PrintWriter(fichero);

            pw.println("Palabras codigo asociadas a Shannon-Fano \n");

            ArrayList<String> palabras = shannonFano.getCaracteres2();
            String[] palCodigo = shannonFano.getCodigos();

            for ( int i = 0 ; i < palabras.size() ; i++ ){
                pw.printf("%s -> %s \n", palabras.get(i), palCodigo[i]);
            }

            pw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

}
