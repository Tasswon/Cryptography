import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DoubleEncryption {
    // converts each letter in a plaintext to a binary value
    public String text_to_binary(String plaintext) {
        plaintext = plaintext.toLowerCase();

        String binary_text = "";
        for (int i = 0; i < plaintext.length(); i++) {
            char character_val = plaintext.charAt(i);
            String temp_binary = "";

            // checks value will be converted
            // a-z
            if (character_val >= 97 && character_val < 123) {
                character_val -= 97;
            }
            // space
            else if (character_val == 32) {
                character_val -= 6;
            }
            // period
            else if (character_val == 46) {
                character_val -= 19;
            }
            // comma
            else if (character_val == 44) {
                character_val -= 16;
            }
            // question mark
            else if (character_val == 63) {
                character_val -= 34;
            }
            // left and right bracket
            else if (character_val == 40 || character_val == 41) {
                character_val -= 10;
            }
            // closes program if invalid value entered
            else {
                System.out.println("The string entered contains values outside of the keyspace! Program cancelled...");
                System.exit(0);
            }

            // converts string to binary
            temp_binary = Integer.toBinaryString(character_val);

            //prepends 0's to start of string if less than 5
            if (temp_binary.length() < 5) {
                int len_temp_binary = temp_binary.length();
                for (int j = len_temp_binary; j < 5; j++) {
                    temp_binary = "0" + temp_binary;
                }
            }
            binary_text += temp_binary;
        }
        return binary_text;
    }


    //generate the encryption key for permutation
    public List<Integer> key(int m) {
        List<Integer> key = new ArrayList<Integer>();
        for (int i = 0; i < m; i++) {
            key.add(i);
        }
        //randomizes the key permutations (how the shifts will work)
        Collections.shuffle(key);
        return key;
    }


    //encrypt the plaintext with permutation
    public String permutation(String text, int m, List<Integer> key) {
        //encrypts the plaintext based on the value of m and the random swap ordering in the key
        String updated_text = "";
        while (text.length() > 0) {
            String sub = text.substring(0, m);
            text = text.substring(m);

            // uses value of m to build subarray of the overall text
            String[] arr = new String[m];
            for (int i = 0; i < arr.length; i++) {
                int index = key.get(i);
                char x = sub.charAt(index);
                arr[i] = "" + sub.charAt(index);
            }
            for (int i = 0; i < arr.length; i++) {
                updated_text += arr[i];
            }
        }
        return updated_text;
    }


    // xor two input values
    public String xor(String one, String two) {
        String three = "";
        for (int i = 0; i < one.length(); i++) {
            int a = (int) one.charAt(i);
            int b = (int) two.charAt(i);

            int result = a ^ b;

            three += result;
        }
        return three;
    }


    // simple xor encryption of block and key size 6
    public String block_xor(String plaintext, String key) {
        String ciphertext = "";

        // xor blocks of the plaintext with the key
        while (plaintext.length() > 0) {
            String temp_text = plaintext.substring(0, 6);
            plaintext = plaintext.substring(6);
            String xor_result = xor(temp_text, key);

            ciphertext += xor_result;
        }
        return ciphertext;
    }


    // perform a meet-in-the-middle attack to obtain the encryption keys
    public void break_keys(String plaintext, String ciphertext) {
        // generates all possible permutations for block size 5
        ArrayList<Integer> p = new ArrayList<>();
        p.add(0);
        p.add(1);
        p.add(2);
        p.add(3);
        p.add(4);
        ArrayList<ArrayList<Integer>> permutation_list = gen_perms(p, 5);

        // generates all possible permutations for binary size 6
        ArrayList<String> binary_list = gen_binary(6);

        // perform meet-in-the-middle attack
        // perform permutation encryption on plaintext and xor encryption on ciphertexts, stop when they match
        for(int i = 0; i < permutation_list.size(); i++) {
            ArrayList<Integer> perm_val = permutation_list.get(i);
            String permuted_value = permutation(plaintext, 5, perm_val);
            for(int j = 0; j < binary_list.size(); j++) {
                String bin_val = binary_list.get(j);
                String binary_value = block_xor(ciphertext, bin_val);
                if(permuted_value.equals(binary_value)) {
                    System.out.println("\nThe permutation key is: " + perm_val);
                    System.out.println("The key is: " + bin_val);
                    System.exit(0);
                }
            }
        }
    }


    // generates a list of all possible permutations
    private ArrayList<ArrayList<Integer>> gen_perms(ArrayList<Integer> vals, int perm_size) {
        ArrayList<ArrayList<Integer>> permutation_list = new ArrayList<>();
        // initialize the permutation_list (can't be empty)
        permutation_list.add(new ArrayList<>());
        for (int i = 0; i < perm_size; i++) {
            ArrayList<ArrayList<Integer>> updated = new ArrayList<>();
            for(int j = 0; j < permutation_list.size(); j++) {
                ArrayList<Integer> temp1 = permutation_list.get(j);
                for (int k = 0; k < temp1.size()+1; k++) {
                    temp1.add(k, vals.get(i));
                    ArrayList<Integer> temp2 = new ArrayList<>(temp1);
                    updated.add(temp2);
                    temp1.remove(k);
                }
            }
            permutation_list = updated;
        }
        return permutation_list;
    }


    // generate all unique binary values for block size 6
    private ArrayList<String> gen_binary(int bin_size) {
        ArrayList<String> binary_list = new ArrayList<>();
        for(int i = 0; i < 64; i++) {
            String temp = Integer.toBinaryString(i);
            if(temp.length() < 6) {
                int len_temp_binary = temp.length();
                for(int j = len_temp_binary; j < 6; j++) {
                    temp = "0" + temp;
                }
            }
            binary_list.add(temp);
        }
        return binary_list;
    }
}
