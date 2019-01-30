# Cryptography Projects

## Description
The following repositiory is are light programs demonstrating various cryptographic ciphers and functions.

### Substitution
A program demonstrating the classic substitution cipher corresponding to a keyspace of 26 english alphabet characters, a space, a comma, and a period. The key itself is a random permutation, and the program shows both encryption and decryption.

### Permutation
A program demonstrating the permutation cipher corresponding to a keyspace of 26 english alphabet characters, a space, a comma, and a period. The initial program inputs a value of m for the size of the permutation and a plaintext. The permutation is randomized, and the program shows both encryption and decryption.

### Vegenere
The program demonstrates the Vegenere Cipher by breaking a ciphertext and attaining the plaintext.

**Ciphertext:**\
cjnpkgrlilqwawbnuptgkerwxuzviaiiysxckwdntjawhqcutttvp\
tewtrpgvcwlkkkgczafsihrimixukrwxrfmgfgkfxgukpjvvzmcmj\
vawbnuptgcicvxvkgczkekgcqbchvnrqhhwiadfrcyxgvzqqtuvbd\
guvttkccdpvvfphftamzxqwrtgukcelqlrxgvycwtncbjkkeerecj\
qihvrjzpkkfexqgjtpjfupemswwxcjqxzpjtxkvlyaeaemwhovudk\
mnfxegfrwxtdyiaecyhlgjfpogymbxyfpzxxvpngkxfitnkfdniyr\
wxukssxpkqabmvkgcqbciagpadfrcyxgvyyimjvwpkgscwbpurwxq\
kftkorrwvnrqhxurlslgvjxmvccraceathhtfpmeygczwgutttvtt\
katmcvgiltwcsmjmvyghitfzaxodkbf

**Keysize:** 5\
**Key:** crypt\
**Plaintext:** aspareantsofchildreninthesixthgradeatyourschoolwearereluctantlywritingyoutoprotestthepoormethinstructionthechildrenaregettinginmrjokesclassourcomplaintisbasedonseveralfactorsmrjokeoftenstepsoutinthehallduringclasstotalkwithpeoplewhowalkbyheassignsvirtuallynohomeworkwhileothermathclasseshavehomeworkeverynightalthoughthestudentslikemrjoketheycomplainthattheyarebehindtheothermathclassesandseelittlechanceofchatchingupweareveryconcernedaboutthisproblemmmmm

The solution is found by computing the index of coincidence and mutual index of coincidence. They key size is determined and then using this information the key is found.
