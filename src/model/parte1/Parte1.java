package model.parte1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import Jama.Matrix;

public class Parte1 {

	public static void main(String[] args) {

		try {
			String path = "DatosTP1.txt";
			File file = new File(path);
			BufferedReader archivo = new BufferedReader(new FileReader(file));
            String datos = archivo.readLine();
            int len = datos.length();
            
            int[][] matrizApariciones = new int[3][3];    //Matriz de apariciones si el simbolo previo a X es A | B | C
            int[]  aparicionesTotales = new int[3];       //Apariciones totales por simbolo
            double[][]  matrizEstados = new double[3][3]; //Matriz de transicion de estados (probabilidades)

			//Inicializacion de contadores
            for(int i=0 ; i<3 ; i++) {
				aparicionesTotales[i] = 0;
            	for(int j=0 ; j<3 ; j++) {
            		matrizApariciones[i][j] = 0;
            	}
            }

			for(int i=1 ; i<len ; i++) {
            	matrizApariciones[BuscaIndice(datos.charAt(i-1))][BuscaIndice(datos.charAt(i))] += 1;
            	aparicionesTotales[BuscaIndice(datos.charAt(i))] += 1;
			}
            
            //Calculo de probabilidades condicionales
            for(int i=0 ; i<3 ; i++) {
            	for(int j=0 ; j<3 ; j++) {
            		matrizEstados[i][j] = (double) matrizApariciones[i][j] / aparicionesTotales[i];
            	}
            }
            
            //Comenzamos con calculo de vector estacionario

			// a*(m[0][0]-1) +    b*m[0][1]     +    c*m[0][2]     =    0
			// a*m[1][0]     +    b*(m[1][1]-1) +    c*m[1][2]     =    0
			// a*m[2][0]     +    b*m[2][1]     +    c*(m[2][2]-1) =    0
			// a             +    b             +    c             =    1

			double[][] matrizCoef = new double[3][3]; //Inicializo el sistema de ec
            double[]   resultados = new double[3];
            
            // Creacion de Matriz de Coeficientes

			int cantVar = 3;

			for(int j = 0 ; j < cantVar ; j++) {
            	matrizCoef[0][j] = 1.; // a + b + c + ... + n
            }
            for(int i = 1 ; i < cantVar ; i++){
				for(int j = 0 ; j < cantVar ; j++) {
					matrizCoef[i][j] = matrizEstados[i][j];
					if(i == j) //Diagonal
						matrizCoef[i][j] -= 1.00;
				}
			}

			System.out.println("Matriz de Coeficientes del sistema");
			for(int x=0 ; x < matrizCoef.length ; x++) {
            	  System.out.print("|");
            	  for (int y=0 ; y < matrizCoef[x].length ; y++) {
            	    System.out.printf("%.3f", matrizCoef[x][y]);
            	    if (y != matrizCoef[x].length-1) System.out.print("\t");
            	  }
            	  System.out.println("|");
			}
			System.out.println();

			// Creacion de Vector Resultado

			resultados[0] = 1.;
			for (int i = 1 ; i < cantVar ; i++){
				resultados[i] = 0.;
			}

			System.out.println("Vector Resultado del sistema");
			System.out.print("[  ");
			for(int h=0; h < cantVar; h++){
				System.out.printf("%.3f  ",resultados[h]);
			}
			System.out.println("]\n");

			//Resolucion del sistema utilizando Eliminacion de Gauss

			EliminacionGauss gauss = new EliminacionGauss();

			gauss.resolver(matrizCoef,resultados);





		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static int BuscaIndice(char i) {
		
		if(i == 'A')
			return 0;     // posicion 0 si es A
		else if(i == 'B')
			return 1;     // posicion 1 si es B
		else
			return 2;     // posicion 2 si es C
	}
	
	

}
