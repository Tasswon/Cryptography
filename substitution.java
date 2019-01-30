import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class substitution {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);

        //input plaintext
        System.out.print("Enter a plaintext: ");
        String text = scanner.nextLine();
        text = text.toLowerCase();

        System.out.println("Plaintext: " + text);

        //generate the encryption key
        String [] keyspace = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", " ", ",", "."};
        List<String> key = key(text, keyspace);

        System.out.println("\nThe following is the key:");
        //Print out the key
        for(int i = 0; i < keyspace.length; i++) {
            System.out.print(i + " | ");
            System.out.print(keyspace[i]);
            System.out.print(" | ");
            System.out.print(key.get(i));
            System.out.println(" | ");
        }

        //encrypts the plaintext
        String ciphertext = encrypt(text, keyspace, key);
        System.out.println("\nCiphertext: " + ciphertext + "");

        //decrypts the ciphertext
        String plaintext = decrypt(ciphertext, keyspace, key);
        System.out.println("Decrypted Text: " + plaintext + "\n");
    }


    //generate the encryption key
    private static List<String> key(String text, String [] keyspace) {
        //keyspace and key correspond to the substitution cipher (one-to-one relationship)
        List<String> key = Arrays.asList(keyspace.clone());

        //Randomizes the characters in key2 for substitution
        Collections.shuffle(key);
        return key;
    }

    //encrypts the plaintext string
    public static String encrypt(String text, String [] keyspace, List<String> key) {
        //Encrypt the plaintext (loop and change by referencing key1 to key2)
        for(int i = 0; i < text.length(); i++) {
            int current_index = Arrays.asList(keyspace).indexOf("" + text.charAt(i));
            String replace_char = key.get(current_index);
            text = text.substring(0, i) + replace_char + text.substring(i + 1);
        }
        return text;
    }

    //decrypt the ciphertext
    public static String decrypt(String text, String [] keyspace, List<String> key) {
        //Decrypt the ciphertext (loop and change by referencing key2 to key1)
        for(int i = 0; i < text.length(); i++) {
            int current_index = key.indexOf("" + text.charAt(i));
            String replace_char = keyspace[current_index];
            text = text.substring(0, i) + replace_char + text.substring(i + 1);
        }
        return text;
    }
}
