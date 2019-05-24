// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// pseudo code:
// (LOOP)
	// i = 0
	// if RAM[KBD] == 0 goto LOOP3
	// (LOOP2)
		// if i > 8191 goto STOP3
		// RAM[SCREEN + i] = -1
		// i++
		// goto LOOP3
	// (STOP2)
	// goto LOOP
	// (LOOP3)
		// if i > 8191 goto STOP2
		// RAM[SCREEN + i] = 0
		// i++
		// goto LOOP2
	// (STOP3)
	// goto LOOP
// (STOP)
// Put your code here.

(LOOP)
	@i
	M = 0
	@KBD
	D = M
	@WHITE
	D; JEQ
	
	(BLACK)
		@i
		D = M
		@8191
		D = D - A
		@BEND
		D; JGT
		
		@SCREEN
		D = A
		@i
		A = D + M
		M = -1
		@i
		M = M + 1
		
		@BLACK
		0; JMP
	(BEND)
	
	@LOOP
	0; JMP
	
	(WHITE)
		@i
		D = M
		@8191
		D = D - A
		@WEND
		D; JGT
		
		@SCREEN
		D = A
		@i
		A = D + M
		M = 0
		@i
		M = M + 1
		
		@WHITE
		0; JMP
	(WEND)

	@LOOP
	0; JMP
(STOP)
