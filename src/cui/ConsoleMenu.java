package cui;

import domain.DomainController;

import java.util.Scanner;

public class ConsoleMenu {
    private DomainController _dc;
    private Scanner _input;

    public ConsoleMenu(DomainController dc, Scanner input){
        this._dc = dc;
        this._input = input;

        while(true) {
            System.out.printf("%nDeze consoleapplicatie kan je gebruiken om datasets te controleren voor je ze in de GUI gebruikt%n");
            System.out.printf("--MUST HAVE--%n%n1. %s%n2. %s%n3. %s%n4. %s%n5. %s%n6. %s%n%n--NICE TO HAVE--%n%n7. %s%n8. %s%n9. %s%n%n",
                    "Inschrijven voor uitstap",
                    "Aanwezigheden raadplegen",
                    "Stand clubkampioenschap",
                    "Leden beheren",
                    "Uistappen beheren",
                    "Overzicht uitstappen & inschrijvingen",
                    "Lesmateriaal beheren",
                    "Afdrukken overzicht",
                    "Overzicht raadplegingen van lesmateriaal"
            );
            System.out.print("Geef je keuze: ");
            int choice = input.nextInt();
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Ongeldig");
                    break;
            }
        }
    }
}
