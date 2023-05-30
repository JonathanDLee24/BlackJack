/*
Program Request Derrick Dollesin:

Create a program where users are to play blackjack. 
A random number generator gives the player a card with a value of 1 - 11. 
The program then adds the two numbers and if they are above 21 the player loses immediately. 
If they are less the player is able to ask for another number but if over 21 to the sum then they lose. 
Do the same for a computer player who must ask for another number if sum of first two cards 
are less than 15. Player or computer hit 21 they win, or whoever is closer to 21 wins.

Author: Jonathan Lee
Professor: Gita Faroughi
Class: Sierra College CSCI13
Date: Feb 12 2022

This system works I would like options for multiple decks
*/


import java.util.*;
import java.util.List;
import java.awt.*;
import javax.imageio.ImageIO;//needed for image
import java.awt.image.BufferedImage;//needed to buffer images
import javax.swing.*; //for GUI
import java.awt.event.*; //for actions use
import java.awt.Graphics;
import java.text.*; //text
import java.io.*; //for serializeable
import javax.imageio.ImageIO;//needed for image
import java.awt.image.BufferedImage;//needed to buffer images
import javax.sound.sampled.Clip; //for use with sounds
import javax.sound.sampled.AudioInputStream; //for use with sounds
import javax.sound.sampled.AudioSystem; //for use with sounds

public class blackjackprogramLeeTA
{
   static int player_wins = 0;
   static int dealer_wins = 0;
   static int hand_number = 0;
   static int push = 0;
   static Clip clip;
   static Clip clip_two;
   static AudioInputStream audioInputStream; 
   static AudioInputStream audioInputStream_two;

   public static void main (String[] args)
   {
      //outputimage("9_of_clubs.png"); //test point
      Scanner console = new Scanner (System.in);
      String play = "";
      display(); //wecome screen
      List deck = deck(console); //get a new deck made
      run(deck, play); //run method this does not split basic
      System.out.println("Press Enter to play again or hit Q/q to exit: ");
      play = console.nextLine();
      while (!play.equalsIgnoreCase("q") && deck.size() > 0)
      {
         play = run(deck, play);
         System.out.println();
         System.out.println("Total cards left currently in deck is " + deck.size());
         System.out.println();
         System.out.println("Press Enter to play again or hit Q/q to exit: ");
         play = console.nextLine();//fix me add win counters
      }
      System.out.println("Dealer Change of Shift Sorry Come back soon");
      System.out.println("Great game you won "+player_wins+" and Dealer won "+dealer_wins);
      goodBye();
   }


