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
@THAT
M = D
@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@THAT
D = D + M
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
@1
D = A
@SP
A = M
M = D
@SP
M = M + 1
@1
D = A
@THAT
D = D + M
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
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
@2
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
@0
D = A
@ARG
D = D + M
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
(MAIN_LOOP_START)
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
@COMPUTE_ELEMENT
D; JNE
@END_PROGRAM
0; JMP
(COMPUTE_ELEMENT)
@0
D = A
@THAT
A = D + M
D = M
@SP
A = M
M = D
@SP
M = M + 1
@1
D = A
@THAT
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
@2
D = A
@THAT
D = D + M
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@1
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
@SP
M = M - 1
A = M
D = M
@THAT
M = D
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
@1
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
@0
D = A
@ARG
D = D + M
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
@MAIN_LOOP_START
0; JMP
(END_PROGRAM)
