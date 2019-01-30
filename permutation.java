import java.util.*;

public class permutation {
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);

        //input plaintext
        System.out.print("Enter a plaintext: ");
        String text = scanner.nextLine();
        text = text.toLowerCase();

        //enter a key size which cannot exceed the size of the plaintext
        int m = 0;
        while(true) {
            System.out.print("Enter a keysize: ");
            m = scanner.nextInt();

            if(m > text.length() - 1) {
                System.out.println("Keysize cannot excede the size of the plaintext! Try Again");
            }
            else {
                break;
            }
        }

        // sets the amount of padding that will be needed based on value of m
        int padding = m - (text.length() % m);

        System.out.println("\nValue of 'm': " + m);
        System.out.println("Plaintext: " + text);

        //generate the key
        List<Integer> key = key(m);
        System.out.println("\nThe following is the key:");
        for(int i = 0; i < key.size(); i++) {
            System.out.print(i + " - ");
            System.out.println(key.get(i));
        }

        //encrypts the plaintext
        String ciphertext = encrypt(text, padding, m, key);
        System.out.println("\nCiphertext: " + ciphertext);

        //decrypts the ciphertext
        String plaintext = decrypt(ciphertext, m, key);
        System.out.println("\nDecrypted Text: " + plaintext);

        //prints text without the padding
        System.out.println("Decrypted Without Padding: " + plaintext.substring(0, plaintext.length() - padding));
    }


    //generate the encryption key
    public static List<Integer> key(int m) {
        List<Integer> key = new ArrayList<Integer>();
        for(int i = 0; i < m; i++) {
            key.add(i);
        }

        //randomizes the key permutations (how the shifts will work)
        Collections.shuffle(key);
        return key;
    }

    //encrypt the plaintext
    public static String encrypt(String text, int padding, int m, List<Integer> key) {
        //creates random padding values for the plaintext and adds them on (could be the same letter, but this is better)
        Random rand = new Random();
        for(int i = 0; i < padding; i++) {
            int min = 97;
            int max = 122;
            int val = rand.nextInt((max - min) + 1) + min;
            char a = (char) val;
            text += a;
        }
        System.out.println("\nPadded Plaintext: " + text);

        //encrypts the plaintext based on the value of m and the random swap ordering in the key
        String updated_text = "";
        while(text.length() > 0){
            String sub = text.substring(0, m);
            text = text.substring(m);

            //uses value of m to build subarray of the overall text
            String [] arr = new String[m];
            for(int i = 0; i < arr.length; i++) {
                int index = key.get(i);
                arr[index] = "" + sub.charAt(i);
            }
            for(int i = 0; i < arr.length; i++) {
                updated_text += arr[i];
            }
        }
        return updated_text;
    }

    //decrypts the ciphertext
    public static String decrypt(String text, int m, List<Integer> key) {
        //decrypts the plaintext based on the value of m and the random swap ordering in the key (similar to encryption)
        String decrypted_text = "";
        while(text.length() > 0){
            String sub = text.substring(0, m);
            text = text.substring(m);

            //uses value of m to build subarray of the overall text
            String [] arr = new String[m];
            for(int i = 0; i < arr.length; i++) {
                int index = key.get(i);
                arr[i] = "" + sub.charAt(index);
            }
            for(int i = 0; i < arr.length; i++) {
                decrypted_text += arr[i];
            }
        }
        return decrypted_text;
    }
}
