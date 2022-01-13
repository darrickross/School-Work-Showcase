; File: bookcmp.asm
;
;Defines the bookcmp subroutine for use by the sort algorithm in sort_books.c
;

%define TITLE_OFFSET 0
%define AUTHOR_OFFSET 41
%define YEAR_OFFSET 64

        SECTION .text                ; Code section.
        global  bookcmp              ; let loader see entry point

bookcmp:
    push   rbp                       ; push the base pointer
    mov    rbp, rsp                  ; move stack pointer onto rbp (Prologue)

    push   rdi                       ; push registers we want to use
    push   rsi
    ;help reduce problems
    push rbx
    push rcx
    ;end of help reduce problems

    mov    rdi, [rbp - 8]            ; move first book into rdi
    mov    rsi, [rbp - 16]           ; move second book into rsi
    mov    ebx, [rdi+YEAR_OFFSET]    ; fetch the year field
    cmp    ebx, [rsi+YEAR_OFFSET]    ; and compare the year field to book1's
    jne    L_eq                      ; If they're different, do not sort, treat the titles as lexicographically equivalent

cmpTitles:                           ; Fall through to here if years same
                                     ; Compare the book title strings

    mov    rax, 0                    ;Start the counter i
titleLoop:
    mov    bl, [rax + rdi + TITLE_OFFSET]   ;Move the b1.title[rax] char into bl
    mov    cl, [rax + rsi + TITLE_OFFSET]   ;Move the b2.title[rax] char into cl
    cmp    bl, cl                           ;Cmp the chars
    jne    charDontMatch                    ;If they dont match jump
    ;otherwise the chars match
    cmp    bl, 0                            ;If bl equal \0
    je     L_eq                             ;bl is at the end
    cmp    cl, 0                            ;If cl equal \0
    je     L_eq                             ;cl is at the end

    ;otherwise if neither are \0
    inc    rax                             ;rax++
    jmp    titleLoop                       ;nextChar

charDontMatch:
    cmp     bl, cl                          ;compare b and c
    jl      L_lt                            ;b < c
    ;else
    jmp     L_gt                            ;b > c

cmpDone:                             ; Things to do after titles are compared

L_lt:
    mov    rax, -1                   ; book1 is strictly less than book2
    jmp    end

L_eq:
    mov    rax, 0                    ; book1 equals book2
    jmp    end

L_gt:
    mov    rax, 1                    ; book1 is strictly greater than book2

    ;; Clean up and finish
end:
    ;help reduce problems
    pop rcx
    pop rbx
    ;end of help reduce problems

    pop    rsi                       ; clean up
    pop    rdi
    leave                            ; Sets RSP to RBP and pops off RBP (Epilogue)
    ret
