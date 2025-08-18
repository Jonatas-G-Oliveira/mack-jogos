public class Tank{
    private String name;
    private boolean alive;
    private int ammo;
    private int armor;
   
    
    public Tank(String name){
        this.setName(name);
        this.setAlive(true);
        this.setArmor(60);
        this.setAmmo(5);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name.length() >= 2){
            this.name = name;
        }else{
            System.out.println("Nome Invalido | Precisa ser > 2");
        }
    }

    public boolean getAlive(){
        return this.alive;
    }

    public void setAlive(Boolean alive){
        this.alive = alive;
    }

    public int getArmor(){
        return this.armor;
    }

    public void setArmor(int armor){
        this.armor = armor;
    }

    public int getAmmo(){
        return this.ammo;
    }

    public void setAmmo(int ammo){
        this.ammo = ammo;
    }

    @Override
    public String toString(){
        if(this.alive){
            return this.name + " ," + this.armor + "armor " + this.ammo + "sheels";
        }else{
            return this.name + "DEAD";
        }
    }
    //Fim de jogo
    public void explode(){
        this.alive = false;
        System.out.println("Explodes");
    }
    
    public void hit(){
        this.armor -= 20;
        System.out.println(this.name + "is hit");
        if(this.armor <= 0){
            this.explode();
        }
    }   

    public void fire_at(Tank enemy){
        if(this.ammo >= 1){
            this.ammo -= 1;
            System.out.println(this.name + "fires on " + enemy.name);
            enemy.hit();
        }else{
            System.out.println(this.name + "has no sheel!!");
        }
    }
}