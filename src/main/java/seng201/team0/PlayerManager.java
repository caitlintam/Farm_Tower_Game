package seng201.team0;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import seng201.team0.gui.RandomEventController;
import seng201.team0.models.Cart;
import seng201.team0.models.Tower;
import seng201.team0.models.Upgrade;

public class PlayerManager {
    private String name;
    private int currentRoundNumber = 0;
    private int numGameRounds = 0;
    private  int gameDifficulty;
    private final Consumer<PlayerManager> setupScreenLauncher;
    private final Consumer<PlayerManager> towerSetUpScreenLauncher;
    private final Consumer<PlayerManager> homeScreenLauncher;
    private final Consumer<PlayerManager> shopScreenLauncher;
    private final Consumer<PlayerManager> inventoryScreenLauncher;
    private final Consumer<PlayerManager> applyUpgradeScreenLauncher;
    private final Consumer<PlayerManager> chooseRoundDifficultyScreenLauncher;
    private final Consumer<PlayerManager> mainGameScreenLauncher;
    private final Consumer<PlayerManager> wonRoundScreenLauncher;
    private final Consumer<PlayerManager> lostRoundScreenLauncher;
    private final Consumer<PlayerManager> gameCompletionScreenLauncher;
    private final Consumer<PlayerManager> randomEventScreenLauncher;
    private final Runnable clearScreen;
    private double money = 1000.00;
    private List<Tower> towerInventory;
    private List<Upgrade> upgradeInventory;
    private List<Tower> towersInGame;
    private List<Tower> reserveTowers;
    private List<Tower> initialTowerList;
    private int timeOnTrack;
    //private DoubleProperty numRounds;
    private int currentTrackDistance;
    private List<Integer> trackDistanceOptionsList;

    private List<Cart> newCartsInRound;
    private CartManager cartManager;
    private List<Cart> cartsInRound;
    private RandomEventManager randomEventManager;
    private RandomEventController randomEventController;
    private List<Integer> randomEventRoundsList;

    public PlayerManager(Consumer<PlayerManager> setupScreenLauncher, Consumer<PlayerManager> towerSetUpScreenLauncher, Runnable clearScreen, Consumer<PlayerManager> homeScreenLauncher, Consumer<PlayerManager> shopScreenLauncher, Consumer<PlayerManager> inventoryScreenLauncher, Consumer<PlayerManager> applyUpgradeScreenLauncher, Consumer<PlayerManager> chooseRoundDifficultyScreenLauncher, Consumer<PlayerManager> mainGameScreenLauncher, Consumer<PlayerManager> wonRoundScreenLauncher, Consumer<PlayerManager> lostRoundScreenLauncher, Consumer<PlayerManager> gameCompletionScreenLauncher, Consumer<PlayerManager> randomEventScreenLauncher) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.towerSetUpScreenLauncher = towerSetUpScreenLauncher;
        this.homeScreenLauncher = homeScreenLauncher;
        this.shopScreenLauncher = shopScreenLauncher;
        this.clearScreen = clearScreen;
        this.inventoryScreenLauncher = inventoryScreenLauncher;
        this.applyUpgradeScreenLauncher = applyUpgradeScreenLauncher;
        this.chooseRoundDifficultyScreenLauncher = chooseRoundDifficultyScreenLauncher;
        this.mainGameScreenLauncher = mainGameScreenLauncher;
        this.lostRoundScreenLauncher = lostRoundScreenLauncher;
        this.wonRoundScreenLauncher = wonRoundScreenLauncher;
        this.gameCompletionScreenLauncher = gameCompletionScreenLauncher;
        this.towerInventory = new ArrayList<Tower>();
        this.upgradeInventory = new ArrayList<Upgrade>();
        this.towersInGame = new ArrayList<Tower>();
        this.reserveTowers = new ArrayList<Tower>();
        trackDistanceOptionsList = new ArrayList<Integer>();
            trackDistanceOptionsList.add(170);
            trackDistanceOptionsList.add(150);
            trackDistanceOptionsList.add(130);

        this.newCartsInRound = new ArrayList<Cart>();
        this.cartManager = new CartManager(this, new TowerManager());
        this.cartsInRound = cartManager.getCartsInRound();
        this.randomEventScreenLauncher = randomEventScreenLauncher;
        this.randomEventManager = new RandomEventManager(this);


