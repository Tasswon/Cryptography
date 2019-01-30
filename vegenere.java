import java.util.ArrayList;

public class problem_4 {
    public static void main(String [] args) {

        //plaintext to decrypt based on assignment requirements
        String text = "cjnpkgrlilqwawbnuptgkerwxuzviaiiysxckwdntjawhqcutttvptewtrpgvcwlkkkgczafsihrimixukrwxrfmgfgkfxgukpjvvzmcmjvawbnuptgcicvxvkgczkekgcqbchvnrqhhwiadfrcyxgvzqqtuvbdguvttkccdpvvfphftamzxqwrtgukcelqlrxgvycwtncbjkkeerecjqihvrjzpkkfexqgjtpjfupemswwxcjqxzpjtxkvlyaeaemwhovudkmnfxegfrwxtdyiaecyhlgjfpogymbxyfpzxxvpngkxfitnkfdniyrwxukssxpkqabmvkgcqbciagpadfrcyxgvyyimjvwpkgscwbpurwxqkftkorrwvnrqhxurlslgvjxmvccraceathhtfpmeygczwgutttvttkatmcvgiltwcsmjmvyghitfzaxodkbf";
        text = text.toLowerCase();

        double [] average_ioc = new double[10];

        System.out.println("Index of coincidence for each key size:");

        // tests the index from m = 2 to m = 9
        for(int m = 2; m < 10; m++) {
            System.out.println("\tCurrent Key Test: " + m);
            double sum = 0;
            double average = 0;

            //calculates values of Ic based on size of m
            for(int k = 0; k < m; k++) {
                int len = text.length();
                int counter = 0;

                //--------------------------------------------------------------------------------------------------------------

                char[][] text_arr = new char[m][(len / m)];

                //write ciphertext into columns, each of length m
                for (int i = 0; i < text_arr[0].length; i++) {
                    for (int j = 0; j < text_arr.length; j++) {
                        if (counter > text.length() - 1) {
                            break;
                        }
                        text_arr[j][i] = text.charAt(counter);
                        ++counter;
                    }
                }

                //--------------------------------------------------------------------------------------------------------------

                //count occurrences of each letter in the ciphertext
                int count[] = new int[26];
                for (int i = 0; i < text_arr[0].length; i++) {
                    int index = (int) text_arr[k][i] - 97;
                    count[index] = ++count[index];
                }

                //perform formula for Ic using the previously gathered frequencies
                int n = text_arr[0].length;
                int temp_ioc = 0;
                for (int i = 0; i < count.length; i++) {
                    if (count[i] == 0) {
                        continue;
                    }
                    temp_ioc = temp_ioc + (count[i] * (count[i] - 1));
                }
                double ioc = (double) temp_ioc / (n * (n - 1));
                System.out.println("\t\tIndex of coincidence: " + ioc);
                sum = sum + ioc;

                //--------------------------------------------------------------------------------------------------------------
            }
            average = sum / m;
            average_ioc[m] = average;
            System.out.println();
        }

        //--------------------------------------------------------------------------------------------------------------

        //chooses the index key size based on the average Ic value closest to 0.065
        int m = 0;
        double current = Integer.MAX_VALUE;
        for(int i = 0; i < average_ioc.length; i++) {
            if(average_ioc[i] == 0) {
                continue;
            }
            double test_deviation = Math.abs(average_ioc[i] - 0.065);
            if(test_deviation < current) {
                m = i;
                current = test_deviation;
            }
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------\n");

        System.out.println("Suspected Key Size: " + m);

        System.out.println("\n--------------------------------------------------------------------------------------------------------------\n");

        //--------------------------------------------------------------------------------------------------------------

        //write ciphertext into columns, each of length m (the key that was just determined)
        int len = text.length();
        int counter = 0;
        char[][] text_arr = new char[m][(len / m)];

        for (int i = 0; i < text_arr[0].length; i++) {
            for (int j = 0; j < text_arr.length; j++) {
                if (counter > text.length() - 1) {
                    break;
                }
                text_arr[j][i] = text.charAt(counter);
                counter = ++counter;
            }
        }

        //--------------------------------------------------------------------------------------------------------------

        //generate Observed Mutual Indices of Coincidence
        double [][][] mic = new double[m][m][26];
        ArrayList<int[]> equations = new ArrayList<int[]>();
        for(int i = 0; i < m; i++) {
            for(int j = i + 1; j < m; j++) {
                int count1[] = new int[26];
                int count2[] = new int[26];

                //get frequency of each letter for the respective rows being tried
                for(int l = 0; l < text_arr[0].length; l++) {
                    int index = (int) text_arr[i][l] - 97;
                    count1[index] = ++count1[index];

                    index = (int) text_arr[j][l] - 97;
                    count2[index] = ++count2[index];
                }
                double temp_ioc = 0;
                double temp_total = 0;
                int g_index = 0;
                int [] temp_eq = {i, j, 0, 0};

                //get MIc values for 0 to 25
                for(int g = 0; g < 26; g++) {
                    for(int k = 0; k < 26; k++) {
                        int kg = k - g;
                        if(k - g < 0) {
                            kg = 26 + (k - g);
                        }
                        temp_ioc = temp_ioc + (count1[k] * (count2[kg]));
                    }
                    temp_ioc = temp_ioc / (text_arr[0].length * text_arr[0].length);
                    mic[i][j][g] = temp_ioc;
                    if(temp_ioc > temp_total) {
                        temp_total = temp_ioc;
                        g_index = g;
                    }
                }
                temp_eq[2] = g_index;
                temp_eq[3] = 26 - g_index;
                equations.add(temp_eq);
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        System.out.println("Observed Mutual Indices of Coincidence:");
        for(int i = 0; i < m; i++) {
            for(int j = i + 1; j < m; j++) {
                System.out.print("\t" + i + "," + j + " => ");
                for (int g = 0; g < 26; g++) {
                    System.out.print(mic[i][j][g] + " ");
                }
                System.out.println();
            }
        }

        //--------------------------------------------------------------------------------------------------------------

        System.out.println("\n--------------------------------------------------------------------------------------------------------------\n");

        //get the linear equations, as well as the shift values for the potential key
        System.out.println("Linear Equations Ki = Kj + g  | 26 - g");
        for (int i = 0; i < equations.size(); i++){
            int[] tmp = equations.get(i);
            for (int j = 0; j < tmp.length; j++) {
                System.out.print(tmp[j] + "\t");
            }
            System.out.println();
        }

        //--------------------------------------------------------------------------------------------------------------

        //create a key array that will hold the potential key values calculated below
        int key_try [] = new int[m];
        key_try[0] = 0;
        for (int i = 0; i < m - 1; i++){
            int [] tmp = equations.get(i);
            key_try[i + 1] = tmp[3];
        }

        System.out.println("\n--------------------------------------------------------------------------------------------------------------\n");

        //generate a key and use it to decrypt the ciphertext
        System.out.println("Suspected keys with decryption:");
        String key = "";
        //loop through every possible K value from 0 to 25
        for(int i = 0; i < 26; i++) {
            //generate a key to attempt for decipher
            for(int j = 0; j < key_try.length; j++) {
                int temp_val = key_try[j] + 97 + i;
                if (temp_val > 122) {
                    temp_val = temp_val - 26;
                }
                char temp = (char) temp_val;
                key = key + temp;
            }
            System.out.println("\t" + i + " - " + key);
            System.out.print("\t\t");
            int y = 0;
            String temp_text = "";
            String sub_text = "";
            //use the key to decrypt the text, shift by on block size "m"
            for(int x = 0; x < text.length(); x++) {
                if(y % m == 0) {
                    for(int z = 0; z < temp_text.length(); z++) {
                        int c1 = (int) temp_text.charAt(z);
                        int c2 = (int) key.charAt(z);
                        char c3 = (char) (c1 - c2 + 97);
                        if (c3 < 97) {
                            c3 = (char) (c3 + 26);
                        }
                        sub_text = sub_text + c3;
                    }
                    System.out.print(sub_text);
                    sub_text = "";
                    temp_text = "";
                }
                temp_text = temp_text + text.charAt(x);
                y++;
                //used if the m does not perfectly divide the text (text % m != 0)
                if(y > text.length() - (text.length() % m) - 1) {
                    for(int z = 0; z < temp_text.length(); z++) {
                        int c1 = (int) temp_text.charAt(z);
                        int c2 = (int) key.charAt(z);
                        char c3 = (char) (c1 - c2 + 97);
                        if (c3 < 97) {
                            c3 = (char) (c3 + 26);
                        }
                        sub_text = sub_text + c3;
                    }
                    System.out.print(sub_text);
                    sub_text = "";
                    temp_text = "";
                }
            }
            System.out.println();
            key = "";
        }

    }
}
