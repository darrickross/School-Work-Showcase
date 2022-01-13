; Convert user input to hexadecimal number.
;
; Assemble using NASM:  nasm -f elf64 hexConverter.asm
; Compile with gcc:     gcc hexConverter.o cfunc.c -o converter.out
;

%define STDIN         0
%define STDOUT        1
%define SYSCALL_EXIT  60
%define SYSCALL_READ  0
%define SYSCALL_WRITE 1
%define BUFLEN 		  21
%define HEXLEN        16


        SECTION .data                   ; initialized data section

msg1:   db "Enter Number: "             ; user prompt
len1:   equ $-msg1                      ; length of first message

msg2:   db "Invalid Input!", 10         ; error message
len2:   equ $-msg2

msg3:   db "Hexadecimal value is: "     ; Feedback
len3:   equ $-msg3

msg4:   db 10      						; Linefeed
len4:   equ $-msg4

hexHelper:    db "0123456789ABCDEF"     ;String to get the resulting number
                                            ;using the number as an index


        SECTION .bss                 ; uninitialized data section
buf:    resb BUFLEN                  ; buffer for read
newstr: resb BUFLEN                  ; converted string
count:  resb 4                       ; reserve storage for user input bytes
hexStr: resb HEXLEN

        SECTION .text                ; Code section.
        global  main                 ; let gcc see entry point
        extern printhex				 ; This routine is defined in the c function
        extern printf				 ; This routine will be utilized in the
                                        ;c function

main:	nop                          ; Entry point.
start:                               ; address for gdb

        ; prompt user for input
        ;
        mov rax, SYSCALL_WRITE       ; write function
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg1                ; Arg2: addr of message
        mov rdx, len1                ; Arg3: length of message
        syscall                      ; 64-bit system call

        ; read user input
        ;
        mov rax, SYSCALL_READ        ; read function
        mov rdi, STDIN               ; Arg1: file descriptor
        mov rsi, buf                 ; Arg2: addr of message
        mov rdx, BUFLEN              ; Arg3: length of message
        syscall                      ; 64-bit system call

        ; error check
        ;
        mov [count], rax             ; save length of string read
        cmp rax, 0                   ; check if any chars read
        jle invalid                  ; <=0 chars read = not valid

		cmp rax, 21					 ; check if 21 characters were read
		je	read_OK					 ; 21 characters read, we are good
		cmp rax, 20					 ; check if 20 characters were read
		je	read_OK					 ; 20 characters read, we are good

invalid:mov rax, SYSCALL_WRITE       ; Or Print Error Message
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg2                ; Arg2: addr of message
        mov rdx, len2                ; Arg3: length of message
        syscall                      ; 64-bit system call
        jmp     exit                 ; skip over rest

read_OK:

        ; Loop for conversion
        ; assuming count > 0
        ;

        ;Steps
        ;check that each char is a number, compare to '0' and '9'
        ;convert ascii to number

        ;for each char starting at the back
        ;Convert it to a number from ascii

;===============================================================================
mov r11, buf
;Place r9 at the end of the string
        mov r9, buf                 ;start at the location of the input
        mov rax, [count]            ;Number of characters so that we can add
        add r9, rax                 ;add the number of characters to the
        sub r9, 2                   ;At first this will be \0, then \n and we
                                        ;want to skip these chars and go to nums

;start to convert
        xor rbx, rbx                ;clear out b (this is where the result will
                                        ;be at the end, if we make it that far)
        mov r10, 1                  ;start the digit at ones digit

asciiToNumLoop:
        xor rax, rax                ;clear a
        mov al, [r9]                ;current char

;check that the char is a number '0' <= char =< '9'
        cmp al, '0'
        jl invalid                  ;if the char is below '0' its invalid and
                                        ;we stop

        cmp al, '9'
        jg invalid                  ;if the char is above '9' its invalid and
                                        ;we stop

;char is a number (still in ascii tho)

;convert the char

        sub al, '0'                 ;take the char out of ascii
        mul r10                     ;multiply a register by r10 (the digits
                                        ;place, 1, 10, 100, 1000, ...)

        add rbx, rax                ; add the result to rbx (the total)

;increase the digits place counter (r10)
        xor rax, rax                ;clear a
        mov rax, r10                ;place the current digit into
        mov r10, 10                 ;place 10, so that we can multiply it into
                                        ;the current digit counter
        mul r10                     ;do the multiplication
        mov r10, rax                ;the new digit count goes into r10

;update stuff
        dec r9

;Check if loop again
cmp r11, r9                         ;if r9 is less than the mem loc of buf (r11)
jle asciiToNumLoop                   ;loop again.

push rbx                            ;save before the message print
;===============================================================================

init:
									; printout the precursor message
		mov rax, SYSCALL_WRITE      ; Print Message
        mov rdi, STDOUT             ; Arg1: file descriptor
        mov rsi, msg3               ; Arg2: addr of message
        mov rdx, len3               ; Arg3: length of message
        syscall                     ; 64-bit system call

HexConvInit:
        pop rax                     ;restore the number this time to rax for
                                        ;the division.
        xor rbx, rbx                ;clear b
        mov bl, 16                  ;now bl is used for division into hex


        xor r13, r13                ;Clear 13 for the index in filling string
        add r13, HEXLEN             ;Set 13 to the length of hexStr
        dec r13                     ;Dec once so that it looks at the last spot

        ;We now have the number in rax
        ;time to convert to hex

        ;Steps
            ;Take the number in rax and divide by 16 (bl)
            ;get the result from rdx, and get the char equivelent
                ;(use the string hexHelper)
            ;Place the resulting char into a string (filling back to front)
            ;repeat

hexLoop:
        xor rdx, rdx                ;clear d for the remainder
        div rbx                     ;do division using
            ;the quotient is in rax
            ;the remainder is in rdx
        xor rcx, rcx                ;clear c for result
        mov cl, [hexHelper + rdx]
        mov [hexStr + r13], cl
        mov r14, [hexStr + r13]     ;<==============REMOVE THIS
                                    ;take the result from the division and use
                                        ;it as an index to the string hexHelper
                                        ;and place that into hexStr

        dec r13                     ;Dec the index (rcx)

        ;Save to back of string
        cmp r13, 0                  ;If we have passed the starting point (0)
        jl prePrintLoop             ;Jump to the print part

        cmp rax, 0                  ;Check that the quotient is not 0
        jne hexLoop                 ;if the quotient is not 0, back to the start

;===============================================================================

prePrintLoop:
        mov rcx, 0                  ;Reset the index pointer

printHexStringLoop:
;save all registers
;send to printhex (place char into rdi, and set rax to 0)
;restore registers


        mov rdi, [hexStr + rcx]     ;put the current character into rdi

        push rcx                    ;save rcx

        xor rax, rax                ;set rax to 0

        call printhex               ;Call current char

        pop rcx                     ;restore c

        inc rcx                     ;inc c

        cmp rcx, HEXLEN             ;if c < length
        jl printHexStringLoop       ;loop again

									 ; printout the linefeed
		mov rax, SYSCALL_WRITE       ; Print Message
        mov rdi, STDOUT              ; Arg1: file descriptor
        mov rsi, msg4                ; Arg2: addr of message
        mov rdx, len4                ; Arg3: length of message
        syscall                      ; 64-bit system call


        ; final exit
        ;
exit:   mov 	rax, SYSCALL_EXIT
        mov 	rdi, 0               ; exit to shell
        syscall
