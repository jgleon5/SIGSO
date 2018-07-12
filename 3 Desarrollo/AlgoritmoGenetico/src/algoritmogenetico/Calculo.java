/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmogenetico;
import java.io.Console;
import java.io.File;
import java.util.Random;


/**
 *
 * @author william
 */
public class Calculo {
    //tamaño de la poblacion
        private int POP = 30;
        //geneo tipo
        private int LEN = 10;
        //tasa de mutacion, change it have a play
        private double MUT = 0.1;
        //la tasa de recombinación 
        private double REC = 0.5;
        //cuantas partidas se debe jugar
        private double END = 1000;
        //la variable sumaarg es el resultado final de la suma de las 5 cartas que debera dar 36
        private double SUMTARG = 36;
        //la variable prodtarg es el resultado de multiplicar las 5 cartas para que de como resultado 360
        private double PRODTARG = 360;
        //matriz de genes de 30 miembros, 10 cartas cada uno
        private int[][] gene = new int[30][10];
        //utilizado para crear aleatoriedad (simula el proceso de seleecion) selecciona al azar los genes
        Random aleatorio = new Random();

    public Calculo(){
    }
    public void run()
        {
            //declare pop member a,b, winner and loser
            int a, b, Winner, Loser;
            //initialise the population (randomly)
            init_pop();
            //start a tournament
            for (int tournamentNo = 0; tournamentNo < END; tournamentNo++)
            {
                //pull 2 population members at random
                a = (int)(POP * aleatorio.nextDouble());
                b = (int)(POP * aleatorio.nextDouble());
                //have a fight, see who has best genes
                if (evaluate(a) < evaluate(b))
                {
                    Winner = a;
                    Loser = b;
                }
                else
                {
                    Winner = b;
                    Loser = a;
                }
                //Possibly do some gene jiggling, on all genes of loser
                //again depends on randomness (simulating the 
                //natural selection
                //process of evolutionary selection)
                for (int i = 0; i < LEN; i++)
                {
                    //maybe do some recombination
                    if (aleatorio.nextDouble() < REC)
                        gene[Loser] [i] = gene[Winner][i];
                    //maybe do some muttion
                    if (aleatorio.nextDouble() < MUT)
                        gene[Loser][i] = 1 - gene[Loser][i];
                    //then test to see if the new population member 
                    //is a winner
                    if (evaluate(Loser) == 0.0)
                        display(tournamentNo, Loser);
                }
            }
    }
    private void display(int tournaments, int n)
        {
            System.out.println("\r\n==============================\r\n");
            System.out.println("After " + tournaments + 
                              " tournaments, Solution sum pile " + 
                              "(should be 36) cards are : ");
            for (int i = 0; i < LEN; i++) {
              if (gene[n][i] == 0) {
                System.out.println(i + 1);
              }
            }
            System.out.println("\r\nAnd Product pile " + 
                              "(should be 360)  cards are : ");
            for (int i = 0; i < LEN; i++) {
              if (gene[n][i] == 1) {
                  System.out.println(i + 1);
              }
            }
        }
    private double evaluate(int n)
        {
            //initialise field values
            int sum = 0, prod = 1;
            double scaled_sum_error, scaled_prod_error, combined_error;
            //loop though all genes for this population member
            for (int i = 0; i < LEN; i++)
            {
                //if the gene value is 0, then put it in 
                //the sum (pile 0), and calculate sum
                if (gene[n][i] == 0)
                {
                    sum += (1 + i);
                }
                //if the gene value is 1, then put it in 
                //the product (pile 1), and calculate sum
                else
                {
                    prod *= (1 + i);
                }
            }
             scaled_sum_error = (sum - SUMTARG) / SUMTARG;
            scaled_prod_error = (prod - PRODTARG) / PRODTARG;
            combined_error = Math.abs(scaled_sum_error) + 
                             Math.abs(scaled_prod_error);

            return combined_error;
        }
    private void init_pop()
        {
            //for entire population
            for (int i = 0; i < POP; i++)
            {
                //for all genes
                for (int j = 0; j < LEN; j++)
                {
                    //randomly create gene values
                    if (aleatorio.nextDouble() < 0.5)
                    {
                        gene[i][j] = 0;
                    }
                    else
                    {
                        gene[i][j] = 1;
                    }
                }
            }
        }
    public static void main(String[] args) {
        
        //create a new Microbial GA
            Calculo GA = new Calculo();
            GA.run();
            //read a line, to stop the Console window closing
    }
    
}
