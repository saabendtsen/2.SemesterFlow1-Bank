import java.util.Scanner;

public class Menu {

    Scanner sc = new Scanner(System.in);
    Account account;
    TransaktionHandler th;
    Database database = new Database(Main.DBUSER,Main.DBPASS,Main.DBURL);
    DBController dbc = new DBController(database);

    public void mainMenu() {

        boolean running = true;

        while (running) {
            System.out.println("Ohøj landkrappe, og velkommen til banken over dem alle.");
            System.out.println("Du har nu følgende muligheder.");
            System.out.println("Tryk 1) for at logge ind som bruger.");
            System.out.println("Tryk 2) for at logge ind som adminstator.");
            System.out.println("Eller tryk 'q' for at stoppe programmet");

            String cmd = sc.nextLine();

            if (cmd.equals("1")) {
                costumerLogInMenu();
            } else if (cmd.equals("2")) {
                adminLoginMenu();
            } else if (cmd.equals("q")) {
                running = false;
            }
        }
    }

    public void costumerLogInMenu(){
        boolean running = true;
        System.out.println("Du har valgt at logge ind som bruger");
        System.out.println("Tryk 1) for at logge ind");
        System.out.println("Tryk 2) for at gå tilbage");

        String cmd = sc.nextLine();
//        String cmd1 = sc.nextLine();
        while(running){
            if(cmd.equals("1")){
                System.out.println("Indtast dit brugernavn og afslut med enter: ");
                int input = Integer.parseInt(sc.nextLine());
//                System.out.println("Indtast dit kodeord og afslut med enter: ");
//                cmd1 = sc.nextLine();
                //TODO: Check account ID in database, This comes from the databases
                Account acc = new Account(input, 120);
                costumerMenu(acc);
            } else if(cmd.equals("2")){
                running = false;
            }
        }
    }

    public void costumerMenu(Account account_id){
        boolean running = true;
        System.out.println("Du er nu logget ind som bruger");
        System.out.println("Din saldo er: " + account.getCurrentAmount());
        System.out.println("Tryk 1) for at indsætte penge på din konto");
        System.out.println("Tryk 2) for at trække penge ud af din konto");
        System.out.println("Eller tryk 'q' for at logge ud");

        double deposit = 0;

        String cmd = sc.nextLine();
        while(running){
            if(cmd.equals("1")){
                System.out.println("Hvor mange penge vil du gerne indsætte på din konto? ");
                double input = Double.parseDouble(sc.nextLine());
                th.deposit(account_id, input);
                System.out.println("Din nye er saldo er nu: " + account.getCurrentAmount());
            }
            if (cmd.equals("2")){
                System.out.println("Indtast beløb du vil du hæve");
                double input = Double.parseDouble(sc.nextLine());
                th.withdraw(account_id,input);
            }
        }
    }

    public void adminLoginMenu(){
        boolean running = true;
        System.out.println("Du har valgt at logge ind som admin");
        System.out.println("Tryk 1) for at logge ind");
        System.out.println("Tryk 2) for at gå tilbage");

        String cmd = sc.nextLine();

        while(running){
            if(cmd.equals("1")){
                System.out.println("Indtast dit brugernavn og afslut med enter: ");
                cmd = sc.nextLine();
                System.out.println("Indtast dit kodeord og afslut med enter: ");
                cmd = sc.nextLine();
                //Call the admins table
            }else if(cmd.equals("2")) {
                running = false;
            }
        }
    }

    public void adminMenu(){
        boolean running = true;

        System.out.println("Velkommen til Admin menu, hvad vil du foretage dig?");
        System.out.println("Tryk 1) for at administrere en kunde");
        System.out.println("Tryk 2) for at flytte midler mellem to kunder");

        String cmd = sc.nextLine();

        while(running) {
            if (cmd.equals("1")) {
                //dbc.showAllAccounts
                System.out.println("Vælg en konto at administrere");
                double input = Double.parseDouble(sc.nextLine());

                //TODO hent account data fra DB
                //costumerMenu(det valgte account);

            }
            if (cmd.equals("2")) {
                System.out.println("hvilkent konto vil du hæve fra?");
                int fromAccountID = Integer.parseInt(sc.nextLine());
                //Det kommer fra databasen
                Account fromAcount = new Account(fromAccountID,dbc.returnCurrentAccountAmount(fromAccountID));
                System.out.println("Hvilken konto skal modtage");
                int toAccountID = Integer.parseInt(sc.nextLine());
                Account toAccount = new Account(toAccountID, dbc.returnCurrentAccountAmount(toAccountID));
                System.out.println("Vælg beløb");
                double amount = Double.parseDouble(sc.nextLine());
                th.transferBetweenAccount(fromAcount,toAccount,amount);

            }
        }

    }
}
