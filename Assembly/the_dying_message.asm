.data #Defines Constants
    space: .asciiz "\n" # as the name suggests. used for spacing 
    
    sanmandefuantei: .asciiz "Sanman de fauntei na kono sekai\n"
    kakumeihookoshiteyo: .asciiz "Kakumei ho okoshite yo\n"
    
    aigaseikaininare: .asciiz "'AI' GA SEIKAI NI NAREEEEEE\n"
    
    fuanteinantei: .asciiz "Fuantei nantei kankan ni kiechate\n"
    hakumeinohiterasu: .asciiz "Hakumei no hi terasu sono mukou hee\n"
    
    kirokumo: .asciiz "Kiroku mo kioku mo nai hito, maishou\n"
    kibuomo: .asciiz "Kibuo mo mirai mo nai kuni, gaishou\n"
    
    nagaiyoru: .asciiz "Nagai yoru wo oe asayake wo mita\n"
    akakusomaru: .asciiz "Akaku somaru senaka toooooooo\n"
    
    NAWIM: .asciiz "NEE ANATA WA ITSUKA INAKU NARI MASU KA?\n"
    OWARI: .asciiz "OWARI KAKE NO KONO SEKAI DE\n"
    YOAKE: .asciiz "YOAKE MAE NO UREI TAEGATAI KODOKU \n"
    AWAI: .asciiz "AWAI OMOI TO SEKIBAKU WO DAITA\n"
    
    senmei: .asciiz "Senmei na dangan no kioku ni\n"
    daremo: .asciiz "Daremo ga hi yotte waa\n"
    
    daiipo: .asciiz "DAIIPO GA DE NAIII\n"
    
    anzen: .asciiz "Anzen wa kankan ni kiechate\n"
    akueoyou: .asciiz "Akukeoyou oboshita boku no kokoro heee\n"
    
    nagaiamegayami: .asciiz "Nagai ame ga yami atsui hizashi ga\n"
    terasu: .asciiz "Terasu ookina senaka tooooooooo\n"
    
    NAWIYWMMK: .asciiz "NEE ANATA WA ITSUMO YUME WO MITE MASU KA?\n"
    FUSAGARETA: .asciiz "FUSAGARETA MICCHI WO HIRAKU MONO NI NARU\n"
    MESSEJII: .asciiz "'MESSEJII' WO IDA ITA TSURAI YOOOOOOOOO\n"
    
    mujou: .asciiz "Mujou ni mo toki ga kitte kanata he tabidata\n"
    kankan: .asciiz "kankan na knajou kamae kudake chiiru tame ni\n"
    yuukan: .asciiz "yuukan na anata wa sora wo kirisaita\n"
    seikan: .asciiz "seikan na hyoujou gamae kaze ni nattaaaa\n"
    
    DOUKOKU: .asciiz "DOOOOOKOOOOOOOKUUUUUUUU KAKUSHITE\n"
    
    SEKIBAKU: .asciiz "SEKIBAAAAAAAAAKUUUUUUU KAKUSHITE\n"
    
    ikiru: .asciiz "ikiru koto sae nozome nai nara\n"
    tadasekai: .asciiz "tada sekai no kanjite iki wo haku\n"
    
    nagaiyoruwooe: .asciiz "nagai yoru wo oe senaka nado nai\n"
    sokoniaru: .asciiz "soko ni aru no wa tadaaaaaaaa hiroiiiii buruuuuuuuu\n"
    
    NAWIGWKMK: .asciiz "NEE ANATA WA ITSUMO GENKI WO KURE MASU KA?\n"
    KURAGARI: .asciiz "KURAGARI DE KAWASHITA CHIISANA YAKUSOKU GA\n"
    WATASHI: .asciiz "WATASHI NO IKIRU KATE SA\n"
    
    neewatashi: .asciiz "nee watashi wa nido to kurai kao shinai yo\n"
    saegiru: .asciiz "saegiru kage mono mie nu kara\n"
    akarui: .asciiz "akarui hizashi wo zonbuni abinagara\n"
    muika: .asciiz "'Muika' no taoruuu tare ta wo daitaaaaaaaa\n"    
    
    UTSUP: .asciiz "UTSU-P - The Dying Message"
.text

main: #Starting Point
    li $v0, 4 # 4 is the system call code for printing the "string". For printing integer it is 1.
    
    la $a0, sanmandefuantei #to print out our message, we have to load the address of the variable "message" to the register $a0
    syscall #invoking the system to call the function which is stored in $v0. Here, we are calling the print function which is specified in $v0

    la $a0, kakumeihookoshiteyo
    syscall 
    
    la $a0, space
    syscall
    
    la $a0, aigaseikaininare
    syscall 
    
    la $a0, space
    syscall
    
    la $a0, fuanteinantei
    syscall 
    
    la $a0, hakumeinohiterasu
    syscall 
    
    la $a0, space
    syscall
    
    la $a0, kirokumo
    syscall 
    
    la $a0, kibuomo
    syscall 
    
    la $a0, space
    syscall
	
    la $a0, nagaiyoru
    syscall 
    
    la $a0, akakusomaru
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, NAWIM
    syscall 
    
    la $a0, OWARI
    syscall 
    
    la $a0, YOAKE
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, senmei
    syscall 
    
    la $a0, daremo
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, daiipo
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, anzen
    syscall 
    
    la $a0, akueoyou
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, nagaiamegayami
    syscall 
    
    la $a0, terasu
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, NAWIYWMMK
    syscall 
    
    la $a0, OWARI
    syscall 
    
    la $a0, FUSAGARETA
    syscall 
    
    la $a0, MESSEJII
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, mujou
    syscall 
    
    la $a0, kankan
    syscall 
    
    la $a0, yuukan
    syscall 
    
    la $a0, seikan
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, DOUKOKU
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, SEKIBAKU
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, ikiru
    syscall 
    
    la $a0, tadasekai
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, nagaiyoruwooe
    syscall 
    
    la $a0, sokoniaru
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, NAWIGWKMK
    syscall 
    
    la $a0, OWARI
    syscall 
    
    la $a0, KURAGARI
    syscall 
    
    la $a0, WATASHI
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, neewatashi
    syscall 
    
    la $a0, saegiru
    syscall 
    
    la $a0, akarui
    syscall 
    
    la $a0, muika
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, space
    syscall 
    
    la $a0, UTSUP
    syscall 
    
    li $v0, 10 # 10 is the system call code to terimate the program
    syscall  #program is terminated
    
    #Utsu-P is just too good
