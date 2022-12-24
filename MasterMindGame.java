/* $Title: Master Mind;      $Author: Jina Pak;
   $Course/Section - CPSC1150-3;        $St.# - 100377523;
*/
import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.util.Arrays;

/**
 * This class is made to be Master Mind game.
 * @author Jina Pak
 * @since 3/30, 2022
 */
public class MasterMind {
    /**
     * To turn on cheater mode, set the value of it "true". Set the value "false" if you want to turn off cheater mode.
     */
    static boolean cheaterMode = false;


    /**
     * scanner for getting user inputs from keyboard
     */
    static Scanner keyboard = new Scanner(System.in);



    /**
     * This is the main method where every other methods are merged 
     * @param args is generic parameter for main method.
     */
    public static void main(String[] args){
        userInteraction();
       

    }


    /**
     * In userInteraction method, the program is controlled in a certain order and way of the assignment instruction. 
     * It has all things from beginning to the end of the flow.
     */
    private static void userInteraction(){
        
        char keepGoing;  
        breakLine();
        System.out.println("Hi guest, welcome to Master Mind.");
        breakLine();
        int count = 1;
        int sum = 0;
        int gameCount = 1;
        int[] copyKey; 

       
        do {
            int howMany = numberOfdigits();
            int[] computerKey = generateArray(howMany);
            copyKey = computerKey;
            int guessLimit = chooseLimitation();
            System.out.println("All set! It is your turn now.");
            breakLine();
            int[] userKey = getUserGuess(howMany);
            System.out.println("User guess is " + Arrays.toString(userKey));
            boolean keyCheck = isEqual(computerKey, userKey);
            boolean quit = false;
            
            

            
            while (keyCheck == false && count < guessLimit){
                breakLine();
                int rightPlace = inRightPlaces(computerKey, userKey);
                System.out.println(rightPlace + " of digits in your guess are correct and in their right places.");
                int notInRightPlace = notInRightPlaces(computerKey, userKey);
                System.out.println(notInRightPlace + " of digits in your guess are correct but NOT in their right places.");
                System.out.println("Your tried " + count + " guess(es) so far!");
                System.out.println("You have " + (guessLimit - count) + " out of " + guessLimit + " guess chance(s) left.");
                
                if (count == 3){
                    hint1(copyKey);
                }

                if (count > 3 && count % 3 == 0 && count != guessLimit){
                    hint2(copyKey);
                }
                
                breakLine();
                System.out.println("Let's guess the answer again based on the hints.");
                System.out.println("If you want to stop playing, enter N, if not, just type any letter excluding a word staring N/n.");
                keepGoing = keyboard.next().toUpperCase().charAt(0);
                
            


                if (keepGoing == 'N'){
                    keyCheck = true;
                    quit = true;
                }
                    
                else{
                    
                    userKey = getUserGuess(howMany);
                    System.out.println("User guess is " + Arrays.toString(userKey));
                    keyCheck = isEqual(computerKey, userKey);
                    count ++;
                
                }
                

            }

            if (count == guessLimit && keyCheck == false){
                breakLine();
                System.out.println("Unfortunately, you ran out of your all chances to guess.");
                System.out.println("The answer was " + Arrays.toString(copyKey));
            }

            
            sum += count;
            

            keepGoing = 'N';
            
            if (keyCheck == true && quit == false){
                breakLine();
                gameCount ++;
                System.out.println("You got correct! Congratulations on your success.");
                System.out.println("The answer was " + Arrays.toString(computerKey));
                System.out.println("You tried " + count + " guess(es)");
            }
            if (quit == false){
                breakLine();
                keepGoing = 'Y';
                System.out.println("Do you want to play another round? Please enter any button if you want to play Master Mind again, if not, please enter 'N'. ");
                keepGoing = keyboard.next().toUpperCase().charAt(0);
            }

            count = 1;

        } while(keepGoing != 'N');

        breakLine();
        System.out.println("Thank you for playing Master Mind.");
        System.out.println("Your average performance/tries was : " + (sum / gameCount) + " guess(es) per a game.");
    
    }


    /**
     * This method makes a user choose the number of chances to guess the answer upto to limit the tries. 
     * @return 6 or 8 or 10 or 12
     */
    private static int chooseLimitation(){
        System.out.println("How many guesses do you want to try upto per a game? You can choose only one of 6 / 8 / 10 / 12 guesses");
        int number = keyboard.nextInt();
        while (number != 6 && number != 8 && number != 10 && number != 12 ){
            System.out.println("Only enter 6 or 8 or 10 or 12 for setting your number of guess chances.");
            number = keyboard.nextInt();
        }

        return number;
    }


    /**
     * This is for breaking a line to make a structure more organized
     */
    private static void breakLine(){
        System.out.println();
    }
    

    /**
     * This method makes a user choose of the number of digits in a sequence. The digits of a sequence should be more than 1 and less than 10
     * @return one of numbers from 2 to 10 
     */
    private static int numberOfdigits(){
        
        System.out.println("How many number of digits do you want to guess?");
        int howMany = keyboard.nextInt();
     
        while (howMany > 10 || howMany < 2){
           
            System.out.println("The number of digits should be in the range from 2 to 10.");
            howMany = keyboard.nextInt();
        }


        return howMany;
    }



