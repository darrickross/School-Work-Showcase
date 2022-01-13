;;; layout of the structure
%define TITLE_OFFSET 0
%define AUTHOR_OFFSET 48
%define PRICE_OFFSET 96
%define YEAR_OFFSET 104
%define NEXT_OFFSET 112

;;; our usual system call stuff
%define STDOUT 1
%define SYSCALL_EXIT  60
%define SYSCALL_WRITE 1

    SECTION .data
;;; Here we declare initialized data. For example: messages, prompts,
;;; and numbers that we know in advance

newline:        db 10

    SECTION .bss
;;; Here we declare uninitialized data. We're reserving space (and
;;; potentially associating names with that space) that our code
;;; will use as it executes. Think of these as "global variables"

    SECTION .text
;;; This is where our program lives.
global _start                               ; make start global so ld can find it
extern library
global authorsForPrice

printNewline:
        push rax
        push rbx
        push rcx
        push rdx
        push rsi

        mov rax, SYSCALL_WRITE
        mov rdi, STDOUT
        mov rsi, newline
        mov rdx, 1
        syscall

        pop rsi
        pop rdx
        pop rcx
        pop rbx
        pop rax
        ret

;;; rax should point to the string. on return, rax is the length
stringLength:
;;; Students: Feel free to use this code in your submission but you
;;; must add comments explaining the code to prove that you
;;; know how it works.

        push rsi            ;Save the pointer back to the place this was called
        mov rsi, rax        ;Place pointer to the string (in rax) into rsi
        mov rax, 0          ;Initialize counter for how large the string is

loopsl:
        cmp [rsi], byte 0   ;Is the current byte at the loc rsi a \0 char??
        je endsl            ;if so, we found the end, go to end
                            ;I guess we didnt find it...
        inc rax             ;i +=1, the counter is incremented 1
        inc rsi             ;move the pointer to the next byte
        jmp loopsl          ;Start the loop again

endsl:
        pop rsi             ;Restore where you came from
        ret                 ;Go back to where you came from (rax = string size)

;;; this label will be called as a subroutine by the code in driver.asm
authorsForPrice:
        ;; protect the registers we use

        push rax
        push rbx
        push rcx
        push rdx
        push rsi

;Start
        mov rsi, [library]              ;Place the first book into rsi

;Loop in book loop
bookLoop:
;current book price = [rsi + PRICE_OFFSET]

    fld    qword [rsi + PRICE_OFFSET]   ; place cur book.price into st0
    fcomip st0,st1                      ; nextBook compare Original, pop nextBook
    jbe bookLoopInc                     ; if nextBook > Original

;Compare the current books price to ST0 floating point stack
;if that value is less or equal to ST0 jump to bookLoopInc

printName:

        lea rax, [rsi + AUTHOR_OFFSET]  ; Load-Effective-Address computes the address in
                                        ; the brackets and returns it instead of looking it up.

        call stringLength               ; after this, RAX will have the length of the author name

        mov rdx, rax                    ; copy it to the count register for the system call
        mov rax, SYSCALL_WRITE
        mov rdi, STDOUT
        lea rcx, [rsi + AUTHOR_OFFSET]
        push rsi                        ; preserve RSI
        mov rsi, rcx
        syscall

        pop rsi                         ; restore RSI

        call printNewline               ;Print new line

bookLoopInc:
        mov rsi, [rsi+NEXT_OFFSET]      ;Set rsi to the loc of the next book
        cmp rsi, 0                      ;Compare the mem address of the new book to 0
        jne bookLoop                    ;As long as it != 0 loop again
                                        ;Otherwise (rsi==0) we move to clean up

cleanUp:
        fstp st0                        ; pop off top of stack

        pop rsi
        pop rdx
        pop rcx
        pop rbx
        pop rax

        ret
