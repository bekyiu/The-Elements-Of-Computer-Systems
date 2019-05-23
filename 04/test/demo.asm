// RAM[2] = RAM[1] + RAM[0]
@R0
D = M

@R1
D = D + M

@R2
M = D

@R6
0;JMP