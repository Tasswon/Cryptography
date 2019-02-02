import java.util.Random;

public class MDES {

    // converts each letter in a plaintext to a binary value
    public String text_to_binary(String plaintext) {
        plaintext = plaintext.toLowerCase();

        // loop for the length of the plaintext
        String binary_text = "";
        for(int i = 0; i < plaintext.length(); i++) {
            char character_val = plaintext.charAt(i);
            String temp_binary = "";

            // checks value will be converted
            // a-z
            if(character_val >= 97 && character_val < 123) {
                character_val -= 97;
            }
            // space
            else if(character_val == 32) {
                character_val -= 6;
            }
            // period
            else if(character_val == 46) {
                character_val -= 19;
            }
            // comma
            else if(character_val == 44) {
                character_val -= 16;
            }
            // question mark
            else if(character_val == 63) {
                character_val -= 34;
            }
            // left and right bracket
            else if(character_val == 40 || character_val == 41){
                character_val -= 10;
            }
            // closes program if invalid value entered
            else {
                System.out.println("The string entered contains values outside of the keyspace! Program cancelled...");
                System.exit(0);
            }

            // converts string to binary
            temp_binary =  Integer.toBinaryString(character_val);

            //prepends 0's to start of string if less than 5
            if(temp_binary.length() < 5) {
                int len_temp_binary = temp_binary.length();
                for(int j = len_temp_binary; j < 5; j++) {
                    temp_binary = "0" + temp_binary;
                }
            }
            binary_text += temp_binary;
        }
        return binary_text;
    }


    // converts each binary value back into the the original plaintext
    public String binary_to_text(String binary_text) {
        String plaintext = "";

        // loop until blocks of binary text have finished
        while(binary_text.length() != 0) {
            String temp_binary = binary_text.substring(0, 5);
            binary_text = binary_text.substring(5);
            int character_val = Integer.parseInt(temp_binary,2);

            // converts binary value back
            // a-z
            if(character_val >= 0 && character_val < 26) {
                character_val += 97;
            }
            // space
            else if(character_val == 26) {
                character_val += 6;
            }
            // period
            else if(character_val == 27) {
                character_val += 19;
            }
            // comma
            else if(character_val == 28) {
                character_val += 16;
            }
            // question mark
            else if(character_val == 29) {
                character_val += 34;
            }
            // left and right bracket
            else if(character_val == 30 || character_val == 31){
                character_val += 10;
            }
            // closes program if invalid value entered
            else {
                System.out.println("The string entered contains values outside of the keyspace! Program cancelled...");
                System.exit(0);
            }
            plaintext += (char) character_val;
        }
        return plaintext;
    }


