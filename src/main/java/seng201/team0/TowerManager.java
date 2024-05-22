package seng201.team0;

import seng201.team0.models.Player;
import seng201.team0.models.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TowerManager {

    private List<Tower> towerList;
    private final List<Tower> defaultTowers = new ArrayList<>();


    public TowerManager(){


        defaultTowers.addAll(List.of(
                new Tower("Piglet Palace", 10, "Pigs", 30, 1, 120.00, "Reserve"),
                new Tower("Cowtopia Castle", 8, "Cows", 25, 1,140.00, "Reserve"),
                new Tower("Haybale Haven", 12, "Hay", 27, 1, 135.00,"Reserve"),
                new Tower("Timber Turret", 7, "Timber", 24, 1, 140.00,"Reserve"),
                new Tower("Water Tower ", 6, "Water", 25, 1, 130.00,"Reserve"),
                new Tower("Steel Steeple", 11, "Steel", 32, 1, 125.00,"Reserve"),
                new Tower("Corn Castle", 7, "Corn", 29, 1, 150.00,"Reserve"),
                new Tower("Wheat Tower", 9, "Wheat", 28, 1, 130.00,"Reserve"),
                new Tower("Chicken Coop", 12, "Chickens", 27, 1, 130.00,"Reserve")));
    }

    public List<Tower> getTowerList(){
        return towerList;
    }
    public void setTowerList(List<Tower> towerList){
        this.towerList = towerList;
    }

    public List<Tower> getDefaultTowers(){
        return defaultTowers;
    }

    public void setTowerStatus(Tower tower) {
        if (towerList.contains(tower)){
            tower.setTowerStatus("In-Game");
        }else{
            tower.setTowerStatus("Reserve");
        }
    }


}
