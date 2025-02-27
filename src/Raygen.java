package src;

public class Raygen{
    private boolean isRunning;
    private int currentFrame;
    RayCasterEngine engine;
    CollisionManager collisionManager;
    Player player;
    UI ui;
    Settings settings;
    static Map map;

    public static void main(String[] args){

    }

    public void startGame(){
        isRunning = true;

    }

    public void render(){

    }

    public void stopGame(){
        isRunning = false;
    }
}