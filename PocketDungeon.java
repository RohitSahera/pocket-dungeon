
import java.util.Random;
import java.util.Scanner;

class Player {

    String name;
    int health;
    Random rand = new Random();
    int maxHealth;

    Player(String name, int health, int maxHealth) {
        this.name = name;
        this.health = health;
        this.maxHealth = maxHealth;
    }

    // Reduce health safely
    public void takeDamage(int damage) {
        if (damage < 0) {
            damage = 0; // Prevent negative damage

        }
        this.health -= damage;

        // Clamp health to 0
        if (this.health < 0) {
            this.health = 0;
        }

        displayHealth();

        // Trigger game over if health is exactly 0
        if (this.health == 0) {
            dead();
        }
    }

    // Method to handle death
    public void dead() {
        System.out.println(this.name + " has been defeated!");
    }

    // Display Hero's Health
    public void displayHealth() {
        System.out.println("Hero's Health :" + this.health);
    }

    // Attack modes
    public void lightAttack(Monster monster) {
        int damage = rand.nextInt(5) + 5; // 5–9 damage
        monster.takeDamage(damage);
        System.out.println(this.name + " used Light Attack for " + damage + " damage!");
    }

    public void heavyAttack(Monster monster) {
        if (rand.nextInt(100) < 80) { // 80% hit chance
            int damage = rand.nextInt(10) + 10; // 10–19 damage
            monster.takeDamage(damage);
            System.out.println(this.name + " used Heavy Attack for " + damage + " damage!");
        } else {
            System.out.println(this.name + "'s Heavy Attack missed!");
        }
    }

    public void criticalStrike(Monster monster) {
        if (rand.nextInt(100) < 25) { // 25% chance
            int damage = rand.nextInt(20) + 20; // 20–39 damage
            monster.takeDamage(damage);
            System.out.println("CRITICAL STRIKE! " + this.name + " dealt " + damage + " damage!");
        } else {
            System.out.println(this.name + "'s Critical Strike failed!");
        }
    }

    public void heal() {
        int healAmount = rand.nextInt(10) + 10; // 10–19 heal
        // Increase health but cap it at maxHealth
        int oldHealth = this.health;
        this.health = Math.min(this.health + healAmount, maxHealth);
        int actualHealing = this.health - oldHealth;

        System.out.println(this.name + " healed for " + actualHealing + " HP!");
        displayHealth();
    }
}

class Monster {

    String name;
    int health;
    int lightAttack;
    int criticalHit;
    int megaSlash;
    Random rand = new Random();

    Monster(String name, int health, int lightAttack, int CriticalHit, int MegaSlash) {
        this.name = name;
        this.health = health;
        this.lightAttack = lightAttack;
        this.criticalHit = CriticalHit;
        this.megaSlash = MegaSlash;
    }

    public void dead() {
        System.out.println(this.name + " has been defeated!");
    }

    public void randomAttack(Player hero) {
        int choice = rand.nextInt(3);

        switch (choice) {
            case 0:
                System.out.println(this.name + " uses light Attack for " + this.lightAttack + " damage!");
                lightAttack(hero);
                break;
            case 1:
                System.out.println(this.name + " uses Critical Hit for " + this.criticalHit + " damage!");
                criticalHit(hero);
                break;
            case 2:
                System.out.println(this.name + " uses Mega Slash" + this.megaSlash + " damage!");
                megaSlash(hero);
                break;
            default:
                System.out.println("Invalid action!");
        }
    }

    public void lightAttack(Player hero) {
        hero.takeDamage(this.lightAttack);
    }

    public void criticalHit(Player hero) {
        hero.takeDamage(this.criticalHit);
    }

    public void megaSlash(Player hero) {
        hero.takeDamage(this.megaSlash);
    }

    public void takeDamage(int damage) {
        if (damage < 0) {
            damage = 0; // Prevent negative damage

        }
        this.health -= damage;

        // Clamp health to 0
        if (this.health < 0) {
            this.health = 0;
        }

        displayHealth();

        // Trigger game over if health is exactly 0
        if (this.health == 0) {
            dead();
        }
    }

    public void displayMonsterHealth(int damage) {
        System.out.println("You hit " + this.name + " for " + damage + " damage!");
        System.out.println(this.name + " health: " + this.health);
    }

    public void displayHealth() {
        System.out.println(this.name + "'s Health :" + this.health);
    }
}

public class PocketDungeon {

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;
        while (playAgain) {
            Player hero = new Player("Hero", 100, 100);
            Monster slime = new Monster("Dungeon Slime", 20, 10, 25, 40);
            System.out.println("=== POCKET DUNGEON ===");
            System.out.println("1. Light Attack");
            System.out.println("2. Heavy Attack");
            System.out.println("3. Critical Strike");
            System.out.println("4. Heal");
            while (hero.health != 0 && slime.health != 0) {
                System.out.print("Choose your action: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        hero.lightAttack(slime);
                        break;
                    case 2:
                        hero.heavyAttack(slime);
                        break;
                    case 3:
                        hero.criticalStrike(slime);
                        break;
                    case 4:
                        hero.heal();
                        break;
                    default:
                        System.out.println("Invalid action!");
                }
                if (slime.health > 0) {
                    slime.randomAttack(hero);
                }
            }
            if (hero.health == 0) {
                System.out.println("GAME OVER! You were defeated.");
            } else if (slime.health == 0) {
                System.out.println("VICTORY! You defeated the Dungeon Slime!");
            }
            System.out.print("Play Again? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            // Validate input
            while (input.isEmpty() || (input.charAt(0) != 'y' && input.charAt(0) != 'n')) {
                System.out.print("Invalid choice. Please enter 'y' or 'n': ");
                input = scanner.nextLine().trim().toLowerCase();
            }

            playAgain = input.equals("y");
        }
    }
}
