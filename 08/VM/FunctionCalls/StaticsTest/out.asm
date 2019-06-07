@261
D = A
@SP
M = D
@Sys.init
0; JMP
(Class1.set)
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
@SP
M = M - 1
A = M
D = M
@Class1.0
M = D
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
@Class1.1
M = D
@0
D = A
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
(Class1.get)
@0
D = A
@times
M = D
(LOOP1)
@times
D = M
@END1
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
@LOOP1
0; JMP
(END1)
@Class1.0
D = M
@SP
A = M
M = D
@SP
M = M + 1
@Class1.1
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
(Class2.set)
@0
D = A
@times
M = D
(LOOP2)
@times
D = M
@END2
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
@LOOP2
0; JMP
(END2)
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
@Class2.0
M = D
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
@Class2.1
M = D
@0
D = A
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
(Class2.get)
@0
D = A
@times
M = D
(LOOP3)
@times
D = M
@END3
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
@LOOP3
0; JMP
(END3)
@Class2.0
D = M
@SP
A = M
M = D
@SP
M = M + 1
@Class2.1
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
@6
D = A
@SP
A = M
M = D
@SP
M = M + 1
@8
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Class1.set$ret.5
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
@2
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class1.set
0; JMP
(Class1.set$ret.5)
@0
D = A
@5
D = D + A
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
@23
D = A
@SP
A = M
M = D
@SP
M = M + 1
@15
D = A
@SP
A = M
M = D
@SP
M = M + 1
@Class2.set$ret.6
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
@2
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class2.set
0; JMP
(Class2.set$ret.6)
@0
D = A
@5
D = D + A
@addr
M = D
@SP
M = M - 1
A = M
D = M
@addr
A = M
M = D
@Class1.get$ret.7
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
@0
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class1.get
0; JMP
(Class1.get$ret.7)
@Class2.get$ret.8
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
@0
D = D - A
@5
D = D - A
@ARG
M = D
@SP
D = M
@LCL
M = D
@Class2.get
0; JMP
(Class2.get$ret.8)
(WHILE)
@WHILE
0; JMP
