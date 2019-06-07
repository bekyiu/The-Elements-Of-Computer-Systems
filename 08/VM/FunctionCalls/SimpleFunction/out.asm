(SimpleFunction.test)
@2
D = A
@times
M = D
(LOOP0)
@times
D = M
@END0
D; JEQ
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@times
M = M - 1
@LOOP0
0; JMP
(END0)
@0
D = A
@LCL
A = D + M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@1
D = A
@LCL
A = D + M
D = M
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
@SP
M = M - 1
A = M
D = !M

M = D
@SP
M = M + 1
@0
D = A
@ARG
A = D + M
D = M
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
@1
D = A
@ARG
A = D + M
D = M
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
@LCL
D = M
@frame
M = D
@5
D = D - A
A = D
D = M
@ret
M = D
@SP
M = M - 1
A = M
D = M
@ARG
A = M
M = D
@ARG
D = M + 1
@SP
M = D
@frame
D = M
D = D - 1
A = D
D = M
@THAT
M = D
@frame
D = M
@2
D = D - A
A = D
D = M
@THIS
M = D
@frame
D = M
@3
D = D - A
A = D
D = M
@ARG
M = D
@frame
D = M
@4
D = D - A
A = D
D = M
@LCL
M = D
@ret
A = M
0; JMP
