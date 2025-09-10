import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class UsaClasses{
    public static void main(String[] args){

        //Obtendo a quantidade de tanks pelo usuario
        Scanner teclado = new Scanner(System.in);
        System.out.println("Digite a quantidade de tanks: [2 - 10]");
        int qtd_tanks = teclado.nextInt();
        teclado.nextLine();
        while(qtd_tanks < 2 || qtd_tanks > 10){
            System.out.println("Digite um valor entre 2 e 10");
            qtd_tanks = teclado.nextInt();
        }
      
        ////Instanciando os tanks de ataque
        ArrayList<Tank> pelotao_tanks = new ArrayList<>();
        for (int i = 0; i < qtd_tanks; i++){
            System.out.println("Digite o nome do tank [ "+ i +" ]" );
            String nome_tank = teclado.nextLine();
            pelotao_tanks.add(new Tank('>' + nome_tank));
        }

        //Exibindo os tanques criandos
        System.out.print("Tanks Criados -> ");
        for(int i = 0; i < qtd_tanks; i++){
            System.out.print(pelotao_tanks.get(i).getName() + " | ");
        }
        System.out.println("\nComeçar a Guerra!!!");
       
        //Iniciando partida
        int rodadas = 0;
        int ataque_anterior = -1;
        while(pelotao_tanks.size() > 1){
            //Selecionando os tanks
            Random rand = new Random();

            //Garantindo que um tank não possa atacar por 2 rodadas seguidas
            int pos_ataque = rand.nextInt(pelotao_tanks.size());
            while(pos_ataque == ataque_anterior){
                pos_ataque = rand.nextInt(pelotao_tanks.size());
            }
            ataque_anterior = pos_ataque;

            //Selecionando Tank que será atacado
            System.out.println("Tank " + pos_ataque + " Pode Atacar um tank de 0 a " + (pelotao_tanks.size()-1));
            int pos_defesa = teclado.nextInt();
            while(pos_defesa == pos_ataque || pos_defesa > pelotao_tanks.size()-1 || pos_defesa < 0){
                System.out.println("Você Selecionou uma posicão inválida, digite novamente");
                pos_defesa = teclado.nextInt();
            }
            Tank tank_ataque = pelotao_tanks.get(pos_ataque);
            Tank tank_defesa = pelotao_tanks.get(pos_defesa);

            //Atacando
            System.out.println("----- Rodada " + rodadas + " ------");
            tank_ataque.fire_at(tank_defesa);
            System.out.println(tank_ataque);
            System.out.println(tank_defesa);
            if(tank_defesa.getAlive() == false){
                pelotao_tanks.remove(pos_defesa);
            }
            rodadas++;
        }
        //Vencedor
        System.out.println("O " + pelotao_tanks.size() + "º vencedor é " + pelotao_tanks.getFirst());
    }
}