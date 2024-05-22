package seng201.team0;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import seng201.team0.models.Tower;
import seng201.team0.PlayerManager;

public class RandomEventManager {
    private PlayerManager playerManager;
    private List<Integer> randomEventRounds;
    private Random random;
    private List<Tower> reserveTowers;
    private String randomEventText;

    public RandomEventManager(PlayerManager playerManager) {
        this.random = new Random();
        this.randomEventRounds = new ArrayList<Integer>();
        this.playerManager = playerManager;
    }

    public boolean isRandomEvent(int currentRound) {
        return randomEventRounds.contains(currentRound);
    }

    public void generateRandomEvent() {
        int eventType = random.nextInt(3);
        switch (eventType) {
            case 0:
                executeLevelIncrease(); //method for increasing tower level
                break;
            case 1:
                executeLevelDecrease(); // method for decreasing tower level
                break;
            case 2:
                executeBreakTower(); // method to break a tower
                // set text in round controller to " tower name has broken "
                break;
        }
    }

    private void executeLevelIncrease() {
        Tower tower = getRandomTower();
        try{
            tower.setTowerLevel(tower.getTowerLevel() + 1);
            System.out.println("Random Event: Tower " + tower.getTowerName() + "increased levels");
            String levelIncreaseText = tower.getTowerName() + "has increased levels to level: " + tower.getTowerLevel();
            this.randomEventText = levelIncreaseText;
        }catch(NullPointerException e){
            System.out.println("Uh Oh, error, no random tower generated");
        }
    }

//    private void executeLevelIncrease() {
//        Tower tower = getRandomTower();
//        if (tower != null) {
//            tower.setTowerLevel(tower.getTowerLevel() + 1);
//            System.out.println("Random Event: Tower " + tower.getTowerName() + "increased levels");
//            String levelIncreaseText = tower.getTowerName() + "has increased levels to level: " + tower.getTowerLevel();
//            this.randomEventText = levelIncreaseText;
//        }
//    }

    private void executeLevelDecrease() {
        Tower tower = getRandomTower();
        try{
            if (tower.getTowerLevel() > 1){
                tower.setTowerLevel(tower.getTowerLevel() - 1);
                System.out.println("Random Event: Tower " + tower.getTowerName() + " decreased levels");
                String levelDecreaseText = "Random Event: " +tower.getTowerName() + " has decreased levels to level: " + tower.getTowerLevel();
                this.randomEventText = levelDecreaseText;
            }else{
                System.out.println("Random Event: Tower can't be decreased below level 1");
                String levelDecreaseText = "Random Event: Tower can't be decreased below level 1";
                this.randomEventText = levelDecreaseText;
            }
        }catch(NullPointerException e){
            System.out.println("Uh Oh, error, no random tower generated");
        }
       //     playerManager.setRandomEventText(levelDecreaseText);
        }


    private void executeBreakTower() {
        Tower tower = getRandomTower();
        try{
            System.out.println("invent size before"+ playerManager.getTowerInventory().size());
            playerManager.removeTowerFromInventory(tower);
            System.out.println("invent size after"+ playerManager.getTowerInventory().size());
            System.out.println("Random Event: " + tower.getTowerName() + " broke and was removed.");
            // set text for random event fxml
            String brokenTowerText = tower.getTowerName() + " broke! it has now been removed from your inventory";
            this.randomEventText = brokenTowerText;
        }catch(NullPointerException e){
            System.out.println("Uh Oh, error, no random tower generated");
        }
    }
    public String getRandomEventText(){
        return randomEventText;
    }

    public List<Integer> getRandomEventRounds() {
        return randomEventRounds;
    }

    private Tower getRandomTower() {
        try {
            double usedTowerProbability = 0.8;
            double regTowerProbability = 0.2;
            if (random.nextDouble() < usedTowerProbability) {
                return playerManager.getTowersInGame().get(random.nextInt(playerManager.getTowersInGame().size()));
            } else {
                return playerManager.getReserveTowers().get(random.nextInt(playerManager.getReserveTowers().size()));
            }
        } catch (NullPointerException e) {
            if (playerManager.getReserveTowers().isEmpty()) { //  to stop invocation error or non postiive bound
                System.out.println("Uh Oh, reserve towers empty");
                return playerManager.getTowersInGame().get(random.nextInt(playerManager.getTowersInGame().size()));
            }return playerManager.getTowersInGame().get(random.nextInt(playerManager.getTowersInGame().size()));
        }
    }
//    private Tower getRandomTower() {
//        if (playerManager.getTowerInventory().isEmpty()) {
//            return null;
//        } else {
//            double usedTowerProbability = 0.8;
//            double regTowerProbability = 0.2;
//            if (random.nextDouble() < usedTowerProbability) {
//                return playerManager.getTowersInGame().get(random.nextInt(playerManager.getTowersInGame().size()));
//            } else {
//                if (playerManager.getReserveTowers().isEmpty()){ //  to stop invocation error or non postiive bound
//                    return playerManager.getTowersInGame().get(random.nextInt(playerManager.getTowersInGame().size()));
//                }else{
//                    return playerManager.getReserveTowers().get(random.nextInt(playerManager.getReserveTowers().size()));
//                }
//            }
//        }
//    }

    public void setRandomEventRounds(){
        List<Integer> randomEventRoundsList = new ArrayList<Integer>();
        List<Integer> potentialRoundsList = new ArrayList<Integer>();
        for (int i=0; i <= playerManager.getNumGameRounds();i++){
            potentialRoundsList.add(i);
        }
        Collections.shuffle(potentialRoundsList);
        for (int i=0; i <= playerManager.getNumGameRounds()/3; i++){
            randomEventRoundsList.add(potentialRoundsList.get(i));
        }
        this.randomEventRounds = randomEventRoundsList;
    }

}