        launchSetupScreen();
    }
    public void setRandomEventRoundsList(){
        randomEventManager.setRandomEventRounds();
        this.randomEventRoundsList = randomEventManager.getRandomEventRounds();
        System.out.println(randomEventRoundsList);
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;}
    public double getMoney(){
        return money;
    }
    public void setMoney(double money){
        this.money = money;
    }
    public List<Tower> getTowerInventory(){
        return towerInventory;
    }
    public List<Upgrade> getUpgradeInventory(){
        return upgradeInventory;
    }

    public int getNumGameRounds(){ return numGameRounds;}
    public void setNumGameRounds(int gameRounds){ this.numGameRounds = gameRounds;}
    public int getCurrentRoundNumber(){ return currentRoundNumber;}
    public void setCurrentRoundNumber(int currentRoundNumber){ this.currentRoundNumber = currentRoundNumber;}
    public int getGameDifficulty(){ return gameDifficulty;}
    public void setGameDifficulty(int gameDifficulty){
        this.gameDifficulty = gameDifficulty;
        // easier (smaller) gamedifficulty = more initialmoney
        setMoney((4-gameDifficulty) * 500);
    }
    public List<Tower> getTowersInGame(){return towersInGame;}
    //public List<Tower> getInitialTowerList(){return initialTowerList;}
  //  public List<Tower> setInitialTowerList(List<Tower> towers){this.initialTowerList = towers;}
    public void launchSetupScreen() {
        setupScreenLauncher.accept(this);
    }
    public void closeSetupScreen(){
        clearScreen.run();
    }
    public void launchTowerSetUpScreen(){
        towerSetUpScreenLauncher.accept(this);
    }
    public void closeTowerSetUpScreen(){
        clearScreen.run();
    }
    public void closeMainScreen() {
        clearScreen.run();
    }
    public void launchHomeScreen(){
        homeScreenLauncher.accept(this);
        // catch if rounds exceeded
    }
    public void launchShopScreen() {
        shopScreenLauncher.accept(this);
    }
    public void launchInventoryScreen(){
        inventoryScreenLauncher.accept(this);
    }
    public void launchApplyUpgradeScreen(){applyUpgradeScreenLauncher.accept(this);}
    public void launchChooseRoundDifficultyScreen(){chooseRoundDifficultyScreenLauncher.accept(this);
    }

    public void launchMainGameScreen() {
        mainGameScreenLauncher.accept(this);
    }
    public void launchRandomEventScreen() {
       randomEventScreenLauncher.accept(this);
    }
    public void launchWonRoundScreen(){
        wonRoundScreenLauncher.accept(this);
    }
    public void launchLostRoundScreen(){lostRoundScreenLauncher.accept(this);
    }
    private void launchGameCompletionScreen() {gameCompletionScreenLauncher.accept(this);
    }

    public void closeChooseRoundDifficultyScreen(){clearScreen.run();}
    public void closeShopScreen(){
        clearScreen.run();
    }
    public void closeApplyUpgradeScreen(){clearScreen.run();}
    public void closeWonRoundScreen(){ clearScreen.run();}
    public void closeLostRoundScreen(){clearScreen.run();}
    public void closeRandomEventScreen(){
        clearScreen.run();
    }


    public void addTowersToInventory(Tower tower) {
        Tower newTower = new Tower(tower.getTowerName(), tower.getTowerResourceAmount(), tower.getTowerResourceType(),
                tower.getTowerReloadSpeed(), tower.getTowerLevel(), tower.getTowerCost(),
                tower.getTowerStatus());
        // Add the new tower object to the inventory
        towerInventory.add(newTower);

        //towerInventory.add(tower);
    }
    public void addUpgradesToInventory(Upgrade upgrade){
        Upgrade newUpgrade = new Upgrade(upgrade.getUpgradeName(), upgrade.getUpgradeCost());
        upgradeInventory.add(newUpgrade);

    }
    public void closeInventoryScreen(){
        clearScreen.run();
    }

    public void removeTowerFromInventory(Tower selectedTower) {
        towerInventory.remove(selectedTower);
    }

    public void removeUpgradeFromInventory(Upgrade selectedUpgrade) {
        upgradeInventory.remove(selectedUpgrade);
    }
    public List<String> getTowersResTypeInGame(){
        List<String> listOfInGameTowerResTypes = new ArrayList<String>();
        for (Tower tower: towersInGame){
            listOfInGameTowerResTypes.add(tower.getTowerResourceType());
        }
        return listOfInGameTowerResTypes;
    }
    public void setTowersInGame() {
        // Filter towerInventory to get only the towers that are in-game
        towersInGame = towerInventory.stream()
                .filter(tower -> tower.getTowerStatus().equals("In-Game"))
                .collect(Collectors.toList());
    }
    // resets the distance options list, to +5, +10, +15 each round
    // call this method in the page between rounds when next clicked. to refresh track dist.
    public void updateTrackDistanceOptionsList(){
        trackDistanceOptionsList.set(0,currentTrackDistance - 2);
        trackDistanceOptionsList.set(1,currentTrackDistance - 5);
        trackDistanceOptionsList.set(2,currentTrackDistance - 8);
    }
    // sets the current Track Distance after 'difficulty' selected
    public void setInitialTrackDistance(){
        if (currentRoundNumber == 0){
            currentTrackDistance = numGameRounds * 8 + 10;
        }

    }
    public void setCurrentTrackDistance(int selectedDistanceIndex){
        currentTrackDistance = trackDistanceOptionsList.get(selectedDistanceIndex);}
    public int getCurrentTrackDistance(){return currentTrackDistance;}


    // cbb putting at top now
    private int numRoundsWon = 0;
    private int numRoundsLost = 0;
    private boolean roundSuccess = false;
    private boolean gameSuccess = false;
    public void startRound(){
        runRound(currentTrackDistance);
        launchMainGameScreen();

    }

    public List<Integer> getRandomEventsRoundList(){
        return randomEventRoundsList;
    }
    private int earnedMoney;
    public int getEarnedMoney(){
        return earnedMoney;
    }
    public void setEarnedMoney(int currentRoundNumber){
        this.earnedMoney = (currentRoundNumber+1) *12;
    }
    public void evaluateRoundSuccess(){
        if (roundSuccess == true){
            setEarnedMoney(currentRoundNumber);
            System.out.println("money before " + money);
            setMoney(money + earnedMoney);
            System.out.println("money after " + money);
//            setMoney(money+=);
            numRoundsWon += 1;
            launchWonRoundScreen();
        }else{
            System.out.println("No Money eared");
            numRoundsLost += 1;
            launchLostRoundScreen();
        }
    }
    public void setCartsInRound() {
        this.cartsInRound = cartManager.getCartsInRound();
    }
    private String randomText;
    public void toHomeOrRandomEventOrGameFinish() {

        List<Integer> randomEventRounds = randomEventManager.getRandomEventRounds();
        if ((currentRoundNumber+1) > numGameRounds){
            System.out.println("Here! compelte game");
            launchGameCompletionScreen();
        }
        // if current round is a round of a random event, generate the random event
        else if (randomEventRounds.contains(currentRoundNumber)) {
            randomEventManager.generateRandomEvent();

            this.randomText = randomEventManager.getRandomEventText();
            launchRandomEventScreen();
            currentRoundNumber += 1;
        } else {
            currentRoundNumber += 1;
            launchHomeScreen();
        }
    }
    public String getRandomText(){
        return randomText;
    }



    private void evaluateGameProgress(){

    }
    private int currentCartSize;
    // since each round has different track distance
    private String mainGameScreenRoundText;
    public void setMainGameScreenRoundText(String text){
        this.mainGameScreenRoundText = text;
    }
    public String getMainGameScreenRoundText(){
        return mainGameScreenRoundText;
    }
    public void runRound(int trackDistance) {
        mainGameScreenRoundText = "";
        mainGameScreenRoundText += "------- Running Round " + currentRoundNumber +  " ------";

        List<Integer> successfullyFilledCarts = new ArrayList<Integer>();
        List<Integer> failedFilledCarts = new ArrayList<Integer>();
        cartManager.generateNewCartsInGame();
        setCartsInRound();
        mainGameScreenRoundText += "\n num of carts in round" + cartsInRound.size();
        // for each cart;
        for (Cart cart : cartsInRound) {
            currentCartSize = 0;
            mainGameScreenRoundText += "\n\n\n--- Cart " + cart.getCartID() + " -- Primary Resource Type: "+ cart.getPrimaryCartResourceType() + " -- Secondary Resource Type: " + cart.getSecondaryCartResourceType() + " -- Size: "+ cart.getCartSize() + cart.getCartSpeed()+  "  ...is going round the track ---";
       //     System.out.println("--- Cart " + cart.getCartID() + " -- Primary Resource Type: "+ cart.getPrimaryCartResourceType() + " -- Secondary Resource Type: " + cart.getSecondaryCartResourceType() + " -- Size: "+ cart.getCartSize() + cart.getCartSpeed()+  "  ...is going round the track ---");
            // for each tower
            for (Tower tower : towersInGame) {
                // if the resources types match
                if ((Objects.equals(cart.getPrimaryCartResourceType() , tower.getTowerResourceType())) | (Objects.equals(cart.getSecondaryCartResourceType() , tower.getTowerResourceType()))) {
                    mainGameScreenRoundText+= "\nTower: " + tower.getTowerName() + " -- Resource type: " + tower.getTowerResourceType() + " -- Matches with cart: " + cart.getCartID();
   //                 System.out.println("Tower: " + tower.getTowerName() + " -- Resource type: " + tower.getTowerResourceType() + " -- Matches with cart: " + cart.getCartID());
                    // calculate the carts time on the track..  turn time to integer
                    int cartTimeOnTrack = (int) (trackDistance / cart.getCartSpeed());
                    mainGameScreenRoundText += "\nCart is on the Track for " + cartTimeOnTrack + " s\nTower Reload Speed: "+ tower.getTowerReloadSpeed();
     //               System.out.println("cartTimeOnTrack: "+ cartTimeOnTrack);
      //              System.out.println("tower relaod speed: "+ tower.getTowerReloadSpeed());
                    int numTowerReloads = (int) (Math.floorDiv(cartTimeOnTrack, tower.getTowerReloadSpeed()));
                    // for each reload of cart
                    mainGameScreenRoundText += "\nCart is being filled: " + currentCartSize ;
        //            System.out.println("Cart is being filled from current size: " + currentCartSize);
         //           System.out.println("Num tower reloads " + numTowerReloads);
                    for (int i = 0; i <= numTowerReloads; i++) {
                        currentCartSize += tower.getTowerResourceAmount();
                        mainGameScreenRoundText += "\nto " + currentCartSize;
         //               System.out.println("To current size after fill: " + currentCartSize);
                    }
                    mainGameScreenRoundText += "\nCart is filled to "+ currentCartSize + "after " + numTowerReloads + " reloads";
                } else {

      //              System.out.println("Oh no, none of your towers matched cart " + cart.getCartID() + " resource type.");
                }
            }
                    // once done all possible tower reloads, check if filled capacity (>=size) or not ( <size)
        if (currentCartSize >= cart.getCartSize()) {
            mainGameScreenRoundText += "\nYou successfully filled cart \" + cart.getCartID()  ";
            System.out.println("You successfully filled cart " + cart.getCartID()  );
            // adds succesfully filled cart to list
            successfullyFilledCarts.add(cart.getCartID());
            // increase money
  // ////////////////////////////////////////////////// DOTHIS          setMoney(money *= numReloads);///////////////////////////////////////////////////////
            // launch round win screen
            // playerManager.setNumRoundsWon(getNumRoundsWon + 1));
        } else if (currentCartSize < cart.getCartSize()) {
            mainGameScreenRoundText += "\nOh no, none of your towers matched cart " + cart.getCartID() + " resource type.";
            mainGameScreenRoundText += "\nYou didn't manage to fill cart " + cart.getCartID();
            System.out.println("Uh Oh, you didn't manage to fill cart " + cart.getCartID() );
            // adds unsucesfily filled cart to list
            failedFilledCarts.add(cart.getCartID());
            //launch round lose screen
        }
        setMainGameScreenRoundText(mainGameScreenRoundText);

        System.out.println("---------------------------------------------");

            }

        // once all carts have been through round
        // if all carts filled ( failed is empty == true ) won, otherwise false, have a cart not filled
        int numCartsFilled = successfullyFilledCarts.size();
        int numCartsNotFilled = failedFilledCarts.size();
        int numCarts = cartsInRound.size();
        if (numCartsFilled >= ((numCarts/2 ))){
            roundSuccess = true;
        }else{
            roundSuccess = false;
        }

    }
    public void setReserveTowers() {
        for (Tower tower : towersInGame) {
            if (tower.getTowerStatus().equals("Reserve")) { // corrected tower status comparison
                reserveTowers.add(tower);
            }
        }
    }
    public List<Tower> getReserveTowers() {
        return reserveTowers;
    }
    public void setRandomEventText(String text){
        randomEventController.setRandomEventText(text);
    }
}


// need to make round difficulty related to winning money amt.
// harder diff when button clicked, set index to something, times that um by money.