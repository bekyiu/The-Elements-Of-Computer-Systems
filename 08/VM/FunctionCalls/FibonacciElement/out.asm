@261
D = A
@SP
M = D
@Sys.init
0; JMP
(Main.fibonacci)
@0
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
M = M -1
A = M
D = M
@SP
M = M - 1
A = M
D = M - D
@LT1
D; JLT
@SP
A = M
M = 0
@END1
0; JMP
(LT1)
@SP
A = M
M = -1
(END1)
@SP
M = M + 1
@SP
M = M - 1
A = M
D = M
@IF_TRUE
D; JNE
@IF_FALSE
0; JMP
(IF_TRUE)
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
(IF_FALSE)
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
@Main.fibonacci$ret.2
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
D = M
@1
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0; JMP
(Main.fibonacci$ret.2)
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
@Main.fibonacci$ret.3
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
D = M
@1
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0; JMP
(Main.fibonacci$ret.3)
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
(Sys.init)
@0
D = A
@times
M = D
(LOOP4)
@times
D = M
@END4
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
@LOOP4
0; JMP
(END4)
@4
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Main.fibonacci$ret.5
D = A
@SP
A = M
M = D
@SP
M = M + 1
@LCL
D = M
@SP
A = M
M = D
@SP
M = M + 1
@ARG
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THIS
D = M
@SP
A = M
M = D
@SP
M = M + 1
@THAT
D = M
@SP
A = M
M = D
@SP
M = M + 1
@SP
D = M
@1
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Main.fibonacci
0; JMP
(Main.fibonacci$ret.5)
(WHILE)
@WHILE
0; JMP
