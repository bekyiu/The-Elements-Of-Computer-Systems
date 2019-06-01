@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@EQ0
D; JEQ
@SP
A = M
M = 0
@END0
0; JMP
(EQ0)
@SP
A = M
M = -1
(END0)
@SP
M = M + 1
@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@16
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@EQ1
D; JEQ
@SP
A = M
M = 0
@END1
0; JMP
(EQ1)
@SP
A = M
M = -1
(END1)
@SP
M = M + 1
@16
D = A
@SP
A = M
M = D
@SP
M = M + 1
@17
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@EQ2
D; JEQ
@SP
A = M
M = 0
@END2
0; JMP
(EQ2)
@SP
A = M
M = -1
(END2)
@SP
M = M + 1
@892
D = A
@SP
A = M
M = D
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@LT3
D; JLT
@SP
A = M
M = 0
@END3
0; JMP
(LT3)
@SP
A = M
M = -1
(END3)
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@892
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@LT4
D; JLT
@SP
A = M
M = 0
@END4
0; JMP
(LT4)
@SP
A = M
M = -1
(END4)
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@891
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@LT5
D; JLT
@SP
A = M
M = 0
@END5
0; JMP
(LT5)
@SP
A = M
M = -1
(END5)
@SP
M = M + 1
@32767
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@GT6
D; JGT
@SP
A = M
M = 0
@END6
0; JMP
(GT6)
@SP
A = M
M = -1
(END6)
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32767
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@GT7
D; JGT
@SP
A = M
M = 0
@END7
0; JMP
(GT7)
@SP
A = M
M = -1
(END7)
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@32766
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@GT8
D; JGT
@SP
A = M
M = 0
@END8
0; JMP
(GT8)
@SP
A = M
M = -1
(END8)
@SP
M = M + 1
@57
D = A
@SP
A = M
M = D
@SP
M = M + 1
@31
D = A
@SP
A = M
M = D
@SP
M = M + 1
@53
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
D = M + D
M = D
@SP
M = M + 1
@112
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = !M
D = D + 1
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
D = M & D
M = D
@SP
M = M + 1
@82
D = A
@SP
A = M
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@SP
M = M - 1
A = M
D = M | D
M = D
@SP
M = M + 1
@SP
M = M - 1
A = M
D = !M

M = D
@SP
M = M + 1