   private static void outputimage(String cardA, String card_name, String name, String cardString, int loca_x, int loca_y, int hand_number)
   {
      String imageholdname = "";
      cardString = cardString.toLowerCase();
      try
      {
         audioInputStream = AudioSystem.getAudioInputStream(new File("cardhit.wav").getAbsoluteFile());
         clip = AudioSystem.getClip();
         clip.open(audioInputStream); 
         clip.start();
      }
      catch (Exception e)
      {
         System.out.println(e);
      } 
      if(cardString.contains("king"))
      {
         int index = cardString.indexOf("king");
         String suite = cardString.substring(index, cardString.length());
         String type = cardString.substring(0, index-1);
         imageholdname = (suite+" "+card_name+"2.png");//for image file pull
      }
      else if(cardString.contains("ace"))
      {
         int index = cardString.indexOf("ace");
         String suite = cardString.substring(index, cardString.length());
         String type = cardString.substring(0, index-1);
         imageholdname = (suite+" "+card_name+".png");//for image file pull
      }
      else if(cardString.contains("jack"))
      {
         int index = cardString.indexOf("jack");
         String suite = cardString.substring(index, cardString.length());
         String type = cardString.substring(0, index-1);
         imageholdname = (suite+" "+card_name+"2.png");//for image file pull
      }
      else if(cardString.contains("queen"))
      {
         int index = cardString.indexOf("queen");
         String suite = cardString.substring(index, cardString.length());
         String type = cardString.substring(0, index-1);
         imageholdname = (suite+" "+card_name+"2.png");//for image file pull
      }
      else
      {
         imageholdname = (cardA+" "+card_name+".png");//for image file pull
      }
      imageholdname = imageholdname.toLowerCase();
      imageholdname = imageholdname.replace(" ", "_");
      JFrame cardwindow = new JFrame();
      cardwindow.dispose(); //dispose or null only frame if still present
      cardwindow.setTitle(name+"'s Hand #"+hand_number); //set title can also be placed when frame is created 
      cardwindow.setSize(1000,1000); //size limits of windows
      cardwindow.setLocation(loca_x, loca_y);
      try
      {
         BufferedImage image = ImageIO.read(new File(""+imageholdname)); //take new buffer of this file so its not static
         ImageIcon icon = new ImageIcon(image); //create image icon with image file
         JLabel label = new JLabel(); //create blank JLabel
         label.setIcon(icon); // set icon in label
         JScrollPane scroll = new JScrollPane(label); //set scroll with label
         scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);//always on scroll not really needed
         scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); //can see scroll bar has no button slide
         cardwindow.add(scroll);//add scroll to JFrame
         cardwindow.pack();//pack for auto windows size
         cardwindow.setVisible(true);//turn on
      }
      catch (Exception es)
      {
      }
   
   }


   /* This method is the run */
   private static String run(List deck, String play)
   {
      int player_total = 0; //sum
      int cardA = 0; //card 1
      int cardB = 0;// card 2
      String cardString = "";
      String stay = "";
      String card_name = "";
      Scanner console = new Scanner (System.in);
      pause();
      cardA = rand(deck); //get first value
      try
      {
         cardString = (""+deck.get(cardA)); //create string
      }
      catch(Exception a)
      {
         return ("q");
      }
      deck.remove(cardA); //kill card in deck
      cardA = check(cardString); //get card value
      card_name = suit(cardString); //get suit
      cardA = aceCheck(cardA); //check for ace
      hand_number++;
      outputimage(""+cardA, card_name, "User", cardString, 130, 100, hand_number);
      System.out.println(cardA+" "+card_name);
      System.out.println("Your first card's value is " + cardA);
      player_total = cardA; // sets players value to fist card
      int count = 2; // counter
      while (player_total < 21 && !stay.equalsIgnoreCase("s")) // Sentinal loop for s
      {
         cardB = rand(deck); //get ramdom value 1-52 for card b
         try
         {
            cardString = (""+deck.get(cardB)); //get card from deck based on random
         }
         catch(Exception b)
         {
            return ("q");
         }
         deck.remove(cardB); // kill this card from deck
         cardB = check(cardString); // get card true value 
         cardB = aceCheck(cardB); //check for value 11
         card_name = suit(cardString);
         System.out.println(cardB+" "+card_name);
         pause();
         outputimage(""+cardB, card_name, "User", cardString, 230, 200, hand_number); 
         player_total += (cardB); //running total for hand
         System.out.println("Your card #"+count+"'s value is: " + cardB);
         System.out.println("Your current hand's total value is: " + player_total);
         if(player_total > 21)
         {
            System.out.println("BUST!!!");
            break;
         }
         else if(player_total == 21)
         {
            System.out.println("21!!!");
            break;
         }
         else
         {
            System.out.print("Hit or Stand? \n-----> > > Press Enter for Hit or S/s to Stand: ");
            stay = console.nextLine();
            count ++;
            System.out.println();  
         }
      }
      int Dealer = computer (player_total, deck); // call computer method and get dealers value
      System.out.println();
      if (Dealer == player_total && Dealer > 21 && player_total > 21)
      {
         System.out.println("Both Bust");
      }
      else if (Dealer > player_total && Dealer <= 21 && Dealer != player_total || player_total > 21)
      {
         System.out.println("House Wins with "+ Dealer +" over your "+ player_total);
         dealer_wins++;
         System.out.println("Try your luck at the slots");
      }
      else if (Dealer == player_total && Dealer <=21)
      {
         System.out.println("Push");
         push++;
      }
      else
      {
         System.out.println("House loses with "+ Dealer +" against your "+ player_total+". Place your next bets");
         player_wins ++;
      }
      return ""; 
   }
   
            
    /* This method is the Computer Dealer based off a Stand 17 game AI for choice and for Ace 1 or 11 on cards after 2nd */ 
   private static int computer(int player_total, List deck)
   {
      int Dealer = 0;
      int DealerCardA = 0;
      int DealerCardB = 0;
      String cardString= "";
      String stay = "";
      System.out.println();
      System.out.println("Dealers Turn, Lets play some blackjack");
      pause();
      DealerCardA = rand(deck); //get radom card from deck
      try
      {
         cardString = (""+deck.get(DealerCardA)); //creat string
      }
      catch(Exception c)
      {
         return 0;
      }
      deck.remove(DealerCardA); // kill card in deck
      DealerCardA = check(cardString); //check the card
      String Dealer_Card_name = suit(cardString);
      System.out.println(DealerCardA+" "+Dealer_Card_name);
      outputimage(""+DealerCardA, Dealer_Card_name, "Dealer", cardString, 1130, 100, hand_number); 
      pause();
      System.out.println("Dealer's first card has a value of " + DealerCardA);
      DealerCardB = rand(deck);               
      try
      {
         cardString = (""+deck.get(DealerCardB)); //creat string
      }
      catch(Exception d)
      {
         return 0;
      }
      deck.remove(DealerCardB); // kill card in deck
      DealerCardB = check(cardString); //check the
      String Dealer_Card_name_B = suit(cardString);
      System.out.println(DealerCardB+" "+Dealer_Card_name_B);
      outputimage(""+DealerCardB, Dealer_Card_name_B, "Dealer", cardString, 1230, 200, hand_number);
      System.out.println("Dealer's hand holds a "+ DealerCardA + " and a " + DealerCardB);
      Dealer = (DealerCardA + DealerCardB);
      System.out.println("Dealer's current card's values are: " + Dealer);
      int count = 2;
      if (Dealer == player_total)
      {
         System.out.println();
         System.out.println("Dealer standing at "+ Dealer);
         return Dealer;
      }
      while (Dealer < 17 && Dealer <= player_total && player_total <= 21) // Stand 17 game
      {
         pause();
         DealerCardB = rand(deck);
         try
         {
            cardString = (""+deck.get(DealerCardB)); //creat string
         }
         catch(Exception f)
         {
            return 0;
         }
         deck.remove(DealerCardB);
         DealerCardB = check(cardString);
         if(DealerCardB == 11)
         {
            if((DealerCardB+Dealer)>21)
            {
               DealerCardB = 1;
            }
         }
         Dealer_Card_name = suit(cardString);
         System.out.println(DealerCardB+" "+Dealer_Card_name);
         outputimage(""+DealerCardB, Dealer_Card_name, "Dealer", cardString, 1330, 300, hand_number);
         Dealer += (DealerCardB);
         System.out.println("Dealer's card #"+count+" value total is: " + DealerCardB);
         count ++;
         System.out.println("Dealer cards show " + Dealer);
         if(Dealer > 21)
         {
            System.out.println("Dealer Bust!!!");
            return Dealer;
         }
         else if(Dealer == 21)
         {
            System.out.println("21!!!");
            return Dealer;
         }
         else if(Dealer >= player_total)
         {
            System.out.println();
            System.out.println("Dealer standing at "+ Dealer);
            return Dealer;
         }
         else if (Dealer < player_total)
         {
            continue;
         }
      }
      System.out.println();
      System.out.println("Dealer standing at "+ Dealer);
      return Dealer;
   }
    
   
            
    /* Creates a starter deck of 52 cards */
   private static List deck(Scanner console)
   {
      System.out.print("Enter your name: ");
      String name = console.nextLine();
      System.out.println();
      System.out.println("Welcome "+name+" Lets play some Blackjack!!!!");
      String[] suit = "Hearts,Diamonds,Clubs,Spades".split(",");
      String[] values ="  ACE,  2,  3,  4,  5,  6,  7,  8,  9,  10,  King,  Queen,  Jack" .split(",");
      List<String> deck = new ArrayList<String>();
      for(String s: suit)
      {
         for(String v: values)
         {
            deck.add(s + v);
         }
      }
      System.out.println("Total Deck Size is " + deck.size());
      System.out.println("The cards in this deck are. . .");
      System.out.println(deck);
      System.out.println();
      System.out.println("Dealer Is Now Shuffling The Deck");
      shufflesounds(1);
      pause();
      return deck;
   }
   
   
   /* This method will play sounds for a deck being shuffled will recursive call the method 2 times */
   private static void shufflesounds(int hold)
   {
      if(hold>2)//recursive break out
         return;
      try
      {
         audioInputStream = AudioSystem.getAudioInputStream(new File("cardshuffle.wav").getAbsoluteFile());
         clip = AudioSystem.getClip(); 
         clip.open(audioInputStream);
         clip.start();
         Thread.sleep(1000);
         audioInputStream_two = AudioSystem.getAudioInputStream(new File("deckhittable.wav").getAbsoluteFile());
         clip_two = AudioSystem.getClip(); 
         clip_two.open(audioInputStream_two);
         clip_two.start();
         Thread.sleep(2000);
         shufflesounds(hold+1);//recursive call 
      }
      catch (Exception e)
      {
         System.out.println(e);
      }
   }     

   
   /* This will generate a new deck future use */
   private static List deckNew()
   {
      String[] suit = "Hearts,Diamonds,Clubs,Spades".split(",");
      String[] values ="  ACE,  2,  3,  4,  5,  6,  7,  8,  9,  10,  King,  Queen,  Jack" .split(",");
      List<String> deck = new ArrayList<String>();
      for(String s: suit)
      {
         for(String v: values)
         {
            deck.add(s + v);
         }
      }
      System.out.println("Total Deck Size is " + deck.size());
      System.out.println("The cards in this deck are. . .");
      System.out.println(deck);
      System.out.println();
      System.out.println("Dealer Is Now Shuffling The Deck");
      pause();
      return deck;
   }

       
   /* This method checks for ACE and lets you pick 1 or 11 */ //fix me needs to block out everything but 1 or 11
   private static int aceCheck(int cardA) //fix me for AI for dealer last item Boolean?
   {
      Scanner console = new Scanner (System.in);
      if (cardA == 11)
      {
         do {
            System.out.print("You now have an ACE. Value 1 or 11 ? :");
            while (!console.hasNextInt()) //data validation system will only accept int based numbers
            {
               System.out.println("Numerical entry only");
               System.out.print("Enter a number only. Please reenter 1 or 11: --> ");
               console.next();
            }
            cardA = console.nextInt();
         } while (cardA != 11 && cardA != 1);
         System.out.print("ACE of ");
      }
      else
      {
      }
      return cardA;
   }
   
   
   /* This method checks deck for values and returns them over element*/
   private static int check(String card)
   {
      String num = card.substring(card.indexOf("  ")+2);
      switch(num)
      {
         case("10"):
         {
            return 10;
         }
         case("9"):
         {
            return 9;
         }
         case("8"):
         {
            return 8;
         }
         case("7"):
         {
            return 7;
         }
         case("6"):
         {
            return 6;
         }
         case("5"):
         {
            return 5;
         }
         case("4"):
         {
            return 4;
         }
         case("3"):
         {
            return 3;
         }
         case("2"):
         {
            return 2;
         }
         case("1"):
         {
            return 1;
         }
         case("King"):
         {
            System.out.print("King with value ");
            return 10;
         }
         case("Queen"):
         {
            System.out.print("Queen with value ");
            return 10;
         }
         case("Jack"):
         {
            System.out.print("Jack with value ");
            return 10;
         }
         case("ACE"):
         {
            System.out.print("!!!!ACE!!!! ");
            return 11;
         }
         default:
         {
            return 10;
         }
      }
   }
   
   
   
   /* This method checks for suite */
   private static String suit(String card)
   {
      String num = card.substring(0, card.indexOf("  "));
      switch(num)
      {
         case("King"):
         {
            return "King of";
         }
         case("Queen"):
         {
            return "Queen of ";
         }
         case("Jack"):
         {
            return "Jack of";
         }
         case("Hearts"):
         {
            return ("of Hearts");
         }
         case("Diamonds"):
         {
            return ("of Diamonds");
         }
         case("Clubs"):
         {
            return ("of Clubs");
         }
         case("Spades"):
         {
            return ("of Spades");
         }
         default:
         {
            return "ACE of";
         }
      }
   }

        

   /* This method returns random based on current size of deck based off deck size will shrink each card issued*/  //fix me make sure it stops after deck size is 1 or asks for deck reshuffle and call deck method.
   private static int rand(List deck)
   {
      Scanner console = new Scanner (System.in);
      Random rand = new Random();
      if(deck.size() > 0)
      {
         int value = rand.nextInt(deck.size());
         return value;
      }
      else
      {
         System.out.println("OUT OF CARDS!!!");
         System.out.println("New Deck being added ");
      }
      return 0;
   }
   
     
   /* This method pauses */
   private static void pause()
   {
      try
      {
         System.out.println();
         System.out.println();
         System.out.print("Sound of cards placed on the table . . . ");
         Thread.sleep(3000);
         System.out.println();
         System.out.println();
      }
      catch(Exception e)
      {
      }
   }
       
           
 
   /* This method displays art This method prints goodbye asci art 
   Art made online from ASCII art creator. 
   Online Ascii Art Creator. (n.d.). Retrieved February 8, 2022, from https://www.ascii-art-generator.org */
   private static void display()
   {
      System.out.println(".......,cl:;:cooo:...........................................'''''',,,,;:c:;::::;;,,'''...................................'cooddddddoodo:.  ..........");
      System.out.println(".....':;',:clooodoc,....................................'''.'''''',cl:;:loccclol:::cc,'..................................,cloodddol:;:odoc'.  ........");
      System.out.println("...':ol;'';loooodddoc,....................................''',,,;;;clc::clllllol::ccc,,,,,,'''.........................';:clloodo:',,,:lodo:..  ......");
      System.out.println(".':looolloooooddddddddl,..........................'',,,'''',,;;;;:::::ccccllllccc::;;,'''',,,,,'''''..................',',:cllooc,''.  ..,llc,.  .....");
      System.out.println(";cllllooolc:,;codddddddd:.....................'',,;;;;,,'''',;::loolccccccccccc:::;;;,,,,,,;ll:;'''''..................',,',;:cc;,,;:;.  ,;'.'...  ...");
      System.out.println(",;cc;;ll;',;'.,;coddddl,....................''',,;;;;:cccccc:::coxko::::;;;:::::;;;,,',,;:;ckXKo,''......................,,. .;ll:;cool;;;'........  .");
      System.out.println("..,;;;:;';ll;',,:oddl,......................'',;;cxOO0KK00Okxo:;okkd:;:oxxkkkd:,,,;:::::::;lkKOl,,'.........................',:cclc',cc:,'.''''....  .");
      System.out.println("....'',;ccc;''',ldl,........................'',;::o0XKOoc:lkkdc,cxkd;:kXXOOKK0d;':odooodo:;lxkd;;ccc;'......................',::',,..'....',,,,..   ..");
      System.out.println("......'',;'..,;:cc,........................''',;;;:xKKx:;:lxko;':oxd;;ldo:lxkkd;,lddc,cooc:lxkdlddoc,'.......................,,'............'..   ....");
      System.out.println(".......'''',,,,,'','.......................''',,;;,ckOOkxxxxxdc,,ldo;,:lodddxdd::ddo;',,,,;dkOOkxl,.........................',',,,,,,,'......   ......");
      System.out.println("....'''''''''''''',,.......................'''',,,,,lkkxl:::lddl;cdoclddl;:oddd::dxo,'col;:xkOOOx:..........................,,.;ccc:;,'....   ........");
      System.out.println(".........'''''''''',,......................''''',,,':dxd;.',cddo::oollddoclddxdlclxdllxOd:lxkolxxo,..........................,'',,'.......  ..........");
      System.out.println("......',,:c,''''''',;'....................'''''''',',oxxolloool:';ll:;:cllc::ccc;,:looolc;ldd:'cxdc'..............',.........';'''....... ............");
      System.out.println("......':::;,,''''',',,....................''''''''''':ooocclooc'.'',,,,'''''''',''..''';ll:,,'.,:::,...........;;';;'',.........''..'.................");
      System.out.println("......';:c:,'''''','';,...................''''''''''',,,''.ckOd,.;cooool:,'.';dkxoc:,.'cdo:..''...............,;;,''''................................");
      System.out.println(".......':c:;,'''''',',;'..............''''''''''',,,''''''.:kOx;'lxxl:odo:'.;oxxllooc',odo,,okkl'............,:,,,,::,'...............................");
      System.out.println("........',;,''''''',,,;,................'''''''',,,,,'''''.;dxd:.:dxdoxxd:,,codc',clc,:dxoldOkl,.............;;',,:clc,...............................");
      System.out.println("............''',,,,'''..............',::,:c,'''''',,,,,,,,',lxd:;dKKOddxd:;:lxxc.',,''lkOOkxl;''''...........;:,',,,,,...................'',,;;::;....");
      System.out.println(".........''''.....................'::;:::cc:c:',,,,,,,,,,,,,cddlcxOx:,lddl::oxkl,lkxc:xK0OOkl;;;,''...........,c;''.............';,;cclloooooooool,...");
      System.out.println(".................................,,',,;;:::;:c;,,,,,,,,,;:;;codl:cooollodol:cokkxkOd;lOXkcdkdl::;,,''............,:'.'..........:d;,lddodooooooooo:...");
      System.out.println(".................................'''',clol;;;:c,',,,,,;cllc;:odo;,;;;;,;::;,,,:ccc:,':oxl,cddoc::;,''''.........................'l:;codddoooooooooc'..");
      System.out.println(".................................'..'';cc:;,,::'',,,,,;coooloooc;,,,,,,,,,,,,,,,'',,,',,;;:cccc:;;,''''..........................co:;looooooooooool,..");
      System.out.println(".......................................''''',;;'',,,,,,;:ccccc::;;;;;,,,,,,,,,,,,,,,,,,;;;::;;;;,,'''''......................'''.::. ,oddl;:ooooooo:..");
      System.out.println(".....................;oc'.........''.......;c'.''',,,,;;;;;;;::::;;;;;;;;;;;;;;;;;;,,,,,,;;;;;,,,''''....................''''',,.,l:;cooo; .;loooooc'.");
   }
   
   /* This method prints goodbye asci art 
   Art made online from ASCII art creator. 
   Online Ascii Art Creator. (n.d.). Retrieved February 8, 2022, from https://www.ascii-art-generator.org */
   private static void goodBye()
   {
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXKK0KK0000OKXK0KXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOkkOOkkkkk0XKOO0NWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX0000K0OOOOKNXKKKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX000KK00000XNXK0KNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWX00KKK0OO00XWNKKKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWMMWNX0KKK00OO00KNXKKXNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWNXKOdlcc:;;,,,,;:::cloxk0KNWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNKOdl:,..         ..  .        ..,:ldOKNWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKko:,.    ............................    .,:oOXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWMMWWXko;.   ............                ............   .;oONWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWWKx:.   ...''...                                 ...''...  'ckXWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWXx:.  ...'....         ...  ...''...''........        ........ .'cONWWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWN0o'   ..''.       .... .,lo;.,;odol' ,ko,'cxo,,:,.....        .'''. .,dXMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNk:.  ..,'..      ..,;;;,',lok0x:;ccdx, ;Oo,:ocoddocdo;;:'..       .'''.  'oKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWWNk:.  .''..        .'ld:,,,':o,,cl:,'''...',,.,:''ld;ckc,:kd;.        ...''.. .oKWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMNOc.  .''.           .'lxl;;;'......         .     .....,;,cdl,.            ..'...'dNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMWKo'  .',.     ..... ....',,''.......       .................................    .,'..;OWMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMMNO:.  .,'.     .';clccclll;'coooloo:,'.    ..,:coollllodlc,',cdlcclxxxdlccdkc'.    .,,. .dNMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMMNx'  .,,..     .,l0Xx;;::d0o':oKWWXo;,..  .':d0XNOl,..':dXN0c,dd;;'lNWWk;'':xc'.     .';' .cKMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMMXo.  .,'. .     .,lXWN0kxl::,;;,kWW0:....  .;OWWMK:'.   .;kWWNo',..'lXWWk;. .....      ..,'. ;0WMMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMMXl.  .,.....     .,',ldxOKWWXkc';OWW0:.....'';0WWMK:..   .,xWWXo'. .'lXMWk;.    .        ..''. ,0WMMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMMNo.  .,.   ...   .':xo;'',,:OWNl.;0WWKc'''',oO:;kXNNOc,..':dKXOl,...,:dNWW0c,'..          . ..'. ,0MMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMWx.  .,.  .....    .ckxlcccclddc',lxkkkocclcoxo;'';cldollccldo:,.  .';lodoool:,'.       ..,,....'. :KMMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMWMWO,  ',....';;,.    ..'....''....................................    .............       ':;,,,. .'..lNMMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMK:  .;'  .'.'',.     .....     ....      ....     ......     .....    ......   .............''....,,..kWMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMWd.  ,;'.....''.'......''''.....''''.....''',''....''''''....',,,''....',,'''...',,,,'''''',,,,''''','.cNMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMNkllldxdooooooooooooooooooooooooooooooooooooooolllllllllllcclloollllllllllllllccllccllllllcccccccccccox0WMMMMMMMMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMWNNOc:::::ccccccc::::::::::;;::::::cllcccccccc::::cc:::;;,,,'',,,,;,'',,,,''''''''''',;,,;,,,,'...',';0WWMWNXK0000KXWWMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMWXXk,::,,'''''''''''''''','''...'''''......''....................... ..........................   .;;;OWWXOxdxxxxxxxkKNWMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMWKXk';;..    ..''...  ...'.. ...,,,','.''',,,,'''..',,;;,'.. .'',;;;,',:::,.  .,;:::;'',,;:c:::'.  ;:;ON0o:cloddddddddONMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMWKKk':c'.  .cxdlc:ol,.;okxl'.:kklcl:lOOdcdOOko:oxclkdllclxOl'';lOOdol:l00o:'.:k0dccoOOolcoKKoloc. .cc,kXl',:cllloddddodKWWMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMW00k':c,. .:OXd;,,cl:cdodKKo;:odddkc:0Xd:oxoxOkkx;xXd:c::xNO:,cloxxk0lcKKo::coOKd::cOXd::oKXo:,....cc'kX: .,;:clloooood0WMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMM00k.:l,.  .cxdlcccccdo::o0kloo::lxl:xko:ldc:coxo;:odolclol;.':ooc:co:cdxl:clc;lolclo:',;cdxoc'....lo,xNx...,;:lloooookNMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMM0Ox.;l::,',,:cllcc::cccllccc:::ccccc:::;:cccc:::;;:clccc:;,,;cllcc::::lolcc::::clcc:;;;:ccccc:;;;;lc;xWNk;''';cooooxONMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMM0Ox.'cllllcccccccllllolllllllllccllllloololllooooooooooooooooooooooooodddoododdooddodxdddddddddddddc,dNWMN0:..oXK0KNWMMWMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMM0Ox.c0dcccc:c::::::::::::ccc::cc:::::cc::::::::c:ccc::::::::::;::;;:;:;;:;;;;;::;:::::;;:::::::::dXk;oNMMMX:.'xWWWMMMMMMMMMMMMM");
      System.out.println("MMWMMMMMMMMMMMMMMMMMMMM0Ok'oX:.   ....'.......'..............,;;;;;;;,,,,,'..',''.....,;::::'.''''''''......'...........cXO;oNMMM0,.,xWWMMMMMMMMMMMMMM");
      System.out.println("MMWMMMMMMMMMMMMMMMMMMMM0Ok'oXl..  .'................  ......,''''''''............  ...,.''''..              .'...'''....cXO:oNMMMx..;OWWMMMMMMMMMMMMMM");
      System.out.println("MMWMMMMMMMMMMMMMMMMMMMM00k'lXl.   ...;;'......''.......,....;:looodolc:,,'..,::;,'.'..:lddoc'..           . .,.,,,,''.''cK0:dNMMWo.'lXWWMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOk'cXo.   .,;c;,,,;;;;::;;;;,''oo,..ck00kxdoolclo::okXXK0k:;''dKWXo....          ,c..'.,;;;;,'''cK0cxWMMX:.,xWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOk':Ko..  .,::,:c:loooooodl;;,:0Ol,.:xl;;:;,:;;ldlcclxxx0Kdl,'x0x:'.',,'''..... .xk;''.,;;;;;,,,:00cdWWW0,.;kWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOk,:0l'....;oOOxo:coodxoooclx0XWKx;.lK0l;odocllc:looodc;cOko,'xKKOxl;''........cOW0ol,.,;;,,;,,,;O0:lNMMk.':OWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOk,:Ol'....okOXKOxolc:ccldkKKKXWKx:.lXNOllcc:::ccodddold0Xxo;'xXWWWN0dl:;;;clx0NWWKkOc.,,,,,,,,',xO:cXMWo.':0WWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOO;:kl'''.'lollcclccccllooloccok0k:.lXK0kdl:;:coO0KXKK0KNWxl,'kKkdxxddddoddxkkkddkOddc.,''',,''.,ox;cXMN:.,lXWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMKOO,;xl'''.'ll:c:,;::c:;;;::;:cd00k:.lkl::ccccccccccllc:ld0xl,'xklcc:::::;:::cc:codklc:','''',''',ld:cXMK,.'lNWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMX0k,;xl'.'.'loccc:cc:clc:;;;:oOXW0x:.cx:;,;:cc::;;;::;;:lkKdc,.xklc::c;:lc;;;,;cokXXooc...''''''',co::KMk...lNWWMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMX0O;;xl'.'.'oxkOOOoc:::::;,co0WWW0d;.;dc::ccccll:;;;;;ld0WNo:'.dOkxxdl::::::,;ldKWWKoxl....'''''',cl:cKWl  .dWWMMMMMMMMMMMMMMMM");
      System.out.println("MMMMMMMMMMMMMMMMMMMMMMMX0O:;dc'...'dkOWWXdcc::::;ccxNWNNOo;.c00OOdc::::::,;olkNWWNl:,.o0NNWOl:;;;;;,:oOWWW0lxo....'''''',cl:cKX;  .kWWMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMX0O::dc'...'dkONNNOxxdddddxx0KO0XOl,.lKNWWk::::::;;:co0WNNXc;,.oKXNW0ollllcllldKWNNO:ol....',,,,.,cl;:0O.  ,KWWMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMX0O::dc'...'dkONNN0kdc;;',cdOkc,dkc'.lKNNWKxxxxxdllooONWNXXc,'.l0XNWNXK0kxdolldKNWNk;ll...',,,,,',:l::0o. .oNWMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0kc:dc,''.'lokOd:.....     .;l,cx:'.c0XNNNNNNNN0dl;,cKWNXK:''.lOK0Oxxdll:cocc0NNXXx,cl...'',,,'.,:l::Ol .lXWWMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0kc:dl,'''';;l:.  .  .       'o0k:..:OKXXXNX0OOO0OdloxdkK0:.'.cxc,,,'',,,co:;coodkd,;:.....''''',:c:;xc  :XWWMMMWMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0k::dc,',',,,:,. ......     .:kXO:'.:OK0OOOOk0NNNXXXNOcdXK:''.cOd,';:,','.';;;:,.;l,',...''..''.';:;,xc  lNWMMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0kc:xl,',',,,;;''......     ,lodo;'.,o::lodkkxkkkkkxxdl:ld,',.;dx:';;;,,,'.','...,,..,.......''..;:;,xc .xWWMMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0kc:xl,..'';:::::;;;,,,,,,,,;;;;;;,.,c;;::::;;::::;;;;;,,;,;'.;:;;;;;;;;;;;;;;;;,,,,,,......''...;c:;kl ,0WWMMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWMMMMMMMMMMMMMWMMN0kccxdc:cc:cc:::::::::::::c:::cc:cccc:cccc:cc::::::::c::cccccccccc::::ccccllcccc:::ccccllcllllccldkl;xl cNWMMMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWWWWWWWWWWMMMMWMMN0kccKXKXXXXKK00OOOOOOOOO00KKKKKKKKKKKKKKK000O000K00O00000OOOOkdoooooooooooooddddooodddddddddooddk0Kx:xl.xWWMMMMMMMMMMMMMMMMMMM");
      System.out.println("WWWWWWWWWWWWWWWWMMMMWMMN0kl:dxdxxddxdddddoooooooooooooooooooollodoooooooolccclcclllllllooooooooooooooooolllllllcclccccccclllcxl;KWMMMMMMMMMMMMMMMMMMMM");
      System.out.println("Thanks for playing!!!");
      //Sound Effect from Pixabay
   }
}
