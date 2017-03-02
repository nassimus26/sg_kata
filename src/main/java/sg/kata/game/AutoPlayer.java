package sg.kata.game;

import sg.kata.model.Player;

import java.util.Random;

/**
 * Created by nassimus on 09/07/2016.
 */
public class AutoPlayer extends Player{

    public static AutoPlayer wrap(Player player){
        AutoPlayer autoPlayer = new AutoPlayer();
        autoPlayer.setName(player.getName());
        return autoPlayer;
    }

    private static Random random = new Random();
    private static int PossibleBallPosition = 7; // 0 and 6 are considered outside the stadium
    private boolean lostThePoint = false;

    public boolean hasLostThePoint(){
        return lostThePoint;
    }

    public void setLostThePoint(boolean lostThePoint) {
        this.lostThePoint = lostThePoint;
    }

    public int sendTheService() {
        int shoot = random.nextInt(PossibleBallPosition);
        checkTheShoot(shoot);
        return shoot;
    }

    public void checkTheShoot(int shoot){
        if (!isAValidShoot(shoot))
            this.lostThePoint = true;
    }

    public static boolean isAValidShoot(int shoot){
        return shoot>=1 && shoot<=5;
    }

    public int sendBack(int position) {
        int myposition = random.nextInt(PossibleBallPosition);
        if (myposition-position<=random.nextInt(2)+2)// the ball is near to the player
            return sendTheService();
        this.lostThePoint = true;
        return -1;
    }
}
