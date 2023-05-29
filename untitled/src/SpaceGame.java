import java.util.Random;
import java.util.Scanner;

abstract class SpaceShip {
    protected String name;
    protected int health;

    public SpaceShip(String name, int health) {
        this.name = name;
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            System.out.println(name + " został zniszczony!");
        }
    }

    public boolean isAlive() {
        return health > 0;
    }
}

class Player extends SpaceShip {
    private int numHealthPacks;

    public Player(String name) {
        super(name, 100);
        numHealthPacks = 3;
    }

    public void displayInfo() {
        System.out.println("\n--- Informacje o graczu ---");
        System.out.println("Imię gracza: " + name);
        System.out.println("Punkty życia: " + health);
        System.out.println("Pakiety medyczne: " + numHealthPacks);
    }

    public void attack(EnemyShip[] enemies) {
        for (EnemyShip enemy : enemies) {
            if (enemy.isAlive()) {
                int damage = 10;
                enemy.takeDamage(damage);
                System.out.println(name + " zadał " + damage + " obrażeń " + enemy.name);
            }
        }
    }

    public void heal() {
        if (numHealthPacks > 0) {
            int healingAmount = 30;
            health += healingAmount;
            numHealthPacks--;
            System.out.println(name + " Naprawił się " + healingAmount + " punktów życia.");
        } else {
            System.out.println("Brak pakietów medycznych.");
        }
    }
}

abstract class EnemyShip extends SpaceShip {
    public EnemyShip(String name, int health) {
        super(name, health);
    }

    public abstract void attack(SpaceShip target);
}

class SmallEnemyShip extends EnemyShip {
    public SmallEnemyShip() {
        super("Mały Przeciwnik", 30);
    }

    @Override
    public void attack(SpaceShip target) {
        if (target.isAlive()) {
            int damage = 5;
            target.takeDamage(damage);
            System.out.println(name + " zadał " + damage + " obrażeń " + target.name);
        } else {
            System.out.println(name + " nie może zaatakować zniszczonego celu!");
        }
    }
}

class MediumEnemyShip extends EnemyShip {
    public MediumEnemyShip() {
        super("Średni Przeciwnik", 50);
    }

    @Override
    public void attack(SpaceShip target) {
        if (target.isAlive()) {
            int damage = 10;
            target.takeDamage(damage);
            System.out.println(name + " zadał " + damage + " obrażeń " + target.name);
        } else {
            System.out.println(name + " nie może zaatakować zniszczonego celu!");
        }
    }
}

class LargeEnemyShip extends EnemyShip {
    public LargeEnemyShip() {
        super("Duży Przeciwnik", 100);
    }

    @Override
    public void attack(SpaceShip target) {
        if (target.isAlive()) {
            int damage = 15;
            target.takeDamage(damage);
            System.out.println(name + " zadał " + damage + " obrażeń " + target.name);
        } else {
            System.out.println(name + " nie może zaatakować zniszczonego celu!");
        }
    }
}

public class SpaceGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Witaj w grze kosmicznej!");

        System.out.print("Podaj imię gracza: ");
        String playerName = scanner.nextLine();

        Player player = new Player(playerName);
        player.displayInfo();

        int numEnemies = 3;
        EnemyShip[] enemies = new EnemyShip[numEnemies];

        for (int i = 0; i < numEnemies; i++) {
            enemies[i] = createRandomEnemy();
        }

        System.out.println("Rozpoczynamy walkę!");

        while (player.isAlive() && anyEnemiesAlive(enemies)) {
            System.out.println("\nWybierz akcję:");
            System.out.println("1. Atakuj");
            System.out.println("2. Napraw się");

            int choice = scanner.nextInt();

            if (choice == 1) {
                player.attack(enemies);
                for (EnemyShip enemy : enemies) {
                    if (enemy.isAlive()) {
                        enemy.attack(player);
                    }
                }
            } else if (choice == 2) {
                player.heal();
            } else {
                System.out.println("Nieprawidłowy wybór. Przeciwnicy atakują!");
                for (EnemyShip enemy : enemies) {
                    if (enemy.isAlive()) {
                        enemy.attack(player);
                    }
                }
            }
        }

        if (player.isAlive()) {
            System.out.println("\nGratulacje! Wygrałeś walkę kosmiczną!");
        } else {
            System.out.println("\nNiestety, przegrałeś walkę kosmiczną...");
        }
    }

    private static EnemyShip createRandomEnemy() {
        Random random = new Random();
        int enemyType = random.nextInt(3);

        if (enemyType == 0) {
            return new SmallEnemyShip();
        } else if (enemyType == 1) {
            return new MediumEnemyShip();
        } else {
            return new LargeEnemyShip();
        }
    }

    private static boolean anyEnemiesAlive(EnemyShip[] enemies) {
        for (EnemyShip enemy : enemies) {
            if (enemy.isAlive()) {
                return true;
            }
        }
        return false;
    }
}