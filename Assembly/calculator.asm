.data #Constants
#	input: 11
	
	#User interactions
	ask1number: .asciiz "Enter First number: "  						#$t0 contains it's result
	ask2number: .asciiz "Enter Second number: " 						#$t1 contains it's result
	askOperation: .asciiz "Enter Operation: \n 1: add\n 2: sub\n 3: mul\n 4: div\n => " 	#$t2 contains it's result
												#$t3 will contain (int)result between two operands
		#Labels
	#Exit = label to exit programm with 10 code
	
	#ADD = addition operation
	#SUB = subtraction operation
	#MUL = multiplication operation
	#DIV = division operation
.text #actual code below

main: #starting point
	#Defining numerical constants for operations
	
	li $t6, 1 #ADD
	li $t7, 2 #SUB
	li $t8, 3 #MUL
	li $t9, 4 #DIV
	
	#Ask for numbers and operations
		#First Component
	li $v0, 4 # print Strings
	
	la $a0, ask1number
	syscall   
		
	li $v0, 5 #ask for integer input
	syscall
	
	move $t0, $v0
	
		#Secondo Component
	li $v0, 4 #return to printing strings
	
	la $a0, ask2number
	syscall   
	
	li $v0, 5
	syscall 
	
	move $t1, $v0	
	
		#Third Component
	li $v0, 4
	
	la $a0, askOperation
	syscall  
	
	li $v0, 5
	syscall
	
	move $t2, $v0
	
	#End I/O part
		#Actual operations
		#IFs/ELSEs
	beq $t2, $t6, ADD
	beq $t2, $t7, SUB
	beq $t2, $t8, MUL
	beq $t2, $t9, DIV
	
	#Exit
Exit:
	li $v0, 10 #exit
	syscall    #call exit

ADD: 
	add $t3, $t0, $t1

	li $v0, 1
	la $a0, ($t3)
	syscall
	
	j Exit
SUB: 
	sub $t3, $t0, $t1
	
	li $v0, 1
	la $a0, ($t3)
	syscall
	
	j Exit

MUL: 
	mul $t3, $t0, $t1
	
	li $v0, 1
	la $a0, ($t3)
	syscall
	
	j Exit
	
DIV: 
	div $t3, $t0, $t1
	
	li $v0, 1
	la $a0, ($t3)
	syscall
	
	j Exit
