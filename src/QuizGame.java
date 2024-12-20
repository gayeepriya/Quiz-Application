
import java.util.Scanner;

public class QuizGame {

    
	static class Player {
        String name;
        int age;
        int score;
        boolean isReadyToPlay;
        boolean[] lifelinesUsed = new boolean[2]; 

        public Player(String name, int age) {
            this.name = name;
            this.age = age;
            this.score = 0;
            this.isReadyToPlay = false;
            this.lifelinesUsed[0] = false; 
            this.lifelinesUsed[1] = false; 
        }

        public void setReadyToPlay(boolean ready) {
            this.isReadyToPlay = ready;
        }

        public void increaseScore(int amount) {
            this.score += amount;
        }

        public void useLifeline(int lifeline) {
            this.lifelinesUsed[lifeline] = true;
        }

        public boolean isLifelineUsed(int lifeline) {
            return lifelinesUsed[lifeline];
        }

        public int getScore() {
            return score;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your age: ");
        int age = sc.nextInt();
        sc.nextLine(); 

        // Create player object
        Player player = new Player(name, age);

        
        System.out.print("Hello " + player.name + ", are you ready to take the quiz? (yes/no): ");
        String response = sc.nextLine();
        
        if (response.equalsIgnoreCase("no")) {
            System.out.println("Game terminated. Goodbye!");
            return;
        } else if (response.equalsIgnoreCase("yes")) {
            player.setReadyToPlay(true);
            System.out.println("Great! Let's start the quiz.");
        } else {
            System.out.println("Invalid response. Game terminated.");
            return;
        }

        // Displaying  rules
        System.out.println("Rules of the game: ");
        System.out.println("1. You will be asked multiple-choice questions.");
        System.out.println("2. You have two lifelines: ");
        System.out.println("   a) 50-50 (removes two incorrect answers).");
        System.out.println("   b) Phone a Friend (gives a hint).");
        System.out.println("3. You can use each lifeline only once.");
        System.out.println("4. For every correct answer, you win a certain amount of money.");
        System.out.println("5. Wrong answer terminates the game.");

        // Start quiz with first question
        askQuestion(player, sc, 1);
    }

    // Method to ask a question
    public static void askQuestion(Player player, Scanner sc, int questionNumber) {
        String question;
        String[] options = new String[4];
        String correctAnswer;
        int rewardAmount;

        // Define questions, options, and rewards
        if (questionNumber == 1) {
            question = "What is the capital of Italy?";
            options[0] = "Berlin";
            options[1] = "Madrid";
            options[2] = "Paris";
            options[3] = "Rome";
            correctAnswer = "Rome";
            rewardAmount = 100;

        } else {
            System.out.println("No more questions available. Your total score: " + player.getScore());
            return;
        }

        // Ask the question
        System.out.println(question);
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        // Allow player to use lifeline or answer
        System.out.println("Do you want to use a lifeline? (yes/no): ");
        String useLifeline = sc.nextLine();

        if (useLifeline.equalsIgnoreCase("yes")) {
            if (player.isLifelineUsed(0) && player.isLifelineUsed(1)) {
                System.out.println("You have no lifelines left. Please choose an answer.");
            } else {
                System.out.println("Choose your lifeline (1 - 50-50, 2 - Phone a Friend): ");
                int lifelineChoice = sc.nextInt();
                sc.nextLine(); // Consume newline character

                if (lifelineChoice == 1 && !player.isLifelineUsed(0)) {
                    System.out.println("50-50 Lifeline used: Two incorrect answers are removed.");
                    removeTwoIncorrectAnswers(options, correctAnswer);
                    player.useLifeline(0);
                } else if (lifelineChoice == 2 && !player.isLifelineUsed(1)) {
                    System.out.println("Phone a Friend Lifeline used: A hint is provided.");
                    giveHint(correctAnswer);
                    player.useLifeline(1);
                } else {
                    System.out.println("Lifeline not available or invalid choice. Please answer the question.");
                }
            }
        }

        
        System.out.print("Your answer (1-4): ");
        int playerAnswer = sc.nextInt();
        sc.nextLine(); 

        // Check the answer
        if (options[playerAnswer - 1].equalsIgnoreCase(correctAnswer)) {
            System.out.println("Correct answer! You win " + rewardAmount + " points.");
            player.increaseScore(rewardAmount);
            askQuestion(player, sc, questionNumber + 1); // Ask next question
        } else {
            System.out.println("Wrong answer! You lose with a total score of " + player.getScore());
            return;
        }
    }

    
    public static void removeTwoIncorrectAnswers(String[] options, String correctAnswer) {
        System.out.println("Remaining options:");
        int removed = 0;
        for (int i = 0; i < 4; i++) {
            if (!options[i].equalsIgnoreCase(correctAnswer)) {
                options[i] = null;
                removed++;
                if (removed == 2) break;
            }
        }
        
        for (int i = 0; i < 4; i++) {
            if (options[i] != null) {
                System.out.println(i + 1 + ". " + options[i]);
            }
        }
    }

    
    public static void giveHint(String correctAnswer) {
       
        System.out.println("Hint: The first letter of the correct answer is " + correctAnswer.charAt(0) + ".");
    }
}
