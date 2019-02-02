# Cryptography Projects

## Description
The following repositiory contains light programs demonstrating various cryptographic ciphers and functions.

### Substitution
A program demonstrating the classic substitution cipher corresponding to a keyspace of 26 english alphabet characters, a space, a comma, and a period. The key itself is a random permutation, and the program shows both encryption and decryption.

### Permutation
A program demonstrating the permutation cipher corresponding to a keyspace of 26 english alphabet characters, a space, a comma, and a period. The initial program inputs a value of m for the size of the permutation and a plaintext. The permutation is randomized, and the program shows both encryption and decryption.

### Vegenere
The program demonstrates the Vegenere Cipher by breaking a ciphertext and attaining the plaintext.

**Ciphertext:**\
cjnpkgrlilqwawbnuptgkerwxuzviaiiysxckwdntjawhqcutttvptewtrpgvcwlkkkgczafsihrimixukrwxrfmgfgkfxgukpjvvzmcmjvawbnuptgcicvxvkgczkekgcqbchvnrqhhwiadfrcyxgvzqqtuvbdguvttkccdpvvfphftamzxqwrtgukcelqlrxgvycwtncbjkkeerecjqihvrjzpkkfexqgjtpjfupemswwxcjqxzpjtxkvlyaeaemwhovudkmnfxegfrwxtdyiaecyhlgjfpogymbxyfpzxxvpngkxfitnkfdniyrwxukssxpkqabmvkgcqbciagpadfrcyxgvyyimjvwpkgscwbpurwxqkftkorrwvnrqhxurlslgvjxmvccraceathhtfpmeygczwgutttvttkatmcvgiltwcsmjmvyghitfzaxodkbf

**Keysize:** 5\
**Key:** crypt\
**Plaintext:** aspareantsofchildreninthesixthgradeatyourschoolwearereluctantlywritingyoutoprotestthepoormethinstructionthechildrenaregettinginmrjokesclassourcomplaintisbasedonseveralfactorsmrjokeoftenstepsoutinthehallduringclasstotalkwithpeoplewhowalkbyheassignsvirtuallynohomeworkwhileothermathclasseshavehomeworkeverynightalthoughthestudentslikemrjoketheycomplainthattheyarebehindtheothermathclassesandseelittlechanceofchatchingupweareveryconcernedaboutthisproblemmmmm

The solution is found by computing the index of coincidence and mutual index of coincidence. They key size is determined and then using this information the key is found.

### MDES
A class holding the functions for a minimized version of the DES encryption method. The class includes the classic method, as well as the alternate CBC (cipher block chaining) mode of the algorithm.

To Encrypt: name.binary_to_text(plaintext) => name.split_encrypt(binary_text, key)

To Decrypt: name.split_decrypt(ciphertext, key) => name.binary_to_text(binary_text)

### DoubleEncryption
A demonstration of a product encryption method that is vulnerable to a "meet-in-the-middle" attack. The first encryption method uses a permutation cipher operated on binary values, while a second utilizes a simple block cipher that XOR's a key value with each block. The first encryption method runs, and then the result of this is fed into the second. The resulting ciphertext can easily be attacked by performing a known-plaintext attack (using both a plaintext and ciphertext). Every possible permutation of the key and binary value is tested respectively with the encryption methods until a match is found between them. The point at which the match is found gives the key. 
