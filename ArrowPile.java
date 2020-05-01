/*
 * Cierra Ditmore
 * CS 2365
 * Note: double check your remove_arrow method
 */
package bangdicegame;
import java.util.Scanner;

/**
 *
 * @author cmdma
 */
public class ArrowPile {
    public int remaining;
    public int chiefArrow;
    
    public ArrowPile(){
        this.remaining = 9;
        this.chiefArrow = 1;
    }
    
    public void remove_arrow (GameFunctions game){
        Scanner input = new Scanner(System.in);
        char decision = 'n';
        
        if (this.remaining > 0){
            if (this.chiefArrow == 1 && game.expansions && !game.get_current_player().isAi){
                System.out.print("Would you like to draw the chief's arrow? (Y/N): ");
                decision = input.nextLine().charAt(0);
                
                while (decision != 'y' && decision != 'Y' && decision != 'N' && decision != 'n'){
                    System.out.print("Invalid input. Please enter Y or N: ");
                    decision = input.nextLine().charAt(0);
                }
            }
            
            if (decision == 'y' || decision == 'Y'){
                System.out.println("You have taken the chief's arrow.");
                game.get_current_player().gain_arrow();
                game.get_current_player().gain_arrow();
                game.get_current_player().cheifArrow = true;
                this.chiefArrow = 0;
            }
            
            else{
                this.remaining -= 1;
                game.get_current_player().gain_arrow();
                if (this.pileIsEmpty()){
                    this.empty_pile(game, game.numOfPlayers);
                }
            }
        }
        else {
            System.out.println("Remove_arrow Error, no arrows remaining");
        }
    }
    
    /*
    //for ai 
    public void remove_arrow(Character selfPlayer, Character [] playerOrder){
        if (this.remaining > 0){
            this.remaining -= 1; //decrease pile
            selfPlayer.gain_arrow(); //player gets arrow
        }
        else if (this.pileIsEmpty()){
        		System.out.println("======= The Indians Attacked!!! =======");
               //everyone loses as many lives as many arrows that they had and loses all their arrows
            	for (int i=0;i<playerOrder.length;i++) {
            		if (playerOrder[i]!=null) {
	            		int numArrow = playerOrder[i].arrows;
	            		playerOrder[i].lose_life(numArrow);
	            		playerOrder[i].arrows = 0;
            		}
            	}  
            	this.remaining = 9; //set pile back to normal again
       } 
        else {
            System.out.println("Remove_arrow Error, no arrows remaining");
        }
    }
    */
    
    public void add_arrow (Character player){
        this.remaining += 1;
        player.lose_arrow();
    }
    
    public Boolean pileIsEmpty(){
        if (this.remaining == 0){
            return true;
        }
        else {
            return false;
        }
    }
    
    public void empty_pile (GameFunctions game, int totalPlayers){
        int i;
        int playersDead;
        int mostArrowsPosition;
        int mostArrows;
        
        playersDead = 0;
        mostArrowsPosition = 0;
        mostArrows = 0;
        
        System.out.println("\nThe last arrow was drawn and the indians attacked.\n");
        
        
        for (i = 0; i < game.numOfPlayers; i++){
            if (game.playerOrder[i].arrows >= game.playerOrder[i].lifePoints && !game.playerOrder[i].isDead){
                playersDead++;
            }
            if (game.playerOrder[i].arrows > mostArrows){
                mostArrowsPosition = i;
                mostArrows = game.playerOrder[i].arrows;
            }
        }
        
        if (playersDead >= game.numOfPlayers){
            game.game_over = true;
            System.out.println("All players are dead, so the outlaws win.");
        }
        
        if (game.playerOrder[mostArrowsPosition].cheifArrow == true){
            System.out.println(game.playerOrder[mostArrowsPosition].name + " had the most arrows and the chief's arrow, so they will take no damage.");
            game.playerOrder[mostArrowsPosition].arrows = 0;
        }
        
        
        if (!game.game_over){
            for (i = 0; i < totalPlayers; i++){
                if (!game.game_over){
                   while (game.playerOrder[i].arrows > 0){
                        if ("Jourdonnais".equals(game.playerOrder[i].name)){
                            game.playerOrder[i].lose_life(game, this, true);
                            game.playerOrder[i].arrows = 0;
                        }
                        else if (game.playerOrder[i].lifePoints > 0){
                            game.playerOrder[i].lose_arrow();
                            game.playerOrder[i].lose_life(game, this, true);
                        }
                    }
                    game.playerOrder[i].cheifArrow = false; 
                }
            }
        }
        
        this.remaining = 9;
        this.chiefArrow = 1;
    }
}