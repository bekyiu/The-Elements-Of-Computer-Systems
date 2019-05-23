// draw a rectangle, height in RAM[0], width is 16px
// because of 1 pixel correspond to 1 bit
// and the smallest unit we can operate on is 16 bits (a memory register) 
// so, 16px is convenient to operate
// you can every times draw a line
// for (i = 0; i < RAM[0]; i++)
// {
//		draw a 16px line
// }

// pseudo code:
// addr = SCREEN
// n = RAM[0]
// i = 0
// 
// (LOOP)
// 		if i >= n goto STOP
// 		RAM[addr] = -1
// 		addr += 32
// 		i++
// 		goto LOOP
// (STOP)

@SCREEN
D = A
@addr
M = D

@R0
D = M
@n
M = D

@i
M = 0

(LOOP)
	@i
	D = M
	@n
	D = D - M
	@STOP
	D; JGE
	
	@addr
	A = M
	M = -1
	
	@32
	D = A
	@addr
	M = M + D
	
	@i
	M = M + 1
	
	@LOOP
	0; JMP
(STOP)
	@STOP
	0; JMP