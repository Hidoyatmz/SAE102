/**
 * @STATUS          : 100% COMPLETED;
 * @TODO            : Rien ?
 * @OPTIMIZATION    : NOT DONE
 */

import extensions.*;
class EnglishGame extends GeographyGame {
    final String ENGLISHGAMECSV = "englishgame.csv";
    final int MAXROUNDS = 5;
    final int MINSCORE = 3;

    Epreuve initEnglishGame() {
        return newEpreuve(11, "EnglishGame", -1, "Jouez au jeu.", GameState.KEYS);
    }

    boolean startEnglishGame(Epreuve epreuve) {
        CSVFile englishCSV = myLoadCSV(ENGLISHGAMECSV);
        final int CSVMAXROUNDS = rowCount(englishCSV) - 1;
        boolean goodAnswer;
        int score = 0;
        int round = 1;
        int[] played = new int[MAXROUNDS];
        String[] nextQuestion;

        do {
            myClearScreen();
            nextQuestion = getNextQuestion(englishCSV, CSVMAXROUNDS, played, round);
            goodAnswer = askUserQuestion(nextQuestion, round, score) ? true : false;
            if(goodAnswer){
                playSound(SOUND_CORRECT_ANSWER_2,true);
                println(ANSI_LIGHT_GREEN + "Félicitation ! Vous gagnez un point :)" + ANSI_RESET);
                score++;
            } else {
                playSound(SOUND_WRONG_ANSWER,true);
                println(ANSI_LIGHT_RED + "Quel dommage ! La réponsé était " + ANSI_LIGHT_CYAN + nextQuestion[2] + ANSI_RESET);
            }
            delay(2000);
            round++;
        } while((round <= MAXROUNDS) && (score < MINSCORE) && canWin(score, round));

        return score >= MINSCORE;
    }

    boolean askUserQuestion(String[] nextQuestion, int round, int score) {
        boolean res = false;
        String type = stringToInt(nextQuestion[0]) == 1 ? "Français"  : "Anglais";
        type = ANSI_LIGHT_CYAN + type + ANSI_RESET;
        String answer;
        println("Score actuel : " + ANSI_LIGHT_PINK + score + "/" + MINSCORE + ANSI_RESET);
        println("Pour la question n°" + ANSI_LIGHT_BLUE + round + ANSI_RESET + ", traduisez en " + type + " le mot suivant : " + ANSI_LIGHT_BLUE + nextQuestion[1] + ANSI_RESET);
        answer = enterText();
        if(equals(toLowerCase(nextQuestion[2]), toLowerCase(answer))){
            res = true;
        }
        return res;
    }

    boolean canWin(int score, int round) {
        return ((MAXROUNDS-round) + (score+1)) >= MINSCORE;
    }

    String[] getNextQuestion(CSVFile englishCSV, int CSVMAXROUNDS, int[] played, int round) {
        String[] res = new String[3];
        int r;
        do {
            r = randInt(1, CSVMAXROUNDS);
        } while(inArray(played, r));
        res[0] = getCell(englishCSV, r, 0); // type
        res[1] = getCell(englishCSV, r, 1); // question
        res[2] = getCell(englishCSV, r, 2); // answer
        played[(round-1)] = r;
        return res;
    }
}