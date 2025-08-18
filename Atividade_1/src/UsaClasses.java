import java.util.ArrayList;
import java.util.Random;

public class UsaClasses{
    public static void main(String[] args){
        
        //Criando vetores
        String[] nomes_tanks =  {"Jhony", "Jack", "Vitor", "Leticia", "Ana" };
        ArrayList<Tank> pelotao_tanks = new ArrayList<>();
        
        //Instanciando os tanks de ataque
        for (int i = 0; i < 5; i++){
            pelotao_tanks.add(new Tank('>' + nomes_tanks[i]));
        }

        //Iniciando partida
        while(pelotao_tanks.size() > 1){
            //Selecionando aleatóriamente os tanks que não foram mortos
            Random rand = new Random();
            int pos_ataque = rand.nextInt(pelotao_tanks.size());
            
            int pos_defesa = rand.nextInt(pelotao_tanks.size());
            while(pos_defesa == pos_ataque){
                pos_defesa = rand.nextInt(pelotao_tanks.size());
            }

            Tank tank_ataque = pelotao_tanks.get(pos_ataque);
            Tank tank_defesa = pelotao_tanks.get(pos_defesa);

            //Atacando
            tank_ataque.fire_at(tank_defesa);
            System.out.println(tank_ataque);
            System.out.println(tank_defesa);
            if(tank_defesa.getAlive() == false){
                pelotao_tanks.remove(pos_defesa);
            }

        }
        //Vencedor
        System.out.println("O" + pelotao_tanks.size() + "º vencedor é " + pelotao_tanks.getFirst());
    }
}