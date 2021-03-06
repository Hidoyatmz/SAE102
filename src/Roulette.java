/**
 * @STATUS          : 80% COMPLETED;
 * @TODO            : Revoir la manière de faire gagner l'utilisateur. (Utiliser un tableau a 2 dimensions 2*X balles ?)
 * @OPTIMIZATION    : NOT DONE
 */

// import extensions.*;
class Roulette extends Fakir {
    final int SIZE = 16;
    Epreuve initRoulette() {
        return newEpreuve(8, "Roulette", -1, "Dans cette épreuve de chance, vous devez miser sur une des trois couleurs proposées.\nSi le curseur s'arrête sur votre couleur, vous remportez la victoire.", GameState.KEYS);
    }

    boolean startRoulette(Game game, Epreuve epreuve) {
        String[] roulette = new String[SIZE];
        String cursor = "⮝" + ANSI_CURSOR_HIDE;
        boolean res = false;
        int tour = 1;
        int tourTodo = randInt(32,80);
        // Pas très propre mais solution "temporaire"
        int[] posBlue = new int[]{1,5,9,13,17,21,25,29};
        fillRoulette(roulette);
        String userChoice = askUserChoice();
        myClearScreen();
        println("Voyons voir si vous avez raison !");
        printRoulette(roulette);
        cusp();
        do {
            if(tour > 1){
                curp();
                clearEOL();
            }
            print(cursor);
            // Check if we need to move cursor for next occurence
            if(!(tour+1 > tourTodo)){
                cursor = moveCursor(cursor,tour);
                delay(getDelay(tour, tourTodo));
            }
            tour = tour + 1;
        } while (tour <= tourTodo);
        playSound("../ressources/sounds/ambiance/prize.mp3");
        println(ANSI_CURSOR_SHOW);
        if(inArray(posBlue,(countSpaceInString(cursor)+1))){
            res = equals(userChoice, ANSI_BLUE) ? true : false;
        } else {
            res = equals(userChoice, ANSI_RED) ? true : false;
        }
        if(countSpaceInString(cursor) == 14){
            res = equals(userChoice, ANSI_GREEN) ? true : false;
            if(res) {
                game.nbKeys += 1;
            }
        }
        delay(2000);
        return res;
    }

    void fillRoulette(String[] roulette) {
        for(int i = 0; i < length(roulette); i++){
            if(i%2 == 0){
                roulette[i] = ANSI_BLUE;
            } else {
                roulette[i] = ANSI_RED;
            }
            if(i == 7) {
                roulette[i] = ANSI_GREEN;
            }
        }
    }

    /**
     * Returns the delay between each movement of the cursor depending on round
     * @param tour      Current round
     * @param tourTodo  Max round
     * @return          Time to wait between each movement of cursor;
     */

    int getDelay(int tour, int tourTodo){
        if(tour < (tourTodo/3)){
            return 100;
        } else if(tour < (tourTodo/2)){
            return 200;
        } else if(tour < (tourTodo/1.5)){
            return 250;
        } else if(tour < (tourTodo/1.2)){
            return 350;
        } else {
            return 500;
        }
    }

    /**
     * Returns the color chosed by the user.
     * @return String being the color chosed by the user.
     */

    String askUserChoice(){
        String choice;
        myClearScreen();
        cursor(0,0);
        int n;
        println("Vous avez le choix entre ces 3 couleurs :\n1. Bleu\n2. Rouge\n3. Vert");
        print("Quelle couleur voulez vous :");
        cursor(6,0);
        do {
            n = enterNumber();
        } while(!isBetween(n, 1, 3));
        if(n == 1) {
            choice = ANSI_BLUE;
        } else if(n ==2){
            choice = ANSI_RED;
        } else {
            choice = ANSI_GREEN;
        }
        return choice;
    }

    /**
     * Display the wheel on the screen.
     * @param roulette wheel to be displayed.
     */

    void printRoulette(String[] roulette) {
        for(int i = 0; i < length(roulette); i++){
            print(roulette[i] + "•"+ ANSI_RESET + " ");
        }
        println();
    }

    /**
     * Returns a string being the cursor String depending on the round by adding whitespaces.
     * @return String being the string to print to display the cursor
     */

    String moveCursor(String cursor, int tour){
        if(tour%SIZE == 0){
            cursor = "⮝";
        } else {
            cursor = "  " + cursor;
        }
        return cursor;
    }

    /**
     * Count the number of whitespace in the given String.
     * @param cursor    String to count whitespaces.
     * @return          int being then number of whitespaces in the String
     * @version         currently bugged ?
     */
    
    int countSpaceInString(String cursor){
        int res = 0;
        for(int i = 0; i < length(cursor); i++){
            if(charAt(cursor, i) == ' '){
                res++;
            }
        }
        return res;
    }
    
}
