@0
D = A
@SP
A = M
M = D
@SP
M = M + 1
@0
D = A
@LCL
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
(LOOP_START)
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
@0
D = A
@LCL
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
@LOOP_START
D; JNE
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