    // implement a function f, uses s-boxes to output an 8-bit binary string
    public String s_boxes(String bitstring, String key){
        // rejects if the key or are string are not valid sizes
        if(bitstring.length() != 8 || key.length() != 12){
            System.out.println("The bitstring or the key are not a valid length!");
            System.exit(0);
        }

        // appends even number index values to end of string
        String evens = "";
        for(int i = 1; i < bitstring.length(); i += 2) {
            evens = evens + bitstring.charAt(i);
        }
        bitstring += evens;

        // xor even value appended string with the key
        String xor_val = "";
        for(int i = 0; i < bitstring.length(); i++) {
            int a = (int) bitstring.charAt(i);
            int b = (int) key.charAt(i);

            int result = a ^ b;

            xor_val += result;
        }

        // split the xor value into two parts
        String b1 = xor_val.substring(0,6);
        String b2 = xor_val.substring(6,12);

        // s-box bitstring conversions for b1
        int [][] s1 = {
                {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
        };
        int row = Integer.parseInt(b1.charAt(0) + "" + b1.charAt(5),2);
        int col = Integer.parseInt(b1.substring(1, 5),2);
        b1 = Integer.toBinaryString(s1[row][col]);
        // add 0's to start of binary string
        if(b1.length() < 4) {
            int len_temp_binary = b1.length();
            for(int j = len_temp_binary; j < 4; j++) {
                b1 = "0" + b1;
            }
        }

        // s-box bitstring conversions for b2
        int [][] s2 = {
                {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
        };
        row = Integer.parseInt(b2.charAt(0) + "" + b2.charAt(5),2);
        col = Integer.parseInt(b2.substring(1, 5),2);
        b2 = Integer.toBinaryString(s2[row][col]);
        // add 0's to start of binary string
        if(b2.length() < 4) {
            int len_temp_binary = b2.length();
            for(int j = len_temp_binary; j < 4; j++) {
                b2 = "0" + b2;
            }
        }
        return b1 + b2;
    }


    // mini version of DES swap function for encryption L = R ; R = L xor f(R,K)
    public String split_encrypt(String binary_string, String key) {
        // ensures key is the proper 24 bit length
        if(key.length() != 24) {
            System.out.println("Key has to be 24 in length!");
            System.exit(0);
        }

        // pads anything not based on 16
        if(binary_string.length() % 16 != 0) {
            int padding_num = 16 - (binary_string.length() % 16);
            for(int i = 0; i < padding_num; i++) {
                binary_string += "0";
            }
        }
        String final_string = "";
        String temp_key = key;

        // loops for blocks of 16 until binary_string is finished
        while(binary_string.length() != 0) {
            String temp_string = binary_string.substring(0, 16);
            binary_string = binary_string.substring(16);

            // two iterations setup method
            for(int i = 0; i < 2; i++) {
                String l = temp_string.substring(0, 8);
                String r = temp_string.substring(8, 16);
                String update_key = temp_key.substring(0, 12);
                temp_key = temp_key.substring(12);

                temp_string = "";
                temp_string = temp_string + r + xor(l, s_boxes(r, update_key));
            }
            final_string += temp_string;
            temp_key = key;
        }
        return final_string;
    }


    // xor two input values together
    private String xor(String one, String two) {
        String three = "";
        for(int i = 0; i < one.length(); i++) {
            int a = (int) one.charAt(i);
            int b = (int) two.charAt(i);

            int result = a ^ b;

            three += result;
        }
        return three;
    }


    // mini version of DES swap function for decryption R = L ; L = R xor f(L,K)
    public String split_decrypt(String binary_string, String key) {
        // ensure the key is a valid length
        if(key.length() != 24) {
            System.out.println("Key has to be 24 in length!");
            System.exit(0);
        }

        String final_string = "";
        String temp_key = key;
        while(binary_string.length() != 0) {
            String temp_string = binary_string.substring(0, 16);
            binary_string = binary_string.substring(16);

            // two iterations setup method
            for(int i = 0; i < 2; i++) {
                String l = temp_string.substring(0, 8);
                String r = temp_string.substring(8, 16);
                String update_key = temp_key.substring(temp_key.length() - 12);
                temp_key = temp_key.substring(0, temp_key.length() - 12);

                temp_string = "";
                // modified version of previous function pass
                temp_string = temp_string + xor(r, s_boxes(l, update_key)) + l;
            }
            final_string += temp_string;
            temp_key = key;
        }
        return final_string;
    }


    // alternate version of DES encryption using CBC mode
    public String cbc(String binary_string, String key) {
        if(key.length() != 24) {
            System.out.println("Key has to be 24 in length!");
            System.exit(0);
        }

        // pads anything not based on 16
        if(binary_string.length() % 16 != 0) {
            int padding_num = 16 - (binary_string.length() % 16);
            for(int i = 0; i < padding_num; i++) {
                binary_string += "0";
            }
        }
        String final_string = "";
        String temp_key = key;

        // random initialization vector generation
        Random rand = new Random();
        String iv = "";
        for(int i = 0; i < 16; i++) {
            int random_bin = rand.nextInt((1 - 0) + 1) + 0;
            iv += random_bin;
        }
        System.out.println("\nThe randomized IV is the following: \n" + iv);

        //retains previous block to xor with current
        String previous_block = iv;
        while(binary_string.length() != 0) {
            String temp_string = xor(previous_block, binary_string.substring(0, 16));
            binary_string = binary_string.substring(16);

            // two iterations setup method
            for(int i = 0; i < 2; i++) {
                String l = temp_string.substring(0, 8);
                String r = temp_string.substring(8, 16);
                String update_key = temp_key.substring(0, 12);
                temp_key = temp_key.substring(12);

                temp_string = "";
                temp_string = temp_string + r + xor(l, s_boxes(r, update_key));
            }
            // pull current ciphertext block for xor to next plaintext block
            previous_block = temp_string;
            final_string += temp_string;
            temp_key = key;
        }
        return final_string;
    }

}