    /**
     * It generates an answer key. The answer key (sequence) can't have the same digits from each other. Each of the digits in the sequence is unique.
     * @param length is the length oft the answer key
     * @return answer key of computer
     */
    private static int[] generateArray(int length){
        int[] answerKey = new int[length];
        answerKey[0] = (int)Math.floor(Math.random()*10);
        int i = 1;
        
    
        while (i < answerKey.length){
            int digit = (int)Math.floor(Math.random()*10);
            answerKey[i] = digit;
            int j = 0 ;
            while (j < i){
                if (answerKey[i] == answerKey[j]){
                    answerKey[i] = (int)Math.floor(Math.random()*10);
                    j = 0;

                }

                else{
                    j ++;
                }
            }
            i ++;
            
        }

   
        if (cheaterMode){
            System.out.println("The answer key is here : " + Arrays.toString(answerKey));
        }

        return answerKey;
    }


    /**
     * This method gets a user's guess. The user guess is for guessing the computed answer key correctly. 
     * @param howMany limits a length of the sequence of user guesses
     * @return the sequence of user guesses
     */
    private static int[] getUserGuess(int howMany){
        
        int[] userGuess = new int[howMany]; 
        breakLine();
        System.out.println("Enter your guess. All " + howMany + " digits should be different from one another.");
        userGuess[0] = keyboard.nextInt();
       

        while (!isOneDigit(userGuess[0])){
            System.out.println("Enter one digit number, Only a number from 0 to 9 is accepted.");
            userGuess[0] = keyboard.nextInt();
        }

        System.out.println("You entered " + userGuess[0] + ". (" + 1 + "/" + howMany +")");
        for (int i = 1; i < howMany; i ++){
            breakLine();
            System.out.println("Enter your guess.");

            int guess = keyboard.nextInt();

            for (int j = 0; j <= i; j ++){
                while (userGuess[j] == guess){
                    System.out.println("You already entered " + userGuess[j] + " before, try again.");
                    guess = keyboard.nextInt();
                }

                while (!isOneDigit(guess)){
                    System.out.println("Enter one digit number, Only a number from 0 to 9 is accepted.");
                    guess = keyboard.nextInt();
                }
            }
            userGuess[i] = guess;
            
         
         
            System.out.println("You entered " + userGuess[i] + ". (" + (i + 1) + "/" + howMany +")");
        }
        
        return userGuess;

    }


    /**
     * This method decides whether a number has one digit or not.
     * @param number is the subject examined to be determind if it is one digit number by this method
     * @return true - one digit number / false - more than one digit number
     */
    private static boolean isOneDigit(int number){
        int digit = 1 + (int)Math.floor(Math.log10(number));
        if (digit > 1){
            return false;
        }
        
        return true;
    }


    /**
     * Here is the part where two arrays are determined if their elements in each of the arrays are the same.
     * Arrays refer to computer answer and user guess.
     * @param arr1 first array
     * @param arr2 second array
     * @return if they are same - true or not - false
     */
    private static boolean isEqual(int[] arr1, int[] arr2){

        if (Arrays.equals(arr1, arr2)){
            return true;
        }

        return false;
    }


    /**
     * This method will provide information about how many digits are correct and in their right places.
     * @param arr1 should be computer answer key
     * @param arr2 should be user guess
     * @return how many digits are correct in the right places.
     */
    private static int inRightPlaces(int[] arr1, int[] arr2){

        int howMany = 0;
        
        for (int i = 0; i < arr1.length; i ++){
            if (arr1[i] == arr2[i]){
                howMany ++;
            }
        }

        return howMany;
    }



    /**
     * This method will provide information about how many digits are correct but not in their right places. 
     * @param arr1 should be computer answer key
     * @param arr2 should be user guess
     * @return how many digits are correct but not in their right places. 
     */
    private static int notInRightPlaces(int[] arr1, int[] arr2){

        int howMany = 0;

        for (int i = 0; i < arr2.length; i ++){
            int digit = arr2[i];
            if (arr1[i] != arr2[i]){
                for (int j = 0; j < arr1.length; j ++){
                    if (arr1[j] == digit){
                        howMany ++;
                    }
                }
            }
        }

        return howMany;
    }




    /**
     * This hint 1 method activates after 3 tries of user guess.
     * It gives product of whole elements in the answer key.
     * It's useful to get a crucial hint of existence of zero.
     * @param arr1 answerkey
     * @return product of answerkey elements
     */
    private static int hint1(int[] arr1){

        int product = arr1[0];
        for (int i = 1; i < arr1.length; i ++){
            product = product * arr1[i];
        }
        breakLine();
        System.out.println("Good news! A hint is here.");
        System.out.println("You seem like having a hard time. I give you a hint for you.");
        System.out.println("The product of the whole digits from the answer is " + product);
        

        return product;
    }


    /**
     * This method activates whenever a count of tries reaches multiple of 3 excluding 3 tries. When a user tries third times, the user gets hint 1, not this one. 
     * It lets a user know just one element on the answer key at a time. 
     * @param arr1 answerKey
     * @return an element of sequence of the answer key
     */

    private static int hint2(int[] arr1){
        breakLine();
        System.out.println("Good news! Another hint is here.");
        System.out.println("I can give you a glimpse of the answer. Enter only one location on the answer you want to know.");
        System.out.println("For example, if you want to know third digit of the sequence of the answer, enter 3.");
        int wantToKnow = keyboard.nextInt();
        
        while (wantToKnow > arr1.length){
            System.out.println("Enter a number less than and equal to " + arr1.length);
            wantToKnow = keyboard.nextInt();
        }

        
        while (!isOneDigit(wantToKnow)){
            System.out.println("Enter one digit number, Only a number from 0 to 9 is accepted.");
            wantToKnow = keyboard.nextInt();
        }

        int hint = arr1[wantToKnow - 1];

        System.out.println("On the " + wantToKnow + "th(nd/st) location, " + hint + " is sitting.");

        return hint;
    }

}


